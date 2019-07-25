package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.NalogRegBody;
import org.springframework.stereotype.Component;

@Component
public class NalogRegBodySwagConverter implements SwagConverter<NalogRegBody, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.NalogRegBody> {
    @Override
    public NalogRegBody toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.NalogRegBody value, SwagConverterContext ctx) {
        NalogRegBody nalogRegBody = new NalogRegBody();
        nalogRegBody.setDate(value.getDate());
        nalogRegBody.setKpp(value.getKpp());
        nalogRegBody.setNalogCode(value.getNalogCode());
        nalogRegBody.setNalogName(value.getNalogName());
        nalogRegBody.setNalogRegDate(value.getNalogRegDate());
        return nalogRegBody;
    }
}
