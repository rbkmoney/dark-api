package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EgrDetailsLegalEntitySwagConverter implements SwagConverter<EgrDetailsLegalEntity, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsLegalEntity> {

    @Override
    public EgrDetailsLegalEntity toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsLegalEntity value, SwagConverterContext ctx) {
        EgrDetailsLegalEntity egrDetailsLegalEntity = new EgrDetailsLegalEntity();
        if (value.isSetActivities()) {
            egrDetailsLegalEntity.setActivities(ctx.convert(value.getActivities(), Activity.class));
        }
        if (value.isSetEgrRecords()) {
            List<EgrRecord> egrRecordList = value.getEgrRecords().stream()
                    .map(egrRecord -> ctx.convert(egrRecord, EgrRecord.class))
                    .collect(Collectors.toList());
            egrDetailsLegalEntity.setEgrRecords(egrRecordList);
        }
        egrDetailsLegalEntity.setFomsRegNumber(value.getFomsRegNumber());
        egrDetailsLegalEntity.setFssRegNumber(value.getFssRegNumber());
        if (value.isSetNalogRegBody()) {
            egrDetailsLegalEntity.setNalogRegBody(ctx.convert(value.getNalogRegBody(), NalogRegBody.class));
        }
        egrDetailsLegalEntity.setOkpo(value.getOkpo());
        egrDetailsLegalEntity.setPfrRegNumber(value.getPfrRegNumber());
        if (value.isSetRegBody()) {
            egrDetailsLegalEntity.setRegBody(ctx.convert(value.getRegBody(), RegBody.class));
        }
        if (value.isSetRegInfo()) {
            egrDetailsLegalEntity.setRegInfo(ctx.convert(value.getRegInfo(), RegInfo.class));
        }
        if (value.isSetFoundersUl()) {
            List<FounderFl> founderFlList = value.getFoundersFl().stream()
                    .map(founderFL -> ctx.convert(founderFL, FounderFl.class))
                    .collect(Collectors.toList());
            egrDetailsLegalEntity.setFoundersFl(founderFlList);
        }
        if (value.isSetFoundersUl()) {
            List<FounderUl> founderUlList = value.getFoundersUl().stream()
                    .map(founderUL -> ctx.convert(founderUL, FounderUl.class))
                    .collect(Collectors.toList());
            egrDetailsLegalEntity.setFoundersUl(founderUlList);
        }
        if (value.isSetPredecessors()) {
            List<FounderForeign> founderForeignList = value.getFoundersForeign().stream()
                    .map(founderForeign -> ctx.convert(founderForeign, FounderForeign.class))
                    .collect(Collectors.toList());
            egrDetailsLegalEntity.setFoundersForeign(founderForeignList);
        }
        if (value.isSetPredecessors()) {
            List<Predecessor> predecessorList = value.getPredecessors().stream()
                    .map(predecessor -> ctx.convert(predecessor, Predecessor.class))
                    .collect(Collectors.toList());
            egrDetailsLegalEntity.setPredecessor(predecessorList);
        }

        if (value.isSetShareHolders()) {
            ShareHolders shareHolders = new ShareHolders();
            shareHolders.setDate(value.getShareHolders().getDate());
            if (value.getShareHolders().isSetShareHoldersFl()) {
                List<ShareHolderFl> shareHolderFlList = value.getShareHolders().getShareHoldersFl().stream()
                        .map(shareHolderFl -> ctx.convert(shareHolderFl, ShareHolderFl.class))
                        .collect(Collectors.toList());
                shareHolders.setShareHoldersFl(shareHolderFlList);
            }
            if (value.getShareHolders().isSetShareHoldersUl()) {
                List<ShareHolderUl> shareHolderUlList = value.getShareHolders().getShareHoldersUl().stream()
                        .map(shareHolderUL -> ctx.convert(shareHolderUL, ShareHolderUl.class))
                        .collect(Collectors.toList());
                shareHolders.setShareHoldersUl(shareHolderUlList);
            }
            if (value.getShareHolders().isSetShareHoldersFl()) {
                List<ShareHolderOther> shareHolderOtherList = value.getShareHolders().getShareHoldersOther().stream()
                        .map(shareHolderOther -> ctx.convert(shareHolderOther, ShareHolderOther.class))
                        .collect(Collectors.toList());
                shareHolders.setShareHoldersOther(shareHolderOtherList);
            }
            egrDetailsLegalEntity.setShareHolders(shareHolders);
        }

        if (value.isSetStatedCapital()) {
            StatedCapital statedCapital = new StatedCapital();
            statedCapital.setDate(value.getStatedCapital().getDate());
            statedCapital.setSum(value.getStatedCapital().getSum());
            egrDetailsLegalEntity.setStatedCapital(statedCapital);
        }
        if (value.isSetSuccessors()) {
            List<Successor> successorList = value.getSuccessors().stream()
                    .map(successor -> {
                        Successor swagSuccessor = new Successor();
                        swagSuccessor.setDate(successor.getDate());
                        swagSuccessor.setInn(successor.getInn());
                        swagSuccessor.setName(successor.getName());
                        swagSuccessor.setOgrn(successor.getOgrn());
                        return swagSuccessor;
                    })
                    .collect(Collectors.toList());
            egrDetailsLegalEntity.setSuccessor(successorList);
        }

        egrDetailsLegalEntity.setHistory(ctx.convert(value.getHistory(), EgrDetailsHistory.class));

        return egrDetailsLegalEntity;
    }

}
