package com.rbkmoney.dark.api.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.rbkmoney.damsel.domain.Currency;
import com.rbkmoney.dark.api.config.property.DominantProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, Currency> dominantCache(DominantProperties dominantProperties) {
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
        cacheBuilder.expireAfterWrite(dominantProperties.getCacheExpireMinutes(), TimeUnit.MINUTES);
        return cacheBuilder.build();
    }

}
