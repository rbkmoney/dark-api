package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.service.ClaimManagementService;
import com.rbkmoney.swag.claim_management.api.ProcessingApi;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.InlineResponse200;
import com.rbkmoney.swag.claim_management.model.Modification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
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
    public ResponseEntity<Void> revokeClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                                @NotNull Long claimId,
                                                @NotNull Integer claimRevision,
                                                @Size(min = 1, max = 40) String deadline,
                                                @Null String reason) {
        claimManagementService.revokeClaimById(requestId, claimId, claimRevision, deadline, reason);
        log.info("Successful revoke clame with id {}", claimId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InlineResponse200> searchClaims(@NotNull @Size(min = 1, max = 40) String requestId,
                                                          @NotNull Integer limit,
                                                          @Size(min = 1, max = 40) String deadline,
                                                          @Null String continuationToken,
                                                          @Null List<String> claimStatuses) {
        List<Claim> claims =
                claimManagementService.searchClaims(requestId, deadline, limit, continuationToken, claimStatuses);
        log.info("For status list {} found {} claims", claimStatuses, claims.size());
        InlineResponse200 inlineResponse200 = new InlineResponse200()
                .continuationToken(continuationToken)
                .result(claims);
        return ResponseEntity.ok(inlineResponse200);
    }

    @Override
    public ResponseEntity<Void> updateClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                                @NotNull Long claimId,
                                                @NotNull Integer claimRevision,
                                                @Size(min = 1, max = 40) String deadline,
                                                Modification changeset) {
        claimManagementService.updateClaimById(requestId, claimId, claimRevision, deadline, changeset);
        log.info("Successful update clame with id {}", claimId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
