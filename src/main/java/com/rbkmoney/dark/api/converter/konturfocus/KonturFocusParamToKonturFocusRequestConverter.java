package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.dark.api.model.KonturFocusRequestHolder;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqQuery;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusParams;
import org.springframework.stereotype.Component;

@Component
public class KonturFocusParamToKonturFocusRequestConverter implements ThriftConverter<KonturFocusRequestHolder, KonturFocusParams> {

    @Override
    public KonturFocusRequestHolder toThrift(KonturFocusParams konturFocusParams, ThriftConverterContext ctx) {
        KonturFocusRequestHolder konturFocusRequestHolder = new KonturFocusRequestHolder();
        KonturFocusRequest konturFocusRequest = null;
        switch (konturFocusParams.getRequest().getKonturFocusRequestType()) {
            case REQQUERY:
                konturFocusRequestHolder.setKonturFocusEndPoint(KonturFocusEndPoint.req);
                var swagReqQuery = (com.rbkmoney.swag.questionary_aggr_proxy.model.ReqQuery) konturFocusParams.getRequest();
                ReqQuery reqQuery = new ReqQuery();
                reqQuery.setInn(swagReqQuery.getInn());
                reqQuery.setOgrn(swagReqQuery.getOgrn());
                konturFocusRequest = new KonturFocusRequest();
                konturFocusRequest.setReqQuery(reqQuery);
                break;
            case EGRDETAILSQUERY:
                konturFocusRequestHolder.setKonturFocusEndPoint(KonturFocusEndPoint.egrDetails);
                var swagEgrDetails = (com.rbkmoney.swag.questionary_aggr_proxy.model.EgrDetailsQuery) konturFocusParams.getRequest();
                EgrDetailsQuery egrDetailsQuery = new EgrDetailsQuery();
                egrDetailsQuery.setInn(swagEgrDetails.getInn());
                egrDetailsQuery.setOgrn(swagEgrDetails.getOgrn());
                konturFocusRequest = new KonturFocusRequest();
                konturFocusRequest.setEgrDetailsQuery(egrDetailsQuery);
                break;
            case LICENCESQUERY:
                konturFocusRequestHolder.setKonturFocusEndPoint(KonturFocusEndPoint.licences);
                var swagLicenseQuery = ((com.rbkmoney.swag.questionary_aggr_proxy.model.LicencesQuery) konturFocusParams.getRequest());
                LicencesQuery licencesQuery = new LicencesQuery();
                licencesQuery.setInn(swagLicenseQuery.getInn());
                licencesQuery.setOgrn(swagLicenseQuery.getOgrn());
                konturFocusRequest = new KonturFocusRequest();
                konturFocusRequest.setLicencesQuery(licencesQuery);
                break;
            default:
                throw new IllegalArgumentException("Unknown endpoint: " + konturFocusParams.getRequest().getKonturFocusRequestType());
        }

        konturFocusRequestHolder.setKonturFocusRequest(konturFocusRequest);

        return konturFocusRequestHolder;
    }

}
