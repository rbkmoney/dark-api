package com.rbkmoney.dark.api.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SwagConvertManager {

    private final Map<Class<?>, SwagConverter> swagConverterMap;

    private final Map<Class<?>, Converter> simpleConverterMap;

    private final List<SwagConverter> swagConverterList;

    private final List<Converter> simpleConverterList;

    @Autowired
    public SwagConvertManager(List<SwagConverter> swagConverterList,
                              List<Converter> simpleConverterList) {
        this.swagConverterList = swagConverterList;
        this.simpleConverterList = simpleConverterList;
        this.swagConverterMap = new HashMap<>();
        this.simpleConverterMap = new HashMap<>();
        initSwagConverterList();
    }

    private void initSwagConverterList() {
        for (SwagConverter swagConverter : swagConverterList) {
            Class<?> swagTypeArgument = getSwagConverterTypeArgument(swagConverter.getClass());
            swagConverterMap.put(swagTypeArgument, swagConverter);
        }
        for (Converter simpleConverter : simpleConverterList) {
            Class<?> typeArgument = getConverterTypeArgument(simpleConverter.getClass());
            simpleConverterMap.put(typeArgument, simpleConverter);
        }
    }

    public <T, S> S convertFromThrift(T thriftVal, Class<S> swagType) {
        SwagConverter swagConverter = swagConverterMap.get(swagType);
        if (swagConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + swagType.getSimpleName());
        }

        SwagConverterContext swagConverterContext = new SwagConverterContext(Collections.unmodifiableMap(swagConverterMap));

        return (S) swagConverter.toSwag(thriftVal, swagConverterContext);
    }

    public <T, S> T convert(S value, Class<T> resultType) {
        Converter simpleConverter = simpleConverterMap.get(resultType);
        if (simpleConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + resultType.getSimpleName());
        }

        return (T) simpleConverter.convert(value);
    }

    private Class<?> getSwagConverterTypeArgument(Class<?> clazz) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    private Class<?> getConverterTypeArgument(Class<?> clazz) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        return (Class<?>) parameterizedType.getActualTypeArguments()[1];
    }

}
