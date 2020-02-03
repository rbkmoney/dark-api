package com.rbkmoney.dark.api.exceptions.client;

public class ForbiddenException extends DarkApi4xxException {

    public ForbiddenException(String message) {
        super(message);
    }
}
