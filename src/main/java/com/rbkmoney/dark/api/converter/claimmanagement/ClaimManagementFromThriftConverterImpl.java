package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.Modification;
import com.rbkmoney.swag.claim_management.model.ModificationUnit;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.PARTYMODIFICATION;

@Component
public class ClaimManagementFromThriftConverterImpl implements ClaimManagementFromThriftConverter {

    @Override
    public Claim convertClaimFromThrift(com.rbkmoney.damsel.claim_management.Claim sourceClaim) {
        Claim claim = new Claim();
        claim.setId(sourceClaim.getId());
        //TODO: посмотреть какой статус будет
        claim.setStatus(sourceClaim.getStatus().toString());
        claim.setChangeset(convertClaimChangesetFromThrift(sourceClaim.getChangeset()));
        claim.setCreatedAt(OffsetDateTime.parse(sourceClaim.getCreatedAt()));
        claim.setUpdatedAt(OffsetDateTime.parse(sourceClaim.getUpdatedAt()));
        //TODO: метадата это MAP - правильно ли ее так передавать?
        claim.setMetadata(sourceClaim.getMetadata());
        return claim;
    }

    @Override
    public List<Claim> convertClaimListFromThrift(List<com.rbkmoney.damsel.claim_management.Claim> sourceClaimList) {
        List<Claim> claims = new ArrayList<>();
        for (com.rbkmoney.damsel.claim_management.Claim sourceClaim : sourceClaimList) {
            claims.add(convertClaimFromThrift(sourceClaim));
        }
        return claims;
    }

    @Override
    public ClaimChangeset convertClaimChangesetFromThrift(List<com.rbkmoney.damsel.claim_management.ModificationUnit> changeset) {
        ClaimChangeset claimChangeset = new ClaimChangeset();

        for (com.rbkmoney.damsel.claim_management.ModificationUnit unit : changeset) {
            ModificationUnit modificationUnit = new ModificationUnit();
            modificationUnit.setModificationID(unit.getModificationId());
            modificationUnit.setCreatedAt(OffsetDateTime.parse(unit.getCreatedAt()));
            Modification modification = new Modification();
            if (unit.getModification().isSetClaimModfication()) {
                modification.setModificationType(CLAIMMODIFICATION);
            } else if (unit.getModification().isSetPartyModification()) {
                modification.setModificationType(PARTYMODIFICATION);
            }
            claimChangeset.add(modificationUnit);
        }

        return claimChangeset;
    }


}
