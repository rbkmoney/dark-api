package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.ClaimModification;
import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.damsel.claim_management.PartyModification;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.ModificationUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.PARTYMODIFICATION;

@Slf4j
@Component
public class CreateClaimConverter {

    public Claim convertClaimFromThrift(com.rbkmoney.damsel.claim_management.Claim sourceClaim) {
        Claim claim = new Claim();
        claim.setId(sourceClaim.getId());
        //TODO: посмотреть какой статус будет
        claim.setStatus(sourceClaim.getStatus().toString());
        claim.setChangeset(convertClaimChangesetToThrift(sourceClaim.getChangeset()));
        claim.setCreatedAt(OffsetDateTime.parse(sourceClaim.getCreatedAt()));
        claim.setUpdatedAt(OffsetDateTime.parse(sourceClaim.getUpdatedAt()));
        //TODO: метадата это MAP - правильно ли ее так передавать?
        claim.setMetadata(sourceClaim.getMetadata());
        return claim;
    }

    public ClaimChangeset convertClaimChangesetToThrift(List<com.rbkmoney.damsel.claim_management.ModificationUnit> changeset) {
        ClaimChangeset claimChangeset = new ClaimChangeset();

        for (com.rbkmoney.damsel.claim_management.ModificationUnit unit : changeset) {
            ModificationUnit modificationUnit = new ModificationUnit();
            modificationUnit.setModificationID(unit.getModificationId());
            modificationUnit.setCreatedAt(OffsetDateTime.parse(unit.getCreatedAt()));
            com.rbkmoney.swag.claim_management.model.Modification modification = new com.rbkmoney.swag.claim_management.model.Modification();
            if (unit.getModification().isSetClaimModfication()) {
                modification.setModificationType(com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION);
            } else if (unit.getModification().isSetPartyModification()) {
                modification.setModificationType(com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.PARTYMODIFICATION);
            }
            claimChangeset.add(modificationUnit);
        }

        return claimChangeset;
    }

    public List<Modification> convertChangesetToThrift(ClaimChangeset changeset) {
        List<Modification> modificationList = new ArrayList<>();
        for (ModificationUnit unit : changeset) {
            Modification modification = new Modification();
            //TODO: modification в протоколах расходятся. В трифте более подробно - так достаточно?
            if (unit.getModification().getModificationType() == PARTYMODIFICATION) {
                modification.setPartyModification(new PartyModification());
            } else if (unit.getModification().getModificationType() == CLAIMMODIFICATION) {
                modification.setClaimModfication(new ClaimModification());
            } else {
                log.warn("Modification not found");
            }

            modificationList.add(modification);
        }
        return modificationList;
    }

}
