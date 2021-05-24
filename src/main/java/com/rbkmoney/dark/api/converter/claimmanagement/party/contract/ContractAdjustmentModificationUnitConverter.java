package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.ContractAdjustmentModification;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentModificationUnit;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentParams;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTADJUSTMENTMODIFICATIONUNIT;

@Component
@SuppressWarnings("LineLength")
public class ContractAdjustmentModificationUnitConverter implements
        DarkApiConverter<ContractAdjustmentModificationUnit, com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit> {

    @Override
    public ContractAdjustmentModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit swagAdjustmentModificationUnit
    ) {
        ContractAdjustmentModificationUnit adjustmentModificationUnit = new ContractAdjustmentModificationUnit();
        adjustmentModificationUnit.setAdjustmentId(swagAdjustmentModificationUnit.getAdjustmentID());
        ContractAdjustmentModification adjustmentModification = new ContractAdjustmentModification();
        ContractAdjustmentParams contractAdjustmentParams = new ContractAdjustmentParams();

        contractAdjustmentParams.setTemplate(
                new com.rbkmoney.damsel.domain.ContractTemplateRef()
                        .setId(swagAdjustmentModificationUnit.getModification().getCreation().getTemplate().getId())
        );

        adjustmentModification.setCreation(contractAdjustmentParams);
        adjustmentModificationUnit.setModification(adjustmentModification);
        return adjustmentModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit convertToSwag(
            ContractAdjustmentModificationUnit adjustmentModification
    ) {
        var swagContractAdjustmentModificationUnit =
                new com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit();
        swagContractAdjustmentModificationUnit.setAdjustmentID(adjustmentModification.getAdjustmentId());
        swagContractAdjustmentModificationUnit.setContractModificationType(CONTRACTADJUSTMENTMODIFICATIONUNIT);

        if (adjustmentModification.getModification().isSetCreation()) {
            var swagContractAdjustmentModification =
                    new com.rbkmoney.swag.claim_management.model.ContractAdjustmentModification();
            var swagContractAdjustmentParams = new com.rbkmoney.swag.claim_management.model.ContractAdjustmentParams();
            var contractTemplateRef = new com.rbkmoney.swag.claim_management.model.ContractTemplateRef();
            contractTemplateRef.setId(adjustmentModification.getModification().getCreation().getTemplate().getId());
            swagContractAdjustmentParams.setTemplate(contractTemplateRef);
            swagContractAdjustmentModification.setCreation(swagContractAdjustmentParams);

            swagContractAdjustmentModificationUnit.setModification(swagContractAdjustmentModification);
            return swagContractAdjustmentModificationUnit;
        } else {
            throw new IllegalArgumentException("Unknown adjustment modification type!");
        }
    }

}
