package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.domain.InternationalLegalEntity;
import com.rbkmoney.damsel.domain.LegalEntity;
import com.rbkmoney.damsel.domain.RussianLegalEntity;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

        switch (swagLegalEntity.getLegalEntityType()) {
            case RUSSIANLEGALENTITY:
                var swagRussianLegalEntity =
                        (com.rbkmoney.swag.claim_management.model.RussianLegalEntity) swagLegalEntity;
                legalEntity.setRussianLegalEntity(russianLegalEntityConverter.convertToThrift(swagRussianLegalEntity));
                break;
            case INTERNATIONALLEGALENTITY:
                var swagInternationalLegalEntity =
                        (com.rbkmoney.swag.claim_management.model.InternationalLegalEntity) swagLegalEntity;
                legalEntity.setInternationalLegalEntity(
                        internationalLegalEntityConverter.convertToThrift(swagInternationalLegalEntity)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown legal entity type: " + swagLegalEntity.getLegalEntityType());
        }
        return legalEntity;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.LegalEntity convertToSwag(LegalEntity legalEntity) {
        if (legalEntity.isSetRussianLegalEntity()) {
            RussianLegalEntity russianLegalEntity = legalEntity.getRussianLegalEntity();
            return russianLegalEntityConverter.convertToSwag(russianLegalEntity);
        } else if (legalEntity.isSetInternationalLegalEntity()) {
            InternationalLegalEntity internationalLegalEntity = legalEntity.getInternationalLegalEntity();
            return internationalLegalEntityConverter.convertToSwag(internationalLegalEntity);
        } else {
            throw new IllegalArgumentException("Unknown legal entity type!");
        }
    }

}
