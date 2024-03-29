package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.IndividualPerson;
import org.springframework.stereotype.Component;

@Component
public class IndividualPersonConverter implements
        ThriftConverter<IndividualPerson, com.rbkmoney.swag.questionary.model.IndividualPerson>,
        SwagConverter<com.rbkmoney.swag.questionary.model.IndividualPerson, IndividualPerson> {

    @Override
    public com.rbkmoney.swag.questionary.model.IndividualPerson toSwag(IndividualPerson value,
                                                                       SwagConverterContext ctx) {
        var individualPerson = new com.rbkmoney.swag.questionary.model.IndividualPerson();
        individualPerson.setFio(value.getFio());
        individualPerson.setInn(value.getInn());
        return individualPerson;
    }

    @Override
    public IndividualPerson toThrift(com.rbkmoney.swag.questionary.model.IndividualPerson value,
                                     ThriftConverterContext ctx) {
        IndividualPerson individualPerson = new IndividualPerson();
        individualPerson.setFio(value.getFio());
        individualPerson.setInn(value.getInn());
        return individualPerson;
    }

}
