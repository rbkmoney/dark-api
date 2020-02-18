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
        ExchangeAction exchangeAction = extractExchangeAction(value.getAction());
        int exponent = getExponent(value, exchangeAction);

        CurrencyExchange currencyExchange = new CurrencyExchange()
                .action(exchangeAction)
                .amount(MathUtils.convertFromRational(value.getAmount(), (int) value.getFrom().getExponent()))
                .amountExchange(MathUtils.convertFromRational(value.getAmountExchanged(), exponent))
                .from(value.getFrom().getSymbolicCode())
                .to(value.getTo().getSymbolicCode())
                .rate(MathUtils.convertFromRational(value.getRate()));

        if (value.getAmountExchangedWithFee() != null) {
            BigDecimal amountExchangedWithFee = MathUtils.convertFromRational(
                    value.getAmountExchangedWithFee(), exponent);
            currencyExchange.setCryptoCurrencyAmountWithFee(amountExchangedWithFee);
        }

        return currencyExchange;
    }

    private int getExponent(CabiCheckCurrencyResponseDto value, ExchangeAction exchangeAction) {
        int exponent;
        if (exchangeAction == ExchangeAction.SELL) {
            exponent = (int) value.getTo().getExponent();
        } else {
            exponent = (int) value.getFrom().getExponent();
        }
        return exponent;
    }

    private ExchangeAction extractExchangeAction(com.rbkmoney.cabi.ExchangeAction exchangeAction) {
        switch (exchangeAction) {
            case buy: return ExchangeAction.BUY;
            case sell: return ExchangeAction.SELL;
            default: throw new IllegalArgumentException("Unknown action type: " + exchangeAction);
        }
    }

}
