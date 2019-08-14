package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.domain.ContactInfo;
import com.rbkmoney.damsel.domain.PrivateEntity;
import com.rbkmoney.damsel.domain.RussianPrivateEntity;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.Contractor.ContractorTypeEnum.PRIVATEENTITY;
import static com.rbkmoney.swag.claim_management.model.ContractorModification.ContractorModificationTypeEnum.CONTRACTOR;

@Component
public class PrivateEntityConverter
        implements DarkApiConverter<PrivateEntity, com.rbkmoney.swag.claim_management.model.PrivateEntity> {

    @Override
    public PrivateEntity convertToThrift(com.rbkmoney.swag.claim_management.model.PrivateEntity swagPrivateEntity) {
        PrivateEntity privateEntity = new PrivateEntity();
        ContactInfo contactInfo = new ContactInfo()
                .setEmail(swagPrivateEntity.getContactInfo().getEmail())
                .setPhoneNumber(swagPrivateEntity.getContactInfo().getPhoneNumber());
        RussianPrivateEntity russianPrivateEntity = new RussianPrivateEntity()
                .setFirstName(swagPrivateEntity.getFirstName())
                .setMiddleName(swagPrivateEntity.getMiddleName())
                .setSecondName(swagPrivateEntity.getSecondName())
                .setContactInfo(contactInfo);

        privateEntity.setRussianPrivateEntity(russianPrivateEntity);
        return privateEntity;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.PrivateEntity convertToSwag(PrivateEntity privateEntity) {
        var swagRussianPrivateEntity = new com.rbkmoney.swag.claim_management.model.PrivateEntity();
        swagRussianPrivateEntity.setContractorModificationType(CONTRACTOR);
        swagRussianPrivateEntity.setContractorType(PRIVATEENTITY);
        RussianPrivateEntity russianPrivateEntity = privateEntity.getRussianPrivateEntity();
        swagRussianPrivateEntity.setFirstName(russianPrivateEntity.getFirstName());
        swagRussianPrivateEntity.setSecondName(russianPrivateEntity.getSecondName());
        swagRussianPrivateEntity.setMiddleName(russianPrivateEntity.getMiddleName());

        ContactInfo contactInfo = russianPrivateEntity.getContactInfo();
        var swagContactInfo = new com.rbkmoney.swag.claim_management.model.ContactInfo();
        swagContactInfo.setEmail(contactInfo.getEmail());
        swagContactInfo.setPhoneNumber(contactInfo.getPhoneNumber());
        swagRussianPrivateEntity.setContactInfo(swagContactInfo);

        return swagRussianPrivateEntity;
    }

}
