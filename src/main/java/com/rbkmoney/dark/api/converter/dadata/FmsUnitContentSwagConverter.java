package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.FmsUnitContent;
import org.springframework.stereotype.Component;

@Component
public class FmsUnitContentSwagConverter
        implements SwagConverter<FmsUnitContent, com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent> {

    @Override
    public FmsUnitContent toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent value,
                                 SwagConverterContext ctx) {
        return new FmsUnitContent()
                .code(value.getCode())
                .name(value.getName())
                .regionCode(value.getRegionCode())
                .type(value.getType())
                .value(value.getValue())
                .unrestrictedValue(value.getUnrestrictdValue());
    }

}
