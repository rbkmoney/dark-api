package com.rbkmoney.dark.api.converter;

import com.rbkmoney.dark.api.converter.questionary.AdditionalInfoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Service
public class SwagConvertManager {

    private final Map<Class<?>, SwagConverter> swagConverterMap;

    private final Map<Class<?>, ThriftConverter> thriftConverterMap;

    @Autowired
    public SwagConvertManager(List<SwagConverter> swagConverterList,
                              List<ThriftConverter> thriftConverterList) {
        this.swagConverterMap = new HashMap<>();
        this.thriftConverterMap = new HashMap<>();
        initSwagConverterList(swagConverterList, thriftConverterList);
    }

    private void initSwagConverterList(List<SwagConverter> swagConverterList, List<ThriftConverter> thriftConverterList) {
        for (SwagConverter swagConverter : swagConverterList) {
            Class<?> swagTypeArgument = getSwagTypeArgument(swagConverter.getClass());
            swagConverterMap.put(swagTypeArgument, swagConverter);
        }
        for (ThriftConverter thriftConverter : thriftConverterList) {
            Class<?> typeArgument = getThriftTypeArgument(thriftConverter.getClass());
            thriftConverterMap.put(typeArgument, thriftConverter);
        }
    }

    public <T, S> S convertFromThrift(T thriftVal, Class<S> swagType) {
        SwagConverter swagConverter = swagConverterMap.get(swagType);
        if (swagConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + swagType.getName());
        }

        SwagConverterContext swagConverterContext = new SwagConverterContext(Collections.unmodifiableMap(swagConverterMap));

        return (S) swagConverter.toSwag(thriftVal, swagConverterContext);
    }

    public <T, S> T convertToThrift(S swagVal, Class<T> thriftType) {
        ThriftConverter thriftConverter = thriftConverterMap.get(thriftType);
        if (thriftConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + thriftType.getName());
        }

        ThriftConverterContext thriftConverterContext = new ThriftConverterContext(Collections.unmodifiableMap(thriftConverterMap));

        return (T) thriftConverter.toThrift(swagVal, thriftConverterContext);
    }

    private Class<?> getSwagTypeArgument(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        ParameterizedType swagParameterizedType = findConverterParamType(clazz, SwagConverter.class);
        return (Class<?>) swagParameterizedType.getActualTypeArguments()[0];
    }

    private Class<?> getThriftTypeArgument(Class<?> clazz) {
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        ParameterizedType thriftParameterizedType = findConverterParamType(clazz, ThriftConverter.class);
        return (Class<?>) thriftParameterizedType.getActualTypeArguments()[0];
    }

    private ParameterizedType findConverterParamType(Class converterImpl, Class converterType) {
        Type[] genericInterfaces = converterImpl.getGenericInterfaces();
        return Stream.of(genericInterfaces)
                .filter(type -> {
                    return ((ParameterizedType) type).getRawType() == converterType;
                })
                .map(type -> ((ParameterizedType) type))
                .findFirst()
                .orElseThrow(() -> {
                    return new RuntimeException("Not found" + "converterType" + "interface for: " + converterImpl.getName());
                });
    }

}
