package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.LEGALAGREEMENT;

@Component
public class LegalAgreementConverter
        implements DarkApiConverter<LegalAgreement, com.rbkmoney.swag.claim_management.model.LegalAgreement> {

    @Override
    public LegalAgreement convertToThrift(com.rbkmoney.swag.claim_management.model.LegalAgreement swagLegalAgreement) {
        return new LegalAgreement()
                .setLegalAgreementId(swagLegalAgreement.getLegalAgreementID())
                .setSignedAt(swagLegalAgreement.getSignedAt())
                .setValidUntil(swagLegalAgreement.getValidUntil());
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.LegalAgreement convertToSwag(LegalAgreement legalAgreementBinding) {
        var swagLegalAgreement = new com.rbkmoney.swag.claim_management.model.LegalAgreement();
        swagLegalAgreement.setLegalAgreementID(legalAgreementBinding.getLegalAgreementId());
        swagLegalAgreement.setSignedAt(legalAgreementBinding.getSignedAt());
        swagLegalAgreement.setValidUntil(legalAgreementBinding.getValidUntil());
        swagLegalAgreement.setContractModificationType(LEGALAGREEMENT);
        return swagLegalAgreement;
    }

}
