package com.rbkmoney.dark.api.util;

import com.rbkmoney.dark.api.exceptions.server.DarkApi5xxException;
import com.rbkmoney.woody.api.flow.WFlow;
import com.rbkmoney.woody.api.flow.concurrent.WCallable;
import com.rbkmoney.woody.api.trace.ContextUtils;
import org.apache.thrift.TException;

import java.time.Instant;
import java.util.concurrent.Callable;

public class ThriftClientUtils {

    public static <T> T callServiceFork(String apiName, String methodName, String xRequestId, String xRequestDeadline, Callable<T> callable) throws TException {
        WCallable<T> wCallable = new WFlow()
                .createServiceFork(
                        () -> {
                            ContextUtils.setDeadline(Instant.parse(xRequestDeadline));
                            return callable.call();
                        }
                );
        try {
            return wCallable.call();
        } catch (TException ex) {
            throw ex;
        } catch (Exception ex) {
            throw darkApi5xxException(apiName, methodName, xRequestId, ex);
        }
    }

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
