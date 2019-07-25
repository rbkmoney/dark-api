package com.rbkmoney.dark.api.converter;

import org.apache.thrift.TBase;

/**
 * Swag conversion to thrift
 * @param <S> the type of swag
 * @param <T> the type of thrift
 */
public interface SwagConverter<S, T> {

    S toSwag(T value, SwagConverterContext ctx);

}
