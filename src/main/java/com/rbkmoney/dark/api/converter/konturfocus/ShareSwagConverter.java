package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Share;
import org.springframework.stereotype.Component;

@Component
public class ShareSwagConverter implements SwagConverter<Share, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Share> {

    @Override
    public Share toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Share value, SwagConverterContext ctx) {
        return new Share()
                .percentageDenominator(value.getPercentageDenominator())
                .percentageNominator(value.getPercentageNominator())
                .percentagePlain((int) value.getPercentagePlain())
                .sum(value.getSum());
    }

}
