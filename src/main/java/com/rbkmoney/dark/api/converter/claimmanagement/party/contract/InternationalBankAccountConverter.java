package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.domain.CountryCode;
import com.rbkmoney.damsel.domain.InternationalBankAccount;
import com.rbkmoney.damsel.domain.InternationalBankDetails;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.INTERNATIONALBANKACCOUNT;

@Component
public class InternationalBankAccountConverter
        implements
        DarkApiConverter<InternationalBankAccount, com.rbkmoney.swag.claim_management.model.InternationalBankAccount> {

    @Override
    public InternationalBankAccount convertToThrift(
            com.rbkmoney.swag.claim_management.model.InternationalBankAccount swagInternationalBankAccount
    ) {
        InternationalBankAccount internationalBankAccount = new InternationalBankAccount()
                .setAccountHolder(swagInternationalBankAccount.getAccountHolder())
                .setIban(swagInternationalBankAccount.getIban())
                .setNumber(swagInternationalBankAccount.getNumber());

        var swagBankDetails = swagInternationalBankAccount.getBank();

        internationalBankAccount.setBank(swagBankDetails == null
                ? null
                : new InternationalBankDetails()
                .setAbaRtn(swagBankDetails.getAbaRtn())
                .setAddress(swagBankDetails.getAddress())
                .setBic(swagBankDetails.getBic())
                .setName(swagBankDetails.getName())
                .setCountry(convertCountryToResidence(swagBankDetails.getCountry())));

        var swagCorrespondentAccount = swagInternationalBankAccount.getCorrespondentAccount();
        if (swagCorrespondentAccount != null) {
            InternationalBankAccount correspondentAccount = new InternationalBankAccount()
                    .setAccountHolder(swagCorrespondentAccount.getAccountHolder())
                    .setIban(swagCorrespondentAccount.getIban())
                    .setNumber(swagCorrespondentAccount.getNumber());

            var swagCorrespondentAccountBank = swagCorrespondentAccount.getBank();
            if (swagInternationalBankAccount != null) {
                correspondentAccount.setBank(new InternationalBankDetails()
                        .setAbaRtn(swagCorrespondentAccountBank.getAbaRtn())
                        .setAddress(swagCorrespondentAccountBank.getAddress())
                        .setBic(swagCorrespondentAccountBank.getBic())
                        .setName(swagCorrespondentAccountBank.getName())
                        .setCountry(CountryCode.valueOf(swagCorrespondentAccountBank.getCountry())));

                internationalBankAccount.setCorrespondentAccount(correspondentAccount);
            }

        }

        return internationalBankAccount;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.InternationalBankAccount convertToSwag(
            InternationalBankAccount internationalBankAccount
    ) {
        var swagInternationalBankAccount = new com.rbkmoney.swag.claim_management.model.InternationalBankAccount();
        swagInternationalBankAccount.setPayoutToolType(INTERNATIONALBANKACCOUNT);
        swagInternationalBankAccount.setAccountHolder(internationalBankAccount.getAccountHolder());
        swagInternationalBankAccount.setNumber(internationalBankAccount.getNumber());
        swagInternationalBankAccount.setIban(internationalBankAccount.getIban());

        var swagBank = new com.rbkmoney.swag.claim_management.model.InternationalBankDetails();
        if (internationalBankAccount.isSetBank()) {
            InternationalBankDetails bank = internationalBankAccount.getBank();
            swagBank.setAbaRtn(bank.getAbaRtn());
            swagBank.setAddress(bank.getAddress());
            swagBank.setBic(bank.getBic());
            swagBank.setName(bank.getName());
            swagBank.setCountry(bank.getCountry() != null ? bank.getCountry().name() : null);

            swagInternationalBankAccount.setBank(swagBank);
        }

        var swagCorrespondentAccount = new com.rbkmoney.swag.claim_management.model.CorrespondentAccount();
        if (internationalBankAccount.isSetCorrespondentAccount()) {
            InternationalBankAccount correspondentAccount = internationalBankAccount.getCorrespondentAccount();
            swagCorrespondentAccount.setAccountHolder(correspondentAccount.getAccountHolder());
            swagCorrespondentAccount.setIban(correspondentAccount.getIban());
            swagCorrespondentAccount.setNumber(correspondentAccount.getNumber());

            var swagCorrespondentBankDetails = new com.rbkmoney.swag.claim_management.model.CorrespondentBankDetails();
            if (correspondentAccount.isSetBank()) {
                InternationalBankDetails correspondentAccountBank = correspondentAccount.getBank();
                swagCorrespondentBankDetails.setAbaRtn(correspondentAccountBank.getAbaRtn());
                swagCorrespondentBankDetails.setAddress(correspondentAccountBank.getAddress());
                swagCorrespondentBankDetails.setBic(correspondentAccountBank.getBic());
                swagCorrespondentBankDetails.setName(correspondentAccountBank.getName());
                swagCorrespondentBankDetails.setCountry(correspondentAccountBank.getCountry() != null
                        ? correspondentAccountBank.getCountry().name()
                        : null);

                swagCorrespondentAccount.setBank(swagCorrespondentBankDetails);
            }
            swagInternationalBankAccount.setCorrespondentAccount(swagCorrespondentAccount);
        }

        return swagInternationalBankAccount;
    }

    private CountryCode convertCountryToResidence(String country) {
        return country != null ? CountryCode.valueOf(country) : null;
    }

}
