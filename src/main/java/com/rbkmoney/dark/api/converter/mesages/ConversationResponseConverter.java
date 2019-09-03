package com.rbkmoney.dark.api.converter.mesages;

import com.rbkmoney.damsel.messages.GetConversationResponse;
import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.messages.model.Conversation;
import com.rbkmoney.swag.messages.model.ConversationResponse;
import com.rbkmoney.swag.messages.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConversationResponseConverter implements SwagConverter<ConversationResponse, GetConversationResponse>,
        ThriftConverter<GetConversationResponse, ConversationResponse> {

    @Override
    public ConversationResponse toSwag(GetConversationResponse value, SwagConverterContext ctx) {
        ConversationResponse conversationResponse = new ConversationResponse();
        List<Conversation> conversationList = value.getConversations().stream()
                .map(conversation -> ctx.convert(conversation, Conversation.class))
                .collect(Collectors.toList());
        conversationResponse.setConversations(conversationList);

        Map<String, User> users = value.getUsers().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> ctx.convert(e.getValue(), User.class)
                ));
        conversationResponse.setUsers(users);

        return conversationResponse;
    }

    @Override
    public GetConversationResponse toThrift(ConversationResponse value, ThriftConverterContext ctx) {
        GetConversationResponse getConversationResponse = new GetConversationResponse();
        List<com.rbkmoney.damsel.messages.Conversation> conversationList = value.getConversations().stream()
                .map(conversation -> ctx.convert(conversation, com.rbkmoney.damsel.messages.Conversation.class))
                .collect(Collectors.toList());
        getConversationResponse.setConversations(conversationList);

        Map<String, com.rbkmoney.damsel.messages.User> users = value.getUsers().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> ctx.convert(e.getValue(), com.rbkmoney.damsel.messages.User.class)
                ));
        getConversationResponse.setUsers(users);

        return getConversationResponse;
    }

}
