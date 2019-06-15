package com.rbkmoney.dark.api.magista.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    public static final String PAYMENT_ID_PARAM = "payment_id";
    public static final String PAYMENT_STATUS_PARAM = "payment_status";
    public static final String PAYMENT_EMAIL_PARAM = "payment_email";
    public static final String PAYMENT_IP_PARAM = "payment_ip";
    public static final String PAYMENT_FINGERPRINT_PARAM = "payment_fingerprint";
    public static final String PAYMENT_BANK_CARD_BIN_PARAM = "payment_bin";
    public static final String PAYMENT_BANK_CARD_LAST_DIGITS_PARAM = "payment_last_digits";
    public static final String PAYMENT_BANK_CARD_PAYMENT_SYSTEM_PARAM = "payment_system";
    public static final String PAYMENT_BANK_CARD_TOKEN_PROVIDER_PARAM = "payment_token_provider";
    public static final String PAYMENT_METHOD_PARAM = "payment_method";
    public static final String PAYMENT_TERMINAL_PROVIDER_PARAM = "payment_terminal_provider";
    public static final String PAYMENT_AMOUNT_PARAM = "payment_amount";
    public static final String PAYMENT_FLOW_PARAM = "payment_flow";
    public static final String PAYMENT_DOMAIN_REVISION_PARAM = "payment_domain_revision";
    public static final String FROM_PAYMENT_DOMAIN_REVISION_PARAM = "from_payment_domain_revision";
    public static final String TO_PAYMENT_DOMAIN_REVISION_PARAM = "to_payment_domain_revision";
    public static final String PAYMENT_CUSTOMER_ID_PARAM = "payment_customer_id";

    @Data
    private class EnrichedPayments {
        private String shopID;
        private String merchantId;
        private OffsetDateTime fromTime;
        private OffsetDateTime toTime;
        private Integer limit;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentStatus;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentFlow;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentMethod;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentTerminalProvider;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String invoiceID;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentID;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String payerEmail;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String payerIP;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String payerFingerprint;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentCustomerID;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentBin;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentLastDigits;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentTokenProvider;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentSystem;
        @JsonInclude(JsonInclude.Include.NON_NULL) private Long paymentAmount;
    }

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
        query.enrichedPayments.paymentCustomerID = customerID;
        query.enrichedPayments.paymentBin = bin;
        query.enrichedPayments.paymentLastDigits = lastDigits;
        query.enrichedPayments.paymentTokenProvider = bankCardTokenProvider;
        query.enrichedPayments.paymentSystem = bankCardPaymentSystem;
        query.enrichedPayments.paymentAmount = paymentAmount;

    }
}
