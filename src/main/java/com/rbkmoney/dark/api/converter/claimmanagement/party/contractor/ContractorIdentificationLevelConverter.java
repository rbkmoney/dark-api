package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.domain.ContractorIdentificationLevel;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractorModification.ContractorModificationTypeEnum.CONTRACTORIDENTIFICATIONLEVEL;

@Component
public class ContractorIdentificationLevelConverter
        implements
        DarkApiConverter<ContractorIdentificationLevel, com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel> {

    @Override
    public ContractorIdentificationLevel convertToThrift(
            com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel swagContractorIdentificationLevel
    ) {
        return ContractorIdentificationLevel.findByValue(
                swagContractorIdentificationLevel.getContractorIdentificationLevel());
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel convertToSwag(
            ContractorIdentificationLevel identificationLevelModification
    ) {
        var swagContractorIdentificationLevel =
                new com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel();
        swagContractorIdentificationLevel.setContractorModificationType(CONTRACTORIDENTIFICATIONLEVEL);
        swagContractorIdentificationLevel.setContractorIdentificationLevel(identificationLevelModification.getValue());

        return swagContractorIdentificationLevel;
    }

}
