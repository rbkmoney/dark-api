package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Head;
import org.springframework.stereotype.Component;

@Component
public class HeadSwagConverter
        implements SwagConverter<Head, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Head> {

    @Override
    public Head toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Head value, SwagConverterContext ctx) {
        Head head = new Head();
        head.setFio(value.getFio());
        head.setPosition(value.getPosition());
        head.setInnfl(value.getInnfl());
        head.setFirstDate(value.getFirstDate());
        head.setDate(value.getDate());
        return head;
    }

}
