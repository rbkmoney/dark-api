package com.rbkmoney.dark.api.util;

import com.rbkmoney.dark.api.exceptions.server.DarkApi5xxException;
import org.apache.thrift.TException;

public class ExceptionUtils {

    public static DarkApi5xxException darkApi5xxException(String apiName, String methodName, String xRequestId, Exception ex) {
        return new DarkApi5xxException(
                String.format("Some Exception while requesting api='%s', method='%s', xRequestId=%s", apiName, methodName, xRequestId),
                ex
        );
    }

    public static DarkApi5xxException darkApi5xxException(String apiName, String methodName, String xRequestId, TException ex) {
        return new DarkApi5xxException(
                String.format("Some TException while requesting api='%s', method='%s', xRequestId=%s", apiName, methodName, xRequestId),
                ex
        );
    }
}
