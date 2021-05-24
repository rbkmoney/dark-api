package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.FounderForeign;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Share;
import org.springframework.stereotype.Component;

@Component
public class FounderForeignSwagConverter
        implements SwagConverter<FounderForeign, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderForeign> {

    @Override
    public FounderForeign toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderForeign value,
                                 SwagConverterContext ctx) {
        FounderForeign founderForeign = new FounderForeign();
        founderForeign.setCountry(value.getCountry());
        founderForeign.setDate(value.getDate());
        founderForeign.setFirstDate(value.getFirstDate());
        founderForeign.setFullName(value.getFullName());
        if (value.isSetShare()) {
            founderForeign.setShare(ctx.convert(value.getShare(), Share.class));
        }
        return founderForeign;
    }

}
