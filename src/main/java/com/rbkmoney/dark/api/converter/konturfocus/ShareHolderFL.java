package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ShareHolderFl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ShareHolderFL implements
        SwagConverter<ShareHolderFl, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl> {

    @Override
    public ShareHolderFl toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl value,
                                SwagConverterContext ctx) {
        return new ShareHolderFl()
                .address(value.getAddress())
                .capitalSharesPercent(BigDecimal.valueOf(value.getCapitalSharesPercent()))
                .date(value.getDate())
                .fio(value.getFio())
                .votingSharesPercent(BigDecimal.valueOf(value.getVotingSharesPercent()));
    }
}
