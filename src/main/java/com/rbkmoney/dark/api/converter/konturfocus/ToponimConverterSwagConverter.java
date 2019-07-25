package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Toponim;
import org.springframework.stereotype.Component;

@Component
public class ToponimConverterSwagConverter implements SwagConverter<Toponim, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Toponim> {
    @Override
    public Toponim toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Toponim value, SwagConverterContext ctx) {
        Toponim swagToponim = new Toponim();
        swagToponim.setTopoValue(value.getTopoValue());
        swagToponim.setTopoFullName(value.getTopoFullName());
        swagToponim.setTopoShortName(value.getTopoShortName());
        return swagToponim;
    }
}
