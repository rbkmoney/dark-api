package com.rbkmoney.dark.api.converter;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class SwagConverterContext {

    private final Map<Class<?>, SwagConverter> converterMap;

    public <T, S> S convert(T thriftVal, Class<S> swagType) {
        SwagConverter swagConverter = converterMap.get(swagType);
        if (swagConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + swagType.getSimpleName());
        }

        return (S) swagConverter.toSwag(thriftVal, this);
    }

}
