package com.rbkmoney.dark.api.exceptions.client;

import lombok.Getter;

@Getter
public class NotFoundException extends DarkApi4xxException {

    private final Object response;

    public NotFoundException(String message, Throwable cause, Object response) {
        super(message, cause);
        this.response = response;
    }
}
