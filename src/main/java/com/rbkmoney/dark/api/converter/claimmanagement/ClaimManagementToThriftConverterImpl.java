package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.*;
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
public class ClaimManagementToThriftConverterImpl implements ClaimManagementToThriftConverter {

    @Override
    public List<Modification> convertChangesetToThrift(ClaimChangeset changeset) {
        List<Modification> modificationList = new ArrayList<>();
        for (ModificationUnit unit : changeset) {
            modificationList.add(convertModificationToThrift(unit.getModification()));
        }
        return modificationList;
    }

    @Override
    public List<Modification> convertModificationUnitToThrift(List<com.rbkmoney.swag.claim_management.model.Modification> unitModifications) {
        List<Modification> modificationList = new ArrayList<>();
        for (com.rbkmoney.swag.claim_management.model.Modification unitModification : unitModifications) {
            modificationList.add(convertModificationToThrift(unitModification));
        }
        return modificationList;
    }

    private Modification convertModificationToThrift(com.rbkmoney.swag.claim_management.model.Modification unitModification) {
        Modification modification = new Modification();
        //TODO: modification в протоколах расходятся. В трифте более подробно - так достаточно?
        if (unitModification.getModificationType() == PARTYMODIFICATION) {
            modification.setPartyModification(new PartyModification());
        } else if (unitModification.getModificationType() == CLAIMMODIFICATION) {
            modification.setClaimModfication(new ClaimModification());
        } else {
            log.warn("Modification not found");
        }
        return modification;
    }

    @Override
    public ClaimChangeset convertClaimChangesetToThrift(List<com.rbkmoney.damsel.claim_management.ModificationUnit> changeset) {
        ClaimChangeset claimChangeset = new ClaimChangeset();

        for (com.rbkmoney.damsel.claim_management.ModificationUnit unit : changeset) {
            ModificationUnit modificationUnit = new ModificationUnit();
            modificationUnit.setModificationID(unit.getModificationId());
            modificationUnit.setCreatedAt(OffsetDateTime.parse(unit.getCreatedAt()));
            com.rbkmoney.swag.claim_management.model.Modification modification =
                    new com.rbkmoney.swag.claim_management.model.Modification();
            if (unit.getModification().isSetClaimModfication()) {
                modification.setModificationType(CLAIMMODIFICATION);
            } else if (unit.getModification().isSetPartyModification()) {
                modification.setModificationType(PARTYMODIFICATION);
            }
            claimChangeset.add(modificationUnit);
        }

        return claimChangeset;
    }

    @Override
    public ClaimSearchQuery convertSearchClaimsToThrift(String requestId,
                                                        Integer limit,
                                                        String continuationToken,
                                                        List<String> claimStatuses) {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        //TODO: правильно ли занесен requestID?
        claimSearchQuery.setPartyId(requestId);
        claimSearchQuery.setLimit(limit);
        claimSearchQuery.setToken(continuationToken);
        claimSearchQuery.setStatuses(convertStatusesToThrift(claimStatuses));
        return claimSearchQuery;
    }

    @Override
    public List<ClaimStatus> convertStatusesToThrift(List<String> sourceClaimStatuses) {
        List<ClaimStatus> claimStatuses = new ArrayList<>();
        for (String sourceClaimStatus : sourceClaimStatuses) {
            ClaimStatus status = new ClaimStatus();
            //status.setAccepted(new ClaimAccepted());
            //TODO: реализовать заполнение статусов
        }
        return claimStatuses;
    }

}
