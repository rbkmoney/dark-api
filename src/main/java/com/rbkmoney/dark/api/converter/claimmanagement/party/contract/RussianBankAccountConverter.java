package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.domain.RussianBankAccount;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.RUSSIANBANKACCOUNT;

@Component
@RequiredArgsConstructor
public class RussianBankAccountConverter
        implements DarkApiConverter<RussianBankAccount, com.rbkmoney.swag.claim_management.model.RussianBankAccount> {

    @Override
    public RussianBankAccount convertToThrift(
            com.rbkmoney.swag.claim_management.model.RussianBankAccount swagRussianBankAccount
    ) {
        return new RussianBankAccount()
                .setBankName(swagRussianBankAccount.getBankName())
                .setBankBik(swagRussianBankAccount.getBankBik())
                .setBankPostAccount(swagRussianBankAccount.getBankPostAccount())
                .setAccount(swagRussianBankAccount.getAccount());
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.RussianBankAccount convertToSwag(RussianBankAccount russianBankAccount) {
        var swagRussianBankAccount = new com.rbkmoney.swag.claim_management.model.RussianBankAccount();
        swagRussianBankAccount.setPayoutToolType(RUSSIANBANKACCOUNT);
        swagRussianBankAccount.setAccount(russianBankAccount.getAccount());
        swagRussianBankAccount.setBankName(russianBankAccount.getBankName());
        swagRussianBankAccount.setBankBik(russianBankAccount.getBankBik());
        swagRussianBankAccount.setBankPostAccount(russianBankAccount.getBankPostAccount());

        return swagRussianBankAccount;
    }

}
