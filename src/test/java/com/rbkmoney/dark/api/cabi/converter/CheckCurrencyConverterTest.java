package com.rbkmoney.dark.api.cabi.converter;

import com.rbkmoney.cabi.CheckCurrencyExchangeParams;
import com.rbkmoney.damsel.domain.Currency;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.cabi.CheckCurrencyRequestConverter;
import com.rbkmoney.dark.api.converter.cabi.CurrencyExchangeConverter;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyRequestDto;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyResponseDto;
import com.rbkmoney.dark.api.util.MathUtils;
import com.rbkmoney.swag.cabi.model.CurrencyExchange;
import com.rbkmoney.swag.cabi.model.ExchangeAction;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
public class CheckCurrencyConverterTest {

    public static final Currency BTC_CURRENCY = new Currency("Bitcoin", "BTC", (short) 999, (short) 6);

    public static final Currency RUB_CURRENCY = new Currency("Russian ruble", "RUB", (short) 643, (short) 2);

    @Test
    public void checkCurrencyConvertToSwagTest() {
        CabiCheckCurrencyRequestDto cabiCheckCurrencyRequestDto = new CabiCheckCurrencyRequestDto(
                BTC_CURRENCY,
                RUB_CURRENCY,
                ExchangeAction.SELL,
                BigDecimal.valueOf(1.14)

        );
        SwagConverterContext swagConverterContext = Mockito.mock(SwagConverterContext.class);
        CheckCurrencyExchangeParams checkCurrencyExchangeParams =
                new CheckCurrencyRequestConverter().toSwag(cabiCheckCurrencyRequestDto, swagConverterContext);
        Assert.assertEquals(cabiCheckCurrencyRequestDto.getAction().name().toLowerCase(),
                checkCurrencyExchangeParams.getAction().name().toLowerCase());
        compareCurrency(cabiCheckCurrencyRequestDto.getFrom(), checkCurrencyExchangeParams.getExchangeFrom());
        compareCurrency(cabiCheckCurrencyRequestDto.getTo(), checkCurrencyExchangeParams.getExchangeTo());
        BigDecimal amount = MathUtils.convertFromRational(checkCurrencyExchangeParams.getAmount());
        Assert.assertEquals(0, cabiCheckCurrencyRequestDto.getAmount().compareTo(amount));
    }

    @Test
    public void checkCurrencyConvertFromSwagTest() {
        CabiCheckCurrencyResponseDto cabiCheckCurrencyResponseDto = new CabiCheckCurrencyResponseDto();
        cabiCheckCurrencyResponseDto.setAction(com.rbkmoney.cabi.ExchangeAction.sell);
        cabiCheckCurrencyResponseDto.setFrom(BTC_CURRENCY);
        cabiCheckCurrencyResponseDto.setTo(RUB_CURRENCY);
        BigDecimal amount = BigDecimal.valueOf(0.000032);
        cabiCheckCurrencyResponseDto.setAmount(MathUtils.covertToRational(amount));
        BigDecimal rate = BigDecimal.valueOf(425.25);
        cabiCheckCurrencyResponseDto.setRate(MathUtils.covertToRational(rate));
        BigDecimal amountExchanged = BigDecimal.valueOf(0.31);
        cabiCheckCurrencyResponseDto.setAmountExchanged(MathUtils.covertToRational(amountExchanged));

        SwagConverterContext swagConverterContext = Mockito.mock(SwagConverterContext.class);
        CurrencyExchange currencyExchange = new CurrencyExchangeConverter().toSwag(cabiCheckCurrencyResponseDto, swagConverterContext);

        Assert.assertEquals(cabiCheckCurrencyResponseDto.getFrom().getSymbolicCode(), currencyExchange.getFrom());
        Assert.assertEquals(cabiCheckCurrencyResponseDto.getTo().getSymbolicCode(), currencyExchange.getTo());
        Assert.assertEquals(0, amount.compareTo(currencyExchange.getAmount()));
        Assert.assertEquals(0, rate.compareTo(currencyExchange.getRate()));
        Assert.assertEquals(0, amountExchanged.compareTo(currencyExchange.getAmountExchange()));
    }

    public void compareCurrency(Currency damselCurrency, com.rbkmoney.cabi.Currency cabiCurrency) {
        Assert.assertEquals(cabiCurrency.getExponent(), damselCurrency.getExponent());
        Assert.assertEquals(cabiCurrency.getSymbolicCode(), damselCurrency.getSymbolicCode());
    }

}
