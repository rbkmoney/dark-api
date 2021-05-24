package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.manage.QuestionaryData;
import com.rbkmoney.swag.questionary.model.BankAccount;
import com.rbkmoney.swag.questionary.model.ContactInfo;
import com.rbkmoney.swag.questionary.model.Contractor;
import com.rbkmoney.swag.questionary.model.ShopInfo;
import org.springframework.stereotype.Component;

@Component
public class QuestionaryDataConverter implements
        ThriftConverter<QuestionaryData, com.rbkmoney.swag.questionary.model.QuestionaryData>,
        SwagConverter<com.rbkmoney.swag.questionary.model.QuestionaryData, QuestionaryData> {

    @Override
    public com.rbkmoney.swag.questionary.model.QuestionaryData toSwag(QuestionaryData value, SwagConverterContext ctx) {
        var questionaryData = new com.rbkmoney.swag.questionary.model.QuestionaryData();
        if (value.isSetBankAccount()) {
            questionaryData.setBankAccount(ctx.convert(value.getBankAccount(), BankAccount.class));
        }
        if (value.isSetContactInfo()) {
            questionaryData.setContactInfo(ctx.convert(value.getContactInfo(), ContactInfo.class));
        }
        if (value.isSetShopInfo()) {
            questionaryData.setShopInfo(ctx.convert(value.getShopInfo(), ShopInfo.class));
        }
        if (value.isSetContractor()) {
            questionaryData.setContractor(ctx.convert(value.getContractor(), Contractor.class));
        }

        return questionaryData;
    }

    @Override
    public QuestionaryData toThrift(com.rbkmoney.swag.questionary.model.QuestionaryData value,
                                    ThriftConverterContext ctx) {
        QuestionaryData questionaryData = new QuestionaryData();
        if (value.getBankAccount() != null) {
            questionaryData
                    .setBankAccount(ctx.convert(value.getBankAccount(), com.rbkmoney.questionary.BankAccount.class));
        }
        if (value.getContactInfo() != null) {
            questionaryData
                    .setContactInfo(ctx.convert(value.getContactInfo(), com.rbkmoney.questionary.ContactInfo.class));
        }
        if (value.getShopInfo() != null) {
            questionaryData.setShopInfo(ctx.convert(value.getShopInfo(), com.rbkmoney.questionary.ShopInfo.class));
        }
        if (value.getContractor() != null) {
            questionaryData
                    .setContractor(ctx.convert(value.getContractor(), com.rbkmoney.questionary.Contractor.class));
        }
        return questionaryData;
    }

}
