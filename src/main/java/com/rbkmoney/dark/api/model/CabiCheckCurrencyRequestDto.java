package com.rbkmoney.dark.api.model;

import com.rbkmoney.damsel.domain.Currency;
import com.rbkmoney.swag.cabi.model.ExchangeAction;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CabiCheckCurrencyRequestDto {
    private final Currency from;
    private final Currency to;
    private final ExchangeAction action;
    private final BigDecimal amount;
}
