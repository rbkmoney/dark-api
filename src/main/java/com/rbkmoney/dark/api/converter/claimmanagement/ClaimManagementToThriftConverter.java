package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.ClaimSearchQuery;
import com.rbkmoney.damsel.claim_management.ClaimStatus;
import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;

import java.util.List;

public interface ClaimManagementToThriftConverter {

    List<Modification> convertChangesetToThrift(ClaimChangeset changeset);

    List<Modification> convertModificationUnitToThrift(List<com.rbkmoney.swag.claim_management.model.Modification> unitModifications);

    ClaimChangeset convertClaimChangesetToThrift(List<com.rbkmoney.damsel.claim_management.ModificationUnit> changeset);

    ClaimSearchQuery convertSearchClaimsToThrift(String requestId, Integer limit, String continuationToken, List<String> claimStatuses);

    List<ClaimStatus> convertStatusesToThrift(List<String> sourceClaimStatuses);

}
