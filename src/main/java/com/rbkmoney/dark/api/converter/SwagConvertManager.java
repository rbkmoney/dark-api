package com.rbkmoney.dark.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SwagConvertManager {

    private final Map<Class<?>, SwagConverter> swagConverterMap;

    private final List<SwagConverter> swagConverterList;

    @Autowired
    public SwagConvertManager(List<SwagConverter> swagConverterList) {
        this.swagConverterList = swagConverterList;
        this.swagConverterMap = new ConcurrentHashMap<>();
        initConverterList();
    }

    private void initConverterList() {
        for (SwagConverter swagConverter : swagConverterList) {
            Class<? extends SwagConverter> clazz = swagConverter.getClass();
            ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
            Class<?> swagTypeArgument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
            swagConverterMap.put(swagTypeArgument, swagConverter);
        }
    }

    public <T, S> S convert(T thriftVal, Class<S> swagType) {
        SwagConverter swagConverter = swagConverterMap.get(swagType);
        if (swagConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + swagType.getSimpleName());
        }

        SwagConverterContext swagConverterContext = new SwagConverterContext(Collections.unmodifiableMap(swagConverterMap));

        return (S) swagConverter.toSwag(thriftVal, swagConverterContext);
    }

}
