package com.rbkmoney.dark.api.converter.cabi;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyResponseDto;
import com.rbkmoney.dark.api.util.MathUtils;
import com.rbkmoney.swag.cabi.model.CurrencyExchange;
import com.rbkmoney.swag.cabi.model.ExchangeAction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyExchangeConverter implements SwagConverter<CurrencyExchange, CabiCheckCurrencyResponseDto> {

    @Override
    public CurrencyExchange toSwag(CabiCheckCurrencyResponseDto value, SwagConverterContext ctx) {
        CurrencyExchange currencyExchange = new CurrencyExchange();
        int exponent;
        switch (value.getAction()) {
            case sell:
                currencyExchange.setAction(ExchangeAction.SELL);
                exponent = (int) value.getTo().getExponent();
                break;
            case buy:
                currencyExchange.setAction(ExchangeAction.BUY);
                exponent = (int) value.getFrom().getExponent();
                break;
            default:
                throw new IllegalArgumentException("Unknown action type: " + value.getAction());
        }
        final BigDecimal amount = MathUtils.convertFromRational(value.getAmount(), (int) value.getFrom().getExponent());
        currencyExchange.setAmount(amount);
        final BigDecimal amountExchanged =
                MathUtils.convertFromRational(value.getAmountExchanged(), exponent);
        currencyExchange.setAmountExchange(amountExchanged);
        if (value.getAmountExchangedWithFee() != null) {
            final BigDecimal amountExchangedWithFee = MathUtils.convertFromRational(
                    value.getAmountExchangedWithFee(), exponent);
            currencyExchange.setCryptoCurrencyAmountWithFee(amountExchangedWithFee);
        }
        currencyExchange.setFrom(value.getFrom().getSymbolicCode());
        currencyExchange.setTo(value.getTo().getSymbolicCode());
        currencyExchange.setRate(MathUtils.convertFromRational(value.getRate()));

        return currencyExchange;
    }
}
