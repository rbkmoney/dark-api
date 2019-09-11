package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.*;
import com.rbkmoney.swag.questionary.model.WithChiefAccountant;
import org.springframework.stereotype.Component;

@Component
public class AccountantInfoConverter implements
        ThriftConverter<AccountantInfo, com.rbkmoney.swag.questionary.model.AccountantInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.AccountantInfo, AccountantInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.AccountantInfo toSwag(AccountantInfo value, SwagConverterContext ctx) {
        if (value.isSetWithChiefAccountant()) {
            WithChiefAccountant withChiefAccountant = new WithChiefAccountant();
            withChiefAccountant.setAccountantInfoType(com.rbkmoney.swag.questionary.model.AccountantInfo.AccountantInfoTypeEnum.WITHCHIEFACCOUNTANT);

            return withChiefAccountant;
        } else if (value.isSetWithoutChiefAccountant()) {
            if (value.getWithoutChiefAccountant().isSetHeadAccounting()) {
                var headAccounting = new com.rbkmoney.swag.questionary.model.HeadAccounting();
                headAccounting.setWithoutChiefAccountantType(
                        com.rbkmoney.swag.questionary.model.WithoutChiefAccountant.WithoutChiefAccountantTypeEnum.HEADACCOUNTING);
                headAccounting.setAccountantInfoType(com.rbkmoney.swag.questionary.model.AccountantInfo.AccountantInfoTypeEnum.WITHOUTCHIEFACCOUNTANT);

                return headAccounting;
            } else if (value.getWithoutChiefAccountant().isSetIndividualAccountant()) {
                var individualAccountant = new com.rbkmoney.swag.questionary.model.IndividualAccountant();
                individualAccountant.setWithoutChiefAccountantType(
                        com.rbkmoney.swag.questionary.model.WithoutChiefAccountant.WithoutChiefAccountantTypeEnum.INDIVIDUALACCOUNTANT);
                individualAccountant.setAccountantInfoType(com.rbkmoney.swag.questionary.model.AccountantInfo.AccountantInfoTypeEnum.WITHOUTCHIEFACCOUNTANT);

                return individualAccountant;
            } else if (value.getWithoutChiefAccountant().isSetAccountingOrganization()) {
                var accountingOrganization = new com.rbkmoney.swag.questionary.model.AccountingOrganization();
                accountingOrganization.setInn(value.getWithoutChiefAccountant().getAccountingOrganization().getInn());
                accountingOrganization.setWithoutChiefAccountantType(
                        com.rbkmoney.swag.questionary.model.WithoutChiefAccountant.WithoutChiefAccountantTypeEnum.ACCOUNTINGORGANIZATION);

                return accountingOrganization;
            } else {
                throw new IllegalArgumentException("Unknown withoutChiefAccountant type: "
                        + value.getWithoutChiefAccountant().getClass().getName());
            }
        } else {
            throw new IllegalArgumentException("Unknown withoutChiefAccountant type: "
                    + value.getWithChiefAccountant().getClass().getName());
        }
    }

    @Override
    public AccountantInfo toThrift(com.rbkmoney.swag.questionary.model.AccountantInfo value, ThriftConverterContext ctx) {
        switch (value.getAccountantInfoType()) {
            case WITHCHIEFACCOUNTANT:
                return AccountantInfo.with_chief_accountant(new com.rbkmoney.questionary.WithChiefAccountant());
            case WITHOUTCHIEFACCOUNTANT:
                WithoutChiefAccountant withoutChiefAccountant = new WithoutChiefAccountant();
                var swagWithoutChiefAccountant = (com.rbkmoney.swag.questionary.model.WithoutChiefAccountant) value;
                if (swagWithoutChiefAccountant.getWithoutChiefAccountantType() ==
                        com.rbkmoney.swag.questionary.model.WithoutChiefAccountant.WithoutChiefAccountantTypeEnum.ACCOUNTINGORGANIZATION) {
                    var swagWithoutChiefAccOrg = (com.rbkmoney.swag.questionary.model.AccountingOrganization) swagWithoutChiefAccountant;
                    AccountingOrganization accountingOrganization = new AccountingOrganization();
                    accountingOrganization.setInn(swagWithoutChiefAccOrg.getInn());
                    withoutChiefAccountant.setAccountingOrganization(accountingOrganization);
                } else if (swagWithoutChiefAccountant.getWithoutChiefAccountantType() ==
                        com.rbkmoney.swag.questionary.model.WithoutChiefAccountant.WithoutChiefAccountantTypeEnum.HEADACCOUNTING) {
                    withoutChiefAccountant.setHeadAccounting(new HeadAccounting());
                } else if (swagWithoutChiefAccountant.getWithoutChiefAccountantType() ==
                        com.rbkmoney.swag.questionary.model.WithoutChiefAccountant.WithoutChiefAccountantTypeEnum.INDIVIDUALACCOUNTANT) {
                    withoutChiefAccountant.setIndividualAccountant(new IndividualAccountant());
                } else {
                    throw new IllegalArgumentException("Unknown withoutChiefAccountant type: "
                            + swagWithoutChiefAccountant.getClass().getName());
                }
                return AccountantInfo.without_chief_accountant(withoutChiefAccountant);
            default:
                throw new IllegalArgumentException("Unknown accountantInfo type: " + value.getAccountantInfoType());
        }
    }

}
