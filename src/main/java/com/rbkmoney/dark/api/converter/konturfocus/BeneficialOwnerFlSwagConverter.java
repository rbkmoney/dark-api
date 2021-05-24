package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.BeneficialOwnerFl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@SuppressWarnings("LineLength")
public class BeneficialOwnerFlSwagConverter implements
        SwagConverter<BeneficialOwnerFl, com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerFl> {

    @Override
    public BeneficialOwnerFl toSwag(
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerFl value,
            SwagConverterContext ctx) {
        BeneficialOwnerFl beneficialOwnerFl = new BeneficialOwnerFl();
        beneficialOwnerFl.setFio(value.getFio());
        beneficialOwnerFl.setInnfl(value.getInnfl());
        beneficialOwnerFl.setIsAccurate(value.isIsAccurate());
        beneficialOwnerFl.setShare(BigDecimal.valueOf(value.getShare()));

        return beneficialOwnerFl;
    }

}
