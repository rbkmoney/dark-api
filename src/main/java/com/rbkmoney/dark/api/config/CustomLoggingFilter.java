package com.rbkmoney.dark.api.config;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

/**
 * @since 14.07.17
 **/
public class CustomLoggingFilter extends CommonsRequestLoggingFilter {

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return !request.getServletPath().contains("/health");
    }

}
