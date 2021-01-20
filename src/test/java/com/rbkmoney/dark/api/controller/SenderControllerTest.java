package com.rbkmoney.dark.api.controller;

import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import com.rbkmoney.dark.api.config.AbstractKeycloakOpenIdAsWiremockConfig;
import com.rbkmoney.dark.api.config.property.FeedbackProperties;
import com.rbkmoney.swag.sender.model.InlineObject;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

public class SenderControllerTest extends AbstractKeycloakOpenIdAsWiremockConfig {

    @MockBean
    private MessageSenderSrv.Iface messageSenderClientMock;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FeedbackProperties feedbackProperties;

    @BeforeEach
    public void setUp() throws Exception {
        Authentication authentication = Mockito.mock(Authentication.class);
        KeycloakSecurityContext keycloakSecurityContext = new KeycloakSecurityContext("tokenTest", new AccessToken(), "testToken", new IDToken());
        KeycloakPrincipal keycloakPrincipal = new KeycloakPrincipal<>("test", keycloakSecurityContext);
        Mockito.when(authentication.getPrincipal()).thenReturn(keycloakPrincipal);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void sendFeedbackEmailMessageTest() throws Exception {
        InlineObject emailPayload = new InlineObject();
        emailPayload.setText("Some test text");
        String content = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(emailPayload);

        mvc.perform(post("/message/feedback")
                .header("Authorization", "Bearer " + generateRBKadminJwt())
                .header("X-Request-ID", "testRequestId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().isOk());

        ArgumentCaptor<Message> argumentCaptor = ArgumentCaptor.forClass(Message.class);
        verify(messageSenderClientMock, atMostOnce()).send(argumentCaptor.capture());
        Message captorValue = argumentCaptor.getValue();
        Assert.assertTrue(captorValue.getMessageMail().getSubject().startsWith("Обратная связь от"));
        Assert.assertEquals(feedbackProperties.getFromEmail(),
                captorValue.getMessageMail().getFromEmail());
        Assert.assertEquals("feedback@rbkmoney.com",
                captorValue.getMessageMail().getToEmails().get(0));
        Assert.assertEquals(emailPayload.getText(),
                captorValue.getMessageMail().getMailBody().getText());
    }

}
