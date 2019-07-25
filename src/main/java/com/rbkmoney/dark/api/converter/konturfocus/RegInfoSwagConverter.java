package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.RegInfo;
import org.springframework.stereotype.Component;

@Component
public class RegInfoSwagConverter implements SwagConverter<RegInfo, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.RegInfo> {

    @Override
    public RegInfo toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.RegInfo value, SwagConverterContext ctx) {
        RegInfo regInfo = new RegInfo();
        regInfo.setOgrnDate(value.getOgrnDate());
        regInfo.setRegName(value.getRegName());
        return regInfo;
    }

}
