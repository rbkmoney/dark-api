package com.rbkmoney.dark.api.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConverterUtils {

    public static boolean safeSetValue(Boolean value) {
        return value != null && value;
    }

    public static int safeSetValue(Integer value) {
        return value == null ? 0 : value;
    }

}
