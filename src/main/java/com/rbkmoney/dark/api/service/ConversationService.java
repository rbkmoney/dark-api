package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.messages.*;
import com.rbkmoney.dark.api.converter.SwagConvertManager;
import com.rbkmoney.swag.messages.model.ConversationParam;
import com.rbkmoney.swag.messages.model.ConversationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {

    private final MessageServiceSrv.Iface messageServiceSrv;

    private final SwagConvertManager swagConvertManager;

    public void saveConversation(List<ConversationParam> conversationParams, User user) {
        String createdTime = Instant.now().toString();
        List<Conversation> conversationList = conversationParams.stream()
                .map(conversationParam -> {
                    Conversation conversation = new Conversation();
                    conversation.setConversationId(conversationParam.getConversationId());
                    List<Message> messageList = conversationParam.getMessages().stream()
                            .map(message -> {
                                return new Message()
                                        .setMessageId(message.getMessageId())
                                        .setText(message.getText())
                                        .setUserId(user.getUserId())
                                        .setTimestamp(createdTime);
                            })
                            .collect(Collectors.toList());
                    conversation.setMessages(messageList);
                    return conversation;
                })
                .collect(Collectors.toList());
        try {
            messageServiceSrv.saveConversations(conversationList, user);
        } catch (TException e) {
            log.error("Save conversation failed", e);
            throw new RuntimeException(e);
        }
    }

    public ConversationResponse getConversation(List<String> conversationIdList,
                                                com.rbkmoney.swag.messages.model.ConversationStatus conversationStatus) {
        var swagConversationFilter = new com.rbkmoney.swag.messages.model.ConversationFilter()
                .conversationStatus(conversationStatus);
        ConversationFilter conversationFilter = swagConvertManager.convertToThrift(swagConversationFilter, ConversationFilter.class);
        try {
            GetConversationResponse conversations = messageServiceSrv.getConversations(conversationIdList, conversationFilter);
            return swagConvertManager.convertFromThrift(conversations, ConversationResponse.class);
        } catch (ConversationsNotFound e) {
            log.error("Conversation not found: {}", e.getIds(), e);
            throw new IllegalArgumentException(e);
        } catch (TException e) {
            log.error("Get conversation failed", e);
            throw new RuntimeException(e);
        }
    }

}
