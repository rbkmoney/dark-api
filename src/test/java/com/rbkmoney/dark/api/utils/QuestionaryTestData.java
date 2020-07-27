package com.rbkmoney.dark.api.utils;

import com.rbkmoney.file.storage.base.Residence;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.swag.questionary.model.*;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Collections;

import static com.rbkmoney.swag.questionary.model.LegalEntity.LegalEntityTypeEnum.INTERNATIONALLEGALENTITY;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionaryTestData {

    public static QuestionaryParams createIndividualEntityQuestionarySwag() {
        QuestionaryParams questionaryParams = EnhancedRandom.random(QuestionaryParams.class);
        questionaryParams.setVersion("0");
        IndividualEntityContractor individualEntityContractor = new IndividualEntityContractor();
        individualEntityContractor.setContractorType(Contractor.ContractorTypeEnum.INDIVIDUALENTITYCONTRACTOR);

        RussianIndividualEntity russianIndividualEntity = EnhancedRandom.random(RussianIndividualEntity.class);
        russianIndividualEntity.setIdentityDocument(
                EnhancedRandom.random(RussianDomesticPassport.class)
                        .identityDocumentType(IdentityDocument.IdentityDocumentTypeEnum.RUSSIANDOMESTICPASSPORT));

        IndividualRegistrationInfo individualRegistrationInfo = EnhancedRandom.random(IndividualRegistrationInfo.class);
        individualRegistrationInfo.setRegistrationInfoType(RegistrationInfo.RegistrationInfoTypeEnum.INDIVIDUALREGISTRATIONINFO);
        russianIndividualEntity.setRegistrationInfo(individualRegistrationInfo);

        IndividualResidencyInfo individualResidencyInfo = EnhancedRandom.random(IndividualResidencyInfo.class);
        individualResidencyInfo.setResidencyInfoType(ResidencyInfo.ResidencyInfoTypeEnum.INDIVIDUALRESIDENCYINFO);
        russianIndividualEntity.setResidencyInfo(individualResidencyInfo);

        russianIndividualEntity.getAdditionalInfo().setBankAccount(EnhancedRandom.random(com.rbkmoney.swag.questionary.model.RussianBankAccount.class));

        russianIndividualEntity.getAdditionalInfo().setFinancialPosition(
                Collections.singletonList(
                        new AnnualFinancialStatements().financialPositionType(FinancialPosition.FinancialPositionTypeEnum.ANNUALFINANCIALSTATEMENTS)));

        russianIndividualEntity.getAdditionalInfo().setBusinessInfo(
                Collections.singletonList(
                        new AnotherBusiness().description("test").businessInfoType(BusinessInfo.BusinessInfoTypeEnum.ANOTHERBUSINESS)));

        WithoutChiefAccountingOrganization withoutChiefAccountingOrganization = new WithoutChiefAccountingOrganization();
        withoutChiefAccountingOrganization.setAccountantInfoType(AccountantInfo.AccountantInfoTypeEnum.WITHOUTCHIEFACCOUNTINGORGANIZATION);
        withoutChiefAccountingOrganization.setInn("test inn");
        russianIndividualEntity.getAdditionalInfo().setAccountantInfo(withoutChiefAccountingOrganization);

        BeneficialOwner beneficialOwner = EnhancedRandom.random(BeneficialOwner.class);
        beneficialOwner.setResidencyInfo(EnhancedRandom.random(IndividualResidencyInfo.class)
                .residencyInfoType(ResidencyInfo.ResidencyInfoTypeEnum.INDIVIDUALRESIDENCYINFO));
        beneficialOwner.setIdentityDocument(EnhancedRandom.random(RussianDomesticPassport.class)
                .identityDocumentType(IdentityDocument.IdentityDocumentTypeEnum.RUSSIANDOMESTICPASSPORT));
        beneficialOwner.setRussianPrivateEntity(EnhancedRandom.random(RussianPrivateEntity.class));
        russianIndividualEntity.setBeneficialOwners(Collections.singletonList(beneficialOwner));

        russianIndividualEntity.setPropertyInfoDocumentType(EnhancedRandom.random(OtherPropertyInfoDocumentType.class)
                .documentType(PropertyInfoDocumentType.DocumentTypeEnum.OTHERPROPERTYINFODOCUMENTTYPE));

        individualEntityContractor.setIndividualEntity(russianIndividualEntity);

        questionaryParams.getData().setContractor(individualEntityContractor);
        questionaryParams.getData().setBankAccount(EnhancedRandom.random(com.rbkmoney.swag.questionary.model.RussianBankAccount.class));
        questionaryParams.getData().getShopInfo().setLocation(
                new ShopLocationUrl().url("testUrl").locationType(ShopLocation.LocationTypeEnum.SHOPLOCATIONURL));

        return questionaryParams;
    }

    public static com.rbkmoney.questionary.manage.QuestionaryParams createIndividualEntityQuestionaryThrift() throws IOException {
        var questionaryParams = new com.rbkmoney.questionary.manage.QuestionaryParams();
        questionaryParams.setId("123456");
        questionaryParams.setOwnerId("12413");
        questionaryParams.setPartyId("12345");
        var questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();
        questionaryData = new MockTBaseProcessor(MockMode.ALL)
                .process(questionaryData, new TBaseHandler<>(com.rbkmoney.questionary.manage.QuestionaryData.class));
        var individualEntity = new com.rbkmoney.questionary.IndividualEntity();
        individualEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(individualEntity, new TBaseHandler<>(com.rbkmoney.questionary.IndividualEntity.class));
        questionaryData.setContractor(com.rbkmoney.questionary.Contractor.individual_entity(individualEntity));
        questionaryParams.setData(questionaryData);
        return questionaryParams;
    }

    public static QuestionaryParams createLegalEntityQuestionarySwag() {
        QuestionaryParams questionaryParams = EnhancedRandom.random(QuestionaryParams.class);
        questionaryParams.setVersion("0");
        questionaryParams.getData().setShopInfo(
                EnhancedRandom.random(ShopInfo.class).
                        location(EnhancedRandom.random(ShopLocationUrl.class)
                                .locationType(ShopLocation.LocationTypeEnum.SHOPLOCATIONURL)));

        LegalEntityContractor legalEntityContractor = new LegalEntityContractor();
        legalEntityContractor.setContractorType(Contractor.ContractorTypeEnum.LEGALENTITYCONTRACTOR);
        RussianLegalEntity russianLegalEntity = EnhancedRandom.random(RussianLegalEntity.class);
        russianLegalEntity.setRegistrationInfo(EnhancedRandom.random(LegalRegistrationInfo.class)
                .registrationInfoType(RegistrationInfo.RegistrationInfoTypeEnum.LEGALREGISTRATIONINFO));
        russianLegalEntity.setResidencyInfo(EnhancedRandom.random(LegalResidencyInfo.class)
                .residencyInfoType(ResidencyInfo.ResidencyInfoTypeEnum.LEGALRESIDENCYINFO));
        russianLegalEntity.getLegalOwnerInfo().setIdentityDocument(EnhancedRandom.random(RussianDomesticPassport.class)
                .identityDocumentType(IdentityDocument.IdentityDocumentTypeEnum.RUSSIANDOMESTICPASSPORT));
        russianLegalEntity.getAdditionalInfo().setBankAccount(EnhancedRandom.random(com.rbkmoney.swag.questionary.model.RussianBankAccount.class));
        russianLegalEntity.getAdditionalInfo().setFinancialPosition(Collections.singletonList(new AnnualTaxReturnWithMark()
                .financialPositionType(FinancialPosition.FinancialPositionTypeEnum.ANNUALTAXRETURNWITHMARK)));
        russianLegalEntity.getAdditionalInfo().setBusinessInfo(Collections.singletonList(new RetailTradeBusiness()
                .businessInfoType(BusinessInfo.BusinessInfoTypeEnum.RETAILTRADEBUSINESS)));
        russianLegalEntity.getAdditionalInfo().setAccountantInfo(EnhancedRandom.random(WithChiefAccountant.class)
                .accountantInfoType(AccountantInfo.AccountantInfoTypeEnum.WITHCHIEFACCOUNTANT));

        LegalOwnerInfo legalOwnerInfo = EnhancedRandom.random(LegalOwnerInfo.class);
        legalOwnerInfo.setIdentityDocument(EnhancedRandom.random(RussianDomesticPassport.class));
        russianLegalEntity.setLegalOwnerInfo(legalOwnerInfo);

        BeneficialOwner beneficialOwner = EnhancedRandom.random(BeneficialOwner.class);
        beneficialOwner.setResidencyInfo(EnhancedRandom.random(LegalResidencyInfo.class)
                .residencyInfoType(ResidencyInfo.ResidencyInfoTypeEnum.LEGALRESIDENCYINFO));
        beneficialOwner.setIdentityDocument(EnhancedRandom.random(RussianDomesticPassport.class)
                .identityDocumentType(IdentityDocument.IdentityDocumentTypeEnum.RUSSIANDOMESTICPASSPORT));
        beneficialOwner.setRussianPrivateEntity(EnhancedRandom.random(RussianPrivateEntity.class));
        russianLegalEntity.setBeneficialOwner(Collections.singletonList(beneficialOwner));

        FoundersInfo foundersInfo = new FoundersInfo();
        foundersInfo.setFounders(Collections.singletonList(EnhancedRandom.random(RussianLegalEntityFounder.class)
                .founderType(Founder.FounderTypeEnum.RUSSIANLEGALENTITYFOUNDER)));
        foundersInfo.setHeads(Collections.singletonList(EnhancedRandom.random(FounderHead.class)));
        foundersInfo.setLegalOwner(EnhancedRandom.random(FounderHead.class));
        russianLegalEntity.setFoundersInfo(foundersInfo);

        russianLegalEntity.setPropertyInfoDocumentType(EnhancedRandom.random(OtherPropertyInfoDocumentType.class)
                .documentType(PropertyInfoDocumentType.DocumentTypeEnum.OTHERPROPERTYINFODOCUMENTTYPE));

        legalEntityContractor.setLegalEntity(russianLegalEntity);

        questionaryParams.getData().setContractor(legalEntityContractor);
        questionaryParams.getData().setBankAccount(EnhancedRandom.random(RussianBankAccount.class));

        return questionaryParams;
    }

    public static com.rbkmoney.questionary.manage.QuestionaryParams createLegalEntityQuestionaryThrift() throws IOException {
        var questionaryParams = new com.rbkmoney.questionary.manage.QuestionaryParams();
        questionaryParams.setId("123456");
        questionaryParams.setOwnerId("12413");
        questionaryParams.setPartyId("12345");
        var questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();
        var bankAccount = new com.rbkmoney.questionary.BankAccount();
        var russianBankAccount = new com.rbkmoney.questionary.RussianBankAccount();
        russianBankAccount = new MockTBaseProcessor(MockMode.ALL)
                .process(russianBankAccount, new TBaseHandler<>(com.rbkmoney.questionary.RussianBankAccount.class));
        bankAccount.setRussianBankAccount(russianBankAccount);
        questionaryData.setBankAccount(bankAccount);

        var shopInfo = new com.rbkmoney.questionary.ShopInfo();
        shopInfo = new MockTBaseProcessor(MockMode.ALL)
                .process(shopInfo, new TBaseHandler<>(com.rbkmoney.questionary.ShopInfo.class));
        questionaryData.setShopInfo(shopInfo);

        var contactInfo = new com.rbkmoney.questionary.ContactInfo();
        contactInfo = new MockTBaseProcessor(MockMode.ALL)
                .process(contactInfo, new TBaseHandler<>(com.rbkmoney.questionary.ContactInfo.class));
        questionaryData.setContactInfo(contactInfo);

        var legalEntity = new com.rbkmoney.questionary.LegalEntity();
        var russianLegalEntity = new com.rbkmoney.questionary.RussianLegalEntity();
        russianLegalEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(russianLegalEntity, new TBaseHandler<>(com.rbkmoney.questionary.RussianLegalEntity.class));
        legalEntity.setRussianLegalEntity(russianLegalEntity);
        questionaryData.setContractor(com.rbkmoney.questionary.Contractor.legal_entity(legalEntity));
        questionaryParams.setData(questionaryData);
        return questionaryParams;
    }

    public static QuestionaryParams createInternationalLegalEntityQuestionarySwag() {
        QuestionaryParams questionaryParams = EnhancedRandom.random(QuestionaryParams.class);
        questionaryParams.setVersion("0");
        questionaryParams.getData().setShopInfo(
                EnhancedRandom.random(ShopInfo.class).
                        location(EnhancedRandom.random(ShopLocationUrl.class)
                                .locationType(ShopLocation.LocationTypeEnum.SHOPLOCATIONURL)));

        LegalEntityContractor legalEntityContractor = new LegalEntityContractor();
        legalEntityContractor.setContractorType(Contractor.ContractorTypeEnum.LEGALENTITYCONTRACTOR);
        InternationalLegalEntity internationalLegalEntity = new InternationalLegalEntity()
                .tradingName("TrName")
                .registeredNumber("RegName")
                .registeredAddress("RedAddr")
                .actualAddress("ActAddr")
                .legalName("LgName");
        internationalLegalEntity.setLegalEntityType(INTERNATIONALLEGALENTITY);
        legalEntityContractor.setLegalEntity(internationalLegalEntity);

        questionaryParams.getData().setContractor(legalEntityContractor);
        questionaryParams.getData().setBankAccount(EnhancedRandom.random(InternationalBankAccount.class));

        return questionaryParams;
    }

    public static com.rbkmoney.questionary.manage.QuestionaryParams createInternationalLegalEntityQuestionaryThrift()
            throws IOException {
        var questionaryParams = new com.rbkmoney.questionary.manage.QuestionaryParams();
        questionaryParams.setId("123456");
        questionaryParams.setOwnerId("12413");
        questionaryParams.setPartyId("12345");
        var questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();
        var bankAccount = new com.rbkmoney.questionary.BankAccount();
        bankAccount.setInternationalBankAccount(createTestIntBankAccount());
        questionaryData.setBankAccount(bankAccount);

        var shopInfo = new com.rbkmoney.questionary.ShopInfo();
        shopInfo = new MockTBaseProcessor(MockMode.ALL)
                .process(shopInfo, new TBaseHandler<>(com.rbkmoney.questionary.ShopInfo.class));
        questionaryData.setShopInfo(shopInfo);

        var contactInfo = new com.rbkmoney.questionary.ContactInfo();
        contactInfo = new MockTBaseProcessor(MockMode.ALL)
                .process(contactInfo, new TBaseHandler<>(com.rbkmoney.questionary.ContactInfo.class));
        questionaryData.setContactInfo(contactInfo);

        var legalEntity = new com.rbkmoney.questionary.LegalEntity();
        var internationalLegalEntity = new com.rbkmoney.questionary.InternationalLegalEntity();
        internationalLegalEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(
                        internationalLegalEntity,
                        new TBaseHandler<>(com.rbkmoney.questionary.InternationalLegalEntity.class)
                );
        legalEntity.setInternationalLegalEntity(internationalLegalEntity);
        questionaryData.setContractor(com.rbkmoney.questionary.Contractor.legal_entity(legalEntity));
        questionaryParams.setData(questionaryData);
        return questionaryParams;
    }

    private static com.rbkmoney.questionary.InternationalBankAccount createTestIntBankAccount() {
        return new com.rbkmoney.questionary.InternationalBankAccount()
                .setNumber("101")
                .setIban("ibbb")
                .setAccountHolder("holder-1")
                .setBank(new com.rbkmoney.questionary.InternationalBankDetails()
                        .setName("test")
                        .setAddress("addr")
                        .setAbaRtn("abba")
                        .setBic("001122")
                        .setCountry(Residence.RUS)
                );
    }

}
