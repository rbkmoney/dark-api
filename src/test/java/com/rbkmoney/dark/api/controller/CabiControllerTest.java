package com.rbkmoney.dark.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;
import com.rbkmoney.cabi.CheckCurrencyExchangeParams;
import com.rbkmoney.cabi.CryptoApiSrv;
import com.rbkmoney.cabi.CurrencyRequestFail;
import com.rbkmoney.cabi.ExchangeAction;
import com.rbkmoney.cabi.base.Rational;
import com.rbkmoney.damsel.domain.Currency;
import com.rbkmoney.dark.api.DarkApiApplication;
import com.rbkmoney.dark.api.auth.utils.JwtTokenBuilder;
import com.rbkmoney.dark.api.service.DominantService;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.dark.api.service.PartyManagementService;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DarkApiApplication.class})
@AutoConfigureMockMvc
public class CabiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

    @MockBean
    private PartyManagementService partyManagementService;

    @MockBean
    private KeycloakService keycloakService;

    @MockBean
    private CryptoApiSrv.Iface cryptoApiService;

    @MockBean
    private DominantService dominantService;

    @Before
    public void setUp() throws Exception {
        doNothing().when(partyManagementService).checkStatus(anyString());
        doNothing().when(partyManagementService).checkStatus();
        when(keycloakService.getPartyId()).thenReturn(UUID.randomUUID().toString());
        when(dominantService.getCurrency("BTC")).thenReturn(new Currency("Bitcoin", "BTC", (short) 999, (short) 6));
        when(dominantService.getCurrency("USD")).thenReturn(new Currency("Доллары США", "USD", (short) 840, (short) 2));
        when(dominantService.getCurrency("RUR")).thenReturn(new Currency("Российские рубли", "RUR", (short) 643, (short) 2));

        // work with double value as BigDecimal
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        objectMapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);

        Configuration.setDefaults(new Configuration.Defaults() {

            private final JsonProvider jsonProvider = new JacksonJsonProvider(objectMapper);
            private final MappingProvider mappingProvider = new JacksonMappingProvider(objectMapper);

            @Override
            public JsonProvider jsonProvider() {
                return jsonProvider;
            }

            @Override
            public MappingProvider mappingProvider() {
                return mappingProvider;
            }

            @Override
            public Set<Option> options() {
                return EnumSet.noneOf(Option.class);
            }
        });
    }

    @Test
    public void checkCurrencyExchangeSellTest() throws Exception {
        whenCurrencyExchangeResponse(new Rational(235158, 1000000),
                new Rational(99672377, 10000),
                new Rational(235458, 1000000),
                new Rational(10000, 100),
                "USD",
                "BTC",
                ExchangeAction.sell);

        thenPerformCurrencyRequest("0.235158", "0.235458", 100.00, "9967.2377", "USD", "BTC", "SELL");
    }

    @Test
    public void checkCurrencyExchangeBuyTest() throws Exception {
        whenCurrencyExchangeResponse(new Rational(1018093, 1000000),
                new Rational(98222894, 10000),
                null,
                new Rational(100, 100),
                "BTC",
                "USD",
                ExchangeAction.buy);

        thenPerformCurrencyRequest("1.018093", null, 1.00, "9822.2894", "BTC", "USD", "BUY");
    }

    @Test
    public void checkCurrencyExchangeBuyRubTest() throws Exception {
        whenCurrencyExchangeResponse(new Rational(101654, 10),
                new Rational(6546496651L, 10000),
                new Rational(15528, 1000000),
                new Rational(15428, 1000000),
                "RUR",
                "BTC",
                ExchangeAction.buy);

        thenPerformCurrencyRequest("10165.40", "0.015528", 0.02, "654649.6651", "RUR", "BTC", "BUY");
    }

    @Test
    public void checkCurrencyExchangeFail400Test() throws Exception {
        when(cryptoApiService.checkCurrencyExchange(any(CheckCurrencyExchangeParams.class)))
                .thenThrow(CurrencyRequestFail.class);
        mockMvc.perform(get("/currency")
                .header("Authorization", "Bearer " + jwtTokenBuilder.generateJwtWithRoles("RBKadmin"))
                .param("from", "BTC")
                .param("to", "RUR")
                .param("action", "BUY")
                .param("amount", "100"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("CABI 'checkCurrencyExchange' invalid request"));
    }

    @Test
    public void checkCurrencyExchangeFail500Test() throws Exception {
        when(cryptoApiService.checkCurrencyExchange(any(CheckCurrencyExchangeParams.class)))
                .thenThrow(TException.class);
        mockMvc.perform(get("/currency")
                .header("Authorization", "Bearer " + jwtTokenBuilder.generateJwtWithRoles("RBKadmin"))
                .param("from", "BTC")
                .param("to", "RUR")
                .param("action", "BUY")
                .param("amount", "100"))
                .andExpect(status().is5xxServerError());
    }

    private void whenCurrencyExchangeResponse(Rational amountExchange,
                                              Rational rate,
                                              Rational cryptoAmountExchangedWithFee,
                                              Rational amount,
                                              String from,
                                              String to,
                                              ExchangeAction action) throws TException {
        com.rbkmoney.cabi.CurrencyExchange currencyExchange = new com.rbkmoney.cabi.CurrencyExchange();
        currencyExchange.setAmountExchanged(amountExchange);
        currencyExchange.setRate(rate);
        currencyExchange.setAmountExchangedWithFee(cryptoAmountExchangedWithFee);
        currencyExchange.setAmount(amount);
        currencyExchange.setExchangeFrom(from);
        currencyExchange.setExchangeTo(to);
        currencyExchange.setAction(action);

        when(cryptoApiService.checkCurrencyExchange(any(CheckCurrencyExchangeParams.class)))
                .thenReturn(currencyExchange);
    }

    private void thenPerformCurrencyRequest(String amountExchange,
                                            String cryptoCurrencyAmountWithFee,
                                            Double amount,
                                            String rate,
                                            String from,
                                            String to,
                                            String action) throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/currency")
                .header("Authorization", "Bearer " + jwtTokenBuilder.generateJwtWithRoles("RBKadmin"))
                .param("from", from)
                .param("to", to)
                .param("action", action)
                .param("amount", amount.toString()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.amountExchange").value(amountExchange));
        if (cryptoCurrencyAmountWithFee == null) {
            resultActions.andExpect(jsonPath("$.cryptoCurrencyAmountWithFee").doesNotExist());
        } else {
            resultActions.andExpect(jsonPath("$.cryptoCurrencyAmountWithFee").value(cryptoCurrencyAmountWithFee));
        }
        resultActions.andExpect(jsonPath("$.amount").value(amount))
                .andExpect(jsonPath("$.rate").value(rate))
                .andExpect(jsonPath("$.from").value(from))
                .andExpect(jsonPath("$.to").value(to))
                .andExpect(jsonPath("$.action").value(action));
    }
}
