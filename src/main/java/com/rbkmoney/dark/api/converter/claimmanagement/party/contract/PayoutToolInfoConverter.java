package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.domain.InternationalBankAccount;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import com.rbkmoney.damsel.domain.RussianBankAccount;
import com.rbkmoney.damsel.domain.WalletInfo;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.WALLETINFO;

@Component
@RequiredArgsConstructor
public class PayoutToolInfoConverter
        implements DarkApiConverter<PayoutToolInfo, com.rbkmoney.swag.claim_management.model.PayoutToolInfo> {

    private final DarkApiConverter<InternationalBankAccount,
            com.rbkmoney.swag.claim_management.model.InternationalBankAccount> internationalBankAccountConverter;

    private final DarkApiConverter<RussianBankAccount,
            com.rbkmoney.swag.claim_management.model.RussianBankAccount> claimRussianBankAccountConverter;

    @Override
    public PayoutToolInfo convertToThrift(com.rbkmoney.swag.claim_management.model.PayoutToolInfo swagPayoutToolInfo) {
        PayoutToolInfo payoutToolInfo = new PayoutToolInfo();

        switch (swagPayoutToolInfo.getPayoutToolType()) {
            case RUSSIANBANKACCOUNT:
                var swagRussianBankAccount =
                        (com.rbkmoney.swag.claim_management.model.RussianBankAccount) swagPayoutToolInfo;
                payoutToolInfo.setRussianBankAccount(
                        claimRussianBankAccountConverter.convertToThrift(swagRussianBankAccount)
                );
                break;
            case INTERNATIONALBANKACCOUNT:
                var swagInternationalBankAccount =
                        (com.rbkmoney.swag.claim_management.model.InternationalBankAccount) swagPayoutToolInfo;
                InternationalBankAccount internationalBankAccount =
                        internationalBankAccountConverter.convertToThrift(swagInternationalBankAccount);
                payoutToolInfo.setInternationalBankAccount(internationalBankAccount);
                break;
            case WALLETINFO:
                var swagWalletInfo =
                        (com.rbkmoney.swag.claim_management.model.WalletInfo) swagPayoutToolInfo;
                payoutToolInfo.setWalletInfo(
                        new WalletInfo().setWalletId(swagWalletInfo.getWalletID())
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown payout tool type: " + swagPayoutToolInfo.getPayoutToolType());
        }
        return payoutToolInfo;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.PayoutToolInfo convertToSwag(PayoutToolInfo payoutToolInfo) {
        if (payoutToolInfo.isSetRussianBankAccount()) {
            RussianBankAccount russianBankAccount = payoutToolInfo.getRussianBankAccount();
            return claimRussianBankAccountConverter.convertToSwag(russianBankAccount);
        } else if (payoutToolInfo.isSetInternationalBankAccount()) {
            InternationalBankAccount internationalBankAccount = payoutToolInfo.getInternationalBankAccount();
            return internationalBankAccountConverter.convertToSwag(internationalBankAccount);
        } else if (payoutToolInfo.isSetWalletInfo()) {
            WalletInfo walletInfo = payoutToolInfo.getWalletInfo();
            var swagWalletInfo = new com.rbkmoney.swag.claim_management.model.WalletInfo();
            swagWalletInfo.setPayoutToolType(WALLETINFO);
            swagWalletInfo.setWalletID(walletInfo.getWalletId());
            return swagWalletInfo;
        } else {
            throw new IllegalArgumentException("Unknown payout tool type!");
        }
    }

}
