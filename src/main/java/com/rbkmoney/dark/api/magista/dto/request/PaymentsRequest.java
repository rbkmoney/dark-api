package com.rbkmoney.dark.api.magista.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.Instant;
import java.time.OffsetDateTime;

/**
 * Created by inalarsanukaev on 20.09.17.
 */
@Data
public class PaymentsRequest {
    private Query query;

    public PaymentsRequest(String shopID,
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
                           Long paymentAmount) {
        query = new Query();
        query.enrichedPayments = new EnrichedPayments();
        query.enrichedPayments.shopID = shopID;
        query.enrichedPayments.merchantId = merchantId;
        query.enrichedPayments.fromTime = fromTime.toInstant();
        query.enrichedPayments.toTime = toTime.toInstant();
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
        query.enrichedPayments.paymentCustomerID = customerID;
        query.enrichedPayments.paymentBin = bin;
        query.enrichedPayments.paymentLastDigits = lastDigits;
        query.enrichedPayments.paymentTokenProvider = bankCardTokenProvider;
        query.enrichedPayments.paymentSystem = bankCardPaymentSystem;
        query.enrichedPayments.paymentAmount = paymentAmount;

    }

    @Data
    private class Query {
        private EnrichedPayments enrichedPayments;
    }

    @Data
    private class EnrichedPayments {
        private String shopID;
        private String merchantId;
        private Instant fromTime;
        private Instant toTime;
        private Integer limit;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentStatus;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentFlow;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentMethod;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentTerminalProvider;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String invoiceID;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentID;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String payerEmail;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String payerIP;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String payerFingerprint;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentCustomerID;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentBin;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentLastDigits;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentTokenProvider;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String paymentSystem;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Long paymentAmount;
    }
}
