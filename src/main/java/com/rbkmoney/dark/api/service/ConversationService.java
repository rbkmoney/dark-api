package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.messages.*;
import com.rbkmoney.dark.api.converter.SwagConvertManager;
import com.rbkmoney.dark.api.exceptions.ConversationUsersNotProvided;
import com.rbkmoney.swag.messages.model.ConversationResponse;
import com.rbkmoney.swag.messages.model.SaveConversationParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConversationService {

    private final MessageServiceSrv.Iface messageServiceSrv;

    private final SwagConvertManager swagConvertManager;

    public void saveConversation(SaveConversationParams conversationParams) {
        List<Conversation> conversationList = conversationParams.getConversations().stream()
                .map(conversation -> swagConvertManager.convertToThrift(conversation, Conversation.class))
                .collect(Collectors.toList());
        List<User> userList = conversationParams.getUsers().stream()
                .map(user -> swagConvertManager.convertToThrift(user, User.class))
                .collect(Collectors.toList());
        try {
            messageServiceSrv.saveConversations(conversationList, userList);
        } catch (UsersNotProvided e) {
            log.error("Not all user provided", e);
            throw new ConversationUsersNotProvided(e.getIds());
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
            log.error("Conversation not found", e);
            throw new IllegalArgumentException(e);
        } catch (TException e) {
            log.error("Get conversation failed", e);
            throw new RuntimeException(e);
        }
    }

}
