package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.OkvedContent;
import org.springframework.stereotype.Component;

@Component
public class OkvedContentSwagConverter implements SwagConverter<OkvedContent, com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedContent> {

    @Override
    public OkvedContent toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedContent value, SwagConverterContext ctx) {
        return new OkvedContent()
                .code(value.getCode())
                .idx(value.getIdx())
                .name(value.getName())
                .section(value.getSection())
                .value(value.getValue());
    }

}
