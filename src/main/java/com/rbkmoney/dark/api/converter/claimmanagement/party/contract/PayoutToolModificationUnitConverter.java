package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.PayoutToolModification;
import com.rbkmoney.damsel.claim_management.PayoutToolModificationUnit;
import com.rbkmoney.damsel.claim_management.PayoutToolParams;
import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.PAYOUTTOOLMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.*;

@Component
@RequiredArgsConstructor
public class PayoutToolModificationUnitConverter
        implements DarkApiConverter<PayoutToolModificationUnit, com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit> {

    private final DarkApiConverter<InternationalBankAccount,
            com.rbkmoney.swag.claim_management.model.InternationalBankAccount> internationalBankAccountConverter;

    private final DarkApiConverter<RussianBankAccount,
            com.rbkmoney.swag.claim_management.model.RussianBankAccount> russianBankAccountConverter;

    @Override
    public PayoutToolModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit swagPayoutToolModificationUnit
    ) {
        PayoutToolModificationUnit payoutToolModificationUnit = new PayoutToolModificationUnit();
        payoutToolModificationUnit.setPayoutToolId(swagPayoutToolModificationUnit.getPayoutToolID());
        PayoutToolModification payoutToolModification = new PayoutToolModification();
        PayoutToolParams creation = new PayoutToolParams();
        var swagCreation = swagPayoutToolModificationUnit.getModification().getCreation();

        creation.setCurrency(
                new CurrencyRef().setSymbolicCode(swagCreation.getCurrency().getSymbolicCode())
        );
        PayoutToolInfo infoModification = new PayoutToolInfo();

        switch (swagCreation.getToolInfo().getPayoutToolType()) {
            case RUSSIANBANKACCOUNT:
                var swagRussianBankAccount =
                        (com.rbkmoney.swag.claim_management.model.RussianBankAccount) swagCreation.getToolInfo();
                infoModification.setRussianBankAccount(
                        russianBankAccountConverter.convertToThrift(swagRussianBankAccount)
                );
                break;
            case INTERNATIONALBANKACCOUNT:
                var swagInternationalBankAccount =
                        (com.rbkmoney.swag.claim_management.model.InternationalBankAccount) swagCreation.getToolInfo();
                InternationalBankAccount internationalBankAccount =
                        internationalBankAccountConverter.convertToThrift(swagInternationalBankAccount);
                infoModification.setInternationalBankAccount(internationalBankAccount);
                break;
            case WALLETINFO:
                var swagWalletInfo =
                        (com.rbkmoney.swag.claim_management.model.WalletInfo) swagCreation.getToolInfo();
                infoModification.setWalletInfo(
                        new WalletInfo().setWalletId(swagWalletInfo.getWalletID())
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown payout tool type: " +
                        swagCreation.getToolInfo().getPayoutToolType());
        }

        payoutToolModification.setCreation(creation);
        payoutToolModification.setInfoModification(infoModification);
        return payoutToolModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit convertToSwag(
            PayoutToolModificationUnit payoutToolModificationUnit
    ) {
        var swagPayoutToolModificationUnit =
                new com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit();
        swagPayoutToolModificationUnit.setPayoutToolID(payoutToolModificationUnit.getPayoutToolId());
        swagPayoutToolModificationUnit.setContractModificationType(PAYOUTTOOLMODIFICATIONUNIT);
        var swagPayoutToolModification = new com.rbkmoney.swag.claim_management.model.PayoutToolModification();
        var swagPayoutToolParams = new com.rbkmoney.swag.claim_management.model.PayoutToolParams();

        PayoutToolModification payoutToolModification = payoutToolModificationUnit.getModification();

        var swagCurrencyRef = new com.rbkmoney.swag.claim_management.model.CurrencyRef();
        swagCurrencyRef.setSymbolicCode(payoutToolModification.getCreation().getCurrency().getSymbolicCode());
        swagPayoutToolParams.setCurrency(swagCurrencyRef);

        PayoutToolInfo toolInfo = payoutToolModification.getCreation().getToolInfo();

        if (toolInfo.isSetRussianBankAccount()) {
            RussianBankAccount russianBankAccount = toolInfo.getRussianBankAccount();
            swagPayoutToolParams.setToolInfo(russianBankAccountConverter.convertToSwag(russianBankAccount));
        } else if (toolInfo.isSetInternationalBankAccount()) {
            InternationalBankAccount internationalBankAccount = toolInfo.getInternationalBankAccount();
            swagPayoutToolParams.setToolInfo(internationalBankAccountConverter.convertToSwag(internationalBankAccount));
        } else if (toolInfo.isSetWalletInfo()) {
            WalletInfo walletInfo = toolInfo.getWalletInfo();
            var swagWalletInfo = new com.rbkmoney.swag.claim_management.model.WalletInfo();
            swagWalletInfo.setPayoutToolType(WALLETINFO);
            swagWalletInfo.setWalletID(walletInfo.getWalletId());
            swagPayoutToolParams.setToolInfo(swagWalletInfo);
        } else {
            throw new IllegalArgumentException("Unknown payout tool type!");
        }

        swagPayoutToolModification.setCreation(swagPayoutToolParams);
        swagPayoutToolModificationUnit.setModification(swagPayoutToolModification);

        return swagPayoutToolModificationUnit;
    }

}
