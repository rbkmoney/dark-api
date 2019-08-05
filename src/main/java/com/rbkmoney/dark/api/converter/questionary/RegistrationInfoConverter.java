package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.RegistrationInfo;
import com.rbkmoney.swag.questionary.model.IndividualRegistrationInfo;
import com.rbkmoney.swag.questionary.model.LegalRegistrationInfo;
import org.springframework.stereotype.Component;

@Component
public class RegistrationInfoConverter implements
        ThriftConverter<RegistrationInfo, com.rbkmoney.swag.questionary.model.RegistrationInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.RegistrationInfo, RegistrationInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.RegistrationInfo toSwag(RegistrationInfo value, SwagConverterContext ctx) {
        if (value.isSetIndividualRegistrationInfo()) {
            return new IndividualRegistrationInfo()
                    .ogrnip(value.getIndividualRegistrationInfo().getOgrnip())
                    .registrationData(value.getIndividualRegistrationInfo().getRegistrationDate())
                    .registrationPlace(value.getIndividualRegistrationInfo().getRegistrationPlace());

        } else if (value.isSetLegalRegistrationInfo()) {
            return new LegalRegistrationInfo()
                    .actualAddress(value.getLegalRegistrationInfo().getActualAddress())
                    .ogrn(value.getLegalRegistrationInfo().getOgrn())
                    .registrationAddress(value.getLegalRegistrationInfo().getRegistrationAddress())
                    .registrationDate(value.getLegalRegistrationInfo().getRegistrationDate())
                    .registrationPlace(value.getLegalRegistrationInfo().getRegistrationPlace());
        } else {
            throw new IllegalArgumentException("Unknown registrationInfo type: " + value.getClass().getName());
        }
    }

    @Override
    public RegistrationInfo toThrift(com.rbkmoney.swag.questionary.model.RegistrationInfo value, ThriftConverterContext ctx) {
        if (value instanceof IndividualRegistrationInfo) {
            var individualRegistrationInfo = new com.rbkmoney.questionary.IndividualRegistrationInfo();
            individualRegistrationInfo.setOgrnip(((IndividualRegistrationInfo) value).getOgrnip());
            individualRegistrationInfo.setRegistrationDate(((IndividualRegistrationInfo) value).getRegistrationData());
            individualRegistrationInfo.setRegistrationPlace(((IndividualRegistrationInfo) value).getRegistrationPlace());

            return RegistrationInfo.individual_registration_info(individualRegistrationInfo);
        } else if (value instanceof LegalRegistrationInfo) {
            var legalRegistrationInfo = new com.rbkmoney.questionary.LegalRegistrationInfo();
            legalRegistrationInfo.setActualAddress(((LegalRegistrationInfo) value).getActualAddress());
            legalRegistrationInfo.setOgrn(((LegalRegistrationInfo) value).getOgrn());
            legalRegistrationInfo.setRegistrationAddress(((LegalRegistrationInfo) value).getRegistrationAddress());
            legalRegistrationInfo.setRegistrationPlace(((LegalRegistrationInfo) value).getRegistrationPlace());
            legalRegistrationInfo.setRegistrationDate(((LegalRegistrationInfo) value).getRegistrationDate());

            return RegistrationInfo.legal_registration_info(legalRegistrationInfo);
        } else {
            throw new IllegalArgumentException("Unknown registrationInfo type: " + value.getClass().getName());
        }
    }

}
