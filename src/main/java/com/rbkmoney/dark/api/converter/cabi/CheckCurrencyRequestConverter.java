package com.rbkmoney.dark.api.converter.cabi;

import com.rbkmoney.cabi.CheckCurrencyExchangeParams;
import com.rbkmoney.cabi.Currency;
import com.rbkmoney.cabi.ExchangeAction;
import com.rbkmoney.cabi.base.Rational;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyRequestDto;
import com.rbkmoney.dark.api.util.MathUtils;
import org.springframework.stereotype.Component;

@Component
public class CheckCurrencyRequestConverter implements ThriftConverter<CheckCurrencyExchangeParams, CabiCheckCurrencyRequestDto> {

    @Override
    public CheckCurrencyExchangeParams toThrift(CabiCheckCurrencyRequestDto value, ThriftConverterContext ctx) {
        CheckCurrencyExchangeParams checkCurrencyExchangeParams = new CheckCurrencyExchangeParams();

        Currency to = new Currency(value.getTo().getSymbolicCode(), value.getTo().getExponent());
        Currency from = new Currency(value.getFrom().getSymbolicCode(), value.getFrom().getExponent());
        checkCurrencyExchangeParams.setExchangeTo(to);
        checkCurrencyExchangeParams.setExchangeFrom(from);

        Rational amount = MathUtils.covertToRational(value.getAmount());
        checkCurrencyExchangeParams.setAmount(amount);
        ExchangeAction exchangeAction = extractExchangeAction(value.getAction());
        checkCurrencyExchangeParams.setAction(exchangeAction);

        return checkCurrencyExchangeParams;
    }

    private ExchangeAction extractExchangeAction(com.rbkmoney.swag.cabi.model.ExchangeAction exchangeAction) {
        switch (exchangeAction) {
            case BUY: return ExchangeAction.buy;
            case SELL: return ExchangeAction.sell;
            default: throw new IllegalArgumentException("Unknown action type: " + exchangeAction);
        }
    }
}
