package com.rbkmoney.dark.api.auth;

import com.rbkmoney.dark.api.auth.utils.JwtTokenBuilder;
import com.rbkmoney.dark.api.auth.utils.KeycloakOpenIdStub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakOpenIdTestConfiguration {

    @Bean
    public KeycloakOpenIdStub keycloakOpenIdStub(@Value("${keycloak.auth-server-url}") String keycloakAuthServerUrl,
                                                 @Value("${keycloak.realm}") String keycloakRealm,
                                                 JwtTokenBuilder jwtTokenBuilder) {
        return new KeycloakOpenIdStub(keycloakAuthServerUrl, keycloakRealm, jwtTokenBuilder);
    }
}
