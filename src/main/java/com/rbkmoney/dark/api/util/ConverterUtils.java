package com.rbkmoney.dark.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConverterUtils {

    public static boolean isSetValue(Boolean value) {
        return value == null ? false : value;
    }

}
