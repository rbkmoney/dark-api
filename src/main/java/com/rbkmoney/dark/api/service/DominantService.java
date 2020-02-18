package com.rbkmoney.dark.api.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.rbkmoney.damsel.domain.Currency;
import com.rbkmoney.damsel.domain.CurrencyRef;
import com.rbkmoney.damsel.domain.Reference;
import com.rbkmoney.damsel.domain_config.Head;
import com.rbkmoney.damsel.domain_config.RepositoryClientSrv;
import com.rbkmoney.damsel.domain_config.VersionedObject;
import com.rbkmoney.dark.api.config.property.DominantProperties;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class DominantService {

    private final Cache<String, Currency> currencyCache;

    private final RepositoryClientSrv.Iface dominantClient;

    public DominantService(RepositoryClientSrv.Iface dominantClient,
                           DominantProperties dominantProperties) {
        this.dominantClient = dominantClient;
        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();
        cacheBuilder.expireAfterWrite(dominantProperties.getCacheExpire(), TimeUnit.MINUTES);
        if (dominantProperties.getCacheMaxSize() > 0) {
            cacheBuilder.maximumSize(dominantProperties.getCacheMaxSize());
        }
        this.currencyCache = cacheBuilder.build();
    }

    public Currency getCurrency(String symbolicCode) {
        return currencyCache.get(symbolicCode, this::getCurrencyFromDominant);
    }

    private Currency getCurrencyFromDominant(String symbolicCode) {
        try {
            final CurrencyRef currencyRef = new CurrencyRef(symbolicCode);
            final VersionedObject versionedObject = dominantClient.checkoutObject(
                    com.rbkmoney.damsel.domain_config.Reference.head(new Head()),
                    Reference.currency(currencyRef));
            return versionedObject.getObject().getCurrency().getData();
        } catch (TException e) {
            throw new CurrencyNotFound(symbolicCode);
        }
    }

}
