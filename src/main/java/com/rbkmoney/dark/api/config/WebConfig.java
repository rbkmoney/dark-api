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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@EnableWebMvc
@Configuration
public class WebConfig {

    public static final String PATH = "/dark-api/v1/";

    @Bean
    public FilterRegistrationBean woodyFilter() {
        WFlow wFlow = new WFlow();
        Filter filter = new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain) throws ServletException, IOException {
                if (request.getRequestURI().startsWith(PATH)) {
                    wFlow.createServiceFork(() -> {
                        try {
                            addWoodyContext(request.getUserPrincipal());
                            filterChain.doFilter(request, response);
                        } catch (IOException | ServletException e) {
                            sneakyThrow(e);
                        }
                    }).run();
                    return;
                }
                filterChain.doFilter(request, response);
            }

            private <E extends Throwable, T> T sneakyThrow(Throwable t) throws E {
                throw (E) t;
            }
        };

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setOrder(-50);
        filterRegistrationBean.setName("woodyFilter");
        filterRegistrationBean.addUrlPatterns(PATH + "*");
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

    @Bean
    public CustomLoggingFilter logFilter() {
        CustomLoggingFilter loggingFilter = new CustomLoggingFilter();
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setMaxPayloadLength(5120);
        return loggingFilter;
    }

}
