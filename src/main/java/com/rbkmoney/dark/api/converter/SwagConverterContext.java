package com.rbkmoney.dark.api.converter;

import java.util.Map;

public class SwagConverterContext {

    private Map<Class<?>, SwagConverter> converterMap;

    public SwagConverterContext(Map<Class<?>, SwagConverter> converterMap) {
        this.converterMap = converterMap;
    }

    public <T, S> S convert(T thriftVal, Class<S> swagType) {
        SwagConverter swagConverter = converterMap.get(swagType);
        if (swagConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + swagType.getSimpleName());
        }

        return (S) swagConverter.toSwag(thriftVal, this);
    }

}
