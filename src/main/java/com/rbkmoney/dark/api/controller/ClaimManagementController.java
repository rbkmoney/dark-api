package com.rbkmoney.dark.api.controller;

import com.rbkmoney.damsel.claim_management.InvalidChangeset;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.exceptions.DeadlineException;
import com.rbkmoney.dark.api.exceptions.client.NotFoundException;
import com.rbkmoney.dark.api.exceptions.client.badrequest.BadRequestException;
import com.rbkmoney.dark.api.service.ClaimManagementService;
import com.rbkmoney.dark.api.service.DeadlineService;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.dark.api.service.PartyManagementService;
import com.rbkmoney.swag.claim_management.api.ProcessingApi;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.Modification;
import com.rbkmoney.swag.claim_management.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClaimManagementController implements ProcessingApi {

    private final ClaimManagementService claimManagementService;
    private final PartyManagementService partyManagementService;
    private final DeadlineService deadlineService;
    private final KeycloakService keycloakService;

    @Override
    @PreAuthorize("hasAuthority('party:read')")
    public ResponseEntity<Claim> createClaim(@NotNull @Size(min = 1, max = 40) String xRequestId,
                                             @NotNull @Valid List<Modification> changeset,
                                             @Size(min = 1, max = 40) String xRequestDeadline) {
        try {
            log.info("Process 'createClaim' get started, xRequestId={}", xRequestId);

            partyManagementService.checkStatus(xRequestId);

            deadlineService.checkDeadline(xRequestDeadline, xRequestId);

            Claim claim = claimManagementService.createClaim(keycloakService.getPartyId(), changeset);

            log.info("Claim created, xRequestId={}, claimId={}", xRequestId, claim.getId());

            return ResponseEntity.ok(claim);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidChangeset ex) {
            String msg = String.format("Invalid changeset, xRequestId=%s, reason='%s'", xRequestId, ex.getReason());
            InlineResponse4001 response = new InlineResponse4001()
                    .code(InlineResponse4001.CodeEnum.INVALIDCHANGESET)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("claim-management", "createClaim", xRequestId, ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:read')")
    public ResponseEntity<Claim> getClaimByID(@NotNull @Size(min = 1, max = 40) String xRequestId,
                                              @NotNull @Valid Long claimId,
                                              @Size(min = 1, max = 40) String xRequestDeadline) {
        try {
            log.info("Process 'getClaimByID' get started, xRequestId={}, claimId={}", xRequestId, claimId);

            partyManagementService.checkStatus(xRequestId);

            deadlineService.checkDeadline(xRequestDeadline, xRequestId);

            Claim claim = claimManagementService.getClaimById(keycloakService.getPartyId(), claimId);

            log.info("Got a claim, xRequestId={}, claimId={}", xRequestId, claimId);

            return ResponseEntity.ok(claim);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            GeneralError response = new GeneralError().message(msg);
            throw badRequestException(msg, ex, response);
        } catch (ClaimNotFound ex) {
            throw notFoundException(xRequestId, claimId, ex);
        } catch (TException ex) {
            throw darkApi5xxException("claim-management", "getClaimByID", xRequestId, ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:write')")
    public ResponseEntity<Void> revokeClaimByID(@NotNull @Size(min = 1, max = 40) String xRequestId,
                                                @NotNull @Valid Long claimId,
                                                @NotNull @Valid Integer claimRevision,
                                                @Size(min = 1, max = 40) String xRequestDeadline,
                                                @Valid String reason) {
        try {
            log.info("Process 'revokeClaimByID' get started, xRequestId={}, claimId={}", xRequestId, claimId);

            partyManagementService.checkStatus(xRequestId);

            deadlineService.checkDeadline(xRequestDeadline, xRequestId);

            claimManagementService.revokeClaimById(keycloakService.getPartyId(), claimId, claimRevision, reason);

            log.info("Successful revoke claim, xRequestId={}, claimId={}", xRequestId, claimId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4002 response = new InlineResponse4002()
                    .code(InlineResponse4002.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidClaimStatus ex) {
            String msg = String.format("Invalid claim status, xRequestId=%s, status=%s", xRequestId, ex.getStatus());
            InlineResponse4002 response = new InlineResponse4002()
                    .code(InlineResponse4002.CodeEnum.INVALIDCLAIMSTATUS)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidClaimRevision ex) {
            String msg = String.format("Invalid claim revision, xRequestId=%s, claimRevision=%s", xRequestId, claimRevision);
            InlineResponse4002 response = new InlineResponse4002()
                    .code(InlineResponse4002.CodeEnum.INVALIDCLAIMREVISION)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (ClaimNotFound ex) {
            throw notFoundException(xRequestId, claimId, ex);
        } catch (TException ex) {
            throw darkApi5xxException("claim-management", "revokeClaimByID", xRequestId, ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:write')")
    public ResponseEntity<Void> requestReviewClaimByID(@NotNull @Size(min = 1, max = 40) String xRequestId,
                                                       @NotNull @Valid Long claimId,
                                                       @NotNull @Valid Integer claimRevision,
                                                       @Size(min = 1, max = 40) String xRequestDeadline) {
        try {
            log.info("Process 'requestReviewClaimByID' get started, xRequestId={}, claimId={}", xRequestId, claimId);

            partyManagementService.checkStatus(xRequestId);

            deadlineService.checkDeadline(xRequestDeadline, xRequestId);

            claimManagementService.requestClaimReviewById(keycloakService.getPartyId(), claimId, claimRevision);

            log.info("Successful request claim review, xRequestId={}, claimId={}", xRequestId, claimId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4002 response = new InlineResponse4002()
                    .code(InlineResponse4002.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidClaimStatus ex) {
            String msg = String.format("Invalid claim status, xRequestId=%s, status=%s", xRequestId, ex.getStatus());
            InlineResponse4002 response = new InlineResponse4002()
                    .code(InlineResponse4002.CodeEnum.INVALIDCLAIMSTATUS)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidClaimRevision ex) {
            String msg = String.format("Invalid claim revision, xRequestId=%s, claimRevision=%s", xRequestId, claimRevision);
            InlineResponse4002 response = new InlineResponse4002()
                    .code(InlineResponse4002.CodeEnum.INVALIDCLAIMREVISION)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (ClaimNotFound ex) {
            throw notFoundException(xRequestId, claimId, ex);
        } catch (TException ex) {
            throw darkApi5xxException("claim-management", "requestClaimReviewById", xRequestId, ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:read')")
    public ResponseEntity<InlineResponse200> searchClaims(@NotNull @Size(min = 1, max = 40) String xRequestId,
                                                          @NotNull @Min(1L) @Max(1000L) @Valid Integer limit,
                                                          @Size(min = 1, max = 40) String xRequestDeadline,
                                                          @Size(min = 1, max = 40) String continuationToken,
                                                          @Valid Long claimId,
                                                          @Valid List<String> claimStatuses) {
        try {
            log.info("Process 'searchClaims' get started, xRequestId={}, claimId={}", xRequestId, claimId);

            partyManagementService.checkStatus(xRequestId);

            deadlineService.checkDeadline(xRequestDeadline, xRequestId);

            InlineResponse200 response = claimManagementService.searchClaims(keycloakService.getPartyId(), limit, continuationToken, claimId, claimStatuses);

            log.info("For status list, xRequestId={}, claimId={}, list statuses={}, size results={}", xRequestId, claimId, claimStatuses, response.getResult().size());

            return ResponseEntity.ok(response);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (LimitExceeded ex) {
            String msg = String.format("Limit exceeded, xRequestId=%s", xRequestId);
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.LIMITEXCEEDED)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (BadContinuationToken ex) {
            String msg = String.format("Bad continuation token, xRequestId=%s, reason=%s", xRequestId, ex.getReason());
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.BADCONTINUATIONTOKEN)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("claim-management", "searchClaims", xRequestId, ex);
        }
    }

    @Override
    @PreAuthorize("hasAuthority('party:write')")
    public ResponseEntity<Void> updateClaimByID(@NotNull @Size(min = 1, max = 40) String xRequestId,
                                                @NotNull @Valid Long claimId,
                                                @NotNull @Valid Integer claimRevision,
                                                @NotNull @Valid List<Modification> changeset,
                                                @Size(min = 1, max = 40) String xRequestDeadline) {
        try {
            log.info("Process 'updateClaimByID' get started, requestId={}, claimId={}", xRequestId, claimId);

            partyManagementService.checkStatus(xRequestId);

            deadlineService.checkDeadline(xRequestDeadline, xRequestId);

            claimManagementService.updateClaimById(keycloakService.getPartyId(), claimId, claimRevision, changeset);

            log.info("Successful update claim, xRequestId={}, claimId={}", xRequestId, claimId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DeadlineException ex) {
            String msg = ex.getMessage();
            InlineResponse4003 response = new InlineResponse4003()
                    .code(InlineResponse4003.CodeEnum.INVALIDDEADLINE)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidClaimStatus ex) {
            String msg = String.format("Invalid claim status, xRequestId=%s, status=%s", xRequestId, ex.getStatus());
            InlineResponse4003 response = new InlineResponse4003()
                    .code(InlineResponse4003.CodeEnum.INVALIDCLAIMSTATUS)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidClaimRevision ex) {
            String msg = String.format("Invalid claim revision, xRequestId=%s, claimRevision=%s", xRequestId, claimRevision);
            InlineResponse4003 response = new InlineResponse4003()
                    .code(InlineResponse4003.CodeEnum.INVALIDCLAIMREVISION)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (ChangesetConflict ex) {
            String msg = String.format("Changeset conflict, xRequestId=%s, conflictedId=%s", xRequestId, ex.getConflictedId());
            InlineResponse4003 response = new InlineResponse4003()
                    .code(InlineResponse4003.CodeEnum.CHANGESETCONFLICT)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (InvalidChangeset ex) {
            String msg = String.format("Invalid changeset, xRequestId=%s, reason='%s'", xRequestId, ex.getReason());
            InlineResponse4003 response = new InlineResponse4003()
                    .code(InlineResponse4003.CodeEnum.INVALIDCHANGESET)
                    .message(msg);
            throw badRequestException(msg, ex, response);
        } catch (ClaimNotFound ex) {
            throw notFoundException(xRequestId, claimId, ex);
        } catch (TException ex) {
            throw darkApi5xxException("claim-management", "updateClaimByID", xRequestId, ex);
        }
    }

    private NotFoundException notFoundException(String xRequestId, Long claimId, ClaimNotFound ex) {
        String msg = messageClaimNotFound(claimId, xRequestId);
        GeneralError response = new GeneralError().message(msg);
        return new NotFoundException(msg, ex, response);
    }

    private String messageClaimNotFound(Long claimId, String xRequestId) {
        return String.format("Claim not found, claimId=%s, xRequestId=%s", String.valueOf(claimId), xRequestId);
    }

    private BadRequestException badRequestException(String msg, Throwable cause, Object response) {
        return new BadRequestException(msg, cause, response);
    }
}
