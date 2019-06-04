package com.rbkmoney.dark.api.service.questionary.utils;

public class ConverterSwagToManageModel {

    public static com.rbkmoney.questionary.BankAccount prepareRussianBankAccount(com.rbkmoney.swag.dark_api.model.RussianBankAccount russianBankAccount) {
        com.rbkmoney.questionary.BankAccount manageRussianBank = new com.rbkmoney.questionary.BankAccount();
        com.rbkmoney.questionary.RussianBankAccount manageRussianBankAccount = new com.rbkmoney.questionary.RussianBankAccount();
        manageRussianBankAccount.setAccount(russianBankAccount.getAccount());
        manageRussianBankAccount.setBankName(russianBankAccount.getBankName());
        manageRussianBankAccount.setBankBik(russianBankAccount.getBankBik());
        manageRussianBankAccount.setBankPostAccount(russianBankAccount.getBankPostAccount());
        manageRussianBank.setRussianBankAccount(manageRussianBankAccount);
        return manageRussianBank;
    }


    public static com.rbkmoney.questionary.ContactInfo prepareContactInfo(com.rbkmoney.swag.dark_api.model.ContactInfo contactInfo) {
        return null;
    }

    public static com.rbkmoney.questionary.ShopInfo prepareShopInfo(com.rbkmoney.swag.dark_api.model.ShopInfo shopInfo) {
        return null;
    }

    public static com.rbkmoney.questionary.Contractor prepareContractor(com.rbkmoney.swag.dark_api.model.Contractor contractor) {

        return null;
    }

}
