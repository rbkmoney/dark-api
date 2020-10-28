package com.rbkmoney.dark.api.utils;

import com.rbkmoney.file.storage.base.Residence;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.swag.questionary.model.*;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.thrift.TBase;

import java.util.Collections;

import static com.rbkmoney.swag.questionary.model.LegalEntity.LegalEntityTypeEnum.INTERNATIONALLEGALENTITY;

@RequiredArgsConstructor
public class QuestionaryTestData {

    private final MockTBaseProcessor mockTBaseProcessor;

    public QuestionaryParams createIndividualEntityQuestionarySwag() {
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

    public com.rbkmoney.questionary.manage.QuestionaryParams createIndividualEntityQuestionaryThrift() {
        com.rbkmoney.questionary.RussianIndividualEntity russianIndividualEntity = new com.rbkmoney.questionary.RussianIndividualEntity();
        russianIndividualEntity = fillTBaseObject(russianIndividualEntity, com.rbkmoney.questionary.RussianIndividualEntity.class);

        var individualEntity = new com.rbkmoney.questionary.IndividualEntity();
        individualEntity.setRussianIndividualEntity(russianIndividualEntity);

        var questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();
        questionaryData = fillTBaseObject(questionaryData, com.rbkmoney.questionary.manage.QuestionaryData.class);
        questionaryData.setContractor(com.rbkmoney.questionary.Contractor.individual_entity(individualEntity));

        var questionaryParams = new com.rbkmoney.questionary.manage.QuestionaryParams();
        questionaryParams.setId("123456");
        questionaryParams.setOwnerId("12413");
        questionaryParams.setPartyId("12345");
        questionaryParams.setData(questionaryData);
        return questionaryParams;
    }

    public QuestionaryParams createLegalEntityQuestionarySwag() {
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

    public com.rbkmoney.questionary.manage.QuestionaryParams createLegalEntityQuestionaryThrift() {
        var russianBankAccount = new com.rbkmoney.questionary.RussianBankAccount();
        russianBankAccount = fillTBaseObject(russianBankAccount, com.rbkmoney.questionary.RussianBankAccount.class);

        var bankAccount = new com.rbkmoney.questionary.BankAccount();
        bankAccount.setRussianBankAccount(russianBankAccount);

        var shopInfo = new com.rbkmoney.questionary.ShopInfo();
        shopInfo = fillTBaseObject(shopInfo, com.rbkmoney.questionary.ShopInfo.class);

        var contactInfo = new com.rbkmoney.questionary.ContactInfo();
        contactInfo = fillTBaseObject(contactInfo, com.rbkmoney.questionary.ContactInfo.class);

        var russianLegalEntity = new com.rbkmoney.questionary.RussianLegalEntity();
        russianLegalEntity = fillTBaseObject(russianLegalEntity, com.rbkmoney.questionary.RussianLegalEntity.class);

        var legalEntity = new com.rbkmoney.questionary.LegalEntity();
        legalEntity.setRussianLegalEntity(russianLegalEntity);

        var questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();
        questionaryData.setBankAccount(bankAccount);
        questionaryData.setShopInfo(shopInfo);
        questionaryData.setContactInfo(contactInfo);
        questionaryData.setContractor(com.rbkmoney.questionary.Contractor.legal_entity(legalEntity));

        var questionaryParams = new com.rbkmoney.questionary.manage.QuestionaryParams();
        questionaryParams.setId("123456");
        questionaryParams.setOwnerId("12413");
        questionaryParams.setPartyId("12345");
        questionaryParams.setData(questionaryData);
        return questionaryParams;
    }

    public QuestionaryParams createInternationalLegalEntityQuestionarySwag() {
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

    public com.rbkmoney.questionary.manage.QuestionaryParams createInternationalLegalEntityQuestionaryThrift() {
        var bankAccount = new com.rbkmoney.questionary.BankAccount();
        bankAccount.setInternationalBankAccount(createTestIntBankAccount());

        var shopInfo = new com.rbkmoney.questionary.ShopInfo();
        shopInfo = fillTBaseObject(shopInfo, com.rbkmoney.questionary.ShopInfo.class);

        var contactInfo = new com.rbkmoney.questionary.ContactInfo();
        contactInfo = fillTBaseObject(contactInfo, com.rbkmoney.questionary.ContactInfo.class);

        var legalEntity = new com.rbkmoney.questionary.LegalEntity();
        var internationalLegalEntity = new com.rbkmoney.questionary.InternationalLegalEntity();
        internationalLegalEntity = fillTBaseObject(internationalLegalEntity, com.rbkmoney.questionary.InternationalLegalEntity.class);
        legalEntity.setInternationalLegalEntity(internationalLegalEntity);

        var questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();
        questionaryData.setBankAccount(bankAccount);
        questionaryData.setContactInfo(contactInfo);
        questionaryData.setShopInfo(shopInfo);
        questionaryData.setContractor(com.rbkmoney.questionary.Contractor.legal_entity(legalEntity));

        var questionaryParams = new com.rbkmoney.questionary.manage.QuestionaryParams();
        questionaryParams.setId("123456");
        questionaryParams.setOwnerId("12413");
        questionaryParams.setPartyId("12345");
        questionaryParams.setData(questionaryData);
        return questionaryParams;
    }

    private com.rbkmoney.questionary.InternationalBankAccount createTestIntBankAccount() {
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

    @SneakyThrows
    public <T extends TBase> T fillTBaseObject(T tBase, Class<T> type) {
        return mockTBaseProcessor.process(tBase, new TBaseHandler<>(type));
    }
}
