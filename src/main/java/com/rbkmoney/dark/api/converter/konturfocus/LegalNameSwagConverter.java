package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.LegalName;
import org.springframework.stereotype.Component;

@Component
public class LegalNameSwagConverter implements SwagConverter<LegalName, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalName> {

    @Override
    public LegalName toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalName value, SwagConverterContext ctx) {
        LegalName swagLegalName = new LegalName();
        swagLegalName.setDate(value.getDate());
        swagLegalName.setFullName(value.getFullName());
        swagLegalName.setShortName(value.getShortName());
        return swagLegalName;
    }

}
