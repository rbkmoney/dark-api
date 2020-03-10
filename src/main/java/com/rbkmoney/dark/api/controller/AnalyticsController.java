package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.exceptions.DeadlineException;
import com.rbkmoney.dark.api.exceptions.client.badrequest.BadRequestException;
import com.rbkmoney.dark.api.service.AnalyticsService;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.swag.analytics.api.AnalyticsApi;
import com.rbkmoney.swag.analytics.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AnalyticsController implements AnalyticsApi {

    private final AnalyticsService analyticsService;
    private final KeycloakService keycloakService;

    @Override
    public ResponseEntity<InlineResponse200> getAveragePayment(String xRequestID,
                                                               @NotNull @Valid OffsetDateTime fromTime,
                                                               @NotNull @Valid OffsetDateTime toTime,
                                                               String xRequestDeadline,
                                                               @Size(min = 1, max = 40) @Valid String shopID) {
        try {
            log.info("Process 'getAveragePayment' get started, xRequestId={}", xRequestID);

            InlineResponse200 result = analyticsService.getAveragePayment(fromTime, toTime, keycloakService.getPartyId(), shopID);

            log.info("AveragePayment result, xRequestId={}, result={}", xRequestID, result);
            return ResponseEntity.ok(result);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("analytics", "getAveragePayment", xRequestID, ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse200> getCurrentBalances(String xRequestID,
                                                                String xRequestDeadline,
                                                                @Size(min = 1, max = 40)
                                                                @Valid String shopID) {
        return null;
    }

    @Override
    public ResponseEntity<InlineResponse200> getPaymentsAmount(String xRequestID,
                                                               @NotNull @Valid OffsetDateTime fromTime,
                                                               @NotNull @Valid OffsetDateTime toTime,
                                                               String xRequestDeadline,
                                                               @Size(min = 1, max = 40) @Valid String shopID) {
        try {
            log.info("Process 'getPaymentsAmount' get started, xRequestId={}", xRequestID);

            InlineResponse200 result = analyticsService.getPaymentsAmount(fromTime, toTime, keycloakService.getPartyId(), shopID);

            log.info("PaymentAmount result, xRequestId={}, result={}", xRequestID, result);
            return ResponseEntity.ok(result);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("analytics", "getPaymentsAmount", xRequestID, ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse2002> getPaymentsCount(String xRequestID,
                                                               @NotNull @Valid OffsetDateTime fromTime,
                                                               @NotNull @Valid OffsetDateTime toTime,
                                                               String xRequestDeadline,
                                                               @Size(min = 1, max = 40) @Valid String shopID) {
        try {
            log.info("Process 'getPaymentsCount' get started, xRequestId={}", xRequestID);

            InlineResponse2002 result = analyticsService.getPaymentsCount(fromTime, toTime, keycloakService.getPartyId(), shopID);

            log.info("PaymentsCount result, xRequestId={}, result={}", xRequestID, result);
            return ResponseEntity.ok(result);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("analytics", "getPaymentsCount", xRequestID, ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse2003> getPaymentsErrorDistribution(String xRequestID,
                                                                           @NotNull @Valid OffsetDateTime fromTime,
                                                                           @NotNull @Valid OffsetDateTime toTime,
                                                                           String xRequestDeadline,
                                                                           @Size(min = 1, max = 40) @Valid String shopID) {
        try {
            log.info("Process 'getPaymentsErrorDistribution' get started, xRequestId={}", xRequestID);

            InlineResponse2003 result = analyticsService.getPaymentsErrorDistribution(fromTime, toTime, keycloakService.getPartyId(), shopID);

            log.info("PaymentsErrorDistribution result, xRequestId={}, result={}", xRequestID, result);
            return ResponseEntity.ok(result);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("analytics", "getPaymentsErrorDistribution", xRequestID, ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse2004> getPaymentsSplitAmount(String xRequestID,
                                                                     @NotNull @Valid OffsetDateTime fromTime,
                                                                     @NotNull @Valid OffsetDateTime toTime,
                                                                     @NotNull @Valid String splitUnit,
                                                                     String xRequestDeadline,
                                                                     @Size(min = 1, max = 40) @Valid String shopID) {
        try {
            log.info("Process 'getPaymentsSplitAmount' get started, xRequestId={}", xRequestID);

            InlineResponse2004 result = analyticsService.getPaymentsSplitAmount(fromTime, toTime, splitUnit,
                    keycloakService.getPartyId(), shopID);

            log.info("PaymentsSplitAmount result, xRequestId={}, result={}", xRequestID, result);
            return ResponseEntity.ok(result);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("analytics", "getPaymentsSplitAmount", xRequestID, ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse2005> getPaymentsSplitCount(String xRequestID,
                                                                    @NotNull @Valid OffsetDateTime fromTime,
                                                                    @NotNull @Valid OffsetDateTime toTime,
                                                                    @NotNull @Valid String splitUnit,
                                                                    String xRequestDeadline,
                                                                    @Size(min = 1, max = 40) @Valid String shopID) {
        try {
            log.info("Process 'getPaymentsSplitCount' get started, xRequestId={}", xRequestID);

            InlineResponse2005 result = analyticsService.getPaymentsSplitCount(fromTime, toTime, splitUnit,
                    keycloakService.getPartyId(), shopID);

            log.info("PaymentsSplitCount result, xRequestId={}, result={}", xRequestID, result);
            return ResponseEntity.ok(result);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("analytics", "getPaymentsSplitCount", xRequestID, ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse2001> getPaymentsToolDistribution(String xRequestID,
                                                                          @NotNull @Valid OffsetDateTime fromTime,
                                                                          @NotNull @Valid OffsetDateTime toTime,
                                                                          String xRequestDeadline,
                                                                          @Size(min = 1, max = 40) @Valid String shopID) {
        try {
            log.info("Process 'getPaymentsToolDistribution' get started, xRequestId={}", xRequestID);

            InlineResponse2001 result = analyticsService.getPaymentsToolDistribution(fromTime, toTime,
                    keycloakService.getPartyId(), shopID);

            log.info("PaymentsToolDistribution result, xRequestId={}, result={}", xRequestID, result);
            return ResponseEntity.ok(result);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("analytics", "getPaymentsToolDistribution", xRequestID, ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse200> getRefundsAmount(String xRequestID,
                                                              @NotNull @Valid OffsetDateTime fromTime,
                                                              @NotNull @Valid OffsetDateTime toTime,
                                                              String xRequestDeadline,
                                                              @Size(min = 1, max = 40) @Valid String shopID) {
        try {
            log.info("Process 'getRefundsAmount' get started, xRequestId={}", xRequestID);

            InlineResponse200 result = analyticsService.getRefundsAmount(fromTime, toTime,
                    keycloakService.getPartyId(), shopID);

            log.info("RefundsAmount result, xRequestId={}, result={}", xRequestID, result);
            return ResponseEntity.ok(result);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("analytics", "getRefundsAmount", xRequestID, ex);
        }
    }
}
