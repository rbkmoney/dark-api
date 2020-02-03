package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.ContractParams;
import com.rbkmoney.damsel.domain.ContractTemplateRef;
import com.rbkmoney.damsel.domain.PaymentInstitutionRef;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTPARAMS;

@Component
public class ContractModificationCreationConverter
        implements DarkApiConverter<ContractParams, com.rbkmoney.swag.claim_management.model.ContractParams> {

    @Override
    public ContractParams convertToThrift(com.rbkmoney.swag.claim_management.model.ContractParams swagContractParams) {
        ContractParams params = new ContractParams();
        params.setContractorId(swagContractParams.getContractorID());

        if (swagContractParams.getTemplate() != null) {
            ContractTemplateRef contractTemplateRef = new ContractTemplateRef();
            contractTemplateRef.setId(swagContractParams.getTemplate().getId());
            params.setTemplate(contractTemplateRef);
        }

        if (swagContractParams.getPaymentInstitution() != null) {
            PaymentInstitutionRef paymentInstitution = new PaymentInstitutionRef();
            paymentInstitution.setId(swagContractParams.getPaymentInstitution().getId());
            params.setPaymentInstitution(paymentInstitution);
        }
        return params;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ContractParams convertToSwag(ContractParams creation) {
        var swagContractParams = new com.rbkmoney.swag.claim_management.model.ContractParams();
        swagContractParams.setContractModificationType(CONTRACTPARAMS);
        swagContractParams.setContractorID(creation.getContractorId());

        PaymentInstitutionRef paymentInstitution = creation.getPaymentInstitution();
        if (paymentInstitution != null) {
            var paymentInstitutionRef = new com.rbkmoney.swag.claim_management.model.PaymentInstitutionRef();
            paymentInstitutionRef.setId(paymentInstitution.getId());
            swagContractParams.setPaymentInstitution(paymentInstitutionRef);
        }

        ContractTemplateRef template = creation.getTemplate();
        if (template != null) {
            var contractTemplateRef = new com.rbkmoney.swag.claim_management.model.ContractTemplateRef();
            contractTemplateRef.setId(template.getId());
            swagContractParams.setTemplate(contractTemplateRef);
        }

        return swagContractParams;
    }

}
