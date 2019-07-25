package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.Address;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

@Component
public class BankContentSwagConverter implements SwagConverter<BankContent, com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankContent> {
    @Override
    public BankContent toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankContent value, SwagConverterContext ctx) {
        BankContent swagBankContent = new BankContent();
        if (value.isSetAddress()) {
            swagBankContent.setAddress(ctx.convert(value.getAddress(), DaDataAddress.class));
        }
        swagBankContent.setBic(value.getBic());
        swagBankContent.setCorrespondentAccount(value.getCorrespondentAccount());
        swagBankContent.setOkpo(value.getOkpo());
        if (value.isSetOpf()) {
            swagBankContent.setOpf(ctx.convert(value.getOpf(), Opf.class));
        }
        swagBankContent.setPhone(value.getPhone());
        swagBankContent.setSwift(value.getSwift());
        swagBankContent.setValue(value.getValue());
        swagBankContent.setUnrestrictedValue(value.getUnrestrictedValue());
        swagBankContent.setRkc(value.getRkc());

        if (value.isSetPayment()) {
            Payment payment = new Payment();
            payment.setName(value.getPayment().getName());
            payment.setFullName(value.getPayment().getFullName());
            payment.setShortName(value.getPayment().getShortName());
            swagBankContent.setPayment(payment);
        }

        if (value.isSetStatus()) {
            swagBankContent.setStatus(ctx.convert(value.getStatus(), DaDataState.class));
        }

        swagBankContent.setRegistrationNumber(value.getRegistrationNumber());

        return swagBankContent;
    }
}
