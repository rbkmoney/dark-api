package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.domain.ArticlesOfAssociation;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.damsel.domain.RepresentativeDocument;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.PowerOfAttorney;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.RepresentativeDocument.DocumentTypeEnum.ARTICLESOFASSOCIATION;
import static com.rbkmoney.swag.claim_management.model.RepresentativeDocument.DocumentTypeEnum.POWEROFATTORNEY;

@Component
public class RepresentativeDocumentConverter
        implements DarkApiConverter<RepresentativeDocument, com.rbkmoney.swag.claim_management.model.RepresentativeDocument> {

    @Override
    public RepresentativeDocument convertToThrift(com.rbkmoney.swag.claim_management.model.RepresentativeDocument swagDocument) {
        RepresentativeDocument signerDocument = new RepresentativeDocument();

        switch (swagDocument.getDocumentType()) {
            case ARTICLESOFASSOCIATION:
                signerDocument.setArticlesOfAssociation(new ArticlesOfAssociation());
                break;
            case POWEROFATTORNEY:
                var swagPowerOfAttorney = (PowerOfAttorney) swagDocument;
                LegalAgreement powerOfAttorney = new LegalAgreement()
                        .setLegalAgreementId(swagPowerOfAttorney.getLegalAgreementID())
                        .setSignedAt(swagPowerOfAttorney.getSignedAt())
                        .setValidUntil(swagPowerOfAttorney.getValidUntil());
                signerDocument.setPowerOfAttorney(powerOfAttorney);
                break;
            default:
                throw new IllegalArgumentException("Unknown report preferences document type: " + swagDocument);
        }
        return signerDocument;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.RepresentativeDocument convertToSwag(RepresentativeDocument signer) {
        if (signer.isSetArticlesOfAssociation()) {
            var swagArticlesOfAssociation = new com.rbkmoney.swag.claim_management.model.ArticlesOfAssociation();
            swagArticlesOfAssociation.setDocumentType(ARTICLESOFASSOCIATION);
            return swagArticlesOfAssociation;
        } else if (signer.isSetPowerOfAttorney()) {
            var swagPowerOfAttorney = new com.rbkmoney.swag.claim_management.model.PowerOfAttorney();
            swagPowerOfAttorney.setDocumentType(POWEROFATTORNEY);
            LegalAgreement powerOfAttorney = signer.getPowerOfAttorney();
            swagPowerOfAttorney.setLegalAgreementID(powerOfAttorney.getLegalAgreementId());
            swagPowerOfAttorney.setSignedAt(powerOfAttorney.getSignedAt());
            swagPowerOfAttorney.setValidUntil(powerOfAttorney.getValidUntil());
            return swagPowerOfAttorney;
        } else {
            throw new IllegalArgumentException("Unknown representative document type!");
        }
    }

}
