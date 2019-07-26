package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ForeignAddress;
import org.springframework.stereotype.Component;

@Component
public class ForeignAddressSwagConverter implements SwagConverter<ForeignAddress, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ForeignAddress> {

    @Override
    public ForeignAddress toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ForeignAddress value, SwagConverterContext ctx) {
        ForeignAddress swagForeignAddress = new ForeignAddress();
        swagForeignAddress.setAddress(value.getAddress());
        swagForeignAddress.setCountryName(value.getCountryName());
        return swagForeignAddress;
    }

}
