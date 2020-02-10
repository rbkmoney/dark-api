package com.rbkmoney.dark.api.converter.cabi;

import com.rbkmoney.cabi.base.Rational;
import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.cabi.model.CurrencyExchange;
import com.rbkmoney.swag.cabi.model.ExchangeAction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CurrencyExchangeConverter implements SwagConverter<CurrencyExchange, com.rbkmoney.cabi.CurrencyExchange> {

    @Override
    public CurrencyExchange toSwag(com.rbkmoney.cabi.CurrencyExchange value, SwagConverterContext ctx) {
        CurrencyExchange currencyExchange = new CurrencyExchange();
        switch (value.getAction()) {
            case sell:
                currencyExchange.setAction(ExchangeAction.SELL);
                break;
            case buy:
                currencyExchange.setAction(ExchangeAction.BUY);
                break;
            default:
                throw new IllegalArgumentException("Unknown action type: " + value.getAction());
        }
        currencyExchange.setAmount(value.getAmount());
        currencyExchange.setAmountExchange(convertFromRational(value.getAmountExchanged()).toPlainString());
        if (value.getCryptoAmountExchangedWithFee() != null) {
            currencyExchange.setCryptoCurrencyAmountWithFee(
                    convertFromRational(value.getCryptoAmountExchangedWithFee()).toPlainString());
        }
        currencyExchange.setFrom(value.getExchangeFrom());
        currencyExchange.setTo(value.getExchangeTo());
        currencyExchange.setRate(convertFromRational(value.getRate()).toPlainString());

        return currencyExchange;
    }

    private BigDecimal convertFromRational(Rational rational) {
        BigDecimal numerator = BigDecimal.valueOf(rational.p);
        BigDecimal denominator = BigDecimal.valueOf(rational.q);
        return numerator.divide(denominator);
    }

}
