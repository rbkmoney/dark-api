package com.rbkmoney.dark.api.magista.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.OffsetDateTime;

/**
 * Created by inalarsanukaev on 20.09.17.
 */
@Data
public class RefundsRequest {
    private Query query;

    @Data
    private class Query {
        private EnrichedRefunds enrichedRefunds;
    }

    @Data
    private class EnrichedRefunds {
        private String shopID;
        private String merchantId;
        private OffsetDateTime fromTime;
        private OffsetDateTime toTime;
        private Integer limit;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String invoiceID;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String paymentID;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String refundID;
        @JsonInclude(JsonInclude.Include.NON_NULL) private String refundStatus;
    }

    public RefundsRequest(String shopID,
                          String merchantId,
                          OffsetDateTime fromTime,
                          OffsetDateTime toTime,
                          Integer limit,
                          String invoiceID,
                          String paymentID,
                          String refundID,
                          String refundStatus) {
        query = new Query();
        query.enrichedRefunds = new EnrichedRefunds();
        query.enrichedRefunds.shopID = shopID;
        query.enrichedRefunds.merchantId = merchantId;
        query.enrichedRefunds.fromTime = fromTime;
        query.enrichedRefunds.toTime = toTime;
        query.enrichedRefunds.limit = limit;
        query.enrichedRefunds.invoiceID = invoiceID;
        query.enrichedRefunds.paymentID = paymentID;
        query.enrichedRefunds.refundID = refundID;
        query.enrichedRefunds.refundStatus = refundStatus;
    }
}