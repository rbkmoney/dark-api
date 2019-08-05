package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;

import java.util.List;

public interface ClaimManagementFromThriftConverter {

    Claim convertClaimFromThrift(com.rbkmoney.damsel.claim_management.Claim sourceClaim);

    List<Claim> convertClaimListFromThrift(List<com.rbkmoney.damsel.claim_management.Claim> sourceClaimList);

    ClaimChangeset convertClaimChangesetFromThrift(List<com.rbkmoney.damsel.claim_management.ModificationUnit> changeset);

}
