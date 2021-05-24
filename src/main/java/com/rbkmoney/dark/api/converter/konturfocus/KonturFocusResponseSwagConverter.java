package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class KonturFocusResponseSwagConverter
        implements
        SwagConverter<KonturFocusResponse, com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse> {

    @Override
    public KonturFocusResponse toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse value,
                                      SwagConverterContext ctx) {
        if (value.isSetReqResponses()) {
            List<ReqResponse> swagReqResponseList = value.getReqResponses().getReqResponses().stream()
                    .map(thriftReqResponse -> convertReqResponse(thriftReqResponse, ctx))
                    .collect(Collectors.toList());
            ReqResponses reqResponses = new ReqResponses();
            reqResponses.setResponses(swagReqResponseList);

            return reqResponses;
        }

        if (value.isSetEgrDetailsResponses()) {
            List<EgrDetailsResponse> swagEgrDetailsResponseList =
                    value.getEgrDetailsResponses().getEgrDetailsResponses().stream()
                            .map(thriftEgrDetailsResponse -> convertEgrDetailsResponse(thriftEgrDetailsResponse, ctx))
                            .collect(Collectors.toList());
            EgrDetailsResponses egrDetailsResponses = new EgrDetailsResponses();
            egrDetailsResponses.setResponses(swagEgrDetailsResponseList);

            return egrDetailsResponses;
        }

        if (value.isSetLicencesResponses()) {
            List<LicencesResponse> swagLicencesResponseList =
                    value.getLicencesResponses().getLicenseResponses().stream()
                            .map(licencesResponse -> ctx.convert(licencesResponse, LicencesResponse.class))
                            .collect(Collectors.toList());
            LicencesResponses licencesResponses = new LicencesResponses();
            licencesResponses.setResponses(swagLicencesResponseList);

            return licencesResponses;
        }

        if (value.isSetBeneficialOwnerResponses()) {
            List<BeneficialOwnerResponse> swagBeneficialOwnerResponseList =
                    value.getBeneficialOwnerResponses().getBeneficialOwnerResponses().stream()
                            .map(beneficialOwnerResponse -> ctx
                                    .convert(beneficialOwnerResponse, BeneficialOwnerResponse.class))
                            .collect(Collectors.toList());
            BeneficialOwnerResponses beneficialOwnerResponses = new BeneficialOwnerResponses();
            beneficialOwnerResponses.setResponses(swagBeneficialOwnerResponseList);

            return beneficialOwnerResponses;
        }

        throw new IllegalArgumentException("Need to specify response value");
    }

    private ReqResponse convertReqResponse(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponse reqResponse,
                                           SwagConverterContext ctx) {
        ReqResponse swagReqResponse = new ReqResponse();
        swagReqResponse.setInn(reqResponse.getInn());
        swagReqResponse.setOgrn(reqResponse.getOgrn());
        swagReqResponse.setFocusHref(reqResponse.getFocusHref());
        if (reqResponse.isSetBriefReport()) {
            var swagBriefReport = new com.rbkmoney.swag.questionary_aggr_proxy.model.BriefReport();
            swagBriefReport.setHref(reqResponse.getBriefReport().getHref());
            if (reqResponse.getBriefReport().isSetSummary()) {
                swagBriefReport.setSummary(convertBriefReportSummary(reqResponse.getBriefReport().getSummary()));
            }
            swagReqResponse.setBriefReport(swagBriefReport);
        }
        if (reqResponse.isSetContactPhones()) {
            swagReqResponse.setContactPhones(convertContactPhones(reqResponse.getContactPhones()));
        }
        if (reqResponse.isSetPrivateEntity()) {
            ReqContractor swagReqContractor = ctx.convert(reqResponse.getPrivateEntity(), ReqContractor.class);
            swagReqResponse.setContractor(swagReqContractor);
        }
        return swagReqResponse;
    }

    private EgrDetailsResponse convertEgrDetailsResponse(
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse egrDetailsResponse,
            SwagConverterContext ctx) {
        EgrDetailsResponse swagEgrDetailsResponse = new EgrDetailsResponse();
        swagEgrDetailsResponse.setInn(egrDetailsResponse.getInn());
        swagEgrDetailsResponse.setFocusHref(egrDetailsResponse.getFocusHref());
        swagEgrDetailsResponse.setOgrn(egrDetailsResponse.getOgrn());
        if (egrDetailsResponse.isSetContractor()) {
            EgrDetailsContractor swagEgrDetailsContractor =
                    ctx.convert(egrDetailsResponse.getContractor(), EgrDetailsContractor.class);
            swagEgrDetailsResponse.setContractor(swagEgrDetailsContractor);
        }
        return swagEgrDetailsResponse;
    }

    private BriefReportSummary convertBriefReportSummary(
            com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.BriefReportSummary briefReportSummary) {
        BriefReportSummary swagBriefReportSummary = new BriefReportSummary();
        swagBriefReportSummary.setGreenStatements(briefReportSummary.isGreenStatements());
        swagBriefReportSummary.setRedStatements(briefReportSummary.isRedStatements());
        swagBriefReportSummary.setYellowStatements(briefReportSummary.isYellowStatements());
        return swagBriefReportSummary;
    }

    private ContactPhones convertContactPhones(
            com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ContactPhones contactPhones) {
        var swagContactPhones = new com.rbkmoney.swag.questionary_aggr_proxy.model.ContactPhones();
        swagContactPhones.setCount(contactPhones.getCount());
        swagContactPhones.setPhones(contactPhones.getPhones());
        return swagContactPhones;
    }

}
