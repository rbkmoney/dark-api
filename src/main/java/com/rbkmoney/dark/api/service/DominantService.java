package com.rbkmoney.dark.api.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.rbkmoney.damsel.domain.Currency;
import com.rbkmoney.damsel.domain.CurrencyRef;
import com.rbkmoney.damsel.domain.Reference;
import com.rbkmoney.damsel.domain_config.Head;
import com.rbkmoney.damsel.domain_config.RepositoryClientSrv;
import com.rbkmoney.damsel.domain_config.VersionedObject;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DominantService {

    private final Cache<String, Currency> dominantCache;

    private final RepositoryClientSrv.Iface dominantClient;

    public Currency getCurrency(String symbolicCode) {
        return dominantCache.get(symbolicCode, this::getCurrencyFromDominant);
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
