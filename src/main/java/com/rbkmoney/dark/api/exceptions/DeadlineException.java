package com.rbkmoney.dark.api.exceptions;

public class DeadlineException extends RuntimeException {

    public DeadlineException() {
    }

    public DeadlineException(String message) {
        super(message);
    }

    public DeadlineException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeadlineException(Throwable cause) {
        super(cause);
    }

    public DeadlineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
