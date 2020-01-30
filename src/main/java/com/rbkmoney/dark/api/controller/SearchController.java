package com.rbkmoney.dark.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rbkmoney.damsel.base.InvalidRequest;
import com.rbkmoney.damsel.merch_stat.BadToken;
import com.rbkmoney.dark.api.exceptions.DeadlineException;
import com.rbkmoney.dark.api.exceptions.client.badrequest.BadRequestException;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.dark.api.service.MagistaService;
import com.rbkmoney.dark.api.service.PartyManagementService;
import com.rbkmoney.swag.dark_api.api.SearchApi;
import com.rbkmoney.swag.dark_api.model.InlineResponse200;
import com.rbkmoney.swag.dark_api.model.InlineResponse400;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.OffsetDateTime;

import static com.rbkmoney.dark.api.util.DeadlineUtils.checkDeadline;
import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

@RestController
@PreAuthorize("hasAuthority('invoices:read')")
@RequiredArgsConstructor
public class SearchController implements SearchApi {

    private final MagistaService magistaService;
    private final PartyManagementService partyManagementService;
    private final KeycloakService keycloakService;

    @Override
    public ResponseEntity<InlineResponse200> searchPayments(String xRequestId,
                                                            @Size(min = 1, max = 40) String shopID,
                                                            @NotNull @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fromTime,
                                                            @NotNull @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime toTime,
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
        try {
            String partyId = keycloakService.getPartyId();

            log.info("Handling request for /search/payments, xRequestId={}, shopID={}, partyId={}", xRequestId, shopID, partyId);

            partyManagementService.checkStatus(xRequestId);

            checkDeadline(xRequestDeadline, xRequestId);

            InlineResponse200 response = magistaService.getPaymentsByQuery(
                    shopID,
                    partyId,
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
                    continuationToken
            );

            return ResponseEntity.ok(response);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidRequest ex) {
            String msg = String.format("Invalid request, xRequestId=%s, errors=%s", xRequestId, String.join(", ", ex.getErrors()));
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.INVALIDREQUEST)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (BadToken ex) {
            String msg = String.format("Bad token, xRequestId=%s, reason='%s'", xRequestId, ex.getReason());
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.BADTOKEN)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (JsonProcessingException | TException ex) {
            throw darkApi5xxException("magista", "searchPayments", xRequestId, ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse200> searchRefunds(String xRequestId,
                                                           @Size(min = 1, max = 40) String shopID,
                                                           @NotNull @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fromTime,
                                                           @NotNull @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime toTime,
                                                           @NotNull @Min(1L) @Max(1000L) @Valid Integer limit,
                                                           String xRequestDeadline,
                                                           @Size(min = 1, max = 40) @Valid String invoiceID,
                                                           @Size(min = 1, max = 40) @Valid String paymentID,
                                                           @Size(min = 1, max = 40) @Valid String refundID,
                                                           @Valid String refundStatus,
                                                           @Valid String continuationToken) {
        try {
            String partyId = keycloakService.getPartyId();

            log.info("Handling request for /search/refunds, xRequestId={}, shopID={}, partyId={}", xRequestId, shopID, partyId);

            partyManagementService.checkStatus(xRequestId);

            checkDeadline(xRequestDeadline, xRequestId);

            InlineResponse200 response = magistaService.getRefundsByQuery(
                    shopID,
                    partyId,
                    fromTime,
                    toTime,
                    limit,
                    invoiceID,
                    paymentID,
                    refundID,
                    refundStatus,
                    continuationToken
            );

            return ResponseEntity.ok(response);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidRequest ex) {
            String msg = String.format("Invalid request, xRequestId=%s, errors=%s", xRequestId, String.join(", ", ex.getErrors()));
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.INVALIDREQUEST)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (BadToken ex) {
            String msg = String.format("Bad token, xRequestId=%s, reason='%s'", xRequestId, ex.getReason());
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.BADTOKEN)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (JsonProcessingException | TException ex) {
            throw darkApi5xxException("magista", "searchRefunds", xRequestId, ex);
        }
    }

    private BadRequestException badRequestException(String msg, Throwable cause, Object response) {
        return new BadRequestException(msg, cause, response);
    }
}
