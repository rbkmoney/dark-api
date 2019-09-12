package com.rbkmoney.dark.api.converter;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ThriftConverterContext {

    private final Map<Class<?>, ThriftConverter> converterMap;

    public <T, S> T convert(S swagVal, Class<T> thriftType) {
        ThriftConverter thriftConverter = converterMap.get(thriftType);
        if (thriftConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + thriftType.getSimpleName());
        }

        return (T) thriftConverter.toThrift(swagVal, this);
    }


}
