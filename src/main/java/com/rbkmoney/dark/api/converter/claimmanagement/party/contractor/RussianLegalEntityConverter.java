package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.domain.RussianBankAccount;
import com.rbkmoney.damsel.domain.RussianLegalEntity;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.LegalEntityType.LegalEntityTypeEnum.RUSSIANLEGALENTITY;

@Component
public class RussianLegalEntityConverter
        implements DarkApiConverter<RussianLegalEntity, com.rbkmoney.swag.claim_management.model.RussianLegalEntity> {

    @Override
    public RussianLegalEntity convertToThrift(
            com.rbkmoney.swag.claim_management.model.RussianLegalEntity swagRussianLegalEntity
    ) {
        var swagRussianBankAccount = swagRussianLegalEntity.getRussianBankAccount();
        RussianBankAccount russianBankAccount = new RussianBankAccount()
                .setBankName(swagRussianBankAccount.getBankName())
                .setBankBik(swagRussianBankAccount.getBankBik())
                .setAccount(swagRussianBankAccount.getAccount())
                .setBankPostAccount(swagRussianBankAccount.getBankPostAccount());

        RussianLegalEntity russianLegalEntity = new RussianLegalEntity()
                .setActualAddress(swagRussianLegalEntity.getActualAddress())
                .setInn(swagRussianLegalEntity.getInn())
                .setPostAddress(swagRussianLegalEntity.getPostAddress())
                .setRegisteredName(swagRussianLegalEntity.getRegisteredName())
                .setRegisteredNumber(swagRussianLegalEntity.getRegisteredNumber())
                .setRepresentativeDocument(swagRussianLegalEntity.getRepresentativeDocument())
                .setRepresentativeFullName(swagRussianLegalEntity.getRepresentativeFullName())
                .setRepresentativePosition(swagRussianLegalEntity.getRepresentativePosition())
                .setRussianBankAccount(russianBankAccount);
        return russianLegalEntity;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.RussianLegalEntity convertToSwag(
            RussianLegalEntity russianLegalEntity
    ) {
        var swagRussianLegalEntity = new com.rbkmoney.swag.claim_management.model.RussianLegalEntity();
        swagRussianLegalEntity.setLegalEntityType(RUSSIANLEGALENTITY);
        swagRussianLegalEntity.setActualAddress(russianLegalEntity.getActualAddress());
        swagRussianLegalEntity.setRepresentativePosition(russianLegalEntity.getRepresentativePosition());
        swagRussianLegalEntity.setInn(russianLegalEntity.getInn());
        swagRussianLegalEntity.setPostAddress(russianLegalEntity.getPostAddress());
        swagRussianLegalEntity.setRegisteredName(russianLegalEntity.getRegisteredName());
        swagRussianLegalEntity.setRegisteredNumber(russianLegalEntity.getRegisteredNumber());
        swagRussianLegalEntity.setRepresentativeDocument(russianLegalEntity.getRepresentativeDocument());
        swagRussianLegalEntity.setRepresentativeFullName(russianLegalEntity.getRepresentativeFullName());

        RussianBankAccount russianBankAccount = russianLegalEntity.getRussianBankAccount();
        var swagRussianBankAccount = new com.rbkmoney.swag.claim_management.model.RussianBankAccount();
        swagRussianBankAccount.setAccount(russianBankAccount.getAccount());
        swagRussianBankAccount.setBankName(russianBankAccount.getBankName());
        swagRussianBankAccount.setBankBik(russianBankAccount.getBankBik());
        swagRussianBankAccount.setBankPostAccount(russianBankAccount.getBankPostAccount());

        swagRussianLegalEntity.setRussianBankAccount(swagRussianBankAccount);

        return swagRussianLegalEntity;
    }

}
