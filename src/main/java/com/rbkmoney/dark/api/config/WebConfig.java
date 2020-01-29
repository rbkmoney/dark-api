package com.rbkmoney.dark.api.config;

import com.rbkmoney.dark.api.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.woody.api.flow.WFlow;
import com.rbkmoney.woody.api.trace.ContextUtils;
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

                                String deadline = request.getHeader("X-Request-Deadline");
                                if (deadline != null) {
                                    ContextUtils.setDeadline(Instant.parse(deadline));
                                }

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

        ContextUtils.setCustomMetadataValue(UserIdentityIdExtensionKit.KEY, accessToken.getSubject());
        ContextUtils.setCustomMetadataValue(UserIdentityUsernameExtensionKit.KEY, accessToken.getPreferredUsername());
        ContextUtils.setCustomMetadataValue(UserIdentityEmailExtensionKit.KEY, accessToken.getEmail());
        ContextUtils.setCustomMetadataValue(UserIdentityRealmExtensionKit.KEY, keycloakSecurityContext.getRealm());
    }
}
