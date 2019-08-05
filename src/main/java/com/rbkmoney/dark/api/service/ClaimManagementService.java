package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.claimmanagement.ClaimManagementFromThriftConverter;
import com.rbkmoney.dark.api.converter.claimmanagement.ClaimManagementToThriftConverter;
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

    private final ClaimManagementToThriftConverter claimManagementToThriftConverter;

    private final ClaimManagementFromThriftConverter claimManagementFromThriftConverter;

    public Claim createClaim(String requestId, ClaimChangeset changeset) throws TException {
        List<Modification> modificationList = claimManagementToThriftConverter.convertChangesetToThrift(changeset);
        com.rbkmoney.damsel.claim_management.Claim claim = claimManagementClient.createClaim(requestId, modificationList);
        return claimManagementFromThriftConverter.convertClaimFromThrift(claim);
    }

    public Claim getClaimByID(String requestId, Long claimId) throws TException {
        com.rbkmoney.damsel.claim_management.Claim claim = claimManagementClient.getClaim(requestId, claimId);
        return claimManagementFromThriftConverter.convertClaimFromThrift(claim);
    }

    public List<Claim> searchClaims(String requestId, Integer limit, String continuationToken, List<String> claimStatuses)
            throws TException {
        ClaimSearchQuery claimSearchQuery =
                claimManagementToThriftConverter.convertSearchClaimsToThrift(requestId, limit, continuationToken, claimStatuses);
        List<com.rbkmoney.damsel.claim_management.Claim> claimList = claimManagementClient.searchClaims(claimSearchQuery);
        return claimManagementFromThriftConverter.convertClaimListFromThrift(claimList);
    }

    public void updateClaimById(String requestId,
                                Long claimId,
                                Integer claimRevision,
                                List<com.rbkmoney.swag.claim_management.model.Modification> changeset) throws TException {
        List<Modification> modificationList = claimManagementToThriftConverter.convertModificationUnitToThrift(changeset);
        claimManagementClient.updateClaim(requestId, claimId, claimRevision, modificationList);
    }

    public void revokeClaimById(String requestId, Long claimId, Integer claimRevision, String reason) throws TException {
        claimManagementClient.revokeClaim(requestId, claimId, claimRevision, reason);
    }

}
