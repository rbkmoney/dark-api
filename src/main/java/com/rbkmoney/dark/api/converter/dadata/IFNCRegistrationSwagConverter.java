package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.Registration;
import com.rbkmoney.swag.questionary_aggr_proxy.model.IFNCRegistration;
import org.springframework.stereotype.Component;

@Component
public class IFNCRegistrationSwagConverter implements SwagConverter<IFNCRegistration, Registration> {
    @Override
    public IFNCRegistration toSwag(Registration value, SwagConverterContext ctx) {
        IFNCRegistration ifncRegistration = new IFNCRegistration();
        ifncRegistration.setType(value.getType());
        ifncRegistration.setIssueAuthority(value.getIssueAuthority());
        ifncRegistration.setIssueDate(value.getIssueDate());
        ifncRegistration.setNumber(value.getNumber());
        ifncRegistration.setSeries(value.getSeries());
        return ifncRegistration;
    }
}
