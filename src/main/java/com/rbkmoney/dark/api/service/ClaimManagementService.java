package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.claimmanagement.ClaimManagementConverter;
import com.rbkmoney.dark.api.util.ThriftClientUtils;
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

    public Claim createClaim(String apiName,
                             String methodName,
                             String xRequestId,
                             String xRequestDeadline,
                             String partyId,
                             List<com.rbkmoney.swag.claim_management.model.Modification> changeset) throws InvalidChangeset, TException {
        List<Modification> modificationList = claimManagementConverter.convertModificationUnitToThrift(changeset);
        var claim = ThriftClientUtils.callServiceFork(
                apiName,
                methodName,
                xRequestId,
                xRequestDeadline,
                () -> claimManagementClient.createClaim(partyId, modificationList)
        );
        return claimManagementConverter.convertClaimToSwag(claim);
    }

    public Claim getClaimById(String apiName,
                              String methodName,
                              String xRequestId,
                              String xRequestDeadline,
                              String partyId,
                              Long claimId) throws ClaimNotFound, TException {
        var claim = ThriftClientUtils.callServiceFork(
                apiName,
                methodName,
                xRequestId,
                xRequestDeadline,
                () -> claimManagementClient.getClaim(partyId, claimId)
        );
        return claimManagementConverter.convertClaimToSwag(claim);
    }

    public void revokeClaimById(String apiName,
                                String methodName,
                                String xRequestId,
                                String xRequestDeadline,
                                String partyId,
                                Long claimId,
                                Integer claimRevision,
                                String reason) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        ThriftClientUtils.callServiceFork(
                apiName,
                methodName,
                xRequestId,
                xRequestDeadline,
                () -> {
                    claimManagementClient.revokeClaim(partyId, claimId, claimRevision, reason);
                    return true;
                }
        );
    }

    public void requestClaimReviewById(String apiName,
                                       String methodName,
                                       String xRequestId,
                                       String xRequestDeadline,
                                       String partyId,
                                       Long claimId,
                                       Integer claimRevision) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, TException {
        ThriftClientUtils.callServiceFork(
                apiName,
                methodName,
                xRequestId,
                xRequestDeadline,
                () -> {
                    claimManagementClient.requestClaimReview(partyId, claimId, claimRevision);
                    return true;
                }
        );
    }

    public InlineResponse200 searchClaims(String apiName,
                                          String methodName,
                                          String xRequestId,
                                          String xRequestDeadline,
                                          String partyId,
                                          Integer limit,
                                          String continuationToken,
                                          Long claimId,
                                          List<String> claimStatuses) throws LimitExceeded, BadContinuationToken, TException {
        ClaimSearchQuery claimSearchQuery = claimManagementConverter.convertSearchClaimsToThrift(partyId, claimId, limit, continuationToken, claimStatuses);
        ClaimSearchResponse claimSearchResponse = ThriftClientUtils.callServiceFork(
                apiName,
                methodName,
                xRequestId,
                xRequestDeadline,
                () -> claimManagementClient.searchClaims(claimSearchQuery)
        );
        return new InlineResponse200()
                .result(claimManagementConverter.convertClaimListToSwag(claimSearchResponse.getResult()))
                .continuationToken(claimSearchResponse.getContinuationToken());
    }

    public void updateClaimById(String apiName,
                                String methodName,
                                String xRequestId,
                                String xRequestDeadline,
                                String partyId,
                                Long claimId,
                                Integer claimRevision,
                                List<com.rbkmoney.swag.claim_management.model.Modification> changeset) throws ClaimNotFound, InvalidClaimStatus, InvalidClaimRevision, ChangesetConflict, InvalidChangeset, TException {
        List<Modification> modificationList = claimManagementConverter.convertModificationUnitToThrift(changeset);
        ThriftClientUtils.callServiceFork(
                apiName,
                methodName,
                xRequestId,
                xRequestDeadline,
                () -> {
                    claimManagementClient.updateClaim(partyId, claimId, claimRevision, modificationList);
                    return true;
                }
        );
    }
}
