package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ContractLegalAgreementBindingModification;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTLEGALAGREEMENTBINDINGMODIFICATION;

@Component
public class LegalAgreementConverter
        implements DarkApiConverter<LegalAgreement, ContractLegalAgreementBindingModification> {

    @Override
    public LegalAgreement convertToThrift(ContractLegalAgreementBindingModification swagContractLegalAgreementBindingModification) {
        var swagLegalAgreement = swagContractLegalAgreementBindingModification.getLegalAgreement();
        return new LegalAgreement()
                .setLegalAgreementId(swagLegalAgreement.getLegalAgreementID())
                .setSignedAt(swagLegalAgreement.getSignedAt())
                .setValidUntil(swagLegalAgreement.getValidUntil());
    }

    @Override
    public ContractLegalAgreementBindingModification convertToSwag(LegalAgreement legalAgreementBinding) {
        var contractLegalAgreementBindingModification = new ContractLegalAgreementBindingModification();
        contractLegalAgreementBindingModification.setLegalAgreement(
                new com.rbkmoney.swag.claim_management.model.LegalAgreement()
                        .legalAgreementID(legalAgreementBinding.getLegalAgreementId())
                        .signedAt(legalAgreementBinding.getSignedAt())
                        .validUntil(legalAgreementBinding.getValidUntil())

        );
        contractLegalAgreementBindingModification.setContractModificationType(CONTRACTLEGALAGREEMENTBINDINGMODIFICATION);
        return contractLegalAgreementBindingModification;
    }

}
