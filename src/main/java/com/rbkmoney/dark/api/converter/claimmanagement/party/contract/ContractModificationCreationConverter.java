package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.ContractParams;
import com.rbkmoney.damsel.domain.ContractTemplateRef;
import com.rbkmoney.damsel.domain.PaymentInstitutionRef;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ContractCreationModification;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTCREATIONMODIFICATION;

@Component
public class ContractModificationCreationConverter
        implements DarkApiConverter<ContractParams, ContractCreationModification> {

    @Override
    public ContractParams convertToThrift(ContractCreationModification swagContractCreationModification) {
        ContractParams params = new ContractParams();
        params.setContractorId(swagContractCreationModification.getContractorID());

        if (swagContractCreationModification.getTemplate() != null) {
            ContractTemplateRef contractTemplateRef = new ContractTemplateRef();
            contractTemplateRef.setId(swagContractCreationModification.getTemplate().getId());
            params.setTemplate(contractTemplateRef);
        }

        if (swagContractCreationModification.getPaymentInstitution() != null) {
            PaymentInstitutionRef paymentInstitution = new PaymentInstitutionRef();
            paymentInstitution.setId(swagContractCreationModification.getPaymentInstitution().getId());
            params.setPaymentInstitution(paymentInstitution);
        }
        return params;
    }

    @Override
    public ContractCreationModification convertToSwag(ContractParams creation) {
        var swagContractCreationModification = new ContractCreationModification();
        swagContractCreationModification.setContractModificationType(CONTRACTCREATIONMODIFICATION);
        swagContractCreationModification.setContractorID(creation.getContractorId());

        PaymentInstitutionRef paymentInstitution = creation.getPaymentInstitution();
        if (paymentInstitution != null) {
            var paymentInstitutionRef = new com.rbkmoney.swag.claim_management.model.PaymentInstitutionRef();
            paymentInstitutionRef.setId(paymentInstitution.getId());
            swagContractCreationModification.setPaymentInstitution(paymentInstitutionRef);
        }

        ContractTemplateRef template = creation.getTemplate();
        if (template != null) {
            var contractTemplateRef = new com.rbkmoney.swag.claim_management.model.ContractTemplateRef();
            contractTemplateRef.setId(template.getId());
            swagContractCreationModification.setTemplate(contractTemplateRef);
        }

        return swagContractCreationModification;
    }

}
