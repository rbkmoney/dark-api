package com.rbkmoney.dark.api.converter;

/**
 * Thrift conversion to swag
 *
 * @param <T> the type of thrift
 * @param <S> the type of swag
 */
public interface ThriftConverter<T, S> {

    T toThrift(S value);

}
