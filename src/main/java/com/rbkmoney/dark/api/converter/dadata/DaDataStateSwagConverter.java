package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataState;
import com.rbkmoney.swag.questionary_aggr_proxy.model.OrgStatus;
import org.springframework.stereotype.Component;

@Component
public class DaDataStateSwagConverter implements SwagConverter<DaDataState, com.rbkmoney.questionary_proxy_aggr.base_dadata.DaDataState> {

    @Override
    public DaDataState toSwag(com.rbkmoney.questionary_proxy_aggr.base_dadata.DaDataState value, SwagConverterContext ctx) {
        DaDataState swagDaDataState = new DaDataState();
        swagDaDataState.setActualityDate(value.getActualityDate());
        swagDaDataState.setLiquidationDate(value.getLiquidationDate());
        swagDaDataState.setRegistratonDate(value.getRegistrationDate());
        if (value.isSetStatus()) {
            swagDaDataState.setStatus(ctx.convert(value.getStatus(), OrgStatus.class));
        }
        return swagDaDataState;
    }

}
