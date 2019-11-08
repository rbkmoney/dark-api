package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ClaimModificationType.ClaimModificationTypeEnum.DOCUMENTMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.DocumentModification.DocumentModificationTypeEnum.DOCUMENTCREATED;

@Component
public class ClaimDocumentModificationConverter
        implements DarkApiConverter<DocumentModificationUnit, com.rbkmoney.swag.claim_management.model.DocumentModificationUnit> {

    @Override
    public DocumentModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.DocumentModificationUnit swagDocModificationUnit
    ) {
        DocumentModificationUnit docModificationUnit = new DocumentModificationUnit();
        docModificationUnit.setId(swagDocModificationUnit.getDocumentId());

        switch (swagDocModificationUnit.getDocumentModification().getDocumentModificationType()) {
            case DOCUMENTCREATED:
                DocumentModification docModification = new DocumentModification();
                docModification.setCreation(new DocumentCreated());
                docModificationUnit.setModification(docModification);
                break;
            default:
                throw new IllegalArgumentException("DocumentModificationType not found: " +
                        swagDocModificationUnit.getDocumentModification().getDocumentModificationType());
        }

        return docModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.DocumentModificationUnit convertToSwag(
            DocumentModificationUnit documentModification
    ) {
        var swagDocumentModificationUnit = new com.rbkmoney.swag.claim_management.model.DocumentModificationUnit();
        swagDocumentModificationUnit.setDocumentId(documentModification.getId());
        swagDocumentModificationUnit.setClaimModificationType(DOCUMENTMODIFICATIONUNIT);
        var swagDocumentModification = new com.rbkmoney.swag.claim_management.model.DocumentModification();

        if (documentModification.getModification().isSetCreation()) {
            swagDocumentModification.setDocumentModificationType(DOCUMENTCREATED);
        } else {
            throw new IllegalArgumentException("Unknown document modification type!");
        }

        swagDocumentModificationUnit.setDocumentModification(swagDocumentModification);
        return swagDocumentModificationUnit;
    }

}
