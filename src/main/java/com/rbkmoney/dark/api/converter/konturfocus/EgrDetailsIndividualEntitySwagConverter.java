package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EgrDetailsIndividualEntitySwagConverter
        implements SwagConverter<EgrDetailsIndividualEntity, com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsIndividualEntity> {

    @Override
    public EgrDetailsIndividualEntity toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsIndividualEntity value,
                                             SwagConverterContext ctx) {
        EgrDetailsIndividualEntity swagEgrDetailsIndividualEntity = new EgrDetailsIndividualEntity();
        swagEgrDetailsIndividualEntity.setFomsRegNumber(value.getFomsRegNumber());
        swagEgrDetailsIndividualEntity.setFssRegNumber(value.getFssRegNumber());
        swagEgrDetailsIndividualEntity.setOkato(value.getOkato());
        swagEgrDetailsIndividualEntity.setOkpo(value.getOkpo());
        swagEgrDetailsIndividualEntity.setPfrRegNumber(value.getPfrRegNumber());
        if (value.isSetActivities()) {
            swagEgrDetailsIndividualEntity.setActivities(ctx.convert(value.getActivities(), Activity.class));
        }
        if (value.isSetEgrRecords()) {
            List<EgrRecord> egrRecordList = value.getEgrRecords().stream()
                    .map(egrRecord -> ctx.convert(egrRecord, EgrRecord.class))
                    .collect(Collectors.toList());

            swagEgrDetailsIndividualEntity.setEgrRecords(egrRecordList);
        }
        if (value.isSetNalogRegBody()) {
            swagEgrDetailsIndividualEntity.setNalogRegBody(ctx.convert(value.getNalogRegBody(), NalogRegBody.class));
        }
        if (value.isSetRegInfo()) {
            swagEgrDetailsIndividualEntity.setRegInfo(ctx.convert(value.getRegInfo(), RegInfo.class));
        }
        if (value.isSetRegBody()) {
            swagEgrDetailsIndividualEntity.setRegBody(ctx.convert(value.getRegBody(), RegBody.class));
        }
        if (value.isSetShortenedAddress()) {
            ShortenedAddress shortenedAddress = new ShortenedAddress();
            if (value.getShortenedAddress().isSetCity()) {
                shortenedAddress.setCity(ctx.convert(value.getShortenedAddress().getCity(), Toponim.class));
            }
            if (value.getShortenedAddress().isSetDistrict()) {
                shortenedAddress.setDistrict(ctx.convert(value.getShortenedAddress().getDistrict(), Toponim.class));
            }
            if (value.getShortenedAddress().isSetSettlement()) {
                shortenedAddress.setSettlement(ctx.convert(value.getShortenedAddress().getSettlement(), Toponim.class));
            }
            if (value.getShortenedAddress().isSetRegionName()) {
                shortenedAddress.setRegionName(ctx.convert(value.getShortenedAddress().getRegionName(), Toponim.class));
            }
            shortenedAddress.setRegionCode(value.getShortenedAddress().getRegionCode());
            swagEgrDetailsIndividualEntity.setShortenedAddress(shortenedAddress);
        }

        return swagEgrDetailsIndividualEntity;
    }

}
