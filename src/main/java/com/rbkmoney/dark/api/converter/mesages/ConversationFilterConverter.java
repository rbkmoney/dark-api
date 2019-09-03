package com.rbkmoney.dark.api.converter.mesages;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.messages.model.ConversationFilter;
import com.rbkmoney.swag.messages.model.ConversationStatus;
import org.springframework.stereotype.Component;

@Component
public class ConversationFilterConverter implements SwagConverter<ConversationFilter, com.rbkmoney.damsel.messages.ConversationFilter>,
        ThriftConverter<com.rbkmoney.damsel.messages.ConversationFilter, ConversationFilter> {

    @Override
    public ConversationFilter toSwag(com.rbkmoney.damsel.messages.ConversationFilter value, SwagConverterContext ctx) {
        ConversationFilter conversationFilter = new ConversationFilter();
        conversationFilter.setConversationStatus(ctx.convert(value.getConversationStatus(), ConversationStatus.class));

        return conversationFilter;
    }

    @Override
    public com.rbkmoney.damsel.messages.ConversationFilter toThrift(ConversationFilter value, ThriftConverterContext ctx) {
        com.rbkmoney.damsel.messages.ConversationFilter conversationFilter = new com.rbkmoney.damsel.messages.ConversationFilter();
        conversationFilter.setConversationStatus(ctx.convert(value.getConversationStatus(), com.rbkmoney.damsel.messages.ConversationStatus.class));

        return conversationFilter;
    }

}
