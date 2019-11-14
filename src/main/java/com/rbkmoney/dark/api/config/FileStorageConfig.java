package com.rbkmoney.dark.api.config;

import com.rbkmoney.file.storage.FileStorageSrv;
import com.rbkmoney.woody.thrift.impl.http.THSpawnClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class FileStorageConfig {

    @Bean
    public FileStorageSrv.Iface fileStorageClient(@Value("${filestorage.client.adapter.url}") Resource resource,
                                                  @Value("${filestorage.client.adapter.networkTimeout}") int timeout) throws IOException {
        return new THSpawnClientBuilder()
                .withAddress(resource.getURI())
                .withNetworkTimeout(timeout)
                .build(FileStorageSrv.Iface.class);
    }

}
