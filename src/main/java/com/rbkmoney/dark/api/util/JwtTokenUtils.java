package com.rbkmoney.dark.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenUtils {

    public static AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        KeycloakSecurityContext keycloakSecurityContext = ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext();

        return keycloakSecurityContext.getToken();
    }

}
