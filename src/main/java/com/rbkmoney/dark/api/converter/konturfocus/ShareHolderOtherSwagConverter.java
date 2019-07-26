package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ShareHolderOther;
import org.springframework.stereotype.Component;

@Component
public class ShareHolderOtherSwagConverter implements SwagConverter<ShareHolderOther, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther> {

    @Override
    public ShareHolderOther toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther value, SwagConverterContext ctx) {
        return new ShareHolderOther()
                .address(value.getAddress())
                .capitalSharesPercent((int) value.getCapitalSharesPercent())
                .date(value.getDate())
                .fullName(value.getFullName())
                .votingSharesPercent((int) value.getVotingSharesPercent());
    }

}
