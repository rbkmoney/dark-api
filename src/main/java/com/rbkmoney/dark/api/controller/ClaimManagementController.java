package com.rbkmoney.dark.api.controller;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.service.ClaimManagementService;
import com.rbkmoney.swag.claim_management.api.ProcessingApi;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.InlineResponse200;
import com.rbkmoney.swag.claim_management.model.Modification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        try {
            Claim claim = claimManagementService.createClaim(requestId, changeset);
            log.info("Claim for request id {} created", requestId);
            return ResponseEntity.ok(claim);
        } catch (ChangesetConflict | PartyNotFound | InvalidChangeset ex) {
            log.error("Incorrect data in the application when creating claim: ", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException createClaim: ", ex);
            throw new RuntimeException(ex);
        }

    }

    @Override
    public ResponseEntity<Claim> getClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                              @NotNull Long claimId,
                                              @Size(min = 1, max = 40) String deadline) {
        try {
            Claim claim = claimManagementService.getClaimByID(requestId, claimId);
            log.info("Got a claim for request id {} and claim id {}", requestId, claimId);
            return ResponseEntity.ok(claim);
        } catch (PartyNotFound | ClaimNotFound ex) {
            log.error("Incorrect data in the application when taking claim: ", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException getClaimByID: ", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ResponseEntity<Void> revokeClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                                @NotNull Long claimId,
                                                @NotNull Integer claimRevision,
                                                @Size(min = 1, max = 40) String deadline,
                                                @Null String reason) {
        try {
            claimManagementService.revokeClaimById(requestId, claimId, claimRevision, reason);
            log.info("Successful revoke clame with id {}", claimId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PartyNotFound | ClaimNotFound | InvalidClaimStatus | InvalidClaimRevision ex) {
            log.error("Incorrect data in the application when revoke claim by id: ", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException revokeClaimById: ", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ResponseEntity<InlineResponse200> searchClaims(@NotNull @Size(min = 1, max = 40) String requestId,
                                                          @NotNull Integer limit,
                                                          @Size(min = 1, max = 40) String deadline,
                                                          @Null String continuationToken,
                                                          @Null List<String> claimStatuses) {
        try {
            String partyId = ((KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
            List<Claim> claims =
                    claimManagementService.searchClaims(partyId, limit, continuationToken, claimStatuses);
            log.info("For status list {} found {} claims", claimStatuses, claims.size());
            InlineResponse200 inlineResponse200 = new InlineResponse200()
                    .continuationToken(continuationToken)
                    .result(claims);
            return ResponseEntity.ok(inlineResponse200);
        } catch (PartyNotFound | LimitExceeded | BadContinuationToken ex) {
            log.error("Incorrect data in the application when search claims: ", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException searchClaims: ", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public ResponseEntity<Void> updateClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                                @NotNull Long claimId,
                                                @NotNull Integer claimRevision,
                                                List<Modification> changeset,
                                                @Size(min = 1, max = 40) String deadline) {
        try {
            claimManagementService.updateClaimById(requestId, claimId, claimRevision, changeset);
            log.info("Successful update clame with id {}", claimId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PartyNotFound | InvalidClaimRevision | InvalidClaimStatus | ChangesetConflict | InvalidChangeset ex) {
            log.error("Incorrect data in the application when update claim by id: ", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException revokeClaimById: ", ex);
            throw new RuntimeException(ex);
        }
    }

}
