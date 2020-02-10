package com.rbkmoney.dark.api.model;

import com.rbkmoney.swag.cabi.model.ExchangeAction;
import lombok.Data;

@Data
public class CabiCheckCurrencyRequestDto {
    private final String from;
    private final String to;
    private final ExchangeAction action;
    private final Long amount;
}
