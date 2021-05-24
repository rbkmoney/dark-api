package com.rbkmoney.dark.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.dark.api.config.AbstractKeycloakOpenIdAsWiremockConfig;
import com.rbkmoney.dark.api.service.ConversationService;
import com.rbkmoney.dark.api.service.PartyManagementService;
import com.rbkmoney.swag.messages.model.ConversationParam;
import com.rbkmoney.swag.messages.model.MessageParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ConversationControllerTest extends AbstractKeycloakOpenIdAsWiremockConfig {

    @MockBean
    private ConversationService conversationService;

    @MockBean
    private PartyManagementService partyManagementService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        KeycloakSecurityContext keycloakSecurityContext =
                new KeycloakSecurityContext("tokenTest", new AccessToken(), "testToken", new IDToken());
        KeycloakPrincipal keycloakPrincipal = new KeycloakPrincipal<>("test", keycloakSecurityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(keycloakPrincipal);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        doNothing().when(partyManagementService).checkStatus(anyString());
        doNothing().when(partyManagementService).checkStatus();
    }

    @Test
    public void saveConversationTest() throws Exception {
        MessageParam messageParam = new MessageParam()
                .messageId("ca0bbfc6-7eb5-4d73-b0e6-d14327a21d40")
                .text("test");

        ConversationParam conversationParam = new ConversationParam()
                .conversationId("c9288adb-b9cb-4b7c-928f-cbbcb107f1b7")
                .messages(List.of(messageParam));

        List<ConversationParam> conversationParams = List.of(conversationParam);

        String conversationParamJson =
                objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(conversationParams);

        mvc.perform(post("/conversation")
                .header("Authorization", "Bearer " + generateRbkAdminJwt())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(conversationParamJson))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}
