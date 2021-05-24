package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.claim_management.ContractorModification;
import com.rbkmoney.damsel.claim_management.ContractorModificationUnit;
import com.rbkmoney.damsel.domain.Contractor;
import com.rbkmoney.damsel.domain.ContractorIdentificationLevel;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.CONTRACTORMODIFICATIONUNIT;

@Component
@RequiredArgsConstructor
@SuppressWarnings("LineLength")
public class ContractorModificationUnitConverter implements
        DarkApiConverter<ContractorModificationUnit, com.rbkmoney.swag.claim_management.model.ContractorModificationUnit> {

    private final DarkApiConverter<Contractor,
            com.rbkmoney.swag.claim_management.model.Contractor> claimContractorConverter;

    private final DarkApiConverter<ContractorIdentificationLevel,
            com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel> identificationLevelConverter;

    @Override
    public ContractorModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.ContractorModificationUnit swagContractorModificationUnit
    ) {
        ContractorModificationUnit contractorModificationUnit = new ContractorModificationUnit();

        contractorModificationUnit.setId(swagContractorModificationUnit.getId());
        ContractorModification contractorModification = new ContractorModification();

        var swagModification = swagContractorModificationUnit.getModification();
        switch (swagModification.getContractorModificationType()) {
            case CONTRACTOR:
                var swagContractor = (com.rbkmoney.swag.claim_management.model.Contractor) swagModification;
                contractorModification.setCreation(claimContractorConverter.convertToThrift(swagContractor));
                break;
            case CONTRACTORIDENTIFICATIONLEVEL:
                var swagContractorIdentificationLevel =
                        (com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel) swagModification;
                contractorModification.setIdentificationLevelModification(
                        identificationLevelConverter.convertToThrift(swagContractorIdentificationLevel)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown contractor modification type: " +
                        swagModification.getContractorModificationType());
        }

        contractorModificationUnit.setModification(contractorModification);
        return contractorModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ContractorModificationUnit convertToSwag(
            ContractorModificationUnit contractorModificationUnit
    ) {
        var swagContractorModificationUnit = new com.rbkmoney.swag.claim_management.model.ContractorModificationUnit();
        swagContractorModificationUnit.setId(contractorModificationUnit.getId());
        swagContractorModificationUnit.setPartyModificationType(CONTRACTORMODIFICATIONUNIT);

        if (contractorModificationUnit.getModification().isSetCreation()) {
            Contractor creation = contractorModificationUnit.getModification().getCreation();
            swagContractorModificationUnit.setModification(claimContractorConverter.convertToSwag(creation));
        } else if (contractorModificationUnit.getModification().isSetIdentificationLevelModification()) {
            ContractorIdentificationLevel identificationLevelModification =
                    contractorModificationUnit.getModification().getIdentificationLevelModification();
            swagContractorModificationUnit.setModification(
                    identificationLevelConverter.convertToSwag(identificationLevelModification)
            );
        } else {
            throw new IllegalArgumentException("Unknown contractor modification type!");
        }

        return swagContractorModificationUnit;
    }

}
