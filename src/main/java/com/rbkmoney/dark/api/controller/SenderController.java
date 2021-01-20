package com.rbkmoney.dark.api.controller;

import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

import com.rbkmoney.damsel.message_sender.MailBody;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import com.rbkmoney.dark.api.config.property.FeedbackProperties;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.swag.sender.api.MessageApi;
import com.rbkmoney.swag.sender.model.InlineObject;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SenderController implements MessageApi {

    private final FeedbackProperties feedbackProperties;

    private final KeycloakService keycloakService;

    private final MessageSenderSrv.Iface messageSenderClient;

    @Override
    public ResponseEntity<Void> sendFeedbackEmailMsg(String xRequestID, @Valid InlineObject inlineObject) {
        try {
            String partyId = keycloakService.getPartyId();
            AccessToken accessToken = keycloakService.getAccessToken();
            MessageMail messageMail = new MessageMail();
            messageMail.setSubject(
                  String.format("Обратная связь от %s (%s)", accessToken.getEmail(), partyId)
            );
            messageMail.setMailBody(new MailBody(inlineObject.getText()));
            messageMail.setFromEmail(feedbackProperties.getFromEmail());
            messageMail.setToEmails(feedbackProperties.getToEmails());
            Message message = Message.message_mail(messageMail);
            messageSenderClient.send(message);

            return ResponseEntity.ok().build();
        } catch (TException e) {
            throw darkApi5xxException("dudoser", "searchPayments", xRequestID, e);
        }
    }
}
