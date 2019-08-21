package com.rbkmoney.dark.api.converter.claimmanagement.claim;

import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ClaimModification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModificationConverter
        implements DarkApiConverter<Modification, com.rbkmoney.swag.claim_management.model.Modification> {

    private final DarkApiConverter<Modification,
            ClaimModification> claimModificationConverter;

    private final DarkApiConverter<Modification,
            com.rbkmoney.swag.claim_management.model.PartyModification> partyModificationConverter;

    @Override
    public Modification convertToThrift(com.rbkmoney.swag.claim_management.model.Modification swagModification) {
        switch (swagModification.getModificationType()) {
            case CLAIMMODIFICATION:
                var claimModification = (com.rbkmoney.swag.claim_management.model.ClaimModification) swagModification;
                return claimModificationConverter.convertToThrift(claimModification);
            case PARTYMODIFICATION:
                var swagPartyModification = (com.rbkmoney.swag.claim_management.model.PartyModification) swagModification;
                return partyModificationConverter.convertToThrift(swagPartyModification);
            default:
                throw new IllegalArgumentException("Unknown claim management modification type: " + swagModification);
        }
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.Modification convertToSwag(Modification modification) {
        if (modification.isSetClaimModfication()) {
            return claimModificationConverter.convertToSwag(modification);
        } else if (modification.isSetPartyModification()) {
            return partyModificationConverter.convertToSwag(modification);
        } else {
            throw new IllegalArgumentException("Unknown modification type!");
        }
    }

}
