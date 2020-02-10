package com.rbkmoney.dark.api.controller;

import com.rbkmoney.cabi.CheckCurrencyExchangeParams;
import com.rbkmoney.cabi.CryptoApiSrv;
import com.rbkmoney.cabi.CurrencyRequestFail;
import com.rbkmoney.cabi.ExchangeAction;
import com.rbkmoney.cabi.base.Rational;
import com.rbkmoney.dark.api.DarkApiApplication;
import com.rbkmoney.dark.api.auth.utils.JwtTokenBuilder;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.dark.api.service.PartyManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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

    @Before
    public void setUp() throws Exception {
        doNothing().when(partyManagementService).checkStatus(anyString());
        doNothing().when(partyManagementService).checkStatus();
        when(keycloakService.getPartyId()).thenReturn(UUID.randomUUID().toString());
    }

    @Test
    public void checkCurrencyExchangeSellTest() throws Exception {
        com.rbkmoney.cabi.CurrencyExchange currencyExchange = new com.rbkmoney.cabi.CurrencyExchange();
        currencyExchange.setAmountExchanged(new Rational(235158, 1000000));
        currencyExchange.setRate(new Rational(99672377, 10000));
        currencyExchange.setCryptoAmountExchangedWithFee(new Rational(235458, 1000000));
        currencyExchange.setAmount(10000L);
        currencyExchange.setExchangeFrom("USD");
        currencyExchange.setExchangeTo("BTC");
        currencyExchange.setAction(com.rbkmoney.cabi.ExchangeAction.sell);

        when(cryptoApiService.checkCurrencyExchange(any(CheckCurrencyExchangeParams.class)))
                .thenReturn(currencyExchange);

        mockMvc.perform(get("/currency")
                .header("Authorization", "Bearer " + jwtTokenBuilder.generateJwtWithRoles("RBKadmin"))
                .param("from", "BTC")
                .param("to", "RUR")
                .param("action", "SELL")
                .param("amount", "10000"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.amountExchange").value("0.235158"))
                .andExpect(jsonPath("$.amount").value(10000L))
                .andExpect(jsonPath("$.rate").value("9967.2377"))
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("BTC"))
                .andExpect(jsonPath("$.action").value("SELL"));
    }

    @Test
    public void checkCurrencyExchangeBuyTest() throws Exception {
        com.rbkmoney.cabi.CurrencyExchange currencyExchange = new com.rbkmoney.cabi.CurrencyExchange();
        currencyExchange.setAmountExchanged(new Rational(996528, 100));
        currencyExchange.setRate(new Rational(99642836, 100));
        currencyExchange.setCryptoAmountExchangedWithFee(new Rational(10001, 1000));
        currencyExchange.setAmount(100L);
        currencyExchange.setExchangeFrom("USD");
        currencyExchange.setExchangeTo("BTC");
        currencyExchange.setAction(ExchangeAction.buy);

        when(cryptoApiService.checkCurrencyExchange(any(CheckCurrencyExchangeParams.class)))
                .thenReturn(currencyExchange);

        mockMvc.perform(get("/currency")
                .header("Authorization", "Bearer " + jwtTokenBuilder.generateJwtWithRoles("RBKadmin"))
                .param("from", "BTC")
                .param("to", "RUR")
                .param("action", "BUY")
                .param("amount", "100"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.amountExchange").value("9965.28"))
                .andExpect(jsonPath("$.cryptoCurrencyAmountWithFee").value("1.0001"))
                .andExpect(jsonPath("$.amount").value(100L))
                .andExpect(jsonPath("$.rate").value("9964.2836"))
                .andExpect(jsonPath("$.from").value("USD"))
                .andExpect(jsonPath("$.to").value("BTC"))
                .andExpect(jsonPath("$.action").value("BUY"));

    }

    @Test
    public void checkCurrencyExchangeFailTest() throws Exception {
        when(cryptoApiService.checkCurrencyExchange(any(CheckCurrencyExchangeParams.class)))
                .thenThrow(CurrencyRequestFail.class);
        mockMvc.perform(get("/currency")
                .header("Authorization", "Bearer " + jwtTokenBuilder.generateJwtWithRoles("RBKadmin"))
                .param("from", "BTC")
                .param("to", "RUR")
                .param("action", "BUY")
                .param("amount", "100"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("CABI 'checkCurrencyExchange' invalid request"));
    }
}
