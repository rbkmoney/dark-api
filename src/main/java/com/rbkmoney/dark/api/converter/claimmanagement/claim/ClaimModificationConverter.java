package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClaimModificationConverter
        implements DarkApiConverter<Modification, com.rbkmoney.swag.claim_management.model.ClaimModification> {

    private final DarkApiConverter<DocumentModificationUnit,
            com.rbkmoney.swag.claim_management.model.DocumentModificationUnit> documentModificationConverter;

    private final DarkApiConverter<CommentModificationUnit,
            com.rbkmoney.swag.claim_management.model.CommentModificationUnit> commentModificationUnitConverter;

    private final DarkApiConverter<StatusModificationUnit,
            com.rbkmoney.swag.claim_management.model.StatusModificationUnit> statusModificationUnitConverter;

    private final DarkApiConverter<FileModificationUnit,
            com.rbkmoney.swag.claim_management.model.FileModificationUnit> fileModificationUnitConverter;

    @Override
    public Modification convertToThrift(com.rbkmoney.swag.claim_management.model.ClaimModification swagClaimModification) {
        Modification modification = new Modification();
        var claimModification = new com.rbkmoney.damsel.claim_management.ClaimModification();

        switch (swagClaimModification.getClaimModificationType()) {
            case DOCUMENTMODIFICATIONUNIT:
                var swagDocModification = (com.rbkmoney.swag.claim_management.model.DocumentModificationUnit) swagClaimModification;
                claimModification.setDocumentModification(documentModificationConverter.convertToThrift(swagDocModification));
                break;
            case COMMENTMODIFICATIONUNIT:
                var commentModificationUnit = (com.rbkmoney.swag.claim_management.model.CommentModificationUnit) swagClaimModification;
                claimModification.setCommentModification(commentModificationUnitConverter.convertToThrift(commentModificationUnit));
                break;
            case STATUSMODIFICATIONUNIT:
                var swagStatusModificationUnit = (com.rbkmoney.swag.claim_management.model.StatusModificationUnit) swagClaimModification;
                claimModification.setStatusModification(statusModificationUnitConverter.convertToThrift(swagStatusModificationUnit));
                break;
            case FILEMODIFICATIONUNIT:
                var fileModificationUnit = (com.rbkmoney.swag.claim_management.model.FileModificationUnit) swagClaimModification;
                claimModification.setFileModification(fileModificationUnitConverter.convertToThrift(fileModificationUnit));
                break;
            default:
                throw new IllegalArgumentException("Unknown claim modification type: " +
                        swagClaimModification.getClaimModificationType());
        }
        modification.setClaimModfication(claimModification);
        return modification;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ClaimModification convertToSwag(Modification unit) {
        ClaimModification claimModfication = unit.getClaimModfication();
        if (claimModfication.isSetDocumentModification()) {
            DocumentModificationUnit documentModification = claimModfication.getDocumentModification();
            return documentModificationConverter.convertToSwag(documentModification);
        } else if (claimModfication.isSetCommentModification()) {
            CommentModificationUnit commentModification = claimModfication.getCommentModification();
            return commentModificationUnitConverter.convertToSwag(commentModification);
        } else if (claimModfication.isSetStatusModification()) {
            StatusModificationUnit statusModification = claimModfication.getStatusModification();
            return statusModificationUnitConverter.convertToSwag(statusModification);
        } else if (claimModfication.isSetFileModification()) {
            FileModificationUnit fileModificationUnit = claimModfication.getFileModification();
            return fileModificationUnitConverter.convertToSwag(fileModificationUnit);
        } else {
            throw new IllegalArgumentException("Unknown claim modification type!");
        }
    }

}
