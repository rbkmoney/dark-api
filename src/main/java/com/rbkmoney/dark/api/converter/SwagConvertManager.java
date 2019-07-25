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

    private final Map<Class<?>, ThriftConverter> thriftConverterMap;

    private final List<SwagConverter> swagConverterList;

    private final List<ThriftConverter> thriftConverterList;

    @Autowired
    public SwagConvertManager(List<SwagConverter> swagConverterList,
                              List<ThriftConverter> thriftConverterList) {
        this.swagConverterList = swagConverterList;
        this.thriftConverterList = thriftConverterList;
        this.swagConverterMap = new HashMap<>();
        this.thriftConverterMap = new HashMap<>();
        initSwagConverterList();
    }

    private void initSwagConverterList() {
        for (SwagConverter swagConverter : swagConverterList) {
            Class<?> swagTypeArgument = getTypeArgument(swagConverter.getClass());
            swagConverterMap.put(swagTypeArgument, swagConverter);
        }
        for (ThriftConverter thriftConverter : thriftConverterList) {
            Class<?> typeArgument = getTypeArgument(thriftConverter.getClass());
            thriftConverterMap.put(typeArgument, thriftConverter);
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

    public <T, S> T convertToThrift(S swagVal, Class<T> resultType) {
        ThriftConverter thriftConverter = thriftConverterMap.get(resultType);
        if (thriftConverter == null) {
            throw new IllegalArgumentException("Unregistered converter type: " + resultType.getSimpleName());
        }

        ThriftConverterContext thriftConverterContext = new ThriftConverterContext(Collections.unmodifiableMap(thriftConverterMap));

        return (T) thriftConverter.toThrift(swagVal, thriftConverterContext);
    }

    private Class<?> getTypeArgument(Class<?> clazz) {
        ParameterizedType parameterizedType = (ParameterizedType) clazz.getGenericInterfaces()[0];
        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

}
