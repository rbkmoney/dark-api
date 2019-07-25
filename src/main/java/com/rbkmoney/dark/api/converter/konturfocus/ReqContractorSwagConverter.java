package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.Contractor;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

@Component
public class ReqContractorSwagConverter implements SwagConverter<ReqContractor, Contractor> {
    @Override
    public ReqContractor toSwag(Contractor value, SwagConverterContext ctx) {
        if (value.isSetIndividualEntity()) {
            return ctx.convert(value.getIndividualEntity(), ReqIndividualEntity.class);
        }
        if (value.isSetLegalEntity()) {
            return ctx.convert(value.getLegalEntity(), ReqLegalEntity.class);
        }
        return null;
    }
}
