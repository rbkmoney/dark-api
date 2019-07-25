package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ShareHolderUl;
import org.springframework.stereotype.Component;

@Component
public class ShareHolderUL implements SwagConverter<ShareHolderUl, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderUL> {

    @Override
    public ShareHolderUl toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderUL value, SwagConverterContext ctx) {
        ShareHolderUl shareHolderUl = new ShareHolderUl();
        shareHolderUl.setAddress(value.getAddress());
        shareHolderUl.setCapitalSharesPercent((int) value.getCapitalSharesPercent());
        shareHolderUl.setDate(value.getDate());
        shareHolderUl.setFullName(value.getFullName());
        shareHolderUl.setInn(value.getInn());
        shareHolderUl.setOgrn(value.getOgrn());
        shareHolderUl.setVotingSharesPercent((int) value.getVotingSharesPercent());
        return shareHolderUl;
    }

}
