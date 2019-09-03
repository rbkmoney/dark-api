package com.rbkmoney.dark.api.converter.mesages;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.messages.model.ConversationStatus;
import org.springframework.stereotype.Component;

@Component
public class ConversationStatusConverter implements SwagConverter<ConversationStatus, com.rbkmoney.damsel.messages.ConversationStatus>,
        ThriftConverter<com.rbkmoney.damsel.messages.ConversationStatus, ConversationStatus> {

    @Override
    public ConversationStatus toSwag(com.rbkmoney.damsel.messages.ConversationStatus value, SwagConverterContext ctx) {
        switch (value) {
            case ACTUAL:
                return ConversationStatus.ACTUAL;
            case OUTDATED:
                return ConversationStatus.OUTDATED;
            default:
                throw new IllegalArgumentException("Unknown conversation status: " + value);
        }
    }

    @Override
    public com.rbkmoney.damsel.messages.ConversationStatus toThrift(ConversationStatus value, ThriftConverterContext ctx) {
        switch (value) {
            case ACTUAL:
                return com.rbkmoney.damsel.messages.ConversationStatus.ACTUAL;
            case OUTDATED:
                return com.rbkmoney.damsel.messages.ConversationStatus.OUTDATED;
            default:
                throw new IllegalArgumentException("Unknown conversation status: " + value);
        }
    }

}
