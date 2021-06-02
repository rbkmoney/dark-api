package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.ExternalInfoModificationUnit;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ClaimModificationType.ClaimModificationTypeEnum.EXTERNALINFOMODIFICATIONUNIT;

@Component
public class ClaimExternalInfoModificationUnitConverter implements
        DarkApiConverter<ExternalInfoModificationUnit,
                com.rbkmoney.swag.claim_management.model.ExternalInfoModificationUnit> {

    @Override
    public ExternalInfoModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.ExternalInfoModificationUnit swagExtInfoModificationUnit
    ) {
        ExternalInfoModificationUnit extInfoModificationUnit = new ExternalInfoModificationUnit();
        extInfoModificationUnit.setDocumentId(swagExtInfoModificationUnit.getDocumentId());
        extInfoModificationUnit.setRoistatId(swagExtInfoModificationUnit.getRoistatId());
        return extInfoModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ExternalInfoModificationUnit convertToSwag(
            ExternalInfoModificationUnit extInfoModificationUnit
    ) {
        var swagExtInfoModificationUnit =
                new com.rbkmoney.swag.claim_management.model.ExternalInfoModificationUnit();
        swagExtInfoModificationUnit.setDocumentId(extInfoModificationUnit.getDocumentId());
        swagExtInfoModificationUnit.setRoistatId(extInfoModificationUnit.getRoistatId());
        swagExtInfoModificationUnit.setClaimModificationType(EXTERNALINFOMODIFICATIONUNIT);
        return swagExtInfoModificationUnit;
    }

}
