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



    public void acceptClaim() {

    }

        public void updateClaimById(String requestID,
                                    String deadline,
                                    Long claimID,
                                    Integer claimRevision,
                                    ClaimChangeset changeset) {
        try {
            List<Modification> modificationList = createClaimConverter.convertChangesetToThrift(changeset);
            claimManagementClient.updateClaim(requestID, claimID, claimRevision, modificationList);
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

    public void denyClaim() {

    }

    public void revokeClaimById(String requestID,
                                String deadline,
                                Long claimID,
                                Integer claimRevision) {
        try {
            //TODO: определить входящий reason
            String reason = null;
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

    public void getMetaData() {

    }

    public void setMetaData() {

    }

    public void removeMetaData() {

    }


}
