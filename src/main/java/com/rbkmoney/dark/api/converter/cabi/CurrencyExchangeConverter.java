package com.rbkmoney.dark.api.converter.cabi;

import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyResponseDto;
import com.rbkmoney.dark.api.util.MathUtils;
import com.rbkmoney.swag.cabi.model.CurrencyExchange;
import com.rbkmoney.swag.cabi.model.ExchangeAction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyExchangeConverter implements ThriftConverter<CurrencyExchange, CabiCheckCurrencyResponseDto> {

    @Override
    public CurrencyExchange toThrift(CabiCheckCurrencyResponseDto value, ThriftConverterContext ctx) {
        CurrencyExchange currencyExchange = new CurrencyExchange();
        ExchangeAction exchangeAction = extractExchangeAction(value.getAction());
        int exponent;
        if (exchangeAction == ExchangeAction.SELL) {
            exponent = (int) value.getTo().getExponent();
        } else {
            exponent = (int) value.getFrom().getExponent();
        }
        BigDecimal amount = MathUtils.convertFromRational(value.getAmount(), (int) value.getFrom().getExponent());
        currencyExchange.setAmount(amount);
        BigDecimal amountExchanged =
                MathUtils.convertFromRational(value.getAmountExchanged(), exponent);
        currencyExchange.setAmountExchange(amountExchanged);
        if (value.getAmountExchangedWithFee() != null) {
            BigDecimal amountExchangedWithFee = MathUtils.convertFromRational(
                    value.getAmountExchangedWithFee(), exponent);
            currencyExchange.setCryptoCurrencyAmountWithFee(amountExchangedWithFee);
        }
        currencyExchange.setFrom(value.getFrom().getSymbolicCode());
        currencyExchange.setTo(value.getTo().getSymbolicCode());
        currencyExchange.setRate(MathUtils.convertFromRational(value.getRate()));

        return currencyExchange;
    }

    private ExchangeAction extractExchangeAction(com.rbkmoney.cabi.ExchangeAction exchangeAction) {
        switch (exchangeAction) {
            case buy: return ExchangeAction.BUY;
            case sell: return ExchangeAction.SELL;
            default: throw new IllegalArgumentException("Unknown action type: " + exchangeAction);
        }
    }

}
