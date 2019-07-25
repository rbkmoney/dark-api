package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ShareHolderFl;
import org.springframework.stereotype.Component;

@Component
public class ShareHolderFL implements SwagConverter<ShareHolderFl, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl> {
    @Override
    public ShareHolderFl toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl value, SwagConverterContext ctx) {
        ShareHolderFl shareHolderFl = new ShareHolderFl();
        shareHolderFl.setAddress(value.getAddress());
        shareHolderFl.setCapitalSharesPercent((int) value.getCapitalSharesPercent());
        shareHolderFl.setDate(value.getDate());
        shareHolderFl.setFio(value.getFio());
        shareHolderFl.setVotingSharesPercent((int) value.getVotingSharesPercent());
        return shareHolderFl;
    }
}
