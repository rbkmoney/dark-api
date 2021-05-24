package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.ResidenceApprove;
import org.springframework.stereotype.Component;

@Component
public class ResidenceApproveConverter implements
        ThriftConverter<ResidenceApprove, com.rbkmoney.swag.questionary.model.ResidenceApprove>,
        SwagConverter<com.rbkmoney.swag.questionary.model.ResidenceApprove, ResidenceApprove> {

    @Override
    public com.rbkmoney.swag.questionary.model.ResidenceApprove toSwag(ResidenceApprove value,
                                                                       SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.ResidenceApprove()
                .beginningDate(value.getBeginningDate())
                .expirationDate(value.getExpirationDate())
                .name(value.getName())
                .number(value.getNumber())
                .series(value.getSeries());
    }

    @Override
    public ResidenceApprove toThrift(com.rbkmoney.swag.questionary.model.ResidenceApprove value,
                                     ThriftConverterContext ctx) {
        ResidenceApprove residenceApprove = new ResidenceApprove();
        residenceApprove.setExpirationDate(value.getExpirationDate());
        residenceApprove.setBeginningDate(value.getBeginningDate());
        residenceApprove.setSeries(value.getSeries());
        residenceApprove.setNumber(value.getNumber());
        residenceApprove.setName(value.getName());
        return residenceApprove;
    }

}
