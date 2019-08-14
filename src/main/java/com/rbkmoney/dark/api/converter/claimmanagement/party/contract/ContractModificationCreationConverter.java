package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.ContractParams;
import com.rbkmoney.damsel.domain.ContractTemplateRef;
import com.rbkmoney.damsel.domain.PaymentInstitutionRef;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTPARAMS;

@Slf4j
@Component
@RequiredArgsConstructor
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

        var paymentInstitutionRef = new com.rbkmoney.swag.claim_management.model.PaymentInstitutionRef();
        paymentInstitutionRef.setId(creation.getPaymentInstitution().getId());
        swagContractParams.setPaymentInstitution(paymentInstitutionRef);

        var contractTemplateRef = new com.rbkmoney.swag.claim_management.model.ContractTemplateRef();
        contractTemplateRef.setId(creation.getTemplate().getId());
        swagContractParams.setTemplate(contractTemplateRef);
        return swagContractParams;
    }

}
