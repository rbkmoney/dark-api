package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.FileCreated;
import com.rbkmoney.damsel.claim_management.FileModification;
import com.rbkmoney.damsel.claim_management.FileModificationUnit;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ClaimModificationType.ClaimModificationTypeEnum.FILEMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.FileModification.FileModificationTypeEnum.FILECREATED;

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
                FileModification fileModification = new FileModification();
                fileModification.setCreation(new FileCreated());
                fileModificationUnit.setModification(fileModification);
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
            swagFileModificationUnit.setFileModification(swagFileModification);
            return swagFileModificationUnit;
        } else {
            throw new IllegalArgumentException("Unknown file modification type!");
        }
    }

}
