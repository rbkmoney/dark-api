package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.CommentCreated;
import com.rbkmoney.damsel.claim_management.CommentModification;
import com.rbkmoney.damsel.claim_management.CommentModificationUnit;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ClaimModificationType.ClaimModificationTypeEnum.COMMENTMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.CommentModification.CommentModificationTypeEnum.COMMENTCREATED;

@Component
public class ClaimCommentModificationUnitConverter
        implements DarkApiConverter<CommentModificationUnit, com.rbkmoney.swag.claim_management.model.CommentModificationUnit> {

    @Override
    public CommentModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.CommentModificationUnit swagCommentModificationUnit
    ) {
        CommentModificationUnit commentModificationUnit = new CommentModificationUnit();
        commentModificationUnit.setId(swagCommentModificationUnit.getCommentId());
        var commentModificationType = swagCommentModificationUnit.getCommentModification().getCommentModificationType();

        switch (commentModificationType) {
            case COMMENTCREATED:
                CommentModification commentModification = new CommentModification();
                commentModification.setCreation(new CommentCreated());
                commentModificationUnit.setModification(commentModification);
                break;
            default:
                throw new IllegalArgumentException("Type " + commentModificationType +
                        " in swagCommentModificationUnit not found!");
        }

        return commentModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.CommentModificationUnit convertToSwag(
            CommentModificationUnit commentModification
    ) {
        var swagCommentModificationUnit = new com.rbkmoney.swag.claim_management.model.CommentModificationUnit();
        swagCommentModificationUnit.setCommentId(commentModification.getId());
        swagCommentModificationUnit.setClaimModificationType(COMMENTMODIFICATIONUNIT);
        var swagCommentModification = new com.rbkmoney.swag.claim_management.model.CommentModification();

        if (commentModification.getModification().isSetCreation()) {
            swagCommentModification.setCommentModificationType(COMMENTCREATED);
        } else {
            throw new IllegalArgumentException("Unknown comment modification type!");
        }

        swagCommentModificationUnit.setCommentModification(swagCommentModification);
        return swagCommentModificationUnit;
    }

}
