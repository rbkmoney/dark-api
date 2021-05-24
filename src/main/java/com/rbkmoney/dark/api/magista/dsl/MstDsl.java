package com.rbkmoney.dark.api.magista.dsl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.rbkmoney.dark.api.magista.dto.request.PaymentsRequest;
import com.rbkmoney.dark.api.magista.dto.request.RefundsRequest;

import java.time.OffsetDateTime;

public class MstDsl {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    public static String createPaymentsRequest(String shopID,
                                               String merchantId,
                                               OffsetDateTime fromTime,
                                               OffsetDateTime toTime,
                                               Integer limit,
                                               String paymentStatus,
                                               String paymentFlow,
                                               String paymentMethod,
                                               String paymentTerminalProvider,
                                               String invoiceID,
                                               String paymentID,
                                               String payerEmail,
                                               String payerIP,
                                               String payerFingerprint,
                                               String customerID,
                                               String bin,
                                               String lastDigits,
                                               String bankCardTokenProvider,
                                               String bankCardPaymentSystem,
                                               Long paymentAmount) throws JsonProcessingException {
        return objectMapper.writeValueAsString(
                new PaymentsRequest(
                        shopID,
                        merchantId,
                        fromTime,
                        toTime,
                        limit,
                        paymentStatus,
                        paymentFlow,
                        paymentMethod,
                        paymentTerminalProvider,
                        invoiceID,
                        paymentID,
                        payerEmail,
                        payerIP,
                        payerFingerprint,
                        customerID,
                        bin,
                        lastDigits,
                        bankCardTokenProvider,
                        bankCardPaymentSystem,
                        paymentAmount
                )
        );
    }

    public static String createRefundsRequest(String shopID,
                                              String merchantId,
                                              OffsetDateTime fromTime,
                                              OffsetDateTime toTime,
                                              Integer limit,
                                              String invoiceID,
                                              String paymentID,
                                              String refundID,
                                              String refundStatus) throws JsonProcessingException {
        return objectMapper.writeValueAsString(
                new RefundsRequest(
                        shopID,
                        merchantId,
                        fromTime,
                        toTime,
                        limit,
                        invoiceID,
                        paymentID,
                        refundID,
                        refundStatus
                )
        );
    }
}
