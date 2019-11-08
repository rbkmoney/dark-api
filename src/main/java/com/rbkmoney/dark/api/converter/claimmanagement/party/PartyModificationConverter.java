package com.rbkmoney.dark.api.converter.claimmanagement.party;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.PARTYMODIFICATION;

@Component
@RequiredArgsConstructor
public class PartyModificationConverter
        implements DarkApiConverter<Modification, com.rbkmoney.swag.claim_management.model.PartyModification> {

    private final DarkApiConverter<ContractModificationUnit,
            com.rbkmoney.swag.claim_management.model.ContractModificationUnit> contractModificationUnitConverter;

    private final DarkApiConverter<ContractorModificationUnit,
            com.rbkmoney.swag.claim_management.model.ContractorModificationUnit> contractorModificationUnitConverter;

    private final DarkApiConverter<ShopModificationUnit,
            com.rbkmoney.swag.claim_management.model.ShopModificationUnit> shopModificationUnitConverter;

    @Override
    public Modification convertToThrift(com.rbkmoney.swag.claim_management.model.PartyModification swagPartyModification) {
        Modification modification = new Modification();
        PartyModification partyModification = new PartyModification();
        var swagPartyModificationType = swagPartyModification.getPartyModificationType();

        switch (swagPartyModificationType.getPartyModificationType()) {
            case CONTRACTMODIFICATIONUNIT:
                var swagContractModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ContractModificationUnit) swagPartyModificationType;
                partyModification.setContractModification(
                        contractModificationUnitConverter.convertToThrift(swagContractModificationUnit)
                );
                break;
            case CONTRACTORMODIFICATIONUNIT:
                var swagContractorModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ContractorModificationUnit) swagPartyModificationType;
                partyModification.setContractorModification(
                        contractorModificationUnitConverter.convertToThrift(swagContractorModificationUnit)
                );
                break;
            case SHOPMODIFICATIONUNIT:
                var swagShopModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ShopModificationUnit) swagPartyModificationType;
                partyModification.setShopModification(
                        shopModificationUnitConverter.convertToThrift(swagShopModificationUnit)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown claim management party modification type: " + swagPartyModification);
        }

        modification.setPartyModification(partyModification);
        return modification;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.PartyModification convertToSwag(Modification unit) {
        var swagPartyModification = new com.rbkmoney.swag.claim_management.model.PartyModification();

        PartyModification partyModification = unit.getPartyModification();
        if (partyModification.isSetContractModification()) {
            ContractModificationUnit contractModificationUnit = partyModification.getContractModification();
            swagPartyModification.setPartyModificationType(contractModificationUnitConverter.convertToSwag(contractModificationUnit));
        } else if (partyModification.isSetContractorModification()) {
            ContractorModificationUnit contractorModificationUnit = partyModification.getContractorModification();
            swagPartyModification.setPartyModificationType(contractorModificationUnitConverter.convertToSwag(contractorModificationUnit));
        } else if (partyModification.isSetShopModification()) {
            ShopModificationUnit shopModificationUnit = partyModification.getShopModification();
            swagPartyModification.setPartyModificationType(shopModificationUnitConverter.convertToSwag(shopModificationUnit));
        } else {
            throw new IllegalArgumentException("Unknown party modification type!");
        }
        swagPartyModification.setModificationType(PARTYMODIFICATION);
        return swagPartyModification;
    }

}
