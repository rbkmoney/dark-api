package com.rbkmoney.dark.api.service;

public class CurrencyNotFound extends RuntimeException {

    private final String symbolicCode;

    public CurrencyNotFound(String symbolicCode) {
        this.symbolicCode = symbolicCode;
    }

}
