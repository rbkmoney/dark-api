package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.BranchType;
import org.springframework.stereotype.Component;

@Component
public class BranchTypeSwagConverter implements SwagConverter<BranchType, com.rbkmoney.questionary_proxy_aggr.base_dadata.BranchType> {

    @Override
    public BranchType toSwag(com.rbkmoney.questionary_proxy_aggr.base_dadata.BranchType value, SwagConverterContext ctx) {
        if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.BranchType.BRANCH) {
            return BranchType.BRANCH;
        } else if (value == com.rbkmoney.questionary_proxy_aggr.base_dadata.BranchType.MAIN) {
            return BranchType.MAIN;
        }
        throw new IllegalArgumentException("Unknown branchType: " + value.name());
    }

}
