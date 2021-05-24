package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ReqKppHistory;
import org.springframework.stereotype.Component;

@Component
public class ReqKppHistorySwagConverter
        implements SwagConverter<ReqKppHistory, com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqKppHistory> {

    @Override
    public ReqKppHistory toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqKppHistory value,
                                SwagConverterContext ctx) {
        ReqKppHistory swagReqKppHistory = new ReqKppHistory();
        swagReqKppHistory.setDate(value.getDate());
        swagReqKppHistory.setKpp(value.getKpp());
        return swagReqKppHistory;
    }

}
