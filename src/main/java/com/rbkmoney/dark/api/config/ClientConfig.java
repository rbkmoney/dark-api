package com.rbkmoney.dark.api.config;

import com.rbkmoney.cabi.CryptoApiSrv;
import com.rbkmoney.damsel.analytics.AnalyticsServiceSrv;
import com.rbkmoney.damsel.claim_management.ClaimManagementSrv;
import com.rbkmoney.damsel.domain_config.RepositoryClientSrv;
import com.rbkmoney.damsel.merch_stat.DarkMessiahStatisticsSrv;
import com.rbkmoney.damsel.message_sender.MessageSenderSrv;
import com.rbkmoney.damsel.messages.MessageServiceSrv;
import com.rbkmoney.damsel.payment_processing.PartyManagementSrv;
import com.rbkmoney.damsel.questionary_proxy_aggr.QuestionaryAggrProxyHandlerSrv;
import com.rbkmoney.dark.api.config.property.CabiProperties;
import com.rbkmoney.dark.api.config.property.ConversationProperties;
import com.rbkmoney.dark.api.config.property.QuestionaryAggrProxyProperties;
import com.rbkmoney.dark.api.config.property.QuestionaryProperties;
import com.rbkmoney.dark.api.meta.UserIdentityEmailExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityIdExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityRealmExtensionKit;
import com.rbkmoney.dark.api.meta.UserIdentityUsernameExtensionKit;
import com.rbkmoney.file.storage.FileStorageSrv;
import com.rbkmoney.questionary.manage.QuestionaryManagerSrv;
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
                                                        @Value("${magista.client.adapter.networkTimeout}") int timeout)
            throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(resource.getURI())
                .withNetworkTimeout(timeout)
                .build(DarkMessiahStatisticsSrv.Iface.class);
    }

    @Bean
    public ClaimManagementSrv.Iface claimManagementClient(
            @Value("${claimmanagement.client.adapter.url}") Resource resource,
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

    @Bean
    public FileStorageSrv.Iface fileStorageClient(@Value("${filestorage.client.adapter.url}") Resource resource,
                                                  @Value("${filestorage.client.adapter.networkTimeout}") int timeout)
            throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(resource.getURI())
                .withNetworkTimeout(timeout)
                .build(FileStorageSrv.Iface.class);
    }

    @Bean
    public MessageServiceSrv.Iface messageServiceClient(ConversationProperties conversationProperties)
            throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(conversationProperties.getUrl().getURI())
                .withNetworkTimeout(conversationProperties.getNetworkTimeout())
                .build(MessageServiceSrv.Iface.class);

    }

    @Bean
    public PartyManagementSrv.Iface partyManagementClient(@Value("${partyManagement.url}") Resource resource,
                                                          @Value("${partyManagement.timeout}") int timeout)
            throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(resource.getURI())
                .withNetworkTimeout(timeout)
                .build(PartyManagementSrv.Iface.class);
    }

    @Bean
    public QuestionaryAggrProxyHandlerSrv.Iface questionaryAggrProxyClient(
            QuestionaryAggrProxyProperties questionaryAggrProxyProperties) throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(questionaryAggrProxyProperties.getUrl().getURI())
                .withNetworkTimeout(questionaryAggrProxyProperties.getNetworkTimeout())
                .build(QuestionaryAggrProxyHandlerSrv.Iface.class);

    }

    @Bean
    public QuestionaryManagerSrv.Iface questionaryManagerClient(QuestionaryProperties questionaryProperties)
            throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(questionaryProperties.getUrl().getURI())
                .withNetworkTimeout(questionaryProperties.getNetworkTimeout())
                .build(QuestionaryManagerSrv.Iface.class);
    }

    @Bean
    public CryptoApiSrv.Iface cabiClient(CabiProperties cabiProperties) throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(cabiProperties.getUrl().getURI())
                .withNetworkTimeout(cabiProperties.getNetworkTimeout())
                .build(CryptoApiSrv.Iface.class);
    }

    @Bean
    public RepositoryClientSrv.Iface dominantClient(@Value("${dominant.url}") Resource resource,
                                                    @Value("${dominant.networkTimeout}") int networkTimeout)
            throws IOException {
        return new THSpawnClientBuilder()
                .withNetworkTimeout(networkTimeout)
                .withAddress(resource.getURI()).build(RepositoryClientSrv.Iface.class);
    }

    @Bean
    public AnalyticsServiceSrv.Iface analyticsClient(@Value("${analytics.url}") Resource resource,
                                                     @Value("${analytics.networkTimeout}") int networkTimeout)
            throws IOException {
        return new THSpawnClientBuilder()
                .withNetworkTimeout(networkTimeout)
                .withAddress(resource.getURI()).build(AnalyticsServiceSrv.Iface.class);
    }

    @Bean
    public MessageSenderSrv.Iface messageSenderClient(@Value("${dudoser.url}") Resource resource,
                                                      @Value("${dudoser.networkTimeout}") int networkTimeout)
            throws IOException {
        return new THSpawnClientBuilder()
                .withNetworkTimeout(networkTimeout)
                .withAddress(resource.getURI()).build(MessageSenderSrv.Iface.class);
    }

}
