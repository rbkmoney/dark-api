package com.rbkmoney.dark.api.exceptions.client;

public class DarkApi4xxException extends RuntimeException {

    public DarkApi4xxException(String message) {
        super(message);
    }

    public DarkApi4xxException(String message, Throwable cause) {
        super(message, cause);
    }
}
