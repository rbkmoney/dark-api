package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.OrgStatus;
import org.springframework.stereotype.Component;

@Component
public class OrgStatusSwagConverter
        implements SwagConverter<OrgStatus, com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus> {

    @Override
    public OrgStatus toSwag(com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus value, SwagConverterContext ctx) {
        if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.ACTIVE) {
            return OrgStatus.ACTIVE;
        } else if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.LIQUIDATED) {
            return OrgStatus.LIQUIDATED;
        } else if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.LIQUIDATING) {
            return OrgStatus.LIQUIDATING;
        } else if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.REORGANIZING) {
            return OrgStatus.REORGANIZING;
        }
        throw new IllegalArgumentException("Unknown orgStatus: " + value.name());
    }

}
