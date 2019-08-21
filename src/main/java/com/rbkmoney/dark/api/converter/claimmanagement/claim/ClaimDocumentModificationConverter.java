package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ClaimModification.ClaimModificationTypeEnum.DOCUMENTMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.DocumentModification.DocumentModificationTypeEnum.DOCUMENTCREATED;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;

@Component
public class ClaimDocumentModificationConverter
        implements DarkApiConverter<DocumentModificationUnit, com.rbkmoney.swag.claim_management.model.DocumentModificationUnit> {

    @Override
    public DocumentModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.DocumentModificationUnit swagDocModificationUnit
    ) {
        DocumentModificationUnit docModificationUnit = new DocumentModificationUnit();
        docModificationUnit.setId(swagDocModificationUnit.getId());

        switch (swagDocModificationUnit.getModification().getDocumentModificationType()) {
            case DOCUMENTCREATED:
                DocumentModification docModification = new DocumentModification();
                docModification.setCreation(new DocumentCreated());
                docModificationUnit.setModification(docModification);
                break;
            default:
                throw new IllegalArgumentException("DocumentModificationType not found: " +
                        swagDocModificationUnit.getModification().getDocumentModificationType());
        }

        return docModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.DocumentModificationUnit convertToSwag(
            DocumentModificationUnit documentModification
    ) {
        var swagDocumentModificationUnit = new com.rbkmoney.swag.claim_management.model.DocumentModificationUnit();
        swagDocumentModificationUnit.setId(documentModification.getId());
        swagDocumentModificationUnit.setModificationType(CLAIMMODIFICATION);
        swagDocumentModificationUnit.setClaimModificationType(DOCUMENTMODIFICATIONUNIT);
        var swagDocumentModification = new com.rbkmoney.swag.claim_management.model.DocumentModification();

        if (documentModification.getModification().isSetCreation()) {
            //TODO: проверить корректность
            //var documentCreated = new com.rbkmoney.swag.claim_management.model.DocumentCreated();
            //documentCreated.setDocumentModificationType(DOCUMENTCREATED);
            //swagDocumentModificationUnit.setModification(documentCreated);

            swagDocumentModification.setDocumentModificationType(DOCUMENTCREATED);
        } else {
            throw new IllegalArgumentException("Unknown document modification type!");
        }

        swagDocumentModificationUnit.setModification(swagDocumentModification);
        return swagDocumentModificationUnit;
    }

}
