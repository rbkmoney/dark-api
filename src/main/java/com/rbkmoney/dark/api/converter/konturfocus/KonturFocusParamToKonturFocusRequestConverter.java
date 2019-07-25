package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.model.KonturFocusRequestHolder;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesQuery;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqQuery;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusParams;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class KonturFocusParamToKonturFocusRequestConverter implements Converter<KonturFocusParams, KonturFocusRequestHolder> {

    @Override
    public KonturFocusRequestHolder convert(KonturFocusParams konturFocusParams) {
        KonturFocusRequestHolder konturFocusRequestHolder = new KonturFocusRequestHolder();
        KonturFocusRequest konturFocusRequest = null;
        if (konturFocusParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusEndPoint.REQ
                && konturFocusParams.getRequest() instanceof com.rbkmoney.swag.questionary_aggr_proxy.model.ReqQuery) {
            konturFocusRequestHolder.setKonturFocusEndPoint(KonturFocusEndPoint.req);
            var swagReqQuery = (com.rbkmoney.swag.questionary_aggr_proxy.model.ReqQuery) konturFocusParams.getRequest();
            ReqQuery reqQuery = new ReqQuery();
            reqQuery.setInn(swagReqQuery.getInn());
            reqQuery.setOgrn(swagReqQuery.getOgrn());
            konturFocusRequest = new KonturFocusRequest();
            konturFocusRequest.setReqQuery(reqQuery);
        } else if (konturFocusParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusEndPoint.EGRDETAILS
                && konturFocusParams.getRequest() instanceof com.rbkmoney.swag.questionary_aggr_proxy.model.EgrDetailsQuery) {
            konturFocusRequestHolder.setKonturFocusEndPoint(KonturFocusEndPoint.egrDetails);
            var swagEgrDetails = (com.rbkmoney.swag.questionary_aggr_proxy.model.EgrDetailsQuery) konturFocusParams.getRequest();
            EgrDetailsQuery egrDetailsQuery = new EgrDetailsQuery();
            egrDetailsQuery.setInn(swagEgrDetails.getInn());
            egrDetailsQuery.setOgrn(swagEgrDetails.getOgrn());
            konturFocusRequest = new KonturFocusRequest();
            konturFocusRequest.setEgrDetailsQuery(egrDetailsQuery);
        } else if (konturFocusParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusEndPoint.LICENCES
                && konturFocusParams.getRequest() instanceof com.rbkmoney.swag.questionary_aggr_proxy.model.LicencesQuery) {
            konturFocusRequestHolder.setKonturFocusEndPoint(KonturFocusEndPoint.licences);
            var swagLicenseQuery = ((com.rbkmoney.swag.questionary_aggr_proxy.model.LicencesQuery) konturFocusParams.getRequest());
            LicencesQuery licencesQuery = new LicencesQuery();
            licencesQuery.setInn(swagLicenseQuery.getInn());
            licencesQuery.setOgrn(swagLicenseQuery.getOgrn());
            konturFocusRequest = new KonturFocusRequest();
            konturFocusRequest.setLicencesQuery(licencesQuery);
        }

        if (konturFocusRequest == null) {
            String errMsg = String.format("Need to specify correct request/endpoint. EndPoint '%s' Request '%s'",
                    konturFocusParams.getEndpoint().name(), konturFocusParams.getRequest().getClass().getSimpleName());
            throw new IllegalArgumentException(errMsg);
        }

        konturFocusRequestHolder.setKonturFocusRequest(konturFocusRequest);

        return konturFocusRequestHolder;
    }

}
