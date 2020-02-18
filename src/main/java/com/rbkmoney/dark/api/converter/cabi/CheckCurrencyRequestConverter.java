package com.rbkmoney.dark.api.converter.cabi;

import com.rbkmoney.cabi.CheckCurrencyExchangeParams;
import com.rbkmoney.cabi.Currency;
import com.rbkmoney.cabi.ExchangeAction;
import com.rbkmoney.cabi.base.Rational;
import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyRequestDto;
import com.rbkmoney.dark.api.util.MathUtils;
import org.springframework.stereotype.Component;

@Component
public class CheckCurrencyRequestConverter implements SwagConverter<CheckCurrencyExchangeParams, CabiCheckCurrencyRequestDto> {

    @Override
    public CheckCurrencyExchangeParams toSwag(CabiCheckCurrencyRequestDto value, SwagConverterContext ctx) {
        CheckCurrencyExchangeParams checkCurrencyExchangeParams = new CheckCurrencyExchangeParams();

        Currency to = new Currency();
        to.setSymbolicCode(value.getTo().getSymbolicCode());
        to.setExponent(value.getTo().getExponent());
        checkCurrencyExchangeParams.setExchangeTo(to);

        final Currency from = new Currency();
        from.setSymbolicCode(value.getFrom().getSymbolicCode());
        from.setExponent(value.getFrom().getExponent());
        checkCurrencyExchangeParams.setExchangeFrom(from);

        final Rational amount = MathUtils.covertToRational(value.getAmount());
        checkCurrencyExchangeParams.setAmount(amount);
        switch (value.getAction()) {
            case SELL:
                checkCurrencyExchangeParams.setAction(ExchangeAction.sell);
                break;
            case BUY:
                checkCurrencyExchangeParams.setAction(ExchangeAction.buy);
                break;
            default:
                throw new IllegalArgumentException("Unknown action type" + value.getAction());
        }

        return checkCurrencyExchangeParams;
    }
}
