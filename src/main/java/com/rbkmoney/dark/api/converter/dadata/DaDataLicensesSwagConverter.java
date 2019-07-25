package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.License;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataLicense;
import org.springframework.stereotype.Component;

@Component
public class DaDataLicensesSwagConverter implements SwagConverter<DaDataLicense, License> {
    @Override
    public DaDataLicense toSwag(License value, SwagConverterContext ctx) {
        DaDataLicense daDataLicense = new DaDataLicense();
        daDataLicense.setActivities(value.getActivities());
        daDataLicense.setAddresses(value.getAddresses());
        daDataLicense.setIssueAuthority(value.getIssueAuthority());
        daDataLicense.setIssueDate(value.getIssueDate());
        daDataLicense.setNumber(value.getNumber());
        daDataLicense.setSeries(value.getSeries());
        daDataLicense.setSuspendAuthority(value.getSuspendAuthority());
        daDataLicense.setSuspendDate(value.getSuspendDate());
        daDataLicense.setValidFrom(value.getValidFrom());
        daDataLicense.setValidTo(value.getValidTo());
        return daDataLicense;
    }
}
