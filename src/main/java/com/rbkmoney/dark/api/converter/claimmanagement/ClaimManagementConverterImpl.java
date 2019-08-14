package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.ClaimModification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.rbkmoney.dark.api.converter.claimmanagement.data.ThriftClaimStatus.*;
import static com.rbkmoney.dark.api.converter.claimmanagement.data.ThriftClaimStatus.REVOKED;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimManagementConverterImpl implements ClaimManagementConverter {

    private final DarkApiConverter<Modification,
            ClaimModification> claimModificationConverter;

    private final DarkApiConverter<Modification,
            com.rbkmoney.swag.claim_management.model.PartyModification> partyModificationConverter;

    @Override
    public com.rbkmoney.swag.claim_management.model.Claim convertClaimToSwag(Claim sourceClaim) {
        var claim = new com.rbkmoney.swag.claim_management.model.Claim();
        claim.setId(sourceClaim.getId());
        //TODO: посмотреть какой статус будет
        claim.setStatus(sourceClaim.getStatus().toString());
        claim.setChangeset(convertClaimChangesetToSwag(sourceClaim.getChangeset()));
        claim.setCreatedAt(OffsetDateTime.parse(sourceClaim.getCreatedAt()));
        claim.setUpdatedAt(OffsetDateTime.parse(sourceClaim.getUpdatedAt()));
        //TODO: метадата это MAP - правильно ли ее так передавать?
        claim.setMetadata(sourceClaim.getMetadata());
        return claim;
    }

    @Override
    public List<com.rbkmoney.swag.claim_management.model.Claim> convertClaimListToSwag(List<Claim> sourceClaimList) {
        List<com.rbkmoney.swag.claim_management.model.Claim> claims = new ArrayList<>();
        for (com.rbkmoney.damsel.claim_management.Claim sourceClaim : sourceClaimList) {
            claims.add(convertClaimToSwag(sourceClaim));
        }
        return claims;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ClaimChangeset convertClaimChangesetToSwag(
            List<ModificationUnit> changeset
    ) {
        var claimChangeset = new com.rbkmoney.swag.claim_management.model.ClaimChangeset();

        for (ModificationUnit unit : changeset) {
            var swagModificationUnit = new com.rbkmoney.swag.claim_management.model.ModificationUnit();
            swagModificationUnit.setModificationID(unit.getModificationId());
            swagModificationUnit.setCreatedAt(OffsetDateTime.parse(unit.getCreatedAt()));

            if (unit.getModification().isSetClaimModfication()) {
                swagModificationUnit.setModification(claimModificationConverter.convertToSwag(unit.getModification()));
            } else if (unit.getModification().isSetPartyModification()) {
                swagModificationUnit.setModification(partyModificationConverter.convertToSwag(unit.getModification()));
            } else {
                throw new IllegalArgumentException("Unknown modification type!");
            }
            claimChangeset.add(swagModificationUnit);
        }
        return claimChangeset;
    }

    @Override
    public List<Modification> convertChangesetToThrift(ClaimChangeset changeset) {
        List<Modification> modificationList = new ArrayList<>();
        for (com.rbkmoney.swag.claim_management.model.ModificationUnit unit : changeset) {
            modificationList.add(convertModificationToThrift(unit.getModification()));
        }
        return modificationList;
    }

    @Override
    public List<Modification> convertModificationUnitToThrift(
            List<com.rbkmoney.swag.claim_management.model.Modification> unitModifications
    ) {
        List<Modification> modificationList = new ArrayList<>();
        for (com.rbkmoney.swag.claim_management.model.Modification unitModification : unitModifications) {
            modificationList.add(convertModificationToThrift(unitModification));
        }
        return modificationList;
    }

    private Modification convertModificationToThrift(com.rbkmoney.swag.claim_management.model.Modification swagModification) {
        switch (swagModification.getModificationType()) {
            case CLAIMMODIFICATION:
                var claimModification = (com.rbkmoney.swag.claim_management.model.ClaimModification) swagModification;
                return claimModificationConverter.convertToThrift(claimModification);
            case PARTYMODIFICATION:
                var swagPartyModification = (com.rbkmoney.swag.claim_management.model.PartyModification) swagModification;
                return partyModificationConverter.convertToThrift(swagPartyModification);
            default:
                throw new IllegalArgumentException("Unknown claim management modification type: " + swagModification);
        }
    }

    @Override
    public ClaimSearchQuery convertSearchClaimsToThrift(String partyId,
                                                        Integer limit,
                                                        String continuationToken,
                                                        List<String> claimStatuses) {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId(partyId);
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
            switch (sourceClaimStatus) {
                case PENDING:
                    status.setPending(new ClaimPending());
                    break;
                case REVIEW:
                    status.setReview(new ClaimReview());
                    break;
                case PENDING_ACCEPTANCE:
                    status.setPendingAcceptance(new ClaimPendingAcceptance());
                    break;
                case ACCEPTED:
                    status.setAccepted(new ClaimAccepted());
                    break;
                case DENIED:
                    status.setDenied(new ClaimDenied());
                    break;
                case REVOKED:
                    status.setRevoked(new ClaimRevoked());
                    break;
                default:
                    throw new IllegalArgumentException("Claim status not found: " + sourceClaimStatus);
            }
            claimStatuses.add(status);
        }
        return claimStatuses;
    }

}
