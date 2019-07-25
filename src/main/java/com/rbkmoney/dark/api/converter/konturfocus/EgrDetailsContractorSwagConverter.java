package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Contractor;
import com.rbkmoney.swag.questionary_aggr_proxy.model.EgrDetailsContractor;
import com.rbkmoney.swag.questionary_aggr_proxy.model.EgrDetailsIndividualEntity;
import com.rbkmoney.swag.questionary_aggr_proxy.model.EgrDetailsLegalEntity;
import org.springframework.stereotype.Component;

@Component
public class EgrDetailsContractorSwagConverter implements SwagConverter<EgrDetailsContractor, Contractor> {
    @Override
    public EgrDetailsContractor toSwag(Contractor value, SwagConverterContext ctx) {
        if (value.isSetIndividualEntity()) {
            return ctx.convert(value.getIndividualEntity(), EgrDetailsIndividualEntity.class);
        } else if (value.isSetLegalEntity()) {
            return ctx.convert(value.getLegalEntity(), EgrDetailsLegalEntity.class);
        }
        throw new IllegalArgumentException("Need to specify individualEntity/LegalEntity");
    }
}
