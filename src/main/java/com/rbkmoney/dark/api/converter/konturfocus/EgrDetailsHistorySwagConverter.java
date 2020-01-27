package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EgrDetailsHistorySwagConverter implements SwagConverter<EgrDetailsHistory, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsHistory> {

    @Override
    public EgrDetailsHistory toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsHistory value, SwagConverterContext ctx) {
        EgrDetailsHistory egrDetailsHistory = new EgrDetailsHistory();
        if (value.getShareholders() != null) {
            egrDetailsHistory.setDate(value.getShareholders().getDate());
            if (value.getShareholders().isSetShareholdersFl()) {
                List<ShareHolderFl> shareHolderFlList = value.getShareholders().getShareholdersFl().stream()
                        .map(shareHolderFl -> {
                            return ctx.convert(shareHolderFl, ShareHolderFl.class);
                        })
                        .collect(Collectors.toList());
                egrDetailsHistory.setShareHoldersFl(shareHolderFlList);
            }
            if (value.getShareholders().isSetShareholdersUl()) {
                List<ShareHolderUl> shareHolderUlList = value.getShareholders().getShareholdersUl().stream()
                        .map(shareHolderUL -> {
                            return ctx.convert(shareHolderUL, ShareHolderUl.class);
                        })
                        .collect(Collectors.toList());
                egrDetailsHistory.setShareHoldersUl(shareHolderUlList);
            }
            if (value.getShareholders().isSetShareholdersOther()) {
                List<ShareHolderOther> shareHolderOtherList = value.getShareholders().getShareholdersOther().stream()
                        .map(shareHolderOther -> {
                            return ctx.convert(shareHolderOther, ShareHolderOther.class);
                        })
                        .collect(Collectors.toList());
                egrDetailsHistory.setShareholdersOther(shareHolderOtherList);
            }
        }
        if (value.isSetFoundersFl()) {
            List<FounderFl> founderFlList = value.getFoundersFl().stream()
                    .map(founderFL -> ctx.convert(founderFL, FounderFl.class))
                    .collect(Collectors.toList());
            egrDetailsHistory.setFoundersFl(founderFlList);
        }
        if (value.isSetFoundersFl()) {
            List<FounderUl> founderUlList = value.getFoundersUl().stream()
                    .map(founderUL -> {
                        return ctx.convert(founderUL, FounderUl.class);
                    })
                    .collect(Collectors.toList());
            egrDetailsHistory.setFoundersUl(founderUlList);
        }
        if (value.isSetStatedCapitals()) {
            List<StatedCapital> statedCapitalList = value.getStatedCapitals().stream()
                    .map(statedCapital -> {
                        StatedCapital swagStatedCapital = new StatedCapital();
                        swagStatedCapital.setSum(statedCapital.getSum());
                        swagStatedCapital.setDate(statedCapital.getDate());
                        return swagStatedCapital;
                    })
                    .collect(Collectors.toList());
            egrDetailsHistory.setStatedCapitals(statedCapitalList);
        }

        return egrDetailsHistory;
    }
}
