package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.file.storage.base.Residence;
import com.rbkmoney.questionary.InternationalBankAccount;
import com.rbkmoney.questionary.InternationalBankDetails;
import com.rbkmoney.swag.questionary.model.CorrespondentAccount;
import org.springframework.stereotype.Component;

@Component
public class InternationalBankAccountQuestionaryConverter implements
        ThriftConverter<InternationalBankAccount, com.rbkmoney.swag.questionary.model.InternationalBankAccount>,
        SwagConverter<com.rbkmoney.swag.questionary.model.InternationalBankAccount, InternationalBankAccount> {

    @Override
    public com.rbkmoney.swag.questionary.model.InternationalBankAccount toSwag(
            InternationalBankAccount value,
            SwagConverterContext ctx
    ) {
        var internationalBankAccount = new com.rbkmoney.swag.questionary.model.InternationalBankAccount()
                .number(value.getNumber())
                .iban(value.getIban())
                .accountHolder(value.getAccountHolder())
                .bank(getBankDetails(value.getBank()));

        if (value.isSetCorrespondentAccount()) {
            InternationalBankAccount swagCorrAccount = value.getCorrespondentAccount();
            internationalBankAccount.setCorrespondentAccount(new CorrespondentAccount()
                    .number(swagCorrAccount.getNumber())
                    .iban(swagCorrAccount.getIban())
                    .accountHolder(swagCorrAccount.getAccountHolder())
                    .bank(getCorrBankDetails(swagCorrAccount.getBank()))
            );
        }
        return internationalBankAccount;
    }

    @Override
    public InternationalBankAccount toThrift(
            com.rbkmoney.swag.questionary.model.InternationalBankAccount value,
            ThriftConverterContext ctx
    ) {
        InternationalBankAccount internationalBankAccount = new InternationalBankAccount()
                .setNumber(value.getNumber())
                .setIban(value.getIban())
                .setAccountHolder(value.getAccountHolder())
                .setBank(getBankDetails(value.getBank()));

        if (value.getCorrespondentAccount() != null) {
            var thriftCorrespondentAccount = value.getCorrespondentAccount();
            InternationalBankAccount corrBankAccount = new InternationalBankAccount()
                    .setNumber(thriftCorrespondentAccount.getAccountHolder())
                    .setIban(thriftCorrespondentAccount.getIban())
                    .setAccountHolder(thriftCorrespondentAccount.getAccountHolder())
                    .setBank(getBankDetails(thriftCorrespondentAccount.getBank()));
            internationalBankAccount.setCorrespondentAccount(corrBankAccount);
        }

        return internationalBankAccount;
    }

    private com.rbkmoney.swag.questionary.model.CorrespondentBankDetails getCorrBankDetails(
            InternationalBankDetails swagBank
    ) {
        if (swagBank != null) {
            return new com.rbkmoney.swag.questionary.model.CorrespondentBankDetails()
                    .abaRtn(swagBank.getAbaRtn())
                    .address(swagBank.getAddress())
                    .name(swagBank.getName())
                    .bic(swagBank.getBic())
                    .country(swagBank.getCountry() == null ?
                            null : swagBank.getCountry().getValue());
        } else {
            return null;
        }
    }

    private com.rbkmoney.swag.questionary.model.InternationalBankDetails getBankDetails(
            InternationalBankDetails swagBank
    ) {
        if (swagBank != null) {
            return new com.rbkmoney.swag.questionary.model.InternationalBankDetails()
                    .abaRtn(swagBank.getAbaRtn())
                    .address(swagBank.getAddress())
                    .name(swagBank.getName())
                    .bic(swagBank.getBic())
                    .country(swagBank.getCountry() == null ?
                            null : swagBank.getCountry().getValue());
        } else {
            return null;
        }
    }

    private InternationalBankDetails getBankDetails(
            com.rbkmoney.swag.questionary.model.CorrespondentBankDetails thriftBank
    ) {
        if (thriftBank != null) {
            return new InternationalBankDetails()
                    .setName(thriftBank.getName())
                    .setCountry(thriftBank.getCountry() == null ?
                            null : Residence.findByValue(thriftBank.getCountry()))
                    .setAbaRtn(thriftBank.getAbaRtn())
                    .setAddress(thriftBank.getAddress())
                    .setBic(thriftBank.getBic());
        } else {
            return null;
        }
    }

    private InternationalBankDetails getBankDetails(
            com.rbkmoney.swag.questionary.model.InternationalBankDetails thriftBank
    ) {
        if (thriftBank != null) {
            return new InternationalBankDetails()
                    .setName(thriftBank.getName())
                    .setCountry(thriftBank.getCountry() == null ?
                            null : Residence.findByValue(thriftBank.getCountry()))
                    .setAbaRtn(thriftBank.getAbaRtn())
                    .setAddress(thriftBank.getAddress())
                    .setBic(thriftBank.getBic());
        } else {
            return null;
        }
    }

}
