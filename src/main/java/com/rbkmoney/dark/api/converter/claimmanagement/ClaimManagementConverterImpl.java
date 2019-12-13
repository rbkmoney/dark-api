package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.UserInfo;
import com.rbkmoney.swag.claim_management.model.UserInfo.UserTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimManagementConverterImpl implements ClaimManagementConverter {

    private final DarkApiConverter<ClaimStatus, String> claimStatusConverter;

    private final DarkApiConverter<Modification,
            com.rbkmoney.swag.claim_management.model.Modification> modificationConverter;

    @Override
    public com.rbkmoney.swag.claim_management.model.Claim convertClaimToSwag(Claim sourceClaim) {
        var claim = new com.rbkmoney.swag.claim_management.model.Claim();
        claim.setId(sourceClaim.getId());
        claim.setStatus(claimStatusConverter.convertToSwag(sourceClaim.getStatus()));
        claim.setChangeset(convertClaimChangesetToSwag(sourceClaim.getChangeset()));
        claim.setCreatedAt(OffsetDateTime.parse(sourceClaim.getCreatedAt()));
        claim.setUpdatedAt(OffsetDateTime.parse(sourceClaim.getUpdatedAt()));
        claim.setRevision(sourceClaim.getRevision());
        //TODO: метадата это MAP - правильно ли ее так передавать?
        claim.setMetadata(sourceClaim.getMetadata());
        return claim;
    }

    @Override
    public List<com.rbkmoney.swag.claim_management.model.Claim> convertClaimListToSwag(List<Claim> sourceClaimList) {
        return sourceClaimList.stream()
                .map(this::convertClaimToSwag)
                .collect(Collectors.toList());
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ClaimChangeset convertClaimChangesetToSwag(
            List<ModificationUnit> changeset
    ) {
        return changeset.stream()
                .map(this::convertModificationUnit)
                .collect(Collectors.toCollection(com.rbkmoney.swag.claim_management.model.ClaimChangeset::new));
    }

    private com.rbkmoney.swag.claim_management.model.ModificationUnit convertModificationUnit(ModificationUnit unit) {
        var swagModificationUnit = new com.rbkmoney.swag.claim_management.model.ModificationUnit();
        swagModificationUnit.setModificationID(unit.getModificationId());
        swagModificationUnit.setCreatedAt(OffsetDateTime.parse(unit.getCreatedAt()));

        var userInfo = unit.getUserInfo();
        swagModificationUnit.setUserInfo(
                new UserInfo()
                .userId(userInfo.getId())
                .email(userInfo.getEmail())
                .username(userInfo.getUsername())
                .userType(UserTypeEnum.fromValue(userInfo.getType().getSetField().getFieldName()))
        );

        swagModificationUnit.setModification(modificationConverter.convertToSwag(unit.getModification()));
        return swagModificationUnit;
    }

    @Override
    public List<Modification> convertModificationUnitToThrift(
            List<com.rbkmoney.swag.claim_management.model.Modification> unitModifications
    ) {
        return unitModifications.stream()
                .map(modificationConverter::convertToThrift)
                .collect(Collectors.toList());
    }

    @Override
    public ClaimSearchQuery convertSearchClaimsToThrift(String partyId,
                                                        Long claimId,
                                                        Integer limit,
                                                        String continuationToken,
                                                        List<String> claimStatuses) {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId(partyId);
        claimSearchQuery.setClaimId(claimId);
        claimSearchQuery.setLimit(limit);
        claimSearchQuery.setContinuationToken(continuationToken);
        claimSearchQuery.setStatuses(convertStatusesToThrift(claimStatuses));
        return claimSearchQuery;
    }

    @Override
    public List<ClaimStatus> convertStatusesToThrift(List<String> sourceClaimStatuses) {
        return sourceClaimStatuses == null ? null : sourceClaimStatuses.stream()
                .map(claimStatusConverter::convertToThrift)
                .collect(Collectors.toList());
    }

}
