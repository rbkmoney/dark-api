package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Branch;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ForeignAddress;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ParsedAddressRF;
import org.springframework.stereotype.Component;

@Component
public class BranchSwagConverter
        implements SwagConverter<Branch, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch> {

    @Override
    public Branch toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch value, SwagConverterContext ctx) {
        Branch branch = new Branch();
        branch.setName(value.getName());
        branch.setDate(value.getDate());
        if (value.isSetAddressRf()) {
            branch.setAddressRf(ctx.convert(value.getAddressRf(), ParsedAddressRF.class));
        }
        if (value.isSetForeignAddress()) {
            branch.setForeignAddress(ctx.convert(value.getForeignAddress(), ForeignAddress.class));
        }
        return branch;
    }

}
