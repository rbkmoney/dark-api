package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.ClaimStatus;
import com.rbkmoney.damsel.claim_management.StatusChanged;
import com.rbkmoney.damsel.claim_management.StatusModification;
import com.rbkmoney.damsel.claim_management.StatusModificationUnit;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.StatusModification.StatusModificationTypeEnum.STATUSCHANGED;

@Component
@RequiredArgsConstructor
public class ClaimStatusModificationUnitConverter
        implements
        DarkApiConverter<StatusModificationUnit, com.rbkmoney.swag.claim_management.model.StatusModificationUnit> {

    private final DarkApiConverter<ClaimStatus,
            com.rbkmoney.swag.claim_management.model.StatusModificationUnit> claimStatusModificationConverter;

    @Override
    public StatusModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.StatusModificationUnit swagStatusModificationUnit
    ) {
        StatusModificationUnit statusModificationUnit = new StatusModificationUnit();
        statusModificationUnit.setStatus(claimStatusModificationConverter.convertToThrift(swagStatusModificationUnit));

        if (swagStatusModificationUnit.getStatusModification().getStatusModificationType() == STATUSCHANGED) {
            StatusModification statusModification = new StatusModification();
            statusModification.setChange(new StatusChanged());
            statusModificationUnit.setModification(statusModification);
            return statusModificationUnit;
        } else {
            throw new IllegalArgumentException("Change field for StatusModificationUnit must be set!");
        }
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.StatusModificationUnit convertToSwag(
            StatusModificationUnit statusModification
    ) {
        if (statusModification.getModification().isSetChange()) {
            return claimStatusModificationConverter.convertToSwag(statusModification.getStatus());
        } else {
            throw new IllegalArgumentException("Unknown status modification type!");
        }
    }

}
