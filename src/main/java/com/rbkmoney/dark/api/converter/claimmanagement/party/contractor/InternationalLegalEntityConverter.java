package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.domain.InternationalLegalEntity;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.Contractor.ContractorTypeEnum.LEGALENTITY;
import static com.rbkmoney.swag.claim_management.model.ContractorModification.ContractorModificationTypeEnum.CONTRACTOR;
import static com.rbkmoney.swag.claim_management.model.LegalEntity.LegalEntityTypeEnum.INTERNATIONALLEGALENTITY;

@Component
public class InternationalLegalEntityConverter
        implements DarkApiConverter<InternationalLegalEntity, com.rbkmoney.swag.claim_management.model.InternationalLegalEntity> {

    @Override
    public InternationalLegalEntity convertToThrift(
            com.rbkmoney.swag.claim_management.model.InternationalLegalEntity swagInternationalLegalEntity
    ) {
        return new InternationalLegalEntity()
                .setLegalName(swagInternationalLegalEntity.getLegalName())
                .setActualAddress(swagInternationalLegalEntity.getActualAddress())
                .setRegisteredAddress(swagInternationalLegalEntity.getRegisteredAddress())
                .setRegisteredNumber(swagInternationalLegalEntity.getRegisteredNumber())
                .setTradingName(swagInternationalLegalEntity.getTradingName());
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.InternationalLegalEntity convertToSwag(
            InternationalLegalEntity internationalLegalEntity
    ) {
        var swagInternationalLegalEntity =
                new com.rbkmoney.swag.claim_management.model.InternationalLegalEntity();
        swagInternationalLegalEntity.setContractorModificationType(CONTRACTOR);
        swagInternationalLegalEntity.setContractorType(LEGALENTITY);
        swagInternationalLegalEntity.setLegalEntityType(INTERNATIONALLEGALENTITY);
        swagInternationalLegalEntity.setLegalName(internationalLegalEntity.getLegalName());
        swagInternationalLegalEntity.setTradingName(internationalLegalEntity.getTradingName());
        swagInternationalLegalEntity.setActualAddress(internationalLegalEntity.getActualAddress());
        swagInternationalLegalEntity.setRegisteredAddress(internationalLegalEntity.getRegisteredAddress());
        swagInternationalLegalEntity.setRegisteredNumber(internationalLegalEntity.getRegisteredNumber());

        return swagInternationalLegalEntity;
    }

}
