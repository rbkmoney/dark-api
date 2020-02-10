package com.rbkmoney.dark.api.converter.cabi;

import com.rbkmoney.cabi.CheckCurrencyExchangeParams;
import com.rbkmoney.cabi.ExchangeAction;
import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyRequestHolder;
import org.springframework.stereotype.Component;

@Component
public class CheckCurrencyRequestConverter implements SwagConverter<CheckCurrencyExchangeParams, CabiCheckCurrencyRequestHolder> {

    @Override
    public CheckCurrencyExchangeParams toSwag(CabiCheckCurrencyRequestHolder value, SwagConverterContext ctx) {
        CheckCurrencyExchangeParams checkCurrencyExchangeParams = new CheckCurrencyExchangeParams();
        checkCurrencyExchangeParams.setExchangeTo(value.getTo());
        checkCurrencyExchangeParams.setExchangeFrom(value.getFrom());
        checkCurrencyExchangeParams.setAmount(value.getAmount());
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
