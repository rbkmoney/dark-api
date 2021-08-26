package com.rbkmoney.dark.api.controller;

import com.rbkmoney.damsel.message_sender.MailBody;
import com.rbkmoney.damsel.message_sender.Message;
import com.rbkmoney.damsel.message_sender.MessageExclusionObject;
import com.rbkmoney.damsel.message_sender.MessageExclusionRef;
import com.rbkmoney.damsel.message_sender.MessageMail;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import com.rbkmoney.dark.api.config.property.FeedbackProperties;
import com.rbkmoney.dark.api.converter.mailing.MessageExclusionRuleConverter;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.swag.sender.api.MessageApi;
import com.rbkmoney.swag.sender.model.InlineObject;
import com.rbkmoney.swag.sender.model.MessageExclusionRule;
import com.rbkmoney.swag.sender.model.MessageExclusionRuleList;
import com.rbkmoney.swag.sender.model.MessageExclusionRuleRequest;
import com.rbkmoney.swag.sender.model.MessageExclusionRuleType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.keycloak.representations.AccessToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

@Slf4j
@RestController
@RequiredArgsConstructor
@SuppressWarnings("ParameterName")
public class MessageController implements MessageApi {

    private final FeedbackProperties feedbackProperties;

    private final KeycloakService keycloakService;

    private final MessageSenderSrv.Iface messageSenderClient;

    private final MessageExclusionRuleConverter messageExclusionRuleConverter;

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
            throw darkApi5xxException("dudoser", "send", xRequestID, e);
        }
    }

    @Override
    public ResponseEntity<MessageExclusionRule> createMessageExclusionRule(
            String xRequestID,
            @Valid MessageExclusionRuleRequest request
    ) {
        log.info("Creating message exclusion rule. xRequestID = {} rule = {}", xRequestID, request);
        try {
            MessageExclusionObject result = messageSenderClient.addExclusionRule(
                    messageExclusionRuleConverter.toThrift(request)
            );
            return ResponseEntity.ok(messageExclusionRuleConverter.toSwag(result));
        } catch (TException e) {
            throw darkApi5xxException("dudoser", "addExclusionRule", xRequestID, e);
        }
    }

    @Override
    public ResponseEntity<Void> deleteMessageExclusionRule(String xRequestID, Long ruleID) {
        log.info("Deleting message exclusion rule. xRequestID = {} ruleID = {}", xRequestID, ruleID);
        try {
            messageSenderClient.removeExclusionRule(new MessageExclusionRef(ruleID));
            return ResponseEntity.ok().build();
        } catch (TException e) {
            throw darkApi5xxException("dudoser", "removeExclusionRule", xRequestID, e);
        }
    }

    @Override
    public ResponseEntity<MessageExclusionRule> getMessageExclusionRule(String xRequestID, Long ruleID) {
        log.info("Getting message exclusion rule. xRequestID = {} ruleID = {}", xRequestID, ruleID);
        try {
            MessageExclusionObject result = messageSenderClient.getExclusionRule(new MessageExclusionRef(ruleID));
            return ResponseEntity.ok(messageExclusionRuleConverter.toSwag(result));
        } catch (TException e) {
            throw darkApi5xxException("dudoser", "getExclusionRule", xRequestID, e);
        }
    }

    @Override
    public ResponseEntity<MessageExclusionRuleList> getMessageExclusionRulesByType(
            String xRequestID,
            @NotNull @Valid MessageExclusionRuleType type
    ) {
        log.info("Getting message exclusion rule. xRequestID = {} type = {}", xRequestID, type);
        try {
            List<MessageExclusionObject> result =
                    messageSenderClient.getExclusionRules(messageExclusionRuleConverter.resolveType(type));
            List<MessageExclusionRule> mappedResult = result.stream()
                    .map(messageExclusionRuleConverter::toSwag)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new MessageExclusionRuleList().result(mappedResult));
        } catch (TException e) {
            throw darkApi5xxException("dudoser", "getExclusionRules", xRequestID, e);
        }
    }
}
