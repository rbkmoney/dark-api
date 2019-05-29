package com.rbkmoney.dark.api.magista.dto.request;

import lombok.Data;

import java.time.OffsetDateTime;

/**
 * Created by inalarsanukaev on 20.09.17.
 */
@Data
public class PaymentsRequest {
    private Query query;

    @Data
    private class Query {
        private EnrichedPayments enrichedPayments;
    }

    @Data
    private class EnrichedPayments {
        private String shopID;
        private OffsetDateTime fromTime;
        private OffsetDateTime toTime;
        private Integer limit;
        private String paymentStatus;
        private String paymentFlow;
        private String paymentMethod;
        private String paymentTerminalProvider;
        private String invoiceID;
        private String paymentID;
        private String payerEmail;
        private String payerIP;
        private String payerFingerprint;
        private String customerID;
        private String bin;
        private String lastDigits;
        private String bankCardTokenProvider;
        private String bankCardPaymentSystem;
        private Long paymentAmount;
    }

    public PaymentsRequest(String shopID,
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
                           Long paymentAmount) {
        query = new Query();
        query.enrichedPayments = new EnrichedPayments();
        query.enrichedPayments.shopID = shopID;
        query.enrichedPayments.fromTime = fromTime;
        query.enrichedPayments.toTime = toTime;
        query.enrichedPayments.limit = limit;
        query.enrichedPayments.paymentStatus = paymentStatus;
        query.enrichedPayments.paymentFlow = paymentFlow;
        query.enrichedPayments.paymentMethod = paymentMethod;
        query.enrichedPayments.paymentTerminalProvider = paymentTerminalProvider;
        query.enrichedPayments.invoiceID = invoiceID;
        query.enrichedPayments.paymentID = paymentID;
        query.enrichedPayments.payerEmail = payerEmail;
        query.enrichedPayments.payerIP = payerIP;
        query.enrichedPayments.payerFingerprint = payerFingerprint;
        query.enrichedPayments.customerID = customerID;
        query.enrichedPayments.bin = bin;
        query.enrichedPayments.lastDigits = lastDigits;
        query.enrichedPayments.bankCardTokenProvider = bankCardTokenProvider;
        query.enrichedPayments.bankCardPaymentSystem = bankCardPaymentSystem;
        query.enrichedPayments.paymentAmount = paymentAmount;

    }
}
