package com.rbkmoney.dark.api.exceptions.server;

public class DarkApi5xxException extends RuntimeException {

    public DarkApi5xxException(String message, Throwable cause) {
        super(message, cause);
    }
}
