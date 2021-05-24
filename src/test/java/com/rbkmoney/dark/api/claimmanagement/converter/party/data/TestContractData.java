package com.rbkmoney.dark.api.claimmanagement.converter.party.data;

import com.rbkmoney.damsel.claim_management.ContractModification;
import com.rbkmoney.damsel.claim_management.ContractModificationUnit;
import com.rbkmoney.damsel.domain.CountryCode;
import com.rbkmoney.damsel.domain.InternationalBankAccount;
import com.rbkmoney.damsel.domain.InternationalBankDetails;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.swag.claim_management.model.ContractPayoutToolCreationModification;
import com.rbkmoney.swag.claim_management.model.ContractPayoutToolInfoModification;
import com.rbkmoney.swag.claim_management.model.ContractPayoutToolModificationUnit;
import com.rbkmoney.swag.claim_management.model.ContractTerminationModification;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTTERMINATIONMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTPAYOUTTOOLMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.ContractPayoutToolModification.PayoutToolModificationTypeEnum.CONTRACTPAYOUTTOOLCREATIONMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.ContractPayoutToolModification.PayoutToolModificationTypeEnum.CONTRACTPAYOUTTOOLINFOMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.CONTRACTMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.INTERNATIONALBANKACCOUNT;
import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.RUSSIANBANKACCOUNT;
import static com.rbkmoney.swag.claim_management.model.RepresentativeDocument.DocumentTypeEnum.ARTICLESOFASSOCIATION;
import static com.rbkmoney.swag.claim_management.model.RepresentativeDocument.DocumentTypeEnum.POWEROFATTORNEY;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestContractData {

    public static InternationalBankAccount getTestInternationalBankAccount() {
        InternationalBankAccount thriftInternationalBankAccount = new InternationalBankAccount()
                .setNumber("123456")
                .setIban("54321")
                .setAccountHolder("holder")
                .setBank(new InternationalBankDetails()
                        .setAbaRtn("rtn")
                        .setAddress("addr")
                        .setBic("bic123456")
                        .setCountry(CountryCode.AGO)
                        .setName("some name"))
                .setCorrespondentAccount(new InternationalBankAccount()
                        .setNumber("3332211")
                        .setIban("123322")
                        .setAccountHolder("123123")
                        .setBank(new InternationalBankDetails()
                                .setAbaRtn("21312")
                                .setAddress("qweqwe")
                                .setBic("321321")
                                .setCountry(CountryCode.ALB)
                                .setName("aasdasd")));
        return thriftInternationalBankAccount;
    }

    public static com.rbkmoney.swag.claim_management.model.InternationalBankAccount getTestSwagInternationalBankAccount() {
        var swagInternationalBankAccount =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.InternationalBankAccount.class);
        swagInternationalBankAccount.setPayoutToolType(INTERNATIONALBANKACCOUNT);
        swagInternationalBankAccount.getBank().setCountry("RUS");
        swagInternationalBankAccount.getCorrespondentAccount().getBank().setCountry("RUS");
        return swagInternationalBankAccount;
    }

    public static ContractPayoutToolModificationUnit getTestSwagPayoutToolModificationUnit() {
        var swagPayoutToolModificationUnit =
                EnhancedRandom.random(ContractPayoutToolModificationUnit.class);
        swagPayoutToolModificationUnit.setContractModificationType(CONTRACTPAYOUTTOOLMODIFICATIONUNIT);
        switch (swagPayoutToolModificationUnit.getModification().getPayoutToolModificationType()) {
            case CONTRACTPAYOUTTOOLCREATIONMODIFICATION:
                var swagPayoutToolParams = EnhancedRandom.random(ContractPayoutToolCreationModification.class);
                var swagRussianBankAccount = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.RussianBankAccount.class);
                swagRussianBankAccount.setPayoutToolType(RUSSIANBANKACCOUNT);
                swagPayoutToolParams.setPayoutToolModificationType(CONTRACTPAYOUTTOOLCREATIONMODIFICATION);
                swagPayoutToolParams.setToolInfo(swagRussianBankAccount);
                swagPayoutToolModificationUnit.setModification(swagPayoutToolParams);
                break;
            case CONTRACTPAYOUTTOOLINFOMODIFICATION:
                var contractPayoutToolInfoModification = EnhancedRandom.random(ContractPayoutToolInfoModification.class);
                var swagInternationalBankAccount = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.InternationalBankAccount.class);

                swagInternationalBankAccount.setPayoutToolType(INTERNATIONALBANKACCOUNT);
                swagInternationalBankAccount.getBank().setCountry("RUS");
                swagInternationalBankAccount.getCorrespondentAccount().getBank().setCountry("RUS");
                contractPayoutToolInfoModification.setPayoutToolInfo(swagInternationalBankAccount);
                contractPayoutToolInfoModification.setPayoutToolModificationType(CONTRACTPAYOUTTOOLINFOMODIFICATION);

                swagPayoutToolModificationUnit.setModification(contractPayoutToolInfoModification);
                break;
            default:
                throw new IllegalArgumentException("Unknown PayoutTool modification type!");
        }
        return swagPayoutToolModificationUnit;
    }

    public static com.rbkmoney.swag.claim_management.model.ContractModificationUnit getTestSwagContractModificationUnit() {
        var swagContractModificationUnit = new com.rbkmoney.swag.claim_management.model.ContractModificationUnit();
        swagContractModificationUnit.setId("123");
        swagContractModificationUnit.setPartyModificationType(CONTRACTMODIFICATIONUNIT);
        var swagContractTerm = new ContractTerminationModification();
        swagContractTerm.setContractModificationType(CONTRACTTERMINATIONMODIFICATION);
        swagContractTerm.setReason("some reason!");
        swagContractModificationUnit.setModification(swagContractTerm);
        return swagContractModificationUnit;
    }

    public static ContractModificationUnit getTestThriftContractModificationUnit() {
        ContractModificationUnit thriftContractModificationUnit = new ContractModificationUnit();
        thriftContractModificationUnit.setId("123");
        LegalAgreement legalAgreement = new LegalAgreement()
                .setLegalAgreementId("111")
                .setSignedAt("222")
                .setValidUntil("333");
        ContractModification contractModification = new ContractModification();
        contractModification.setLegalAgreementBinding(legalAgreement);
        thriftContractModificationUnit.setModification(contractModification);
        return thriftContractModificationUnit;
    }

    public static com.rbkmoney.swag.claim_management.model.RepresentativeDocument prepareDocument(
            com.rbkmoney.swag.claim_management.model.RepresentativeDocument swagRepresentativeDocument
    ) {
        switch (swagRepresentativeDocument.getDocumentType()) {
            case ARTICLESOFASSOCIATION:
                var articlesOfAssociation = new com.rbkmoney.swag.claim_management.model.ArticlesOfAssociation();
                articlesOfAssociation.setDocumentType(ARTICLESOFASSOCIATION);
                return articlesOfAssociation;
            case POWEROFATTORNEY:
                var powerOfAttorney = new com.rbkmoney.swag.claim_management.model.PowerOfAttorney();
                powerOfAttorney.setLegalAgreementID("123");
                powerOfAttorney.setSignedAt("324234");
                powerOfAttorney.setValidUntil("12312333");
                powerOfAttorney.setDocumentType(POWEROFATTORNEY);
                return powerOfAttorney;
            default:
                throw new IllegalArgumentException("Unknown representative document type!");
        }
    }

}
