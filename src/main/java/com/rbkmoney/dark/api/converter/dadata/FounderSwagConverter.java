package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.FounderType;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataHID;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Founder;
import org.springframework.stereotype.Component;

@Component
public class FounderSwagConverter implements SwagConverter<Founder, com.rbkmoney.questionary_proxy_aggr.base_dadata.Founder> {
    @Override
    public Founder toSwag(com.rbkmoney.questionary_proxy_aggr.base_dadata.Founder value, SwagConverterContext ctx) {
        Founder founder = new Founder();
        founder.setFio(value.getFio());
        DaDataHID daDataHID = new DaDataHID();
        daDataHID.setHid(value.getHid());
        founder.setHid(daDataHID);
        founder.setInn(value.getInn());
        founder.setName(value.getName());
        founder.setOgrn(value.getOgrn());

        if (value.getType() == FounderType.LEGAL) {
            founder.setType(com.rbkmoney.swag.questionary_aggr_proxy.model.FounderType.LEGAL);
        } else if (value.getType() == FounderType.PHYSICAL) {
            founder.setType(com.rbkmoney.swag.questionary_aggr_proxy.model.FounderType.PHYSICAL);
        }

        return founder;
    }
}
