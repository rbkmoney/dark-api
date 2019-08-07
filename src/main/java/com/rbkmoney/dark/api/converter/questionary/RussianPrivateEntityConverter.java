package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.RussianPrivateEntity;
import com.rbkmoney.swag.questionary.model.ContactInfo;
import com.rbkmoney.swag.questionary.model.PersonAnthroponym;
import org.springframework.stereotype.Component;

@Component
public class RussianPrivateEntityConverter implements
        ThriftConverter<RussianPrivateEntity, com.rbkmoney.swag.questionary.model.RussianPrivateEntity>,
        SwagConverter<com.rbkmoney.swag.questionary.model.RussianPrivateEntity, RussianPrivateEntity> {

    @Override
    public com.rbkmoney.swag.questionary.model.RussianPrivateEntity toSwag(RussianPrivateEntity value, SwagConverterContext ctx) {
        var russianPrivateEntity = new com.rbkmoney.swag.questionary.model.RussianPrivateEntity()
                .actualAddress(value.getActualAddress())
                .birthDate(value.getBirthDate())
                .birthPlace(value.getBirthPlace())
                .citizenship(value.getCitizenship())
                .residenceAddress(value.getResidenceAddress());
        if (value.isSetContactInfo()) {
            russianPrivateEntity.setContactInfo(ctx.convert(value.getContactInfo(), ContactInfo.class));
        }
        if (value.isSetFio()) {
            russianPrivateEntity.setPersonAnthroponym(ctx.convert(value.getFio(), PersonAnthroponym.class));
        }

        return russianPrivateEntity;
    }

    @Override
    public RussianPrivateEntity toThrift(com.rbkmoney.swag.questionary.model.RussianPrivateEntity value, ThriftConverterContext ctx) {
        RussianPrivateEntity russianPrivateEntity = new RussianPrivateEntity()
                .setCitizenship(value.getCitizenship())
                .setBirthDate(value.getBirthDate())
                .setBirthPlace(value.getBirthPlace())
                .setActualAddress(value.getActualAddress())
                .setResidenceAddress(value.getResidenceAddress());
        if (value.getContactInfo() != null) {
            russianPrivateEntity.setContactInfo(ctx.convert(value.getContactInfo(), com.rbkmoney.questionary.ContactInfo.class));
        }
        if (value.getPersonAnthroponym() != null) {
            russianPrivateEntity.setFio(ctx.convert(value.getPersonAnthroponym(), com.rbkmoney.questionary.PersonAnthroponym.class));
        }

        return russianPrivateEntity;
    }

}
