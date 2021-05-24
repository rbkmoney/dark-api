package com.rbkmoney.dark.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rbkmoney.damsel.base.Content;
import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.merch_stat.*;
import com.rbkmoney.dark.api.converter.StatPaymentToPaymentSearchResultConverter;
import com.rbkmoney.dark.api.converter.StatRefundToRefundSearchResultConverter;
import com.rbkmoney.dark.api.magista.dsl.MstDsl;
import com.rbkmoney.swag.dark_api.model.EnrichedSearchResult;
import com.rbkmoney.swag.dark_api.model.InlineResponse200;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MagistaService {

    private final DarkMessiahStatisticsSrv.Iface magistaClient;

    public InlineResponse200 getPaymentsByQuery(String shopID,
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
                                                Long paymentAmount,
                                                String continuationToken)
            throws JsonProcessingException, TException {
        StatResponse statResponse = magistaClient.getByQuery(
                new StatRequest()
                        .setDsl(
                                MstDsl.createPaymentsRequest(
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
                        )
                        .setContinuationToken(continuationToken)
        );

        return fromStatResponse(statResponse);

    }

    public InlineResponse200 getRefundsByQuery(String shopID,
                                               String merchantId,
                                               OffsetDateTime fromTime,
                                               OffsetDateTime toTime,
                                               Integer limit,
                                               String invoiceID,
                                               String paymentID,
                                               String refundID,
                                               String refundStatus,
                                               String continuationToken)
            throws JsonProcessingException, TException {
        StatResponse statResponse = magistaClient.getByQuery(
                new StatRequest()
                        .setDsl(
                                MstDsl.createRefundsRequest(
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
                        )
                        .setContinuationToken(continuationToken)
        );

        return fromStatResponse(statResponse);
    }

    private InlineResponse200 fromStatResponse(StatResponse statResponse) {
        return new InlineResponse200()
                .continuationToken(statResponse.getContinuationToken())
                .result(
                        statResponse.getData().getEnrichedInvoices()
                                .stream()
                                .map(
                                        enrichedStatInvoice -> {
                                            Content invoiceContext = enrichedStatInvoice.getInvoice().getContext();

                                            List<StatRefund> refunds = enrichedStatInvoice.getRefunds();
                                            List<StatPayment> payments = enrichedStatInvoice.getPayments();
                                            return new EnrichedSearchResult()
                                                    .refund(refunds.isEmpty() ? null :
                                                            StatRefundToRefundSearchResultConverter
                                                                    .convert(refunds.get(0)))
                                                    .payment(StatPaymentToPaymentSearchResultConverter
                                                            .convert(payments.get(0), invoiceContext));
                                        }
                                )
                                .collect(Collectors.toList())
                );
    }
}
