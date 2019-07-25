package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ShareHolderOther;
import org.springframework.stereotype.Component;

@Component
public class ShareHolderOtherSwagConverter implements SwagConverter<ShareHolderOther, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther> {
    @Override
    public ShareHolderOther toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther value, SwagConverterContext ctx) {
        ShareHolderOther shareHolderOther = new ShareHolderOther();
        shareHolderOther.setAddress(value.getAddress());
        shareHolderOther.setCapitalSharesPercent((int) value.getCapitalSharesPercent());
        shareHolderOther.setDate(value.getDate());
        shareHolderOther.setFullName(value.getFullName());
        shareHolderOther.setVotingSharesPercent((int) value.getVotingSharesPercent());
        return shareHolderOther;
    }
}
