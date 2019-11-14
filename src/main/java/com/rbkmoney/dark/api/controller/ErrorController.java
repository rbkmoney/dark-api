package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.domain.ErrorResponse;
import com.rbkmoney.dark.api.exceptions.ConversationUsersNotProvided;
import com.rbkmoney.dark.api.exceptions.FileNotFoundException;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;

import java.net.http.HttpTimeoutException;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorController {

    public static final String INVALID_REQUEST = "invalidRequest";

    @ExceptionHandler(ConversationUsersNotProvided.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUsersNotProvided(ConversationUsersNotProvided e) {
        log.error(e.getMessage());
        return ErrorResponse.builder()
                .code(INVALID_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(IllegalArgumentException e) {
        log.error("IllegalArgumentException", e.getCause());
        return ErrorResponse.builder()
                .code(INVALID_REQUEST)
                .message(e.getCause().getMessage())
                .build();
    }

    @ExceptionHandler({
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
    })
    public final ResponseEntity<ErrorResponse> handleBadMediaType(Exception e, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        log.error("Bad MediaType", e);
        if (e instanceof HttpMediaTypeNotSupportedException) {
            HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            List<MediaType> mediaTypes = ((HttpMediaTypeNotSupportedException) e).getSupportedMediaTypes();
            if (!CollectionUtils.isEmpty(mediaTypes)) {
                headers.setAccept(mediaTypes);
            }
            ErrorResponse errorResp = ErrorResponse.builder()
                    .code(INVALID_REQUEST)
                    .message(e.getMessage())
                    .build();
            return new ResponseEntity<>(errorResp, headers, status);
        } else if (e instanceof HttpMediaTypeNotAcceptableException) {
            HttpStatus status = HttpStatus.NOT_ACCEPTABLE;
            return new ResponseEntity<>(null, headers, status);
        } else {
            throw e;
        }
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException", e);
        return ErrorResponse.builder()
                .code(INVALID_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(MissingServletRequestParameterException e) {
        log.error("MethodArgumentNotValidException", e);
        return ErrorResponse.builder()
                .code(INVALID_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleHttpClientError(HttpClientErrorException e) {
        log.error("Some exception: {}, {}", e.getStatusCode(), e.getResponseBodyAsString());
        try {
            return ErrorResponse.builder()
                    .code(e.getStatusCode().getReasonPhrase())
                    .message(e.getResponseBodyAsString())
                    .build();
        } catch (Exception ex) {
            log.error("Error in exception parsing", ex);
            throw new RuntimeException(ex);
        }
    }

    @ExceptionHandler(HttpServerErrorException.ServiceUnavailable.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleHttpTimeoutException(HttpServerErrorException.ServiceUnavailable e) {
        log.error("HttpServerErrorException", e);
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
            log.error("Error in exception parsing", ex);
            throw new RuntimeException(ex);
        }
    }

    @ExceptionHandler(HttpTimeoutException.class)
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public void handleHttpTimeoutException(HttpTimeoutException e) {
        log.error("HttpTimeoutException", e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException", e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleInternalException(Exception e) {
        log.error("InternalServerError", e);
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundException e) {
        log.info("File not found, fileId: {}", e.getFileId(), e);
        return ResponseEntity.notFound().build();
    }


}
