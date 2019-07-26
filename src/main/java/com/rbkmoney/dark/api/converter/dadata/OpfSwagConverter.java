package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Opf;
import org.springframework.stereotype.Component;

@Component
public class OpfSwagConverter implements SwagConverter<Opf, com.rbkmoney.questionary_proxy_aggr.base_dadata.Opf> {

    @Override
    public Opf toSwag(com.rbkmoney.questionary_proxy_aggr.base_dadata.Opf value, SwagConverterContext ctx) {
        return new Opf()
                .code(value.getCode())
                .fullName(value.getFullName())
                .shortName(value.getShortName())
                .type(value.getType());
    }

}
