package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.domain.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.net.http.HttpTimeoutException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorController {

    public static final String INVALID_REQUEST = "invalidRequest";

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(MethodArgumentNotValidException e) {
        log.error("HttpClientErrorException.BadRequest exception e: ", e);
        return ErrorResponse.builder()
                .code(INVALID_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ErrorResponse handleHttpClientError(HttpClientErrorException e) {
        log.error("Some exception: {}, {}", e.getStatusCode(), e.getResponseBodyAsString());
        try {
            return ErrorResponse.builder()
                    .code(e.getStatusCode().getReasonPhrase())
                    .message(e.getResponseBodyAsString())
                    .build();
        } catch (Exception ex) {
            log.error("Error in exception parsing:", ex);
            throw new RuntimeException(ex);
        }
    }

    @ExceptionHandler(HttpServerErrorException.ServiceUnavailable.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleHttpTimeoutException(HttpServerErrorException.ServiceUnavailable e) {
        log.error("HttpServerErrorException e: ", e);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleHttpTimeoutException(HttpClientErrorException.BadRequest e) {
        log.error("Some exception: {}, {}", e.getStatusCode(), e.getResponseBodyAsString());
        try {
            return ErrorResponse.builder()
                    .code(INVALID_REQUEST)
                    .message(e.getResponseBodyAsString())
                    .build();
        } catch (Exception ex) {
            log.error("Error in exception parsing:", ex);
            throw new RuntimeException(ex);
        }
    }

    @ExceptionHandler(HttpTimeoutException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public void handleHttpTimeoutException(HttpTimeoutException e) {
        log.error("HttpTimeoutException e: ", e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException e: ", e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleInternalException(Exception e) {
        log.error("InternalServerError e: ", e);
    }


}