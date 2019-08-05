package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.manage.Questionary;
import com.rbkmoney.swag.questionary.model.QuestionaryData;
import org.springframework.stereotype.Component;

@Component
public class QuestionaryConverter implements
        ThriftConverter<Questionary, com.rbkmoney.swag.questionary.model.Questionary>,
        SwagConverter<com.rbkmoney.swag.questionary.model.Questionary, Questionary> {

    @Override
    public com.rbkmoney.swag.questionary.model.Questionary toSwag(Questionary value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.Questionary()
                .id(value.getId())
                .ownerID(value.getOwnerId())
                .data(ctx.convert(value.getData(), QuestionaryData.class));
    }

    @Override
    public Questionary toThrift(com.rbkmoney.swag.questionary.model.Questionary value, ThriftConverterContext ctx) {
        Questionary questionary = new Questionary();
        questionary.setId(value.getId());
        questionary.setOwnerId(value.getOwnerID());
        questionary.setData(ctx.convert(value.getData(), com.rbkmoney.questionary.manage.QuestionaryData.class));
        return questionary;
    }

}
