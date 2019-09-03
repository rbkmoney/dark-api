package com.rbkmoney.dark.api.config;

import com.rbkmoney.damsel.messages.MessageServiceSrv;
import com.rbkmoney.dark.api.config.property.ConversationProperties;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class ConversationConfig {

    private final ConversationProperties conversationProperties;

    @Bean
    public MessageServiceSrv.Iface conversationHandler() throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(conversationProperties.getUrl().getURI())
                .withNetworkTimeout(conversationProperties.getNetworkTimeout())
                .build(MessageServiceSrv.Iface.class);

    }

}
