package com.rbkmoney.dark.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    public String getPartyId() {
        return ((KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
    }

    public AccessToken getAccessToken() {
        KeycloakPrincipal keycloakPrincipal = (KeycloakPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return keycloakPrincipal.getKeycloakSecurityContext().getToken();
    }
}
