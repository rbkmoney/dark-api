package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.BeneficialOwnerUl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@SuppressWarnings("LineLength")
public class BeneficialOwnerUlSwagConverter implements
        SwagConverter<BeneficialOwnerUl, com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerUl> {

    @Override
    public BeneficialOwnerUl toSwag(
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerUl value,
            SwagConverterContext ctx) {
        BeneficialOwnerUl beneficialOwnerUl = new BeneficialOwnerUl();
        beneficialOwnerUl.setFullName(value.getFullName());
        beneficialOwnerUl.setInn(value.getInn());
        beneficialOwnerUl.setIsAccurate(value.isIsAccurate());
        beneficialOwnerUl.setShare(BigDecimal.valueOf(value.getShare()));
        beneficialOwnerUl.setOgrn(value.getOgrn());

        return beneficialOwnerUl;
    }

}
