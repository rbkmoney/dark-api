package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ShareHolderUl;
import org.springframework.stereotype.Component;

@Component
public class ShareHolderUL implements SwagConverter<ShareHolderUl, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderUL> {

    @Override
    public ShareHolderUl toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderUL value, SwagConverterContext ctx) {
        return new ShareHolderUl()
                .address(value.getAddress())
                .capitalSharesPercent((int) value.getCapitalSharesPercent())
                .date(value.getDate())
                .fullName(value.getFullName())
                .inn(value.getInn())
                .ogrn(value.getOgrn())
                .votingSharesPercent((int) value.getVotingSharesPercent());
    }

}
