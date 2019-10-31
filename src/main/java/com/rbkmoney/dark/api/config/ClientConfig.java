package com.rbkmoney.dark.api.config;

import com.rbkmoney.damsel.claim_management.ClaimManagementSrv;
import com.rbkmoney.damsel.merch_stat.DarkMessiahStatisticsSrv;
import com.rbkmoney.dark.api.meta.*;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Slf4j
@Configuration
public class ClientConfig {

    @Value("${http.timeout.read}")
    public Long readTimeoutSec;

    @Value("${http.timeout.connect}")
    public Long connectTimeoutSec;

    @Bean
    @Primary
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.of(connectTimeoutSec, ChronoUnit.SECONDS))
                .setReadTimeout(Duration.of(readTimeoutSec, ChronoUnit.SECONDS))
                .build();
    }

    @Bean
    public DarkMessiahStatisticsSrv.Iface magistaClient(@Value("${magista.client.adapter.url}") Resource resource,
                                                        @Value("${magista.client.adapter.networkTimeout}") int timeout) throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(resource.getURI())
                .withNetworkTimeout(timeout)
                .build(DarkMessiahStatisticsSrv.Iface.class);
    }

    @Bean
    public ClaimManagementSrv.Iface claimManagementClient(@Value("${claimmanagement.client.adapter.url}") Resource resource,
                                                          @Value("${claimmanagement.client.adapter.networkTimeout}") int timeout) throws IOException {
        return new THSpawnClientBuilder()
                .withMetaExtensions(
                        Arrays.asList(
                                UserIdentityIdExtensionKit.INSTANCE,
                                UserIdentityEmailExtensionKit.INSTANCE,
                                UserIdentityUsernameExtensionKit.INSTANCE,
                                UserIdentityRealmExtensionKit.INSTANCE
                        )
                )
                .withAddress(resource.getURI())
                .withNetworkTimeout(timeout)
                .build(ClaimManagementSrv.Iface.class);
    }

}
