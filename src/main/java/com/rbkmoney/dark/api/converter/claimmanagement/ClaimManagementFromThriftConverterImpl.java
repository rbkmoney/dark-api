package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.swag.claim_management.model.ContractTemplateRef;
import com.rbkmoney.swag.claim_management.model.PaymentInstitutionRef;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.rbkmoney.swag.claim_management.model.CommentModification.CommentModificationTypeEnum.COMMENTCREATED;
import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTPARAMS;
import static com.rbkmoney.swag.claim_management.model.Contractor.ContractorTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.ContractorModification.ContractorModificationTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.DocumentModification.DocumentModificationTypeEnum.DOCUMENTCREATED;
import static com.rbkmoney.swag.claim_management.model.FileModification.FileModificationTypeEnum.FILECREATED;
import static com.rbkmoney.swag.claim_management.model.LegalEntity.LegalEntityTypeEnum.INTERNATIONALLEGALENTITY;
import static com.rbkmoney.swag.claim_management.model.LegalEntity.LegalEntityTypeEnum.RUSSIANLEGALENTITY;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.PARTYMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.PartyModification.PartyModificationTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.RepresentativeDocument.DocumentTypeEnum.ARTICLESOFASSOCIATION;
import static com.rbkmoney.swag.claim_management.model.RepresentativeDocument.DocumentTypeEnum.POWEROFATTORNEY;

@Component
public class ClaimManagementFromThriftConverterImpl implements ClaimManagementFromThriftConverter {

    @Override
    public com.rbkmoney.swag.claim_management.model.Claim convertClaimFromThrift(Claim sourceClaim) {
        var claim = new com.rbkmoney.swag.claim_management.model.Claim();
        claim.setId(sourceClaim.getId());
        //TODO: посмотреть какой статус будет
        claim.setStatus(sourceClaim.getStatus().toString());
        claim.setChangeset(convertClaimChangesetFromThrift(sourceClaim.getChangeset()));
        claim.setCreatedAt(OffsetDateTime.parse(sourceClaim.getCreatedAt()));
        claim.setUpdatedAt(OffsetDateTime.parse(sourceClaim.getUpdatedAt()));
        //TODO: метадата это MAP - правильно ли ее так передавать?
        claim.setMetadata(sourceClaim.getMetadata());
        return claim;
    }

    @Override
    public List<com.rbkmoney.swag.claim_management.model.Claim> convertClaimListFromThrift(List<Claim> sourceClaimList) {
        List<com.rbkmoney.swag.claim_management.model.Claim> claims = new ArrayList<>();
        for (com.rbkmoney.damsel.claim_management.Claim sourceClaim : sourceClaimList) {
            claims.add(convertClaimFromThrift(sourceClaim));
        }
        return claims;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ClaimChangeset convertClaimChangesetFromThrift(
            List<ModificationUnit> changeset
    ) {
        var claimChangeset = new com.rbkmoney.swag.claim_management.model.ClaimChangeset();

        for (ModificationUnit unit : changeset) {
            var swagModificationUnit = new com.rbkmoney.swag.claim_management.model.ModificationUnit();
            swagModificationUnit.setModificationID(unit.getModificationId());
            swagModificationUnit.setCreatedAt(OffsetDateTime.parse(unit.getCreatedAt()));

            if (unit.getModification().isSetClaimModfication()) {
                swagModificationUnit.setModification(convertClaimModificationUnitToSwag(unit));
            } else if (unit.getModification().isSetPartyModification()) {
                swagModificationUnit.setModification(convertPartyModificationUnitToSwag(unit));
            } else {
                throw new IllegalArgumentException("Unknown modification type!");
            }

            claimChangeset.add(swagModificationUnit);
        }

        return claimChangeset;
    }

    private com.rbkmoney.swag.claim_management.model.Modification convertClaimModificationUnitToSwag(
            ModificationUnit unit
    ) {
        ClaimModification claimModfication = unit.getModification().getClaimModfication();
        if (claimModfication.isSetDocumentModification()) {
            DocumentModificationUnit documentModification = claimModfication.getDocumentModification();
            return convertDocumentModificationUnitToSwag(documentModification);
        } else if (claimModfication.isSetCommentModification()) {
            CommentModificationUnit commentModification = claimModfication.getCommentModification();
            return convertCommentModificationUnitToSwag(commentModification);
        } else if (claimModfication.isSetStatusModification()) {
            StatusModificationUnit statusModification = claimModfication.getStatusModification();
            return convertStatusModificationUnitToSwag(statusModification);
        } else if (claimModfication.isSetFileModification()) {
            FileModificationUnit fileModificationUnit = claimModfication.getFileModification();
            return convertFileModificationUnitToSwag(fileModificationUnit);
        } else {
            throw new IllegalArgumentException("Unknown claim modification type!");
        }
    }

    private com.rbkmoney.swag.claim_management.model.Modification convertPartyModificationUnitToSwag(
            ModificationUnit unit
    ) {
        PartyModification partyModification = unit.getModification().getPartyModification();
        if (partyModification.isSetContractModification()) {
            ContractModificationUnit contractModificationUnit = partyModification.getContractModification();

            return convertContractModificationUnitToSwag(contractModificationUnit);
        } else if (partyModification.isSetContractorModification()) {
            ContractorModificationUnit contractorModificationUnit = partyModification.getContractorModification();

            return convertContractorModificationToSwag(contractorModificationUnit);
        } else if (partyModification.isSetShopModification()) {
            ShopModificationUnit shopModificationUnit = partyModification.getShopModification();

            var swagShopModificationUnit = new com.rbkmoney.swag.claim_management.model.ShopModificationUnit();
            swagShopModificationUnit.setPartyModificationType(SHOPMODIFICATIONUNIT);
            swagShopModificationUnit.setModificationType(PARTYMODIFICATION);
            swagShopModificationUnit.setId(shopModificationUnit.getId());
            swagShopModificationUnit.setModification(convertShopModificationToSwag(shopModificationUnit));

            return swagShopModificationUnit;
        } else {
            throw new IllegalArgumentException("Unknown party modification type!");
        }
    }

    private com.rbkmoney.swag.claim_management.model.ContractModificationUnit convertContractModificationUnitToSwag(
            ContractModificationUnit contractModificationUnit
    ) {
        var swagContractModificationUnit = new com.rbkmoney.swag.claim_management.model.ContractModificationUnit();
        swagContractModificationUnit.setId(contractModificationUnit.getId());
        swagContractModificationUnit.setModificationType(PARTYMODIFICATION);
        swagContractModificationUnit.setPartyModificationType(CONTRACTMODIFICATIONUNIT);
        ContractModification contractModification = contractModificationUnit.getModification();

        if (contractModification.isSetCreation()) {
            ContractParams creation = contractModification.getCreation();
            swagContractModificationUnit.setModification(convertContractModificationCreationToSwag(creation));
        } else if (contractModification.isSetAdjustmentModification()) {
            ContractAdjustmentModificationUnit adjustmentModification =
                    contractModification.getAdjustmentModification();
            swagContractModificationUnit.setModification(convertContractAdjustmentModificationUnitToSwag(adjustmentModification));
        } else if (contractModification.isSetContractorModification()) {
            var swagContractModification = new com.rbkmoney.swag.claim_management.model.ContractorID();
            swagContractModification.setContractorID(contractModification.getContractorModification());
            swagContractModificationUnit.setModification(swagContractModification);
        } else if (contractModification.isSetTermination()) {
            var swagContractTermination = new com.rbkmoney.swag.claim_management.model.ContractTermination();
            ContractTermination contractTermination = contractModification.getTermination();
            swagContractTermination.setReason(contractTermination.getReason());
            swagContractModificationUnit.setModification(swagContractTermination);
        } else if (contractModification.isSetLegalAgreementBinding()) {
            LegalAgreement legalAgreementBinding = contractModification.getLegalAgreementBinding();
            swagContractModificationUnit.setModification(convertContractLegalAgreementToSwag(legalAgreementBinding));
        } else if (contractModification.isSetPayoutToolModification()) {
            PayoutToolModificationUnit payoutToolModificationUnit = contractModification.getPayoutToolModification();
            swagContractModificationUnit.setModification(convertPayoutToolModificationUnitToSwag(payoutToolModificationUnit));
        } else if (contractModification.isSetReportPreferencesModification()) {
            ReportPreferences reportPreferences = contractModification.getReportPreferencesModification();
            swagContractModificationUnit.setModification(convertReportPreferencesToSwag(reportPreferences));
        } else {
            throw new IllegalArgumentException("Unknown contract modification type!");
        }
        return swagContractModificationUnit;
    }

    private com.rbkmoney.swag.claim_management.model.ReportPreferences convertReportPreferencesToSwag(
            ReportPreferences reportPreferences
    ) {
        ServiceAcceptanceActPreferences serviceAcceptanceActPreferences =
            reportPreferences.getServiceAcceptanceActPreferences();
        var swagReportPreferences =
                new com.rbkmoney.swag.claim_management.model.ReportPreferences();
        var swagServiceAcceptanceActPreferences =
                new com.rbkmoney.swag.claim_management.model.ServiceAcceptanceActPreferences();
        var swagSchedule = new com.rbkmoney.swag.claim_management.model.BusinessScheduleRef();
        swagSchedule.setId(serviceAcceptanceActPreferences.getSchedule().getId());

        Representative signer = serviceAcceptanceActPreferences.getSigner();
        var swagSigner = new com.rbkmoney.swag.claim_management.model.Representative();
        swagSigner.setFullName(signer.getFullName());
        swagSigner.setPosition(signer.getPosition());
        swagSigner.setDocument(convertRepresentativeDocumentToSwag(signer));

        swagServiceAcceptanceActPreferences.setSchedule(swagSchedule);
        swagServiceAcceptanceActPreferences.setSigner(swagSigner);
        swagReportPreferences.setServiceAcceptanceActPreferences(swagServiceAcceptanceActPreferences);

        return swagReportPreferences;
    }

    private com.rbkmoney.swag.claim_management.model.RepresentativeDocument convertRepresentativeDocumentToSwag(
            Representative signer
    ) {
        if (signer.getDocument().isSetArticlesOfAssociation()) {
            var swagArticlesOfAssociation = new com.rbkmoney.swag.claim_management.model.ArticlesOfAssociation();
            swagArticlesOfAssociation.setDocumentType(ARTICLESOFASSOCIATION);
            return swagArticlesOfAssociation;
        } else if (signer.getDocument().isSetPowerOfAttorney()) {
            var swagPowerOfAttorney = new com.rbkmoney.swag.claim_management.model.PowerOfAttorney();
            swagPowerOfAttorney.setDocumentType(POWEROFATTORNEY);
            LegalAgreement powerOfAttorney = signer.getDocument().getPowerOfAttorney();
            swagPowerOfAttorney.setLegalAgreementID(powerOfAttorney.getLegalAgreementId());
            swagPowerOfAttorney.setSignedAt(powerOfAttorney.getSignedAt());
            swagPowerOfAttorney.setValidUntil(powerOfAttorney.getSignedAt());
            return swagPowerOfAttorney;
        } else {
            throw new IllegalArgumentException("Unknown representative document type!");
        }
    }

    private com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit convertPayoutToolModificationUnitToSwag(
            PayoutToolModificationUnit payoutToolModificationUnit
    ) {
        var swagPayoutToolModificationUnit =
                new com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit();
        swagPayoutToolModificationUnit.setPayoutToolID(payoutToolModificationUnit.getPayoutToolId());
        var swagPayoutToolModification = new com.rbkmoney.swag.claim_management.model.PayoutToolModification();
        var swagPayoutToolParams = new com.rbkmoney.swag.claim_management.model.PayoutToolParams();

        PayoutToolModification payoutToolModification = payoutToolModificationUnit.getModification();

        var swagCurrencyRef = new com.rbkmoney.swag.claim_management.model.CurrencyRef();
        swagCurrencyRef.setSymbolicCode(payoutToolModification.getCreation().getCurrency().getSymbolicCode());
        swagPayoutToolParams.setCurrency(swagCurrencyRef);

        PayoutToolInfo toolInfo = payoutToolModification.getCreation().getToolInfo();

        if (toolInfo.isSetRussianBankAccount()) {
            RussianBankAccount russianBankAccount = toolInfo.getRussianBankAccount();
            swagPayoutToolParams.setToolInfo(convertRussianBankAccountToSwag(russianBankAccount));
        } else if (toolInfo.isSetInternationalBankAccount()) {
            InternationalBankAccount internationalBankAccount = toolInfo.getInternationalBankAccount();
            swagPayoutToolParams.setToolInfo(convertInternationalBankAccountToSwag(internationalBankAccount));
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

    private com.rbkmoney.swag.claim_management.model.InternationalBankAccount convertInternationalBankAccountToSwag(
            InternationalBankAccount internationalBankAccount
    ) {
        var swagInternationalBankAccount = new com.rbkmoney.swag.claim_management.model.InternationalBankAccount();
        swagInternationalBankAccount.setPayoutToolType(INTERNATIONALBANKACCOUNT);
        swagInternationalBankAccount.setAccountHolder(internationalBankAccount.getAccountHolder());
        swagInternationalBankAccount.setNumber(internationalBankAccount.getNumber());
        swagInternationalBankAccount.setIban(internationalBankAccount.getIban());

        var swagBank = new com.rbkmoney.swag.claim_management.model.InternationalBankDetails();
        InternationalBankDetails bank = internationalBankAccount.getBank();
        swagBank.setAbaRtn(bank.getAbaRtn());
        swagBank.setAddress(bank.getAddress());
        swagBank.setBic(bank.getBic());
        swagBank.setName(bank.getName());
        swagBank.setCountry(bank.getCountry().name());

        swagInternationalBankAccount.setBank(swagBank);

        var swagCorrespondentAccount = new com.rbkmoney.swag.claim_management.model.CorrespondentAccount();
        InternationalBankAccount correspondentAccount = internationalBankAccount.getCorrespondentAccount();
        swagCorrespondentAccount.setAccountHolder(correspondentAccount.getAccountHolder());
        swagCorrespondentAccount.setIban(correspondentAccount.getIban());
        swagCorrespondentAccount.setNumber(correspondentAccount.getNumber());

        var swagCorrespondentBankDetails = new com.rbkmoney.swag.claim_management.model.CorrespondentBankDetails();
        InternationalBankDetails correspondentAccountBank = correspondentAccount.getBank();
        swagCorrespondentBankDetails.setAbaRtn(correspondentAccountBank.getAbaRtn());
        swagCorrespondentBankDetails.setAddress(correspondentAccountBank.getAddress());
        swagCorrespondentBankDetails.setBic(correspondentAccountBank.getBic());
        swagCorrespondentBankDetails.setName(correspondentAccountBank.getName());
        swagCorrespondentBankDetails.setCountry(correspondentAccountBank.getCountry().name());

        swagCorrespondentAccount.setBank(swagCorrespondentBankDetails);
        swagInternationalBankAccount.setCorrespondentAccount(swagCorrespondentAccount);

        return swagInternationalBankAccount;
    }

    private com.rbkmoney.swag.claim_management.model.RussianBankAccount convertRussianBankAccountToSwag(
            RussianBankAccount russianBankAccount
    ) {
        var swagRussianBankAccount = new com.rbkmoney.swag.claim_management.model.RussianBankAccount();
        swagRussianBankAccount.setPayoutToolType(RUSSIANBANKACCOUNT);
        swagRussianBankAccount.setAccount(russianBankAccount.getAccount());
        swagRussianBankAccount.setBankName(russianBankAccount.getBankName());
        swagRussianBankAccount.setBankBik(russianBankAccount.getBankBik());
        swagRussianBankAccount.setBankPostAccount(russianBankAccount.getBankPostAccount());

        return swagRussianBankAccount;
    }

    private com.rbkmoney.swag.claim_management.model.LegalAgreement convertContractLegalAgreementToSwag(
            LegalAgreement legalAgreementBinding
    ) {
        var swagLegalAgreement = new com.rbkmoney.swag.claim_management.model.LegalAgreement();
        swagLegalAgreement.setLegalAgreementID(legalAgreementBinding.getLegalAgreementId());
        swagLegalAgreement.setSignedAt(legalAgreementBinding.getSignedAt());
        swagLegalAgreement.setValidUntil(legalAgreementBinding.getValidUntil());
        return swagLegalAgreement;
    }

    private com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit convertContractAdjustmentModificationUnitToSwag(
            ContractAdjustmentModificationUnit adjustmentModification
    ) {
        var swagContractAdjustmentModificationUnit =
                new com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit();
        swagContractAdjustmentModificationUnit.setAdjustmentID(adjustmentModification.getAdjustmentId());

        if (adjustmentModification.getModification().isSetCreation()) {
            var swagContractAdjustmentModification = new com.rbkmoney.swag.claim_management.model.ContractAdjustmentModification();
            var swagContractAdjustmentParams = new com.rbkmoney.swag.claim_management.model.ContractAdjustmentParams();
            ContractTemplateRef contractTemplateRef = new ContractTemplateRef();
            contractTemplateRef.setId(adjustmentModification.getModification().getCreation().getTemplate().getId());
            swagContractAdjustmentParams.setTemplate(contractTemplateRef);
            swagContractAdjustmentModification.setCreation(swagContractAdjustmentParams);
            swagContractAdjustmentModificationUnit.setModification(swagContractAdjustmentModification);

            return swagContractAdjustmentModificationUnit;
        } else {
            throw new IllegalArgumentException("Unknown adjustment modification type!");
        }
    }

    private com.rbkmoney.swag.claim_management.model.ContractParams convertContractModificationCreationToSwag(
            ContractParams creation
    ) {
        var swagContractParams = new com.rbkmoney.swag.claim_management.model.ContractParams();
        swagContractParams.setContractModificationType(CONTRACTPARAMS);
        swagContractParams.setContractorID(creation.getContractorId());

        PaymentInstitutionRef paymentInstitutionRef = new PaymentInstitutionRef();
        paymentInstitutionRef.setId(creation.getPaymentInstitution().getId());
        swagContractParams.setPaymentInstitution(paymentInstitutionRef);

        ContractTemplateRef contractTemplateRef = new ContractTemplateRef();
        contractTemplateRef.setId(creation.getTemplate().getId());
        swagContractParams.setTemplate(contractTemplateRef);
        return swagContractParams;
    }

    private com.rbkmoney.swag.claim_management.model.ContractorModificationUnit convertContractorModificationToSwag(
            ContractorModificationUnit contractorModificationUnit
    ) {
        var swagContractorModificationUnit = new com.rbkmoney.swag.claim_management.model.ContractorModificationUnit();
        swagContractorModificationUnit.setId(contractorModificationUnit.getId());
        swagContractorModificationUnit.setModificationType(PARTYMODIFICATION);
        swagContractorModificationUnit.setPartyModificationType(CONTRACTORMODIFICATIONUNIT);

        if (contractorModificationUnit.getModification().isSetCreation()) {
            Contractor creation = contractorModificationUnit.getModification().getCreation();
            swagContractorModificationUnit.setModification(convertCreationContractorModificationToSwag(creation));
        } else if (contractorModificationUnit.getModification().isSetIdentificationLevelModification()) {
            ContractorIdentificationLevel identificationLevelModification =
                    contractorModificationUnit.getModification().getIdentificationLevelModification();
            swagContractorModificationUnit.setModification(convertContractorIdentificationLevelToSwag(identificationLevelModification));
        } else if (contractorModificationUnit.getModification().isSetIdentityDocumentsModification()) {
            ContractorIdentityDocumentsModification identityDocumentsModification =
                    contractorModificationUnit.getModification().getIdentityDocumentsModification();
            swagContractorModificationUnit.setModification(convertIdentityDocumentsModificationToSwag(identityDocumentsModification));
        } else {
            throw new IllegalArgumentException("Unknown contractor modification type!");
        }

        return swagContractorModificationUnit;
    }

    private com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification convertIdentityDocumentsModificationToSwag(
            ContractorIdentityDocumentsModification identityDocumentsModification
    ) {
        var swagContractorIdentityDocumentsModification =
                new com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification();
        swagContractorIdentityDocumentsModification.setContractorModificationType(CONTRACTORIDENTITYDOCUMENTSMODIFICATION);
        List<byte[]> identityDocuments = identityDocumentsModification.getIdentityDocuments().stream()
                .map(ByteBuffer::array)
                .collect(Collectors.toList());
        swagContractorIdentityDocumentsModification.setIdentityDocuments(identityDocuments);

        return swagContractorIdentityDocumentsModification;
    }

    private com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel convertContractorIdentificationLevelToSwag(
            ContractorIdentificationLevel identificationLevelModification
    ) {
        var swagContractorIdentificationLevel =
                new com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel();
        swagContractorIdentificationLevel.setContractorModificationType(CONTRACTORIDENTIFICATIONLEVEL);
        swagContractorIdentificationLevel.setContractorIdentificationLevel(identificationLevelModification.getValue());

        return swagContractorIdentificationLevel;
    }

    private com.rbkmoney.swag.claim_management.model.ContractorModification convertCreationContractorModificationToSwag(
            Contractor creation
    ) {
        if (creation.isSetLegalEntity()) {
            LegalEntity legalEntity = creation.getLegalEntity();

            if (legalEntity.isSetRussianLegalEntity()) {
                RussianLegalEntity russianLegalEntity = legalEntity.getRussianLegalEntity();
                return convertRussianLegalEntityToSwag(russianLegalEntity);
            } else if (legalEntity.isSetInternationalLegalEntity()) {
                InternationalLegalEntity internationalLegalEntity = legalEntity.getInternationalLegalEntity();
                return convertInternationalLegalEntityToSwag(internationalLegalEntity);
            } else {
                throw new IllegalArgumentException("Unknown legal entity type!");
            }
        } else if (creation.isSetPrivateEntity()) {
            PrivateEntity privateEntity = creation.getPrivateEntity();

            if (privateEntity.isSetRussianPrivateEntity()) {
                RussianPrivateEntity russianPrivateEntity = privateEntity.getRussianPrivateEntity();
                return convertPrivateEntityToSwag(russianPrivateEntity);
            } else {
                throw new IllegalArgumentException("Unknown private entity type!");
            }
        } else if (creation.isSetRegisteredUser()) {
            RegisteredUser registeredUser = creation.getRegisteredUser();
            var swagRegisteredUser = new com.rbkmoney.swag.claim_management.model.RegisteredUser();
            swagRegisteredUser.setContractorType(REGISTEREDUSER);
            swagRegisteredUser.setContractorModificationType(CONTRACTOR);
            swagRegisteredUser.setEmail(registeredUser.getEmail());

            return swagRegisteredUser;
        } else {
            throw new IllegalArgumentException("Unknown contractor type!");
        }
    }

    private com.rbkmoney.swag.claim_management.model.PrivateEntity convertPrivateEntityToSwag(
            RussianPrivateEntity russianPrivateEntity
    ) {
        var swagRussianPrivateEntity = new com.rbkmoney.swag.claim_management.model.PrivateEntity();
        swagRussianPrivateEntity.setContractorModificationType(CONTRACTOR);
        swagRussianPrivateEntity.setContractorType(PRIVATEENTITY);
        swagRussianPrivateEntity.setFirstName(russianPrivateEntity.getFirstName());
        swagRussianPrivateEntity.setSecondName(russianPrivateEntity.getSecondName());
        swagRussianPrivateEntity.setMiddleName(russianPrivateEntity.getMiddleName());

        ContactInfo contactInfo = russianPrivateEntity.getContactInfo();
        var swagContactInfo = new com.rbkmoney.swag.claim_management.model.ContactInfo();
        swagContactInfo.setEmail(contactInfo.getEmail());
        swagContactInfo.setPhoneNumber(contactInfo.getPhoneNumber());
        swagRussianPrivateEntity.setContactInfo(swagContactInfo);

        return swagRussianPrivateEntity;
    }

    private com.rbkmoney.swag.claim_management.model.InternationalLegalEntity convertInternationalLegalEntityToSwag(
            InternationalLegalEntity internationalLegalEntity
    ) {
        var swagInternationalLegalEntity =
                new com.rbkmoney.swag.claim_management.model.InternationalLegalEntity();
        swagInternationalLegalEntity.setContractorModificationType(CONTRACTOR);
        swagInternationalLegalEntity.setContractorType(LEGALENTITY);
        swagInternationalLegalEntity.setLegalEntityType(INTERNATIONALLEGALENTITY);
        swagInternationalLegalEntity.setLegalName(internationalLegalEntity.getLegalName());
        swagInternationalLegalEntity.setTradingName(internationalLegalEntity.getTradingName());
        swagInternationalLegalEntity.setActualAddress(internationalLegalEntity.getActualAddress());
        swagInternationalLegalEntity.setRegisteredAddress(internationalLegalEntity.getRegisteredAddress());
        swagInternationalLegalEntity.setRegisteredNumber(internationalLegalEntity.getRegisteredNumber());

        return swagInternationalLegalEntity;
    }

    private com.rbkmoney.swag.claim_management.model.RussianLegalEntity convertRussianLegalEntityToSwag(
            RussianLegalEntity russianLegalEntity
    ) {
        var swagRussianLegalEntity = new com.rbkmoney.swag.claim_management.model.RussianLegalEntity();
        swagRussianLegalEntity.setContractorModificationType(CONTRACTOR);
        swagRussianLegalEntity.setContractorType(LEGALENTITY);
        swagRussianLegalEntity.setLegalEntityType(RUSSIANLEGALENTITY);
        swagRussianLegalEntity.setActualAddress(russianLegalEntity.getActualAddress());
        swagRussianLegalEntity.setEpresentativePosition(russianLegalEntity.getRepresentativePosition());
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

    private com.rbkmoney.swag.claim_management.model.ShopModification convertShopModificationToSwag(
            ShopModificationUnit shopModificationUnit
    ) {
        var swagShopModification = new com.rbkmoney.swag.claim_management.model.ShopModification();

        ShopModification shopModification = shopModificationUnit.getModification();
        if (shopModification.isSetCreation()) {
            swagShopModification.setCreation(convertShopModificationCreationToSwag(shopModification));
        } else if (shopModification.isSetContractModification()) {
            swagShopModification.setContractModification(convertShopContractModification(shopModification));
        } else if (shopModification.isSetCategoryModification()) {
            CategoryRef swagCategoryModification = shopModification.getCategoryModification();
            var swagCategoryRef = new com.rbkmoney.swag.claim_management.model.CategoryRef();
            swagCategoryRef.setId(swagCategoryModification.getId());

            swagShopModification.setCategoryModification(swagCategoryRef);
        } else if (shopModification.isSetDetailsModification()) {
            ShopDetails detailsModification = shopModification.getDetailsModification();
            var swagShopDetails = new com.rbkmoney.swag.claim_management.model.ShopDetails();
            swagShopDetails.setName(detailsModification.getName());
            swagShopDetails.setDescription(detailsModification.getDescription());

            swagShopModification.setDetailsModification(swagShopDetails);
        } else if (shopModification.isSetLocationModification()) {
            ShopLocation shopLocation = shopModification.getLocationModification();
            var swagShopLocation = new com.rbkmoney.swag.claim_management.model.ShopLocation();
            swagShopLocation.setUrl(shopLocation.getUrl());

            swagShopModification.setLocationModification(swagShopLocation);
        } else if (shopModification.isSetShopAccountCreation()) {
            ShopAccountParams shopAccountCreation = shopModification.getShopAccountCreation();
            var swagShopAccountParams = new com.rbkmoney.swag.claim_management.model.ShopAccountParams();
            var swagCurrencyRef = new com.rbkmoney.swag.claim_management.model.CurrencyRef();
            swagCurrencyRef.setSymbolicCode(shopAccountCreation.getCurrency().getSymbolicCode());

            swagShopAccountParams.setCurrency(swagCurrencyRef);
            swagShopModification.setShopAccountCreation(swagShopAccountParams);
        } else if (shopModification.isSetPayoutScheduleModification()) {
            ScheduleModification payoutScheduleModification = shopModification.getPayoutScheduleModification();
            var swagScheduleModification = new com.rbkmoney.swag.claim_management.model.ScheduleModification();
            var swagBusinessScheduleRef = new com.rbkmoney.swag.claim_management.model.BusinessScheduleRef();
            swagBusinessScheduleRef.setId(payoutScheduleModification.getSchedule().getId());
            swagScheduleModification.setSchedule(swagBusinessScheduleRef);

            swagShopModification.setPayoutScheduleModification(swagScheduleModification);
        } else if (shopModification.isSetPayoutToolModification()) {
            swagShopModification.setPayoutToolModification(shopModification.getPayoutToolModification());
        } else {
            throw new IllegalArgumentException("Unknown party modification type!");
        }

        return swagShopModification;
    }

    private com.rbkmoney.swag.claim_management.model.ShopContractModification convertShopContractModification(
            ShopModification shopModification
    ) {
        ShopContractModification shopContractModification = shopModification.getContractModification();
        var swagShopContractModification = new com.rbkmoney.swag.claim_management.model.ShopContractModification();
        swagShopContractModification.setContractID(shopContractModification.getContractId());
        swagShopContractModification.setPayoutToolID(shopContractModification.getPayoutToolId());
        return swagShopContractModification;
    }

    private com.rbkmoney.swag.claim_management.model.ShopParams convertShopModificationCreationToSwag(
            ShopModification shopModification
    ) {
        ShopParams shopParams = shopModification.getCreation();
        var swagShopParams = new com.rbkmoney.swag.claim_management.model.ShopParams();
        swagShopParams.setContractID(shopParams.getContractId());
        swagShopParams.setPayoutToolID(shopParams.getPayoutToolId());

        var swagCategoryRef = new com.rbkmoney.swag.claim_management.model.CategoryRef();
        swagCategoryRef.setId(shopParams.getCategory().getId());
        swagShopParams.setCategory(swagCategoryRef);

        var swagShopParamsDetails = new com.rbkmoney.swag.claim_management.model.ShopDetails();
        swagShopParamsDetails.setName(shopParams.getDetails().getName());
        swagShopParamsDetails.setDescription(shopParams.getDetails().getDescription());
        swagShopParams.setDetails(swagShopParamsDetails);

        var swagShopLocation = new com.rbkmoney.swag.claim_management.model.ShopLocation();
        swagShopLocation.setUrl(shopParams.getLocation().getUrl());
        swagShopParams.setLocation(swagShopLocation);

        return swagShopParams;
    }

    private com.rbkmoney.swag.claim_management.model.DocumentModificationUnit convertDocumentModificationUnitToSwag(
            DocumentModificationUnit documentModification
    ) {
        var swagDocumentModificationUnit = new com.rbkmoney.swag.claim_management.model.DocumentModificationUnit();
        swagDocumentModificationUnit.setId(documentModification.getId());
        swagDocumentModificationUnit.setModificationType(CLAIMMODIFICATION);
        var swagDocumentModification = new com.rbkmoney.swag.claim_management.model.DocumentModification();

        if (documentModification.getModification().isSetCreation()) {
            swagDocumentModification.setDocumentModificationType(DOCUMENTCREATED);
        } else {
            throw new IllegalArgumentException("Unknown document modification type!");
        }

        swagDocumentModificationUnit.setModification(swagDocumentModification);
        return swagDocumentModificationUnit;
    }

    private com.rbkmoney.swag.claim_management.model.CommentModificationUnit convertCommentModificationUnitToSwag(
            CommentModificationUnit commentModification
    ) {
        var swagCommentModificationUnit = new com.rbkmoney.swag.claim_management.model.CommentModificationUnit();
        swagCommentModificationUnit.setId(commentModification.getId());
        swagCommentModificationUnit.setModificationType(CLAIMMODIFICATION);
        var swagCommentModification = new com.rbkmoney.swag.claim_management.model.CommentModification();

        if (commentModification.getModification().isSetCreation()) {
            swagCommentModification.setCommentModificationType(COMMENTCREATED);
        } else {
            throw new IllegalArgumentException("Unknown comment modification type!");
        }

        swagCommentModificationUnit.setModification(swagCommentModification);
        return swagCommentModificationUnit;
    }

    private com.rbkmoney.swag.claim_management.model.StatusModificationUnit convertStatusModificationUnitToSwag(
            StatusModificationUnit statusModification
    ) {
        var swagStatusModificationUnit = new com.rbkmoney.swag.claim_management.model.StatusModificationUnit();
        swagStatusModificationUnit.setModificationType(CLAIMMODIFICATION);

        if (statusModification.getModification().isSetChange()) {
            swagStatusModificationUnit.setStatus(
                    com.rbkmoney.swag.claim_management.model.StatusModificationUnit.StatusEnum.fromValue(
                            statusModification.getStatus().toString()
                    )
            );
        } else {
            throw new IllegalArgumentException("Unknown status modification type!");
        }
        return swagStatusModificationUnit;
    }

    private com.rbkmoney.swag.claim_management.model.FileModificationUnit convertFileModificationUnitToSwag(
            FileModificationUnit fileModificationUnit
    ) {
        var swagFileModificationUnit = new com.rbkmoney.swag.claim_management.model.FileModificationUnit();
        swagFileModificationUnit.setId(fileModificationUnit.getId());
        swagFileModificationUnit.setModificationType(CLAIMMODIFICATION);

        var swagFileModification = new com.rbkmoney.swag.claim_management.model.FileModification();

        if (fileModificationUnit.getModification().isSetCreation()) {
            swagFileModification.setFileModificationType(FILECREATED);
        } else {
            throw new IllegalArgumentException("Unknown file modification type!");
        }

        swagFileModificationUnit.setModification(swagFileModification);
        return swagFileModificationUnit;
    }


}
