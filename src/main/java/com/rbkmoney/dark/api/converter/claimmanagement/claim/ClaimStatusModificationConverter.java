package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ClaimModification.ClaimModificationTypeEnum.STATUSMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.StatusModification.StatusModificationTypeEnum.STATUSCHANGED;
import static com.rbkmoney.swag.claim_management.model.StatusModificationUnit.StatusEnum.*;

@Component
public class ClaimStatusModificationConverter
        implements DarkApiConverter<ClaimStatus, com.rbkmoney.swag.claim_management.model.StatusModificationUnit> {

    @Override
    public ClaimStatus convertToThrift(
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
                status.getDenied().setReason(swagStatusModificationUnit.getReason());
                return status;
            case REVOKED:
                status.setRevoked(new ClaimRevoked());
                status.getRevoked().setReason(swagStatusModificationUnit.getReason());
                return status;
            default:
                throw new IllegalArgumentException("Status " + swagStatusModificationUnit.getStatus() +
                        "in swagStatusModificationUnit not found!");
        }
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.StatusModificationUnit convertToSwag(ClaimStatus claimStatus) {
        var swagStatus = new com.rbkmoney.swag.claim_management.model.StatusModificationUnit();
        swagStatus.setModificationType(CLAIMMODIFICATION);
        swagStatus.setClaimModificationType(STATUSMODIFICATIONUNIT);

        if (claimStatus.isSetAccepted()) {
            swagStatus.setStatus(ACCEPTED);
        } else if (claimStatus.isSetDenied()) {
            swagStatus.setStatus(DENIED);
            swagStatus.setReason(claimStatus.getDenied().getReason());
        } else if (claimStatus.isSetPending()) {
            swagStatus.setStatus(PENDING);
        } else if (claimStatus.isSetPendingAcceptance()) {
            swagStatus.setStatus(PENDINGACCEPTANCE);
        } else if (claimStatus.isSetReview()) {
            swagStatus.setStatus(REVIEW);
        } else if (claimStatus.isSetRevoked()) {
            swagStatus.setStatus(REVOKED);
            swagStatus.setReason(claimStatus.getRevoked().getReason());
        } else {
            throw new IllegalArgumentException("Claim status not found!");
        }

        var swagStatusModification = new com.rbkmoney.swag.claim_management.model.StatusModification();
        swagStatusModification.setStatusModificationType(STATUSCHANGED);
        swagStatus.setModification(swagStatusModification);
        return swagStatus;
    }

}
