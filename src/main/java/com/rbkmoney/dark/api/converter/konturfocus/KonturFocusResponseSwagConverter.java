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
                                swagBriefReport.setSummary(convertBriefReportSummary(thriftReqResponse.getBriefReport().getSummary()));
                            }
                            swagReqResponse.setBriefReport(swagBriefReport);
                        }
                        if (thriftReqResponse.isSetContactPhones()) {
                            swagReqResponse.setContactPhones(convertContactPhones(thriftReqResponse.getContactPhones()));
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

            return reqResponses;
        }

        if (value.isSetEgrDetailsResponses()) {
            List<EgrDetailsResponse> swagEgrDetailsResponseList = value.getEgrDetailsResponses().getEgrDetailsResponses().stream()
                    .map(thriftEgrDetailsResponse -> {
                        return convertEgrDetailsResponse(thriftEgrDetailsResponse, ctx);
                    })
                    .collect(Collectors.toList());
            EgrDetailsResponses egrDetailsResponses = new EgrDetailsResponses();
            egrDetailsResponses.setResponses(swagEgrDetailsResponseList);

            return egrDetailsResponses;
        }

        if (value.isSetLicencesResponses()) {
            List<LicencesResponse> swagLicencesResponseList = value.getLicencesResponses().getLicenseResponses().stream()
                    .map(licencesResponse -> ctx.convert(licencesResponse, LicencesResponse.class))
                    .collect(Collectors.toList());
            LicencesResponses licencesResponses = new LicencesResponses();
            licencesResponses.setResponses(swagLicencesResponseList);

            return licencesResponses;
        }

        throw new IllegalArgumentException("Need to specify response value");
    }

    private EgrDetailsResponse convertEgrDetailsResponse(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse egrDetailsResponse,
                                                         SwagConverterContext ctx) {
        EgrDetailsResponse swagEgrDetailsResponse = new EgrDetailsResponse();
        swagEgrDetailsResponse.setInn(egrDetailsResponse.getInn());
        swagEgrDetailsResponse.setFocusHref(egrDetailsResponse.getFocusHref());
        swagEgrDetailsResponse.setOgrn(egrDetailsResponse.getOgrn());
        if (egrDetailsResponse.isSetContractor()) {
            EgrDetailsContractor swagEgrDetailsContractor = ctx.convert(egrDetailsResponse.getContractor(), EgrDetailsContractor.class);
            swagEgrDetailsResponse.setContractor(swagEgrDetailsContractor);
        }
        return swagEgrDetailsResponse;
    }

    private BriefReportSummary convertBriefReportSummary(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.BriefReportSummary briefReportSummary) {
        BriefReportSummary swagBriefReportSummary = new BriefReportSummary();
        swagBriefReportSummary.setGreenStatements(briefReportSummary.isGreenStatements());
        swagBriefReportSummary.setRedStatements(briefReportSummary.isRedStatements());
        swagBriefReportSummary.setYellowStatements(briefReportSummary.isYellowStatements());
        return swagBriefReportSummary;
    }

    private ContactPhones convertContactPhones(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ContactPhones contactPhones) {
        var swagContactPhones = new com.rbkmoney.swag.questionary_aggr_proxy.model.ContactPhones();
        swagContactPhones.setCount(contactPhones.getCount());
        swagContactPhones.setPhones(contactPhones.getPhones());
        return swagContactPhones;
    }

}
