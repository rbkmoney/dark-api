package com.rbkmoney.dark.api.config;

import com.rbkmoney.questionary.manage.QuestionaryManagerSrv;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class QuestionaryConfiguration {

    @Value("${questionary.manager.client.adapter.url}")
    private Resource resource;

    @Value("${questionary.manager.client.adapter.networkTimeout}")
    private int networkTimeout;

    @Bean
    public QuestionaryManagerSrv.Iface questionaryManagerSrv() throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(resource.getURI())
                .withNetworkTimeout(networkTimeout)
                .build(QuestionaryManagerSrv.Iface.class);
    }

}
