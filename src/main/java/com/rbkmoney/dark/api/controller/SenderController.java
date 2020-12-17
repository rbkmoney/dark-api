package com.rbkmoney.dark.api.controller;

import com.rbkmoney.damsel.message_sender.MailBody;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import com.rbkmoney.dark.api.config.property.FeedbackProperties;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.swag.sender.api.MesssageApi;
import com.rbkmoney.swag.sender.model.InlineObject;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

@RestController
@RequiredArgsConstructor
public class SenderController implements MesssageApi {

    private final FeedbackProperties feedbackProperties;

    private final KeycloakService keycloakService;

    private final MessageSenderSrv.Iface messageSenderClient;

    @Override
    public ResponseEntity<Void> sendFeedbackEmailMsg(String xRequestID, @Valid InlineObject inlineObject) {
        try {
            String partyId = keycloakService.getPartyId();
            AccessToken accessToken = keycloakService.getAccessToken();
            MessageMail messageMail = new MessageMail();
            messageMail.setSubject("Обратная связь от " + partyId);
            messageMail.setMailBody(new MailBody(inlineObject.getText()));
            messageMail.setFromEmail(accessToken.getEmail());
            messageMail.setToEmails(feedbackProperties.getEmails());
            Message message = Message.message_mail(messageMail);
            messageSenderClient.send(message);

            return ResponseEntity.ok().build();
        } catch (TException e) {
            throw darkApi5xxException("dudoser", "searchPayments", xRequestID, e);
        }
    }
}
