package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

@Component
public class BankContentSwagConverter
        implements SwagConverter<BankContent, com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankContent> {

    @Override
    public BankContent toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankContent value,
                              SwagConverterContext ctx) {
        BankContent swagBankContent = new BankContent()
                .bic(value.getBic())
                .correspondentAccount(value.getCorrespondentAccount())
                .okpo(value.getOkpo())
                .phone(value.getPhone())
                .swift(value.getSwift())
                .value(value.getValue())
                .unrestrictedValue(value.getUnrestrictedValue())
                .rkc(value.getRkc())
                .registrationNumber(value.getRegistrationNumber());

        if (value.isSetAddress()) {
            swagBankContent.setAddress(ctx.convert(value.getAddress(), DaDataAddress.class));
        }
        if (value.isSetOpf()) {
            swagBankContent.setOpf(ctx.convert(value.getOpf(), Opf.class));
        }
        if (value.isSetPayment()) {
            swagBankContent.setPayment(convertPayment(value.getPayment()));
        }
        if (value.isSetStatus()) {
            swagBankContent.setStatus(ctx.convert(value.getStatus(), DaDataState.class));
        }

        return swagBankContent;
    }

    private Payment convertPayment(com.rbkmoney.questionary_proxy_aggr.base_dadata.Payment payment) {
        Payment swagPayment = new Payment();
        swagPayment.setName(payment.getName());
        swagPayment.setFullName(payment.getFullName());
        swagPayment.setShortName(payment.getShortName());
        return swagPayment;
    }

}
