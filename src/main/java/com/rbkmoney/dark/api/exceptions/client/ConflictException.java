package com.rbkmoney.dark.api.exceptions.client;

import lombok.Getter;

@Getter
public class ConflictException extends DarkApi4xxException {

    private final Object response;

    public ConflictException(String message, Object response) {
        super(message);
        this.response = response;
    }

    public ConflictException(String message, Throwable cause, Object response) {
        super(message, cause);
        this.response = response;
    }
}
