package com.rbkmoney.dark.api.model;

import com.rbkmoney.cabi.ExchangeAction;
import com.rbkmoney.cabi.base.Rational;
import com.rbkmoney.damsel.domain.Currency;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CabiCheckCurrencyResponseDto {
    private ExchangeAction action;
    private Currency from;
    private Currency to;
    private Rational rate;
    private Rational amount;
    private Rational amountExchanged;
    private Rational amountExchangedWithFee;
}
