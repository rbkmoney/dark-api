package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.BankAccount;
import com.rbkmoney.questionary.RussianBankAccount;
import com.rbkmoney.swag.questionary.model.BankAccount.BankAccountTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class BankAccountConverter implements
        ThriftConverter<BankAccount, com.rbkmoney.swag.questionary.model.BankAccount>,
        SwagConverter<com.rbkmoney.swag.questionary.model.BankAccount, BankAccount> {

    @Override
    public BankAccount toThrift(com.rbkmoney.swag.questionary.model.BankAccount value, ThriftConverterContext ctx) {
        if (value.getBankAccountType() == BankAccountTypeEnum.RUSSIANBANKACCOUNT) {
            return BankAccount.russian_bank_account(ctx.convert(value, RussianBankAccount.class));
        }
        throw new IllegalArgumentException("Unknown bank account type: " + value.getClass().getName());
    }

    @Override
    public com.rbkmoney.swag.questionary.model.BankAccount toSwag(BankAccount value, SwagConverterContext ctx) {
        if (value.isSetRussianBankAccount()) {
            var russianBankAccount = ctx.convert(value.getRussianBankAccount(), com.rbkmoney.swag.questionary.model.RussianBankAccount.class);
            russianBankAccount.setBankAccountType(BankAccountTypeEnum.RUSSIANBANKACCOUNT);
            return russianBankAccount;
        }
        throw new IllegalArgumentException("Unknown bank account type: " + value.getClass().getName());
    }
}
