package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.LegalAddress;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ParsedAddressRF;
import org.springframework.stereotype.Component;

@Component
public class LegalAddressConverter implements SwagConverter<LegalAddress, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalAddress> {
    @Override
    public LegalAddress toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalAddress value, SwagConverterContext ctx) {
        LegalAddress swagLegalAddress = new LegalAddress();
        if (value.isSetAddressRf()) {
            swagLegalAddress.setAddressRf(ctx.convert(value.getAddressRf(), ParsedAddressRF.class));
        }
        swagLegalAddress.setDate(value.getDate());
        swagLegalAddress.setFirstDate(value.getFirstDate());
        return swagLegalAddress;
    }
}
