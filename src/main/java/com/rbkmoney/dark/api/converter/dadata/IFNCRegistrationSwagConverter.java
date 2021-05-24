package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.Registration;
import com.rbkmoney.swag.questionary_aggr_proxy.model.IFNCRegistration;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("AbbreviationAsWordInName")
public class IFNCRegistrationSwagConverter implements SwagConverter<IFNCRegistration, Registration> {

    @Override
    public IFNCRegistration toSwag(Registration value, SwagConverterContext ctx) {
        return new IFNCRegistration()
                .type(value.getType())
                .issueAuthority(value.getIssueAuthority())
                .issueDate(value.getIssueDate())
                .number(value.getNumber())
                .series(value.getSeries());
    }

}
