package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.RegistrationInfo;
import com.rbkmoney.swag.questionary.model.IndividualRegistrationInfo;
import com.rbkmoney.swag.questionary.model.LegalRegistrationInfo;
import com.rbkmoney.swag.questionary.model.RegistrationInfo.RegistrationInfoTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class RegistrationInfoConverter implements
        ThriftConverter<RegistrationInfo, com.rbkmoney.swag.questionary.model.RegistrationInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.RegistrationInfo, RegistrationInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.RegistrationInfo toSwag(RegistrationInfo value,
                                                                       SwagConverterContext ctx) {
        if (value.isSetIndividualRegistrationInfo()) {
            return new IndividualRegistrationInfo()
                    .ogrnip(value.getIndividualRegistrationInfo().getOgrnip())
                    .registrationDate(value.getIndividualRegistrationInfo().getRegistrationDate())
                    .registrationPlace(value.getIndividualRegistrationInfo().getRegistrationPlace())
                    .registrationInfoType(RegistrationInfoTypeEnum.INDIVIDUALREGISTRATIONINFO);

        } else if (value.isSetLegalRegistrationInfo()) {
            return new LegalRegistrationInfo()
                    .actualAddress(value.getLegalRegistrationInfo().getActualAddress())
                    .ogrn(value.getLegalRegistrationInfo().getOgrn())
                    .registrationAddress(value.getLegalRegistrationInfo().getRegistrationAddress())
                    .registrationDate(value.getLegalRegistrationInfo().getRegistrationDate())
                    .registrationPlace(value.getLegalRegistrationInfo().getRegistrationPlace())
                    .registrationInfoType(RegistrationInfoTypeEnum.LEGALREGISTRATIONINFO);
        } else {
            throw new IllegalArgumentException("Unknown registrationInfo type: " + value.getClass().getName());
        }
    }

    @Override
    public RegistrationInfo toThrift(com.rbkmoney.swag.questionary.model.RegistrationInfo value,
                                     ThriftConverterContext ctx) {
        switch (value.getRegistrationInfoType()) {
            case INDIVIDUALREGISTRATIONINFO:
                var individualRegistrationInfo = new com.rbkmoney.questionary.IndividualRegistrationInfo()
                        .setOgrnip(((IndividualRegistrationInfo) value).getOgrnip())
                        .setRegistrationDate(((IndividualRegistrationInfo) value).getRegistrationDate())
                        .setRegistrationPlace(((IndividualRegistrationInfo) value).getRegistrationPlace());

                return RegistrationInfo.individual_registration_info(individualRegistrationInfo);
            case LEGALREGISTRATIONINFO:
                var legalRegistrationInfo = new com.rbkmoney.questionary.LegalRegistrationInfo()
                        .setActualAddress(((LegalRegistrationInfo) value).getActualAddress())
                        .setOgrn(((LegalRegistrationInfo) value).getOgrn())
                        .setRegistrationAddress(((LegalRegistrationInfo) value).getRegistrationAddress())
                        .setRegistrationPlace(((LegalRegistrationInfo) value).getRegistrationPlace())
                        .setRegistrationDate(((LegalRegistrationInfo) value).getRegistrationDate());

                return RegistrationInfo.legal_registration_info(legalRegistrationInfo);
            default:
                throw new IllegalArgumentException("Unknown registrationInfo type: " + value.getRegistrationInfoType());
        }
    }

}
