package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.BeneficialOwnerOther;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@SuppressWarnings("LineLength")
public class BeneficialOwnerOtherSwagConverter implements
        SwagConverter<BeneficialOwnerOther, com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerOther> {

    @Override
    public BeneficialOwnerOther toSwag(
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerOther value,
            SwagConverterContext ctx) {
        BeneficialOwnerOther beneficialOwnerOther = new BeneficialOwnerOther();
        beneficialOwnerOther.setFullname(value.getFullName());
        beneficialOwnerOther.setShare(BigDecimal.valueOf(value.getShare()));
        beneficialOwnerOther.setIsAccurate(value.isIsAccurate());

        return beneficialOwnerOther;
    }

}
