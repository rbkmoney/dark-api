package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.BeneficialOwnerForeign;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeneficialOwnerForeignSwagConverter
        implements SwagConverter<BeneficialOwnerForeign, com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerForeign> {

    @Override
    public BeneficialOwnerForeign toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerForeign value, SwagConverterContext ctx) {
        BeneficialOwnerForeign beneficialOwnerUl = new BeneficialOwnerForeign();
        beneficialOwnerUl.setFullName(value.getFullName());
        beneficialOwnerUl.setCountry(value.getCountry());
        beneficialOwnerUl.setIsAccurate(value.isIsAccurate());
        beneficialOwnerUl.setShare(BigDecimal.valueOf(value.getShare()));

        return beneficialOwnerUl;
    }

}
