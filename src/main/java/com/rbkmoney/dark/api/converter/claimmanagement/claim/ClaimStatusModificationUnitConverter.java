package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ClaimModification.ClaimModificationTypeEnum.STATUSMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;

@Component
public class ClaimStatusModificationUnitConverter
        implements DarkApiConverter<StatusModificationUnit, com.rbkmoney.swag.claim_management.model.StatusModificationUnit> {

    @Override
    public StatusModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.StatusModificationUnit swagStatusModificationUnit
    ) {
        StatusModificationUnit statusModificationUnit = new StatusModificationUnit();
        statusModificationUnit.setStatus(convertSwagStatusModificationToThrift(swagStatusModificationUnit));

        if (statusModificationUnit.getModification().isSetChange()) {
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
        var swagStatusModificationUnit = new com.rbkmoney.swag.claim_management.model.StatusModificationUnit();
        swagStatusModificationUnit.setModificationType(CLAIMMODIFICATION);
        swagStatusModificationUnit.setClaimModificationType(STATUSMODIFICATIONUNIT);
        if (statusModification.getModification().isSetChange()) {
            swagStatusModificationUnit.setStatus(
                    com.rbkmoney.swag.claim_management.model.StatusModificationUnit.StatusEnum.fromValue(
                            statusModification.getStatus().toString()
                    )
            );
            return swagStatusModificationUnit;
        } else {
            throw new IllegalArgumentException("Unknown status modification type!");
        }
    }

    private ClaimStatus convertSwagStatusModificationToThrift(
            com.rbkmoney.swag.claim_management.model.StatusModificationUnit swagStatusModificationUnit
    ) {
        ClaimStatus status = new ClaimStatus();
        switch (swagStatusModificationUnit.getStatus()) {
            case PENDING:
                status.setPending(new ClaimPending());
                return status;
            case REVIEW:
                status.setReview(new ClaimReview());
                return status;
            case PENDINGACCEPTANCE:
                status.setPendingAcceptance(new ClaimPendingAcceptance());
                return status;
            case ACCEPTED:
                status.setAccepted(new ClaimAccepted());
                return status;
            case DENIED:
                status.setDenied(new ClaimDenied());
                return status;
            case REVOKED:
                status.setRevoked(new ClaimRevoked());
                return status;
            default:
                throw new IllegalArgumentException("Status " + swagStatusModificationUnit.getStatus() +
                        "in swagStatusModificationUnit not found!");
        }
    }

}
