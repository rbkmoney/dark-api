package com.rbkmoney.dark.api.exceptions.client.badrequest;

import com.rbkmoney.dark.api.exceptions.client.DarkApi4xxException;
import lombok.Getter;

@Getter
public class BadRequestException extends DarkApi4xxException {

    private final Object response;

    public BadRequestException(String message, Throwable cause, Object response) {
        super(message, cause);
        this.response = response;
    }

    public BadRequestException(String message, Object response) {
        super(message);
        this.response = response;
    }
}
