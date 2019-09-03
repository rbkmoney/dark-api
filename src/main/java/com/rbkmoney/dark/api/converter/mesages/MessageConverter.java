package com.rbkmoney.dark.api.converter.mesages;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.messages.model.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageConverter implements SwagConverter<Message, com.rbkmoney.damsel.messages.Message>,
        ThriftConverter<com.rbkmoney.damsel.messages.Message, Message> {

    @Override
    public Message toSwag(com.rbkmoney.damsel.messages.Message value, SwagConverterContext ctx) {
        return new Message()
                .messageId(value.getMessageId())
                .text(value.getText())
                .timestamp(value.getTimestamp())
                .userId(value.getUserId());
    }

    @Override
    public com.rbkmoney.damsel.messages.Message toThrift(Message value, ThriftConverterContext ctx) {
        return new com.rbkmoney.damsel.messages.Message()
                .setMessageId(value.getMessageId())
                .setText(value.getText())
                .setTimestamp(value.getTimestamp())
                .setUserId(value.getUserId());
    }

}
