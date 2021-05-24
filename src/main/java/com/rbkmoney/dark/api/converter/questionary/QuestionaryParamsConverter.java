package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.questionary.model.QuestionaryData;
import com.rbkmoney.swag.questionary.model.QuestionaryParams;
import org.springframework.stereotype.Component;

@Component
public class QuestionaryParamsConverter implements
        ThriftConverter<com.rbkmoney.questionary.manage.QuestionaryParams, QuestionaryParams>,
        SwagConverter<QuestionaryParams, com.rbkmoney.questionary.manage.QuestionaryParams> {

    @Override
    public QuestionaryParams toSwag(com.rbkmoney.questionary.manage.QuestionaryParams value, SwagConverterContext ctx) {
        return new QuestionaryParams()
                .id(value.getId())
                .ownerId(value.getOwnerId())
                .data(ctx.convert(value.getData(), QuestionaryData.class));
    }

    @Override
    public com.rbkmoney.questionary.manage.QuestionaryParams toThrift(QuestionaryParams value,
                                                                      ThriftConverterContext ctx) {
        return new com.rbkmoney.questionary.manage.QuestionaryParams()
                .setId(value.getId())
                .setOwnerId(value.getOwnerId())
                .setData(ctx.convert(value.getData(), com.rbkmoney.questionary.manage.QuestionaryData.class));
    }
}
