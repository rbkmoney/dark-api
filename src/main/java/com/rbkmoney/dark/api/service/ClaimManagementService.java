package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.claimmanagement.CreateClaimConverter;
import com.rbkmoney.dark.api.converter.claimmanagement.SearchClaimConverter;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimManagementService {

    private final ClaimManagementSrv.Iface claimManagementClient;

    private final SearchClaimConverter searchClaimConverter;

    private final CreateClaimConverter createClaimConverter;

    public Claim createClaim(String requestID, ClaimChangeset changeset, String deadline) {
        try {
            List<Modification> modificationList = createClaimConverter.convertChangesetToThrift(changeset);
            com.rbkmoney.damsel.claim_management.Claim claim  =
                    claimManagementClient.createClaim(requestID, modificationList);
            return createClaimConverter.convertClaimFromThrift(claim);
        } catch (TException ex) {
            log.error("TException createClaim: ", ex);
            throw new RuntimeException(ex);
        }
    }

    public Claim getClaimByID(String requestID, Long claimID, String deadline) {
        try {
            com.rbkmoney.damsel.claim_management.Claim claim = claimManagementClient.getClaim(requestID, claimID);
            return createClaimConverter.convertClaimFromThrift(claim);
        } catch (TException ex) {
            log.error("TException getClaimByID: ", ex);
            throw new RuntimeException(ex);
        }
    }

    public List<Claim> searchClaims(String requestID,
                                    String deadline,
                                    Integer limit,
                                    String continuationToken,
                                    List<String> claimStatuses) {
        try {
            ClaimSearchQuery claimSearchQuery =
                    searchClaimConverter.convertSearchClaimsToThrift(requestID, deadline, limit, continuationToken, claimStatuses);
            List<com.rbkmoney.damsel.claim_management.Claim> claimList = claimManagementClient.searchClaims(claimSearchQuery);
            return searchClaimConverter.convertClaimListFromThrift(claimList);
        } catch (TException ex) {
            log.error("TException searchClaims: ", ex);
            throw new RuntimeException(ex);
        }
    }

    public void updateClaimById(String requestID,
                                Long claimID,
                                Integer claimRevision,
                                String deadline,
                                com.rbkmoney.swag.claim_management.model.Modification changeset) {
        try {
            Modification modificationList = createClaimConverter.convertModificationUnitToThrift(changeset);
            //TODO: API swag и thrift отличаются
            claimManagementClient.updateClaim(requestID, claimID, claimRevision, Arrays.asList(modificationList));
        } catch (PartyNotFound ex) {
            log.error("");
        } catch (ClaimNotFound ex) {

        } catch (InvalidClaimStatus ex) {

        } catch (InvalidClaimRevision ex) {

        } catch (ChangesetConflict ex) {

        } catch (InvalidChangeset ex) {

        } catch (TException ex) {
            log.error("TException revokeClaimById: ", ex);
            throw new RuntimeException(ex);
        }
    }

    public void revokeClaimById(String requestID,
                                Long claimID,
                                Integer claimRevision,
                                String deadline,
                                String reason) {
        try {
            claimManagementClient.revokeClaim(requestID, claimID, claimRevision, reason);
        } catch (PartyNotFound ex) {

        } catch (ClaimNotFound ex) {

        } catch (InvalidClaimStatus ex) {

        } catch (InvalidClaimRevision ex) {

        } catch (TException ex) {
            log.error("TException revokeClaimById: ", ex);
            throw new RuntimeException(ex);
        }
    }

}
