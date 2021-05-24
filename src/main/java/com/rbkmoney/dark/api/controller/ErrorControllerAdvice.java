package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.exceptions.client.*;
import com.rbkmoney.dark.api.exceptions.client.badrequest.BadRequestException;
import com.rbkmoney.dark.api.exceptions.server.DarkApi5xxException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import java.net.http.HttpTimeoutException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorControllerAdvice {

    // ----------------- 4xx -----------------------------------------------------

    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Object handleBadRequestException(BadRequestException e) {
        log.warn("<- Res [400]: Not valid", e);
        return e.getResponse();
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("<- Res [400]: MethodArgument not valid", e);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("<- Res [400]: Missing ServletRequestParameter", e);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleUnauthorizedException(UnauthorizedException e) {
        log.warn("<- Res [401]: Unauthorized", e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDeniedException(AccessDeniedException e) {
        log.warn("<- Res [403]: Request denied access", e);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleForbiddenException(ForbiddenException e) {
        log.warn("<- Res [403]: Request denied access", e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object handleFileNotFoundException(NotFoundException e) {
        log.warn("<- Res [404]: Not found", e);
        return e.getResponse();
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public void handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException e) {
        log.warn("<- Res [406]: MediaType not acceptable", e);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Object handleConflictException(ConflictException e) {
        log.warn("<- Res [409]: Conflict", e);
        return e.getResponse();
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e, WebRequest request) {
        log.warn("<- Res [415]: MediaType not supported", e);
        return status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                .headers(httpHeaders(e))
                .build();
    }

    @ExceptionHandler({DarkApi4xxException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleDarkApiClientException(DarkApi4xxException e) {
        log.warn("<- Res [400]: Unrecognized Error by controller", e);
    }

    // ----------------- 5xx -----------------------------------------------------

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleHttpClientErrorException(HttpClientErrorException e) {
        log.error("<- Res [500]: Error with using inner http client, code={}, body={}", e.getStatusCode(),
                e.getResponseBodyAsString(), e);
    }

    @ExceptionHandler(HttpTimeoutException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleHttpTimeoutException(HttpTimeoutException e) {
        log.error("<- Res [500]: Timeout with using inner http client", e);
    }

    @ExceptionHandler(DarkApi5xxException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleHttpTimeoutException(DarkApi5xxException e) {
        log.error("<- Res [500]: Unrecognized inner error", e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception e) {
        log.error("<- Res [500]: Unrecognized inner error", e);
    }

    private HttpHeaders httpHeaders(HttpMediaTypeNotSupportedException e) {
        HttpHeaders headers = new HttpHeaders();
        List<MediaType> mediaTypes = e.getSupportedMediaTypes();
        if (!CollectionUtils.isEmpty(mediaTypes)) {
            headers.setAccept(mediaTypes);
        }
        return headers;
    }
}
