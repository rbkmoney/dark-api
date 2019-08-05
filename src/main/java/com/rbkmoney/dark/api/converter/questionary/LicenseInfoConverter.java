package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.questionary.model.LicenseInfo;
import org.springframework.stereotype.Component;

@Component
public class LicenseInfoConverter implements
        ThriftConverter<com.rbkmoney.questionary.LicenseInfo, LicenseInfo>,
        SwagConverter<LicenseInfo, com.rbkmoney.questionary.LicenseInfo> {

    @Override
    public LicenseInfo toSwag(com.rbkmoney.questionary.LicenseInfo value, SwagConverterContext ctx) {
        return new LicenseInfo()
                .effectiveDate(value.getEffectiveDate())
                .expirationDate(value.getExpirationDate())
                .issueDate(value.getIssueDate())
                .issuerName(value.getIssuerName())
                .licensedActivity(value.getLicensedActivity())
                .officialNum(value.getOfficialNum());
    }

    @Override
    public com.rbkmoney.questionary.LicenseInfo toThrift(LicenseInfo value, ThriftConverterContext ctx) {
        var licenseInfo = new com.rbkmoney.questionary.LicenseInfo();
        licenseInfo.setExpirationDate(value.getExpirationDate());
        licenseInfo.setEffectiveDate(value.getEffectiveDate());
        licenseInfo.setIssueDate(value.getIssueDate());
        licenseInfo.setIssuerName(value.getIssuerName());
        licenseInfo.setOfficialNum(value.getOfficialNum());
        licenseInfo.setLicensedActivity(value.getLicensedActivity());

        return licenseInfo;
    }
}
