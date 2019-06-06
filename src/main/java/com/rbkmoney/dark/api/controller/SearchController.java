package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.service.MagistaService;
import com.rbkmoney.swag.dark_api.api.SearchApi;
import com.rbkmoney.swag.dark_api.model.InlineResponse200;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.OffsetDateTime;

@Service
@RequestMapping(name = "/search")
@RequiredArgsConstructor
public class SearchController implements SearchApi {

    private final MagistaService magistaService;

    @RequestMapping(value = "/payments",
            produces = {"application/json; charset=utf-8"},
            consumes = {"application/json; charset=utf-8"},
            method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('invoices:read')")
    @Override
    public ResponseEntity<InlineResponse200> searchPayments(String xRequestID,
                                                            @Size(min = 1, max = 40) String shopID,
                                                            @NotNull @Valid OffsetDateTime fromTime,
                                                            @NotNull @Valid OffsetDateTime toTime,
                                                            @NotNull @Min(1L) @Max(1000L) @Valid Integer limit,
                                                            String xRequestDeadline,
                                                            @Valid String paymentStatus,
                                                            @Valid String paymentFlow,
                                                            @Valid String paymentMethod,
                                                            @Valid String paymentTerminalProvider,
                                                            @Size(min = 1, max = 40) @Valid String invoiceID,
                                                            @Size(min = 1, max = 40) @Valid String paymentID,
                                                            @Size(max = 100) @Valid String payerEmail,
                                                            @Size(max = 45) @Valid String payerIP,
                                                            @Size(max = 1000) @Valid String payerFingerprint,
                                                            @Size(min = 1, max = 40) @Valid String customerID,
                                                            @Pattern(regexp = "^\\d{6,8}$") @Valid String bin,
                                                            @Pattern(regexp = "^\\d{2,4}$") @Valid String lastDigits,
                                                            @Valid String bankCardTokenProvider,
                                                            @Valid String bankCardPaymentSystem,
                                                            @Min(1L) @Valid Long paymentAmount,
                                                            @Valid String continuationToken) {
        InlineResponse200 response = magistaService.getPaymentsByQuery(shopID,
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
                paymentAmount,
                continuationToken);
        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/refunds",
            produces = {"application/json; charset=utf-8"},
            consumes = {"application/json; charset=utf-8"},
            method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('invoices:read')")
    @Override
    public ResponseEntity<InlineResponse200> searchRefunds(String xRequestID,
                                                           @Size(min = 1, max = 40) String shopID,
                                                           @NotNull @Valid OffsetDateTime fromTime,
                                                           @NotNull @Valid OffsetDateTime toTime,
                                                           @NotNull @Min(1L) @Max(1000L) @Valid Integer limit,
                                                           String xRequestDeadline,
                                                           @Size(min = 1, max = 40) @Valid String invoiceID,
                                                           @Size(min = 1, max = 40) @Valid String paymentID,
                                                           @Size(min = 1, max = 40) @Valid String refundID,
                                                           @Valid String refundStatus,
                                                           @Valid String continuationToken) {
        InlineResponse200 response = magistaService.getRefundsByQuery(shopID,
                fromTime,
                toTime,
                limit,
                invoiceID,
                paymentID,
                refundID,
                refundStatus,
                continuationToken);
        return ResponseEntity.ok(response);
    }
}
