package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.claim_management.ClaimManagementSrv;
import com.rbkmoney.damsel.claim_management.ClaimSearchQuery;
import com.rbkmoney.damsel.claim_management.ClaimSearchResponse;
import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.dark.api.converter.claimmanagement.ClaimManagementConverter;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.InlineResponse200;
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

    private final ClaimManagementConverter claimManagementConverter;

    public Claim createClaim(String partyId, List<com.rbkmoney.swag.claim_management.model.Modification> changeset) throws TException {
        List<Modification> modificationList = claimManagementConverter.convertModificationUnitToThrift(changeset);
        com.rbkmoney.damsel.claim_management.Claim claim = claimManagementClient.createClaim(partyId, modificationList);
        return claimManagementConverter.convertClaimToSwag(claim);
    }

    public Claim getClaimById(String partyId, Long claimId) throws TException {
        com.rbkmoney.damsel.claim_management.Claim claim = claimManagementClient.getClaim(partyId, claimId);
        return claimManagementConverter.convertClaimToSwag(claim);
    }

    public InlineResponse200 searchClaims(String partyId, Integer limit, String continuationToken, List<String> claimStatuses)
            throws TException {
        ClaimSearchQuery claimSearchQuery =
                claimManagementConverter.convertSearchClaimsToThrift(partyId, limit, continuationToken, claimStatuses);
        ClaimSearchResponse claimSearchResponse = claimManagementClient.searchClaims(claimSearchQuery);
        return new InlineResponse200()
                .result(claimManagementConverter.convertClaimListToSwag(claimSearchResponse.getResult()))
                .continuationToken(claimSearchQuery.getContinuationToken());
    }

    public void updateClaimById(String partyId,
                                Long claimId,
                                Integer claimRevision,
                                List<com.rbkmoney.swag.claim_management.model.Modification> changeset) throws TException {
        List<Modification> modificationList = claimManagementConverter.convertModificationUnitToThrift(changeset);
        claimManagementClient.updateClaim(partyId, claimId, claimRevision, modificationList);
    }

    public void revokeClaimById(String partyId, Long claimId, Integer claimRevision, String reason) throws TException {
        claimManagementClient.revokeClaim(partyId, claimId, claimRevision, reason);
    }

    public void requestClaimReviewById(String partyId, Long claimId, Integer claimRevision) throws TException {
        claimManagementClient.requestClaimReview(partyId, claimId, claimRevision);
    }

}
