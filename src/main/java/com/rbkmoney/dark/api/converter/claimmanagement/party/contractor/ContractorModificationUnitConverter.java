package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.claim_management.ContractorIdentityDocumentsModification;
import com.rbkmoney.damsel.claim_management.ContractorModification;
import com.rbkmoney.damsel.claim_management.ContractorModificationUnit;
import com.rbkmoney.damsel.domain.Contractor;
import com.rbkmoney.damsel.domain.ContractorIdentificationLevel;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.PARTYMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.PartyModification.PartyModificationTypeEnum.CONTRACTORMODIFICATIONUNIT;

@Component
@RequiredArgsConstructor
public class ContractorModificationUnitConverter
        implements DarkApiConverter<ContractorModificationUnit, com.rbkmoney.swag.claim_management.model.ContractorModificationUnit> {

    private final DarkApiConverter<Contractor,
            com.rbkmoney.swag.claim_management.model.Contractor> contractorConverter;

    private final DarkApiConverter<ContractorIdentityDocumentsModification,
            com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification> documentsModificationConvertor;

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
                contractorModification.setCreation(contractorConverter.convertToThrift(swagContractor));
                break;
            case CONTRACTORIDENTIFICATIONLEVEL:
                var swagContractorIdentificationLevel =
                        (com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel) swagModification;
                contractorModification.setIdentificationLevelModification(
                        identificationLevelConverter.convertToThrift(swagContractorIdentificationLevel)
                );
                break;
            case CONTRACTORIDENTITYDOCUMENTSMODIFICATION:
                var swagContractorIdentityDocumentsModification =
                        (com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification) swagModification;
                contractorModification.setIdentityDocumentsModification(
                        documentsModificationConvertor.convertToThrift(swagContractorIdentityDocumentsModification)
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
        swagContractorModificationUnit.setModificationType(PARTYMODIFICATION);
        swagContractorModificationUnit.setPartyModificationType(CONTRACTORMODIFICATIONUNIT);

        if (contractorModificationUnit.getModification().isSetCreation()) {
            Contractor creation = contractorModificationUnit.getModification().getCreation();
            swagContractorModificationUnit.setModification(contractorConverter.convertToSwag(creation));
        } else if (contractorModificationUnit.getModification().isSetIdentificationLevelModification()) {
            ContractorIdentificationLevel identificationLevelModification =
                    contractorModificationUnit.getModification().getIdentificationLevelModification();
            swagContractorModificationUnit.setModification(
                    identificationLevelConverter.convertToSwag(identificationLevelModification)
            );
        } else if (contractorModificationUnit.getModification().isSetIdentityDocumentsModification()) {
            ContractorIdentityDocumentsModification identityDocumentsModification =
                    contractorModificationUnit.getModification().getIdentityDocumentsModification();
            swagContractorModificationUnit.setModification(
                    documentsModificationConvertor.convertToSwag(identityDocumentsModification)
            );
        } else {
            throw new IllegalArgumentException("Unknown contractor modification type!");
        }

        return swagContractorModificationUnit;
    }

}
