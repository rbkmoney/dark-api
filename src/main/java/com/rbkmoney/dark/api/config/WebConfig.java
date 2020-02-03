package com.rbkmoney.dark.api.config;

import com.rbkmoney.dark.api.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.woody.api.flow.WFlow;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.rbkmoney.dark.api.util.DeadlineUtils.*;
import static com.rbkmoney.woody.api.trace.ContextUtils.setCustomMetadataValue;
import static com.rbkmoney.woody.api.trace.ContextUtils.setDeadline;

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean woodyFilter() {
        WFlow wFlow = new WFlow();
        Filter filter = new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                wFlow.createServiceFork(
                        () -> {
                            try {
                                if (request.getUserPrincipal() != null) {
                                    addWoodyContext(request.getUserPrincipal());
                                }

                                setWoodyDeadline(request);

                                filterChain.doFilter(request, response);
                            } catch (IOException | ServletException e) {
                                sneakyThrow(e);
                            }
                        }
                )
                        .run();
            }

            private <E extends Throwable, T> T sneakyThrow(Throwable t) throws E {
                throw (E) t;
            }
        };

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setOrder(-50);
        filterRegistrationBean.setName("woodyFilter");
        filterRegistrationBean.addUrlPatterns("*");
        return filterRegistrationBean;
    }

    private void addWoodyContext(Principal principal) {
        KeycloakSecurityContext keycloakSecurityContext = ((KeycloakAuthenticationToken) principal).getAccount().getKeycloakSecurityContext();
        AccessToken accessToken = keycloakSecurityContext.getToken();

        setCustomMetadataValue(UserIdentityIdExtensionKit.KEY, accessToken.getSubject());
        setCustomMetadataValue(UserIdentityUsernameExtensionKit.KEY, accessToken.getPreferredUsername());
        setCustomMetadataValue(UserIdentityEmailExtensionKit.KEY, accessToken.getEmail());
        setCustomMetadataValue(UserIdentityRealmExtensionKit.KEY, keycloakSecurityContext.getRealm());
    }

    private void setWoodyDeadline(HttpServletRequest request) {
        String xRequestDeadline = request.getHeader("X-Request-Deadline");
        String xRequestId = request.getHeader("X-Request-ID");
        if (xRequestDeadline != null) {
            setDeadline(getInstant(xRequestDeadline, xRequestId));
        }
    }

    private Instant getInstant(String xRequestDeadline, String xRequestId) {
        Instant instant;
        if (containsRelativeValues(xRequestDeadline, xRequestId)) {
            instant = Instant.now()
                    .plus(extractMilliseconds(xRequestDeadline, xRequestId), ChronoUnit.MILLIS)
                    .plus(extractSeconds(xRequestDeadline, xRequestId), ChronoUnit.MILLIS)
                    .plus(extractMinutes(xRequestDeadline, xRequestId), ChronoUnit.MILLIS);
        } else {
            instant = Instant.parse(xRequestDeadline);
        }
        return instant;
    }
}
