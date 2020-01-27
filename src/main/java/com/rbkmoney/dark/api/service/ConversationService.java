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

    private final MessageServiceSrv.Iface messageServiceClient;

    private final SwagConvertManager swagConvertManager;

    public void saveConversation(List<ConversationParam> conversationParams, User user) throws TException {
        String createdTime = Instant.now().toString();

        List<Conversation> conversationList = conversationParams.stream()
                .map(conversationParam -> getConversation(user, createdTime, conversationParam))
                .collect(Collectors.toList());

        messageServiceClient.saveConversations(conversationList, user);
    }

    public ConversationResponse getConversation(List<String> conversationIdList,
                                                com.rbkmoney.swag.messages.model.ConversationStatus conversationStatus) throws ConversationsNotFound, TException {
        if (conversationStatus == null) {
            conversationStatus = com.rbkmoney.swag.messages.model.ConversationStatus.ACTUAL;
        }
        var swagConversationFilter = new com.rbkmoney.swag.messages.model.ConversationFilter().conversationStatus(conversationStatus);

        ConversationFilter conversationFilter = swagConvertManager.convertToThrift(swagConversationFilter, ConversationFilter.class);

        GetConversationResponse conversations = messageServiceClient.getConversations(conversationIdList, conversationFilter);

        return swagConvertManager.convertFromThrift(conversations, ConversationResponse.class);
    }

    private Conversation getConversation(User user, String createdTime, ConversationParam conversationParam) {
        Conversation conversation = new Conversation();
        conversation.setConversationId(conversationParam.getConversationId());
        List<Message> messageList = conversationParam.getMessages().stream()
                .map(
                        message -> new Message()
                                .setMessageId(message.getMessageId())
                                .setText(message.getText())
                                .setUserId(user.getUserId())
                                .setTimestamp(createdTime)
                )
                .collect(Collectors.toList());
        conversation.setMessages(messageList);
        conversation.setStatus(ConversationStatus.ACTUAL);
        return conversation;
    }
}
