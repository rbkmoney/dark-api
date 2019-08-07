package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.RussianBankAccount;
import org.springframework.stereotype.Component;

@Component
public class RussianBankAccountConverter implements
        ThriftConverter<RussianBankAccount, com.rbkmoney.swag.questionary.model.RussianBankAccount>,
        SwagConverter<com.rbkmoney.swag.questionary.model.RussianBankAccount, RussianBankAccount> {

    @Override
    public com.rbkmoney.swag.questionary.model.RussianBankAccount toSwag(RussianBankAccount value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.RussianBankAccount()
                .account(value.getAccount())
                .bankBik(value.getBankBik())
                .bankName(value.getBankName())
                .bankPostAccount(value.getBankPostAccount());
    }

    @Override
    public RussianBankAccount toThrift(com.rbkmoney.swag.questionary.model.RussianBankAccount value, ThriftConverterContext ctx) {
        return new RussianBankAccount()
                .setBankName(value.getBankName())
                .setBankBik(value.getBankBik())
                .setBankPostAccount(value.getBankPostAccount())
                .setAccount(value.getAccount());
    }

}
