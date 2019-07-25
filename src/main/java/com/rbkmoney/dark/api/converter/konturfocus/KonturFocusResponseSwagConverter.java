package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KonturFocusResponseSwagConverter
        implements SwagConverter<KonturFocusResponse, com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse> {

    @Override
    public KonturFocusResponse toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse value, SwagConverterContext ctx) {
        KonturFocusResponse konturFocusResponse = null;
        if (value.isSetReqResponses()) {
            List<ReqResponse> swagReqResponseList = value.getReqResponses().getReqResponses().stream()
                    .map(thriftReqResponse -> {
                        ReqResponse swagReqResponse = new ReqResponse();
                        swagReqResponse.setInn(thriftReqResponse.getInn());
                        swagReqResponse.setOgrn(thriftReqResponse.getOgrn());
                        swagReqResponse.setFocusHref(thriftReqResponse.getFocusHref());
                        if (thriftReqResponse.isSetBriefReport()) {
                            var swagBriefReport = new com.rbkmoney.swag.questionary_aggr_proxy.model.BriefReport();
                            swagBriefReport.setHref(thriftReqResponse.getBriefReport().getHref());
                            if (thriftReqResponse.getBriefReport().isSetSummary()) {
                                BriefReportSummary swagBriefReportSummary = new BriefReportSummary();
                                swagBriefReportSummary.setGreenStatements(thriftReqResponse.getBriefReport().getSummary().isGreenStatements());
                                swagBriefReportSummary.setRedStatements(thriftReqResponse.getBriefReport().getSummary().isRedStatements());
                                swagBriefReportSummary.setYellowStatements(thriftReqResponse.getBriefReport().getSummary().isYellowStatements());
                                swagBriefReport.setSummary(swagBriefReportSummary);
                            }
                            swagReqResponse.setBriefReport(swagBriefReport);
                        }
                        if (thriftReqResponse.isSetContactPhones()) {
                            var swagContactPhones = new com.rbkmoney.swag.questionary_aggr_proxy.model.ContactPhones();
                            swagContactPhones.setCount(thriftReqResponse.getContactPhones().getCount());
                            swagContactPhones.setPhones(thriftReqResponse.getContactPhones().getPhones());
                            swagReqResponse.setContactPhones(swagContactPhones);
                        }
                        if (thriftReqResponse.isSetPrivateEntity()) {
                            ReqContractor swagReqContractor = ctx.convert(thriftReqResponse.getPrivateEntity(), ReqContractor.class);
                            swagReqResponse.setContractor(swagReqContractor);
                        }
                        return swagReqResponse;
                    })
                    .collect(Collectors.toList());
            ReqResponses reqResponses = new ReqResponses();
            reqResponses.setResponses(swagReqResponseList);
            konturFocusResponse = reqResponses;
        }

        if (value.isSetEgrDetailsResponses()) {
            List<EgrDetailsResponse> swagEgrDetailsResponseList = value.getEgrDetailsResponses().getEgrDetailsResponses().stream()
                    .map(thriftEgrDetailsResponse -> {
                        EgrDetailsResponse swagEgrDetailsResponse = new EgrDetailsResponse();
                        swagEgrDetailsResponse.setInn(thriftEgrDetailsResponse.getInn());
                        swagEgrDetailsResponse.setFocusHref(thriftEgrDetailsResponse.getFocusHref());
                        swagEgrDetailsResponse.setOgrn(thriftEgrDetailsResponse.getOgrn());

                        if (thriftEgrDetailsResponse.isSetContractor()) {
                            EgrDetailsContractor swagEgrDetailsContractor = ctx.convert(thriftEgrDetailsResponse.getContractor(), EgrDetailsContractor.class);
                            swagEgrDetailsResponse.setContractor(swagEgrDetailsContractor);
                        }
                        return swagEgrDetailsResponse;
                    })
                    .collect(Collectors.toList());
            EgrDetailsResponses egrDetailsResponses = new EgrDetailsResponses();
            egrDetailsResponses.setResponses(swagEgrDetailsResponseList);
            konturFocusResponse = egrDetailsResponses;
        }

        if (value.isSetLicencesResponses()) {
            List<LicencesResponse> swagLicencesResponseList = value.getLicencesResponses().getLicenseResponses().stream()
                    .map(licencesResponse -> {
                        return ctx.convert(licencesResponse, LicencesResponse.class);
                    })
                    .collect(Collectors.toList());
            LicencesResponses licencesResponses = new LicencesResponses();
            licencesResponses.setResponses(swagLicencesResponseList);
            konturFocusResponse = licencesResponses;
        }

        if (konturFocusResponse == null) {
            throw new IllegalArgumentException("Need to specify response value");
        }

        return konturFocusResponse;
    }
}
