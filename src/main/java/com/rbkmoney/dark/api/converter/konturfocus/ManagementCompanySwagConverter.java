package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ManagementCompany;
import org.springframework.stereotype.Component;

@Component
public class ManagementCompanySwagConverter implements
        SwagConverter<ManagementCompany, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ManagementCompany> {

    @Override
    public ManagementCompany toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ManagementCompany value,
                                    SwagConverterContext ctx) {
        ManagementCompany managementCompany = new ManagementCompany();
        managementCompany.setInn(value.getInn());
        managementCompany.setOgrn(value.getOgrn());
        managementCompany.setName(value.getName());
        managementCompany.setFirstDate(value.getFirstDate());
        managementCompany.setDate(value.getDate());
        return managementCompany;
    }

}
