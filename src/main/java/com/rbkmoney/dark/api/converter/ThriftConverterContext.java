package com.rbkmoney.dark.api.converter;

import java.util.Map;

public class ThriftConverterContext {

    private Map<Class<?>, ThriftConverter> converterMap;

    public ThriftConverterContext(Map<Class<?>, ThriftConverter> converterMap) {
        this.converterMap = converterMap;
    }

    public <T, S> S convert(T thriftVal, Class<S> swagType) {
        ThriftConverter thriftConverter = converterMap.get(swagType);
        if (thriftConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + swagType.getSimpleName());
        }

        return (S) thriftConverter.toThrift(thriftVal, this);
    }


}
