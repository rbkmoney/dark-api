package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.FioContent;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Gender;
import org.springframework.stereotype.Component;

@Component
public class FioContentSwagConverter
        implements SwagConverter<FioContent, com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioContent> {

    @Override
    public FioContent toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioContent value,
                             SwagConverterContext ctx) {
        FioContent fioContent = new FioContent()
                .name(value.getName())
                .unrestrictedValue(value.getUnrestrictedValue())
                .value(value.getValue())
                .qc((int) value.getQc())
                .patronymic(value.getPatronymic())
                .surname(value.getSurname());
        if (value.isSetGender()) {
            fioContent.setGender(ctx.convert(value.getGender(), Gender.class));
        }

        return fioContent;
    }

}
