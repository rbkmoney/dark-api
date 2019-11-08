package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractorModification.ContractorModificationTypeEnum.CONTRACTOR;
import static com.rbkmoney.swag.claim_management.model.ContractorType.ContractorTypeEnum.REGISTEREDUSER;

@Component
@RequiredArgsConstructor
public class ClaimContractorConverter
        implements DarkApiConverter<Contractor, com.rbkmoney.swag.claim_management.model.Contractor> {

    private final DarkApiConverter<LegalEntity,
            com.rbkmoney.swag.claim_management.model.LegalEntity> claimLegalEntityConverter;

    private final DarkApiConverter<PrivateEntity,
            com.rbkmoney.swag.claim_management.model.PrivateEntity> privateEntityConverter;

    @Override
    public Contractor convertToThrift(com.rbkmoney.swag.claim_management.model.Contractor swagContractor) {
        Contractor contractor = new Contractor();
        var swagContractorType = swagContractor.getContractorType();

        switch (swagContractorType.getContractorType()) {
            case LEGALENTITY:
                var swagLegalEntity = (com.rbkmoney.swag.claim_management.model.LegalEntity) swagContractorType;
                contractor.setLegalEntity(claimLegalEntityConverter.convertToThrift(swagLegalEntity));
                break;
            case PRIVATEENTITY:
                var swagPrivateEntity = (com.rbkmoney.swag.claim_management.model.PrivateEntity) swagContractorType;
                contractor.setPrivateEntity(privateEntityConverter.convertToThrift(swagPrivateEntity));
                break;
            case REGISTEREDUSER:
                var swagRegisteredUser = (com.rbkmoney.swag.claim_management.model.RegisteredUser) swagContractorType;
                contractor.setRegisteredUser(
                        new RegisteredUser().setEmail(swagRegisteredUser.getEmail())
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown contractor type: " + swagContractorType);
        }
        return contractor;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.Contractor convertToSwag(Contractor creation) {
        var swagContractor = new com.rbkmoney.swag.claim_management.model.Contractor();
        if (creation.isSetLegalEntity()) {
            swagContractor.setContractorType(claimLegalEntityConverter.convertToSwag(creation.getLegalEntity()));
        } else if (creation.isSetPrivateEntity()) {
            PrivateEntity privateEntity = creation.getPrivateEntity();

            if (privateEntity.isSetRussianPrivateEntity()) {
                swagContractor.setContractorType(privateEntityConverter.convertToSwag(privateEntity));
            } else {
                throw new IllegalArgumentException("Unknown private entity type!");
            }
        } else if (creation.isSetRegisteredUser()) {
            RegisteredUser registeredUser = creation.getRegisteredUser();
            var swagRegisteredUser = new com.rbkmoney.swag.claim_management.model.RegisteredUser();
            swagRegisteredUser.setContractorType(REGISTEREDUSER);
            swagRegisteredUser.setEmail(registeredUser.getEmail());

            swagContractor.setContractorType(swagRegisteredUser);
        } else {
            throw new IllegalArgumentException("Unknown contractor type!");
        }
        swagContractor.setContractorModificationType(CONTRACTOR);
        return swagContractor;
    }

}
