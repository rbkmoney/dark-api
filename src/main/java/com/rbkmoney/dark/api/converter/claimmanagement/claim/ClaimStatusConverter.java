package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.dark.api.converter.claimmanagement.data.ThriftClaimStatus.*;

@Component
public class ClaimStatusConverter implements DarkApiConverter<ClaimStatus, String> {

    @Override
    public ClaimStatus convertToThrift(String sourceStatus) {
        ClaimStatus status = new ClaimStatus();
        switch (sourceStatus) {
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
                throw new IllegalArgumentException("Claim status not found: " + sourceStatus);
        }
        return status;
    }

    @Override
    public String convertToSwag(ClaimStatus claimStatus) {
        if (claimStatus.isSetPending()) {
            return PENDING;
        } else if (claimStatus.isSetReview()) {
            return REVIEW;
        } else if (claimStatus.isSetPendingAcceptance()) {
            return PENDING_ACCEPTANCE;
        } else if (claimStatus.isSetAccepted()) {
            return ACCEPTED;
        } else if (claimStatus.isSetDenied()) {
            return DENIED;
        } else if (claimStatus.isSetRevoked()) {
            return REVOKED;
        } else {
            throw new IllegalArgumentException("Claim status not found: " + claimStatus);
        }
    }

}
