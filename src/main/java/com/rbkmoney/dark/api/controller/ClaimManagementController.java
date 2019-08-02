package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.service.ClaimManagementService;
import com.rbkmoney.swag.claim_management.api.ProcessingApi;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.InlineResponse200;
import com.rbkmoney.swag.claim_management.model.Modification;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@PreAuthorize("hasAuthority('invoices:read')")
@RequiredArgsConstructor
public class ClaimManagementController implements ProcessingApi {

    private final ClaimManagementService claimManagementService;

    @Override
    public ResponseEntity<Claim> createClaim(@NotNull @Size(min = 1, max = 40) String requestId,
                                             @NotNull ClaimChangeset changeset,
                                             @Size(min = 1, max = 40) String deadline) {
        Claim claim = claimManagementService.createClaim(requestId, changeset, deadline);
        log.info("Claim for request id {} created", requestId);
        return ResponseEntity.ok(claim);
    }

    @Override
    public ResponseEntity<Claim> getClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                              @NotNull Long claimId,
                                              @Size(min = 1, max = 40) String deadline) {
        Claim claim = claimManagementService.getClaimByID(requestId, claimId, deadline);
        log.info("Got a claim for request id {} and claim id {}", requestId, claimId);
        return ResponseEntity.ok(claim);
    }

    @Override
    public ResponseEntity<Void> revokeClaimByID(@ApiParam(value = "Уникальный идентификатор запроса к системе", required = true) @RequestHeader(value = "X-Request-ID", required = true) String xRequestID, @ApiParam(value = "Идентификатор заявки", required = true) @PathVariable("claimID") Long claimID, @NotNull @ApiParam(value = "Версия заявки", required = true) @Valid @RequestParam(value = "claimRevision", required = true) Integer claimRevision, @ApiParam(value = "Максимальное время обработки запроса") @RequestHeader(value = "X-Request-Deadline", required = false) String xRequestDeadline, @ApiParam(value = "") @Valid @RequestBody String reason) {
        if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ProcessingApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<InlineResponse200> searchClaims(@ApiParam(value = "Уникальный идентификатор запроса к системе", required = true) @RequestHeader(value = "X-Request-ID", required = true) String xRequestID, @NotNull @Min(1) @Max(1000) @ApiParam(value = "Лимит выборки", required = true) @Valid @RequestParam(value = "limit", required = true) Integer limit, @ApiParam(value = "Максимальное время обработки запроса") @RequestHeader(value = "X-Request-Deadline", required = false) String xRequestDeadline, @ApiParam(value = "Токен, сигнализирующий о том, что в ответе передана только часть данных. Для получения следующей части нужно повторно обратиться к сервису, указав тот-же набор условий и полученый токен. Если токена нет, получена последняя часть данных. ") @Valid @RequestParam(value = "continuationToken", required = false) String continuationToken, @ApiParam(value = "Статус заявки для поиска", allowableValues = "pending, review, pendingAcceptance, accepted, denied, revoked") @Valid @RequestParam(value = "claimStatuses", required = false) List<String> claimStatuses) {
        if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
            if (getAcceptHeader().get().contains("application/json")) {
                try {
                    return new ResponseEntity<>(getObjectMapper().get().readValue("{  \"result\" : [ {    \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\",    \"metadata\" : \"{}\",    \"id\" : 0,    \"status\" : \"status\",    \"changeset\" : \"\",    \"revision\" : 6,    \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\"  }, {    \"createdAt\" : \"2000-01-23T04:56:07.000+00:00\",    \"metadata\" : \"{}\",    \"id\" : 0,    \"status\" : \"status\",    \"changeset\" : \"\",    \"revision\" : 6,    \"updatedAt\" : \"2000-01-23T04:56:07.000+00:00\"  } ],  \"continuationToken\" : { }}", InlineResponse200.class), HttpStatus.NOT_IMPLEMENTED);
                } catch (IOException e) {
                    log.error("Couldn't serialize response for content type application/json", e);
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ProcessingApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<Void> updateClaimByID(@ApiParam(value = "Уникальный идентификатор запроса к системе", required = true) @RequestHeader(value = "X-Request-ID", required = true) String xRequestID, @ApiParam(value = "Идентификатор заявки", required = true) @PathVariable("claimID") Long claimID, @NotNull @ApiParam(value = "Версия заявки", required = true) @Valid @RequestParam(value = "claimRevision", required = true) Integer claimRevision, @ApiParam(value = "Максимальное время обработки запроса") @RequestHeader(value = "X-Request-Deadline", required = false) String xRequestDeadline, @ApiParam(value = "") @Valid @RequestBody Modification changeset) {
        if (getObjectMapper().isPresent() && getAcceptHeader().isPresent()) {
        } else {
            log.warn("ObjectMapper or HttpServletRequest not configured in default ProcessingApi interface so no example is generated");
        }
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
