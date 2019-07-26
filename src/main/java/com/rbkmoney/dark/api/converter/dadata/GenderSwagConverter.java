package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Gender;
import org.springframework.stereotype.Component;

@Component
public class GenderSwagConverter implements SwagConverter<Gender, com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender> {

    @Override
    public Gender toSwag(com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender value, SwagConverterContext ctx) {
        if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.FEMALE) {
            return Gender.FEMALE;
        } else if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.MALE) {
            return Gender.MALE;
        } else if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.UNKNOWN) {
            return Gender.UNKNOWN;
        }
        throw new IllegalArgumentException("Unknown gender type: " + value.name());
    }

}
