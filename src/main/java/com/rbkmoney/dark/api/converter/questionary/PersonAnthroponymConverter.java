package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.PersonAnthroponym;
import org.springframework.stereotype.Component;

@Component
public class PersonAnthroponymConverter implements
        ThriftConverter<PersonAnthroponym, com.rbkmoney.swag.questionary.model.PersonAnthroponym>,
        SwagConverter<com.rbkmoney.swag.questionary.model.PersonAnthroponym, PersonAnthroponym> {

    @Override
    public com.rbkmoney.swag.questionary.model.PersonAnthroponym toSwag(PersonAnthroponym value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.PersonAnthroponym()
                .firstName(value.getFirstName())
                .middleName(value.getMiddleName())
                .secondName(value.getSecondName());
    }

    @Override
    public PersonAnthroponym toThrift(com.rbkmoney.swag.questionary.model.PersonAnthroponym value, ThriftConverterContext ctx) {
        return new PersonAnthroponym()
                .setFirstName(value.getFirstName())
                .setMiddleName(value.getMiddleName())
                .setSecondName(value.getSecondName());
    }

}
