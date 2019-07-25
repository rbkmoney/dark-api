package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusLicense;
import com.rbkmoney.swag.questionary_aggr_proxy.model.LicencesResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LicencesResponseSwagConverter implements SwagConverter<LicencesResponse, com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse> {

    @Override
    public LicencesResponse toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse value, SwagConverterContext ctx) {
        LicencesResponse licencesResponse = new LicencesResponse();
        licencesResponse.setFocusHref(value.getFocusHref());
        licencesResponse.setInn(value.getInn());
        licencesResponse.setOgrn(value.getOgrn());
        if (value.isSetLicenses()) {
            List<KonturFocusLicense> konturFocusLicenseList = value.getLicenses().stream()
                    .map(license -> {
                        KonturFocusLicense konturFocusLicense = new KonturFocusLicense();
                        konturFocusLicense.setActivity(license.getActivity());
                        konturFocusLicense.setAddresses(license.getAddresses());
                        konturFocusLicense.setDate(license.getDate());
                        konturFocusLicense.setDateStart(license.getDateStart());
                        konturFocusLicense.setDateEnd(license.getDateEnd());
                        konturFocusLicense.setIssuerNamer(license.getIssuerName());
                        konturFocusLicense.setOfficialNum(license.getOfficialNum());
                        konturFocusLicense.setServices(license.getServices());
                        konturFocusLicense.setStatusDescription(license.getStatusDescription());
                        return konturFocusLicense;
                    })
                    .collect(Collectors.toList());
            licencesResponse.setLicenses(konturFocusLicenseList);
        }

        return licencesResponse;
    }

}
