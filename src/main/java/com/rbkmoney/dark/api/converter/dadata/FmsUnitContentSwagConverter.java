package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.FmsUnitContent;
import org.springframework.stereotype.Component;

@Component
public class FmsUnitContentSwagConverter implements SwagConverter<FmsUnitContent, com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent> {
    @Override
    public FmsUnitContent toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent value, SwagConverterContext ctx) {
        FmsUnitContent fmsUnitContent = new FmsUnitContent();
        fmsUnitContent.setCode(value.getCode());
        fmsUnitContent.setName(value.getName());
        fmsUnitContent.setRegionCode(value.getRegionCode());
        fmsUnitContent.setType(value.getType());
        fmsUnitContent.setValue(value.getValue());
        fmsUnitContent.setUnrestrictedValue(value.getUnrestrictdValue());
        return fmsUnitContent;
    }
}
