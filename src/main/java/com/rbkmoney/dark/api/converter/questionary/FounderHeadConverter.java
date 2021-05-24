package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.Head;
import com.rbkmoney.swag.questionary.model.FounderHead;
import com.rbkmoney.swag.questionary.model.IndividualPerson;
import org.springframework.stereotype.Component;

@Component
public class FounderHeadConverter implements ThriftConverter<Head, FounderHead>, SwagConverter<FounderHead, Head> {

    @Override
    public FounderHead toSwag(Head value, SwagConverterContext ctx) {
        FounderHead founderHead = new FounderHead();
        if (value.isSetIndividualPerson()) {
            founderHead.setIndividualPerson(ctx.convert(value.getIndividualPerson(), IndividualPerson.class));
        }
        founderHead.setPosition(value.getPosition());
        return founderHead;
    }

    @Override
    public Head toThrift(FounderHead value, ThriftConverterContext ctx) {
        Head head = new Head();
        if (value.getIndividualPerson() != null) {
            head.setIndividualPerson(
                    ctx.convert(value.getIndividualPerson(), com.rbkmoney.questionary.IndividualPerson.class));
        }
        head.setPosition(value.getPosition());
        return head;
    }

}
