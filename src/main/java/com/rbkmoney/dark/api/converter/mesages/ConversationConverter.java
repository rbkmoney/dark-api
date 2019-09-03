package com.rbkmoney.dark.api.converter.mesages;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.messages.model.Conversation;
import com.rbkmoney.swag.messages.model.ConversationStatus;
import com.rbkmoney.swag.messages.model.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConversationConverter implements SwagConverter<Conversation, com.rbkmoney.damsel.messages.Conversation>,
        ThriftConverter<com.rbkmoney.damsel.messages.Conversation, Conversation> {

    @Override
    public Conversation toSwag(com.rbkmoney.damsel.messages.Conversation value, SwagConverterContext ctx) {
        Conversation conversation = new Conversation();
        conversation.setConversationId(value.getConversationId());
        List<Message> messageList = value.getMessages().stream()
                .map(message -> ctx.convert(message, Message.class))
                .collect(Collectors.toList());
        conversation.setMessages(messageList);
        conversation.setStatus(ctx.convert(value.getStatus(), ConversationStatus.class));

        return conversation;
    }

    @Override
    public com.rbkmoney.damsel.messages.Conversation toThrift(Conversation value, ThriftConverterContext ctx) {
        com.rbkmoney.damsel.messages.Conversation conversation = new com.rbkmoney.damsel.messages.Conversation();
        conversation.setConversationId(value.getConversationId());
        List<com.rbkmoney.damsel.messages.Message> messageList = value.getMessages().stream()
                .map(message -> ctx.convert(message, com.rbkmoney.damsel.messages.Message.class))
                .collect(Collectors.toList());
        conversation.setMessages(messageList);
        conversation.setStatus(ctx.convert(value.getStatus(), com.rbkmoney.damsel.messages.ConversationStatus.class));

        return conversation;
    }
}
