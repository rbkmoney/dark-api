package com.rbkmoney.dark.api.controller;

import com.rbkmoney.damsel.messages.User;
import com.rbkmoney.dark.api.service.ConversationService;
import com.rbkmoney.swag.messages.api.ConversationApi;
import com.rbkmoney.swag.messages.model.ConversationParam;
import com.rbkmoney.swag.messages.model.ConversationResponse;
import com.rbkmoney.swag.messages.model.ConversationStatus;
import lombok.RequiredArgsConstructor;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ConversationController implements ConversationApi {

    private final ConversationService conversationService;

    @Override
    public ResponseEntity<ConversationResponse> getConversations(@NotNull @Valid List<String> conversationId,
                                                                 @Valid ConversationStatus conversationStatus) {
        ConversationResponse conversation = conversationService.getConversation(conversationId, conversationStatus);
        return ResponseEntity.ok(conversation);
    }

    @Override
    public ResponseEntity<Void> saveConversations(@Valid List<ConversationParam> conversationParams) {
        KeycloakSecurityContext keycloakSecurityContext = ((KeycloakPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal())
                .getKeycloakSecurityContext();
        AccessToken accessToken = keycloakSecurityContext.getToken();

        User user = new User().setUserId(accessToken.getSubject())
                .setFullname(accessToken.getPreferredUsername())
                .setEmail(accessToken.getEmail());

        conversationService.saveConversation(conversationParams, user);

        return ResponseEntity.ok().build();
    }

}
