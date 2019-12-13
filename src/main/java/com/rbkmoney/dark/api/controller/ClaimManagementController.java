package com.rbkmoney.dark.api.controller;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.service.ClaimManagementService;
import com.rbkmoney.swag.claim_management.api.ProcessingApi;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.InlineResponse200;
import com.rbkmoney.swag.claim_management.model.Modification;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClaimManagementController implements ProcessingApi {

    private final ClaimManagementService claimManagementService;

    @Override
    @PreAuthorize("hasAuthority('party:read')")
    public ResponseEntity<Claim> createClaim(@NotNull @Size(min = 1, max = 40) String requestId,
                                             @NotNull @Valid List<Modification> changeset,
                                             @Size(min = 1, max = 40) String deadline) {
        try {
            log.info("Process 'createClaim' get started. requestId = {}", requestId);
            String partyId = ((KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
            Claim claim = claimManagementService.createClaim(partyId, changeset);
            log.info("Claim for request id {} created", requestId);
            return ResponseEntity.ok(claim);
        } catch (ChangesetConflict | PartyNotFound | InvalidChangeset ex) {
            log.error("Incorrect data in the application when creating claim", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException createClaim", ex);
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            log.error("Received exception while process 'createClaim'", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:read')")
    public ResponseEntity<Claim> getClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                              @NotNull Long claimId,
                                              @Size(min = 1, max = 40) String deadline) {
        try {
            log.info("Process 'getClaimByID' get started. requestId = {}, claimId = {}", requestId, claimId);
            String partyId = ((KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
            Claim claim = claimManagementService.getClaimById(partyId, claimId);
            log.info("Got a claim for request id {} and claim id {}", requestId, claimId);
            return ResponseEntity.ok(claim);
        } catch (PartyNotFound | ClaimNotFound ex) {
            log.error("Incorrect data in the application when taking claim", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException getClaimByID", ex);
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            log.error("Received exception while process 'getClaimByID'", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:write')")
    public ResponseEntity<Void> revokeClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                                @NotNull Long claimId,
                                                @NotNull Integer claimRevision,
                                                @Size(min = 1, max = 40) String deadline,
                                                @Null String reason) {
        try {
            log.info("Process 'revokeClaimByID' get started. requestId = {}, claimId = {}", requestId, claimId);
            String partyId = ((KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
            claimManagementService.revokeClaimById(partyId, claimId, claimRevision, reason);
            log.info("Successful revoke claim with id {}", claimId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PartyNotFound | ClaimNotFound | InvalidClaimStatus | InvalidClaimRevision ex) {
            log.error("Incorrect data in the application when revoke claim by id", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException revokeClaimById", ex);
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            log.error("Received exception while process 'revokeClaimByID'", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:write')")
    public ResponseEntity<Void> requestReviewClaimByID(String requestId,
                                                       Long claimId,
                                                       @NotNull @Valid Integer claimRevision,
                                                       String deadline) {
        log.info("Process 'requestReviewClaimByID' get started. requestId = {}, claimId = {}", requestId, claimId);
        String partyId = ((KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
        try {
            claimManagementService.requestClaimReviewById(partyId, claimId, claimRevision);
            log.info("Successful request claim review with id {}", claimId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PartyNotFound | ClaimNotFound | InvalidClaimStatus | InvalidClaimRevision ex) {
            log.error("Incorrect data in the application when request claim review by id", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            log.error("Received exception while process 'requestReviewClaimByID'", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:read')")
    public ResponseEntity<InlineResponse200> searchClaims(@NotNull @Size(min = 1, max = 40) String requestId,
                                                          @NotNull Integer limit,
                                                          @Size(min = 1, max = 40) String deadline,
                                                          @Null String continuationToken,
                                                          @Null Long claimID,
                                                          @Null List<String> claimStatuses) {
        try {
            log.info("Process 'searchClaims' get started. requestId = {}", requestId);
            String partyId = ((KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
            InlineResponse200 response = claimManagementService.searchClaims(partyId, claimID, limit, continuationToken, claimStatuses);
            log.info("For status list {} found {} claims", claimStatuses, response.getResult().size());
            return ResponseEntity.ok(response);
        } catch (PartyNotFound | LimitExceeded | BadContinuationToken ex) {
            log.error("Incorrect data in the application when search claims", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException searchClaims", ex);
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            log.error("Received exception while process 'searchClaims'", ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:write')")
    public ResponseEntity<Void> updateClaimByID(@NotNull @Size(min = 1, max = 40) String requestId,
                                                @NotNull Long claimId,
                                                @NotNull Integer claimRevision,
                                                List<Modification> changeset,
                                                @Size(min = 1, max = 40) String deadline) {
        try {
            log.info("Process 'updateClaimByID' get started. requestId = {}, claimId = {}", requestId, claimId);
            String partyId = ((KeycloakPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
            claimManagementService.updateClaimById(partyId, claimId, claimRevision, changeset);
            log.info("Successful update claim with id {}", claimId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (PartyNotFound | InvalidClaimRevision | InvalidClaimStatus | ChangesetConflict | InvalidChangeset ex) {
            log.error("Incorrect data in the application when update claim by id", ex);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (TException ex) {
            log.error("TException updateClaimByID", ex);
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            log.error("Received exception while process 'updateClaimByID'", ex);
            throw new RuntimeException(ex);
        }
    }

}
