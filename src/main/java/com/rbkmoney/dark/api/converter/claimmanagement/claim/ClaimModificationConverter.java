package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;

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

    private final DarkApiConverter<ExternalInfoModificationUnit,
            com.rbkmoney.swag.claim_management.model.ExternalInfoModificationUnit> extInfoModificationUnitConverter;

    @Override
    public Modification convertToThrift(
            com.rbkmoney.swag.claim_management.model.ClaimModification swagClaimModification) {
        Modification modification = new Modification();
        var claimModification =
                new com.rbkmoney.damsel.claim_management.ClaimModification(); // todo: напилить проверку на null
        var swagModificationType = swagClaimModification.getClaimModificationType();

        switch (swagModificationType.getClaimModificationType()) {
            case DOCUMENTMODIFICATIONUNIT:
                var swagDocModification =
                        (com.rbkmoney.swag.claim_management.model.DocumentModificationUnit) swagModificationType;
                claimModification
                        .setDocumentModification(documentModificationConverter.convertToThrift(swagDocModification));
                break;
            case COMMENTMODIFICATIONUNIT:
                var commentModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.CommentModificationUnit) swagModificationType;
                claimModification.setCommentModification(
                        commentModificationUnitConverter.convertToThrift(commentModificationUnit));
                break;
            case STATUSMODIFICATIONUNIT:
                var swagStatusModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.StatusModificationUnit) swagModificationType;
                claimModification.setStatusModification(
                        statusModificationUnitConverter.convertToThrift(swagStatusModificationUnit));
                break;
            case FILEMODIFICATIONUNIT:
                var fileModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.FileModificationUnit) swagModificationType;
                claimModification
                        .setFileModification(fileModificationUnitConverter.convertToThrift(fileModificationUnit));
                break;
            case EXTERNALINFOMODIFICATIONUNIT:
                var extInfoModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ExternalInfoModificationUnit) swagModificationType;
                claimModification.setExternalInfoModification(
                        extInfoModificationUnitConverter.convertToThrift(extInfoModificationUnit)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown claim modification type: " + swagModificationType);
        }
        modification.setClaimModification(claimModification);
        return modification;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ClaimModification convertToSwag(Modification unit) {
        var swagClaimModification = new com.rbkmoney.swag.claim_management.model.ClaimModification();
        ClaimModification claimModification = unit.getClaimModification();
        if (claimModification.isSetDocumentModification()) {
            DocumentModificationUnit documentModification = claimModification.getDocumentModification();
            swagClaimModification
                    .setClaimModificationType(documentModificationConverter.convertToSwag(documentModification));
        } else if (claimModification.isSetCommentModification()) {
            CommentModificationUnit commentModification = claimModification.getCommentModification();
            swagClaimModification
                    .setClaimModificationType(commentModificationUnitConverter.convertToSwag(commentModification));
        } else if (claimModification.isSetStatusModification()) {
            StatusModificationUnit statusModification = claimModification.getStatusModification();
            swagClaimModification
                    .setClaimModificationType(statusModificationUnitConverter.convertToSwag(statusModification));
        } else if (claimModification.isSetFileModification()) {
            FileModificationUnit fileModificationUnit = claimModification.getFileModification();
            swagClaimModification
                    .setClaimModificationType(fileModificationUnitConverter.convertToSwag(fileModificationUnit));
        } else if (claimModification.isSetExternalInfoModification()) {
            ExternalInfoModificationUnit externalInfoModificationUnit = claimModification.getExternalInfoModification();
            swagClaimModification.setClaimModificationType(
                    extInfoModificationUnitConverter.convertToSwag(externalInfoModificationUnit)
            );
        } else {
            throw new IllegalArgumentException("Unknown claim modification type!");
        }
        swagClaimModification.setModificationType(CLAIMMODIFICATION);
        return swagClaimModification;
    }

}
