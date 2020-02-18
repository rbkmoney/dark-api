package com.rbkmoney.dark.api.service;

public class CurrencyNotFound extends RuntimeException {

    private String symbolicCode;

    public CurrencyNotFound(String symbolicCode) {
        this.symbolicCode = symbolicCode;
    }

    public CurrencyNotFound(String message, String symbolicCode) {
        super(message);
        this.symbolicCode = symbolicCode;
    }

    public CurrencyNotFound(String message, Throwable cause, String symbolicCode) {
        super(message, cause);
        this.symbolicCode = symbolicCode;
    }

    public CurrencyNotFound(Throwable cause, String symbolicCode) {
        super(cause);
        this.symbolicCode = symbolicCode;
    }
}
