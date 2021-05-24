package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.domain.InternationalLegalEntity;
import com.rbkmoney.damsel.domain.LegalEntity;
import com.rbkmoney.damsel.domain.RussianLegalEntity;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractorType.ContractorTypeEnum.LEGALENTITY;

@Component
@RequiredArgsConstructor
public class ClaimLegalEntityConverter
        implements DarkApiConverter<LegalEntity, com.rbkmoney.swag.claim_management.model.LegalEntity> {

    private final DarkApiConverter<InternationalLegalEntity,
            com.rbkmoney.swag.claim_management.model.InternationalLegalEntity> internationalLegalEntityConverter;

    private final DarkApiConverter<RussianLegalEntity,
            com.rbkmoney.swag.claim_management.model.RussianLegalEntity> russianLegalEntityConverter;

    @Override
    public LegalEntity convertToThrift(com.rbkmoney.swag.claim_management.model.LegalEntity swagLegalEntity) {
        LegalEntity legalEntity = new LegalEntity();
        var swagLegalEntityType = swagLegalEntity.getLegalEntityType();

        switch (swagLegalEntityType.getLegalEntityType()) {
            case RUSSIANLEGALENTITY:
                var swagRussianLegalEntity =
                        (com.rbkmoney.swag.claim_management.model.RussianLegalEntity) swagLegalEntityType;
                legalEntity.setRussianLegalEntity(russianLegalEntityConverter.convertToThrift(swagRussianLegalEntity));
                break;
            case INTERNATIONALLEGALENTITY:
                var swagInternationalLegalEntity =
                        (com.rbkmoney.swag.claim_management.model.InternationalLegalEntity) swagLegalEntityType;
                legalEntity.setInternationalLegalEntity(
                        internationalLegalEntityConverter.convertToThrift(swagInternationalLegalEntity)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown legal entity type: " + swagLegalEntityType);
        }
        return legalEntity;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.LegalEntity convertToSwag(LegalEntity legalEntity) {
        var swagLegalEntity = new com.rbkmoney.swag.claim_management.model.LegalEntity();
        if (legalEntity.isSetRussianLegalEntity()) {
            RussianLegalEntity russianLegalEntity = legalEntity.getRussianLegalEntity();
            swagLegalEntity.setLegalEntityType(russianLegalEntityConverter.convertToSwag(russianLegalEntity));
        } else if (legalEntity.isSetInternationalLegalEntity()) {
            InternationalLegalEntity internationalLegalEntity = legalEntity.getInternationalLegalEntity();
            swagLegalEntity
                    .setLegalEntityType(internationalLegalEntityConverter.convertToSwag(internationalLegalEntity));
        } else {
            throw new IllegalArgumentException("Unknown legal entity type!");
        }
        swagLegalEntity.setContractorType(LEGALENTITY);
        return swagLegalEntity;
    }

}
