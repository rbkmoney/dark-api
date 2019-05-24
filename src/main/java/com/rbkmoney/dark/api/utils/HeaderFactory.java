package com.rbkmoney.dark.api.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HeaderFactory {

    public static final String X_REQUEST_DEADLINE = "X-Request-Deadline";
    public static final String X_REQUEST_ID = "X-Request-ID";
    public static final String AUTHORIZATION = "Authorization";

    public static HttpHeaders createHeaders(String xRequestID, String xRequestDeadline, String bearer) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, bearer);
        headers.set(X_REQUEST_DEADLINE, xRequestDeadline);
        headers.set(X_REQUEST_ID, xRequestID);
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

}
