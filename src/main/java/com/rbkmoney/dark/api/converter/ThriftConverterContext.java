package com.rbkmoney.dark.api.converter;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ThriftConverterContext {

    private final Map<Class<?>, ThriftConverter> converterMap;

    public <T, S> S convert(T thriftVal, Class<S> swagType) {
        ThriftConverter thriftConverter = converterMap.get(swagType);
        if (thriftConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + swagType.getSimpleName());
        }

        return (S) thriftConverter.toThrift(thriftVal, this);
    }


}
