package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.Contractor.ContractorTypeEnum.REGISTEREDUSER;
import static com.rbkmoney.swag.claim_management.model.ContractorModification.ContractorModificationTypeEnum.CONTRACTOR;

@Component
@RequiredArgsConstructor
public class ContractorConverter implements DarkApiConverter<Contractor, com.rbkmoney.swag.claim_management.model.Contractor> {

    private final DarkApiConverter<LegalEntity,
            com.rbkmoney.swag.claim_management.model.LegalEntity> legalEntityConverter;

    private final DarkApiConverter<PrivateEntity,
            com.rbkmoney.swag.claim_management.model.PrivateEntity> privateEntityConverter;

    @Override
    public Contractor convertToThrift(com.rbkmoney.swag.claim_management.model.Contractor swagContractor) {
        Contractor contractor = new Contractor();
        switch (swagContractor.getContractorType()) {
            case LEGALENTITY:
                var swagLegalEntity = (com.rbkmoney.swag.claim_management.model.LegalEntity) swagContractor;
                contractor.setLegalEntity(legalEntityConverter.convertToThrift(swagLegalEntity));
                break;
            case PRIVATEENTITY:
                var swagPrivateEntity = (com.rbkmoney.swag.claim_management.model.PrivateEntity) swagContractor;
                contractor.setPrivateEntity(privateEntityConverter.convertToThrift(swagPrivateEntity));
                break;
            case REGISTEREDUSER:
                var swagRegisteredUser = (com.rbkmoney.swag.claim_management.model.RegisteredUser) swagContractor;
                contractor.setRegisteredUser(
                        new RegisteredUser().setEmail(swagRegisteredUser.getEmail())
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown contractor type: " + swagContractor.getContractorType());
        }
        return contractor;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.Contractor convertToSwag(Contractor creation) {
        if (creation.isSetLegalEntity()) {
            return legalEntityConverter.convertToSwag(creation.getLegalEntity());
        } else if (creation.isSetPrivateEntity()) {
            PrivateEntity privateEntity = creation.getPrivateEntity();

            if (privateEntity.isSetRussianPrivateEntity()) {
                return privateEntityConverter.convertToSwag(privateEntity);
            } else {
                throw new IllegalArgumentException("Unknown private entity type!");
            }
        } else if (creation.isSetRegisteredUser()) {
            RegisteredUser registeredUser = creation.getRegisteredUser();
            var swagRegisteredUser = new com.rbkmoney.swag.claim_management.model.RegisteredUser();
            swagRegisteredUser.setContractorType(REGISTEREDUSER);
            swagRegisteredUser.setContractorModificationType(CONTRACTOR);
            swagRegisteredUser.setEmail(registeredUser.getEmail());

            return swagRegisteredUser;
        } else {
            throw new IllegalArgumentException("Unknown contractor type!");
        }
    }

}
