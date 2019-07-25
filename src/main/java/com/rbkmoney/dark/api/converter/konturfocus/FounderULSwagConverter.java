package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderUL;
import com.rbkmoney.swag.questionary_aggr_proxy.model.FounderUl;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Share;
import org.springframework.stereotype.Component;

@Component
public class FounderULSwagConverter implements SwagConverter<FounderUl, FounderUL> {
    @Override
    public FounderUl toSwag(FounderUL value, SwagConverterContext ctx) {
        FounderUl founderUl = new FounderUl();
        founderUl.setDate(value.getDate());
        founderUl.setFirstDate(value.getFirstDate());
        founderUl.setInn(value.getInn());
        founderUl.setOgrn(value.getOgrn());
        if (value.isSetShare()) {
            founderUl.setShare(ctx.convert(value.getShare(), Share.class));
        }
        return founderUl;
    }
}
