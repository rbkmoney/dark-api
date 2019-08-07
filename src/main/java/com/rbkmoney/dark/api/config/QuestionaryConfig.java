package com.rbkmoney.dark.api.config;

import com.rbkmoney.dark.api.config.property.QuestionaryProperties;
import com.rbkmoney.questionary.manage.QuestionaryManagerSrv;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class QuestionaryConfig {

    private final QuestionaryProperties questionaryProperties;

    @Bean
    public QuestionaryManagerSrv.Iface questionaryManager() throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(questionaryProperties.getUrl().getURI())
                .withNetworkTimeout(questionaryProperties.getNetworkTimeout())
                .build(QuestionaryManagerSrv.Iface.class);
    }

}
