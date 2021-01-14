package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ClaimModificationType.ClaimModificationTypeEnum.FILEMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.FileModification.FileModificationTypeEnum.*;

@Component
public class ClaimFileModificationUnitConverter
        implements DarkApiConverter<FileModificationUnit, com.rbkmoney.swag.claim_management.model.FileModificationUnit> {

    @Override
    public FileModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.FileModificationUnit swagFileModificationUnit
    ) {
        FileModificationUnit fileModificationUnit = new FileModificationUnit();
        fileModificationUnit.setId(swagFileModificationUnit.getFileId());

        switch (swagFileModificationUnit.getFileModification().getFileModificationType()) {
            case FILECREATED:
                fileModificationUnit.setModification(FileModification.creation(new FileCreated()));
                return fileModificationUnit;
            case FILEDELETED:
                fileModificationUnit.setModification(FileModification.deletion(new FileDeleted()));
                return fileModificationUnit;
            case FILECHANGED:
                fileModificationUnit.setModification(FileModification.changed(new FileChanged()));
                return fileModificationUnit;
            default:
                throw new IllegalArgumentException("Unknown file modification type: " +
                        swagFileModificationUnit.getFileModification().getFileModificationType());
        }
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.FileModificationUnit convertToSwag(
            FileModificationUnit fileModificationUnit
    ) {
        var swagFileModificationUnit = new com.rbkmoney.swag.claim_management.model.FileModificationUnit();
        swagFileModificationUnit.setFileId(fileModificationUnit.getId());
        swagFileModificationUnit.setClaimModificationType(FILEMODIFICATIONUNIT);
        var swagFileModification = new com.rbkmoney.swag.claim_management.model.FileModification();

        if (fileModificationUnit.getModification().isSetCreation()) {
            swagFileModification.setFileModificationType(FILECREATED);
        } else if (fileModificationUnit.getModification().isSetDeletion()) {
            swagFileModification.setFileModificationType(FILEDELETED);
        } else if (fileModificationUnit.getModification().isSetChanged()) {
            swagFileModification.setFileModificationType(FILECHANGED);
        } else {
            throw new IllegalArgumentException("Unknown file modification type!");
        }

        swagFileModificationUnit.setFileModification(swagFileModification);
        return swagFileModificationUnit;
    }

}
