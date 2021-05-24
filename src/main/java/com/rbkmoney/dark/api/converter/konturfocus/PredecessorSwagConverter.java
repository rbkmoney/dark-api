package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Predecessor;
import org.springframework.stereotype.Component;

@Component
public class PredecessorSwagConverter implements
        SwagConverter<Predecessor, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Predecessor> {

    @Override
    public Predecessor toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Predecessor value,
                              SwagConverterContext ctx) {
        Predecessor predecessor = new Predecessor();
        predecessor.setDate(value.getDate());
        predecessor.setInn(value.getInn());
        predecessor.setName(value.getName());
        predecessor.setOgrn(value.getOgrn());
        return predecessor;
    }

}
