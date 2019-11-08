package com.rbkmoney.dark.api.claimmanagement.converter.party.data;

import com.rbkmoney.damsel.claim_management.ContractModification;
import com.rbkmoney.damsel.claim_management.ContractModificationUnit;
import com.rbkmoney.damsel.domain.InternationalBankAccount;
import com.rbkmoney.damsel.domain.InternationalBankDetails;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.damsel.domain.Residence;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTTERMINATION;
import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.PAYOUTTOOLMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.CONTRACTMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.INTERNATIONALBANKACCOUNT;
import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.RUSSIANBANKACCOUNT;
import static com.rbkmoney.swag.claim_management.model.PayoutToolModification.PayoutToolModificationTypeEnum.CREATION;
import static com.rbkmoney.swag.claim_management.model.PayoutToolModification.PayoutToolModificationTypeEnum.INFOMODIFICATION;
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
                        .setCountry(Residence.AGO)
                        .setName("some name"))
                .setCorrespondentAccount(new InternationalBankAccount()
                        .setNumber("3332211")
                        .setIban("123322")
                        .setAccountHolder("123123")
                        .setBank(new InternationalBankDetails()
                                .setAbaRtn("21312")
                                .setAddress("qweqwe")
                                .setBic("321321")
                                .setCountry(Residence.ALB)
                                .setName("aasdasd")));
        return thriftInternationalBankAccount;
    }

    public static com.rbkmoney.swag.claim_management.model.InternationalBankAccount getTestSwagInternationalBankAccount() {
        var swagInternationalBankAccount =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.InternationalBankAccount.class);
        swagInternationalBankAccount.setPayoutToolType(INTERNATIONALBANKACCOUNT);
        swagInternationalBankAccount.setPayoutToolModificationType(null);
        swagInternationalBankAccount.getBank().setCountry("RUS");
        swagInternationalBankAccount.getCorrespondentAccount().getBank().setCountry("RUS");
        return swagInternationalBankAccount;
    }

    public static com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit getTestSwagPayoutToolModificationUnit() {
        var swagPayoutToolModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit.class);
        swagPayoutToolModificationUnit.setContractModificationType(PAYOUTTOOLMODIFICATIONUNIT);
        switch (swagPayoutToolModificationUnit.getModification().getPayoutToolModificationType()) {
            case CREATION:
                var swagPayoutToolParams = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.PayoutToolParams.class);
                var swagRussianBankAccount = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.RussianBankAccount.class);
                swagRussianBankAccount.setPayoutToolType(RUSSIANBANKACCOUNT);
                swagRussianBankAccount.setPayoutToolModificationType(CREATION);
                swagPayoutToolParams.setPayoutToolModificationType(CREATION);
                swagPayoutToolParams.setToolInfo(swagRussianBankAccount);
                swagPayoutToolModificationUnit.setModification(swagPayoutToolParams);
                break;
            case INFOMODIFICATION:
                var swagInternationalBankAccount = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.InternationalBankAccount.class);

                swagInternationalBankAccount.setPayoutToolType(INTERNATIONALBANKACCOUNT);
                swagInternationalBankAccount.setPayoutToolModificationType(INFOMODIFICATION);
                swagInternationalBankAccount.getBank().setCountry("RUS");
                swagInternationalBankAccount.getCorrespondentAccount().getBank().setCountry("RUS");

                swagPayoutToolModificationUnit.setModification(swagInternationalBankAccount);
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
        var swagContractTerm = new com.rbkmoney.swag.claim_management.model.ContractTermination();
        swagContractTerm.setContractModificationType(CONTRACTTERMINATION);
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
