package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.License;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusLicense;
import com.rbkmoney.swag.questionary_aggr_proxy.model.LicencesResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LicencesResponseSwagConverter implements
        SwagConverter<LicencesResponse, com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse> {

    @Override
    public LicencesResponse toSwag(com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse value,
                                   SwagConverterContext ctx) {
        LicencesResponse licencesResponse = new LicencesResponse();
        licencesResponse.setFocusHref(value.getFocusHref());
        licencesResponse.setInn(value.getInn());
        licencesResponse.setOgrn(value.getOgrn());
        if (value.isSetLicenses()) {
            List<KonturFocusLicense> konturFocusLicenseList = value.getLicenses().stream()
                    .map(this::convertLicense)
                    .collect(Collectors.toList());
            licencesResponse.setLicenses(konturFocusLicenseList);
        }

        return licencesResponse;
    }

    private KonturFocusLicense convertLicense(License license) {
        return new KonturFocusLicense()
                .activity(license.getActivity())
                .addresses(license.getAddresses())
                .date(license.getDate())
                .dateStart(license.getDateStart())
                .dateEnd(license.getDateEnd())
                .issuerNamer(license.getIssuerName())
                .officialNum(license.getOfficialNum())
                .services(license.getServices())
                .statusDescription(license.getStatusDescription());
    }

}
