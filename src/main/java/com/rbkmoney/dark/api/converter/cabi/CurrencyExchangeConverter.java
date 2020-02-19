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

        return new CurrencyExchange()
                .action(exchangeAction)
                .amount(MathUtils.convertFromRational(value.getAmount(), (int) value.getFrom().getExponent()))
                .amountExchange(MathUtils.convertFromRational(value.getAmountExchanged(), exponent))
                .cryptoCurrencyAmountWithFee(getAmountWithFee(value, exponent))
                .from(value.getFrom().getSymbolicCode())
                .to(value.getTo().getSymbolicCode())
                .rate(MathUtils.convertFromRational(value.getRate()));
    }

    private BigDecimal getAmountWithFee(CabiCheckCurrencyResponseDto value, int exponent) {
        if (value.getAmountExchangedWithFee() != null) {
            return MathUtils.convertFromRational(value.getAmountExchangedWithFee(), exponent);
        }
        return null;
    }

    private int getExponent(CabiCheckCurrencyResponseDto value, ExchangeAction exchangeAction) {
        switch (exchangeAction) {
            case SELL: return (int) value.getTo().getExponent();
            case BUY: return (int) value.getFrom().getExponent();
            default: throw new IllegalArgumentException("Unknown exchange action: " + exchangeAction);
        }
    }

    private ExchangeAction extractExchangeAction(com.rbkmoney.cabi.ExchangeAction exchangeAction) {
        switch (exchangeAction) {
            case buy: return ExchangeAction.BUY;
            case sell: return ExchangeAction.SELL;
            default: throw new IllegalArgumentException("Unknown action type: " + exchangeAction);
        }
    }

}
