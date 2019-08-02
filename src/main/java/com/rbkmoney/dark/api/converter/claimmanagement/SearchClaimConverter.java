package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.ClaimSearchQuery;
import com.rbkmoney.damsel.claim_management.ClaimStatus;
import com.rbkmoney.swag.claim_management.model.Claim;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.Modification;
import com.rbkmoney.swag.claim_management.model.ModificationUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SearchClaimConverter {

    public List<Claim> convertClaimListFromThrift(List<com.rbkmoney.damsel.claim_management.Claim> sourceClaimList) {
        List<Claim> claims = new ArrayList<>();
        for (com.rbkmoney.damsel.claim_management.Claim sourceClaim : sourceClaimList) {
            Claim claim = new Claim();
            claim.setId(sourceClaim.getId());
            //TODO: посмотреть какой статус будет
            claim.setStatus(sourceClaim.getStatus().toString());
            claim.setChangeset(convertClaimChangesetToThrift(sourceClaim.getChangeset()));
            claim.setCreatedAt(OffsetDateTime.parse(sourceClaim.getCreatedAt()));
            claim.setUpdatedAt(OffsetDateTime.parse(sourceClaim.getUpdatedAt()));
            //TODO: метадата это MAP - правильно ли ее так передавать?
            claim.setMetadata(sourceClaim.getMetadata());
            claims.add(claim);
        }
        return claims;
    }

    public ClaimChangeset convertClaimChangesetToThrift(List<com.rbkmoney.damsel.claim_management.ModificationUnit> changeset) {
        ClaimChangeset claimChangeset = new ClaimChangeset();

        for (com.rbkmoney.damsel.claim_management.ModificationUnit unit : changeset) {
            ModificationUnit modificationUnit = new ModificationUnit();
            modificationUnit.setModificationID(unit.getModificationId());
            modificationUnit.setCreatedAt(OffsetDateTime.parse(unit.getCreatedAt()));
            Modification modification = new Modification();
            if (unit.getModification().isSetClaimModfication()) {
                modification.setModificationType(Modification.ModificationTypeEnum.CLAIMMODIFICATION);
            } else if (unit.getModification().isSetPartyModification()) {
                modification.setModificationType(Modification.ModificationTypeEnum.PARTYMODIFICATION);
            }
            claimChangeset.add(modificationUnit);
        }

        return claimChangeset;
    }

    public ClaimSearchQuery convertSearchClaimsToThrift(String requestID,
                                                        String deadline,
                                                        Integer limit,
                                                        String continuationToken,
                                                        List<String> claimStatuses) {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        //TODO: правильно ли занесен requestID?
        claimSearchQuery.setPartyId(requestID);
        //TODO: куда запихать deadline
        //claimSearchQuery.setDeadline();
        claimSearchQuery.setLimit(limit);
        claimSearchQuery.setToken(continuationToken);
        claimSearchQuery.setStatuses(convertStatusesToThrift(claimStatuses));
        return claimSearchQuery;
    }

    private List<ClaimStatus> convertStatusesToThrift(List<String> sourceClaimStatuses) {
        List<ClaimStatus> claimStatuses = new ArrayList<>();
        for (String sourceClaimStatus : sourceClaimStatuses) {
            ClaimStatus status = new ClaimStatus();
            //status.setAccepted(new ClaimAccepted());
            //TODO: реализовать заполнение статусов
        }
        return claimStatuses;
    }

}
