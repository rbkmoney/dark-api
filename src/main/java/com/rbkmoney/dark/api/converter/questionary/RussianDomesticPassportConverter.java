package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.questionary.model.RussianDomesticPassport;
import org.springframework.stereotype.Component;

@Component
public class RussianDomesticPassportConverter implements
        ThriftConverter<com.rbkmoney.questionary.RussianDomesticPassport, RussianDomesticPassport>,
        SwagConverter<RussianDomesticPassport, com.rbkmoney.questionary.RussianDomesticPassport> {

    @Override
    public RussianDomesticPassport toSwag(com.rbkmoney.questionary.RussianDomesticPassport value, SwagConverterContext ctx) {
        return new RussianDomesticPassport()
                .issuedAt(value.getIssuedAt())
                .issuer(value.getIssuer())
                .issuerCode(value.getIssuerCode())
                .number(value.getNumber())
                .series(value.getSeries());
    }

    @Override
    public com.rbkmoney.questionary.RussianDomesticPassport toThrift(RussianDomesticPassport value, ThriftConverterContext ctx) {
        var russianDomesticPassport = new com.rbkmoney.questionary.RussianDomesticPassport();
        russianDomesticPassport.setIssuerCode(value.getIssuerCode());
        russianDomesticPassport.setIssuer(value.getIssuer());
        russianDomesticPassport.setIssuedAt(value.getIssuedAt());
        russianDomesticPassport.setNumber(value.getNumber());
        russianDomesticPassport.setSeries(value.getSeries());
        return russianDomesticPassport;
    }

}
