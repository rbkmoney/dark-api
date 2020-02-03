package com.rbkmoney.dark.api.exceptions.client;

public class UnauthorizedException extends DarkApi4xxException {

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
