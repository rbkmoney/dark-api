package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.ClaimSearchQuery;
import com.rbkmoney.damsel.claim_management.ClaimStatus;
import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;

import java.util.List;

public interface ClaimManagementConverter {

    Claim convertClaimToSwag(com.rbkmoney.damsel.claim_management.Claim sourceClaim);

    List<Claim> convertClaimListToSwag(List<com.rbkmoney.damsel.claim_management.Claim> sourceClaimList);

    ClaimChangeset convertClaimChangesetToSwag(List<com.rbkmoney.damsel.claim_management.ModificationUnit> changeset);

    List<Modification> convertModificationUnitToThrift(
            List<com.rbkmoney.swag.claim_management.model.Modification> unitModifications);

    ClaimSearchQuery convertSearchClaimsToThrift(String partyId,
                                                 Long claimId,
                                                 Integer limit,
                                                 String continuationToken,
                                                 List<String> claimStatuses);

    List<ClaimStatus> convertStatusesToThrift(List<String> sourceClaimStatuses);

}
