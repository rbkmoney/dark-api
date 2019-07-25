package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.RegBody;
import org.springframework.stereotype.Component;

@Component
public class RegBodySwagConverter implements SwagConverter<RegBody, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.RegBody> {

    @Override
    public RegBody toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.RegBody value, SwagConverterContext ctx) {
        RegBody regBody = new RegBody();
        regBody.setDate(value.getDate());
        regBody.setKpp(value.getKpp());
        regBody.setNalogCode(value.getNalogCode());
        regBody.setNalogName(value.getNalogName());
        regBody.setNalogRegDate(value.getNalogRegDate());
        return regBody;
    }

}
