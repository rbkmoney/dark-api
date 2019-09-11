package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.Founder;
import com.rbkmoney.swag.questionary.model.Founder.FounderTypeEnum;
import com.rbkmoney.swag.questionary.model.IndividualPerson;
import com.rbkmoney.swag.questionary.model.InternationalLegalEntityFounder;
import com.rbkmoney.swag.questionary.model.PersonAnthroponym;
import com.rbkmoney.swag.questionary.model.RussianLegalEntityFounder;
import org.springframework.stereotype.Component;

@Component
public class FounderConverter implements
        ThriftConverter<Founder, com.rbkmoney.swag.questionary.model.Founder>,
        SwagConverter<com.rbkmoney.swag.questionary.model.Founder, Founder> {

    @Override
    public com.rbkmoney.swag.questionary.model.Founder toSwag(Founder value, SwagConverterContext ctx) {
        if (value.isSetIndividualPersonFounder()) {
            IndividualPerson individualPerson = new IndividualPerson();
            if (value.getIndividualPersonFounder().isSetFio()) {
                individualPerson.setFio(ctx.convert(value.getIndividualPersonFounder().getFio(), PersonAnthroponym.class));
            }
            individualPerson.setInn(value.getIndividualPersonFounder().getInn());

            return individualPerson;
        } else if (value.isSetInternationalLegalEntityFounder()) {
            return new InternationalLegalEntityFounder()
                    .country(value.getInternationalLegalEntityFounder().getCountry())
                    .fullName(value.getInternationalLegalEntityFounder().getFullName());
        } else if (value.isSetRussianLegalEntityFounder()) {
            return new RussianLegalEntityFounder()
                    .fullName(value.getRussianLegalEntityFounder().getFullName())
                    .inn(value.getRussianLegalEntityFounder().getInn())
                    .ogrn(value.getRussianLegalEntityFounder().getOgrn());
        } else {
            throw new IllegalArgumentException("Unknown founder type: " + value.getClass().getName());
        }
    }

    @Override
    public Founder toThrift(com.rbkmoney.swag.questionary.model.Founder value, ThriftConverterContext ctx) {
        if (value.getFounderType() == FounderTypeEnum.INDIVIDUALPERSON) {
            var individualPerson = new com.rbkmoney.questionary.IndividualPerson();
            if (((IndividualPerson) value).getFio() != null) {
                individualPerson.setFio(ctx.convert(((IndividualPerson) value).getFio(), com.rbkmoney.questionary.PersonAnthroponym.class));
            }
            individualPerson.setInn(((IndividualPerson) value).getInn());

            return Founder.individual_person_founder(individualPerson);
        } else if (value.getFounderType() == FounderTypeEnum.INTERNATIONALLEGALENTITYFOUNDER) {
            var internationalLegalEntityFounder = new com.rbkmoney.questionary.InternationalLegalEntityFounder()
                    .setFullName(((InternationalLegalEntityFounder) value).getFullName())
                    .setCountry(((InternationalLegalEntityFounder) value).getCountry());

            return Founder.international_legal_entity_founder(internationalLegalEntityFounder);
        } else if (value.getFounderType() == FounderTypeEnum.RUSSIANLEGALENTITYFOUNDER) {
            var russianLegalEntityFounder = new com.rbkmoney.questionary.RussianLegalEntityFounder()
                    .setFullName(((RussianLegalEntityFounder) value).getFullName())
                    .setInn(((RussianLegalEntityFounder) value).getInn())
                    .setOgrn(((RussianLegalEntityFounder) value).getOgrn());

            return Founder.russian_legal_entity_founder(russianLegalEntityFounder);
        } else {
            throw new IllegalArgumentException("Unknown founder type: " + value.getFounderType());
        }

    }

}
