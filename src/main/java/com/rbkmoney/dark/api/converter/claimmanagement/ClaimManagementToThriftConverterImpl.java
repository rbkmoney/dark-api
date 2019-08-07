package com.rbkmoney.dark.api.converter.claimmanagement;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.claim_management.ClaimAccepted;
import com.rbkmoney.damsel.claim_management.ClaimDenied;
import com.rbkmoney.damsel.claim_management.ClaimModification;
import com.rbkmoney.damsel.claim_management.ClaimPending;
import com.rbkmoney.damsel.claim_management.ClaimPendingAcceptance;
import com.rbkmoney.damsel.claim_management.ClaimReview;
import com.rbkmoney.damsel.claim_management.ClaimRevoked;
import com.rbkmoney.damsel.claim_management.CommentCreated;
import com.rbkmoney.damsel.claim_management.CommentModification;
import com.rbkmoney.damsel.claim_management.CommentModificationUnit;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentModification;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentModificationUnit;
import com.rbkmoney.damsel.claim_management.ContractAdjustmentParams;
import com.rbkmoney.damsel.claim_management.ContractModification;
import com.rbkmoney.damsel.claim_management.ContractModificationUnit;
import com.rbkmoney.damsel.claim_management.ContractParams;
import com.rbkmoney.damsel.claim_management.ContractTermination;
import com.rbkmoney.damsel.claim_management.ContractorIdentityDocumentsModification;
import com.rbkmoney.damsel.claim_management.ContractorModification;
import com.rbkmoney.damsel.claim_management.ContractorModificationUnit;
import com.rbkmoney.damsel.claim_management.DocumentCreated;
import com.rbkmoney.damsel.claim_management.DocumentModification;
import com.rbkmoney.damsel.claim_management.DocumentModificationUnit;
import com.rbkmoney.damsel.claim_management.FileCreated;
import com.rbkmoney.damsel.claim_management.FileModification;
import com.rbkmoney.damsel.claim_management.FileModificationUnit;
import com.rbkmoney.damsel.claim_management.Modification;
import com.rbkmoney.damsel.claim_management.PartyModification;
import com.rbkmoney.damsel.claim_management.PayoutToolModification;
import com.rbkmoney.damsel.claim_management.PayoutToolModificationUnit;
import com.rbkmoney.damsel.claim_management.PayoutToolParams;
import com.rbkmoney.damsel.claim_management.ScheduleModification;
import com.rbkmoney.damsel.claim_management.ShopAccountParams;
import com.rbkmoney.damsel.claim_management.ShopContractModification;
import com.rbkmoney.damsel.claim_management.ShopModification;
import com.rbkmoney.damsel.claim_management.ShopModificationUnit;
import com.rbkmoney.damsel.claim_management.ShopParams;
import com.rbkmoney.damsel.claim_management.StatusChanged;
import com.rbkmoney.damsel.claim_management.StatusModification;
import com.rbkmoney.damsel.claim_management.StatusModificationUnit;
import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.damsel.domain.ArticlesOfAssociation;
import com.rbkmoney.damsel.domain.BusinessScheduleRef;
import com.rbkmoney.damsel.domain.CategoryRef;
import com.rbkmoney.damsel.domain.ContactInfo;
import com.rbkmoney.damsel.domain.ContractTemplateRef;
import com.rbkmoney.damsel.domain.Contractor;
import com.rbkmoney.damsel.domain.ContractorIdentificationLevel;
import com.rbkmoney.damsel.domain.CurrencyRef;
import com.rbkmoney.damsel.domain.InternationalBankAccount;
import com.rbkmoney.damsel.domain.InternationalBankDetails;
import com.rbkmoney.damsel.domain.InternationalLegalEntity;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.damsel.domain.LegalEntity;
import com.rbkmoney.damsel.domain.PaymentInstitutionRef;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import com.rbkmoney.damsel.domain.PrivateEntity;
import com.rbkmoney.damsel.domain.RegisteredUser;
import com.rbkmoney.damsel.domain.ReportPreferences;
import com.rbkmoney.damsel.domain.Representative;
import com.rbkmoney.damsel.domain.RepresentativeDocument;
import com.rbkmoney.damsel.domain.RussianBankAccount;
import com.rbkmoney.damsel.domain.RussianLegalEntity;
import com.rbkmoney.damsel.domain.ServiceAcceptanceActPreferences;
import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.damsel.domain.ShopLocation;
import com.rbkmoney.damsel.domain.WalletInfo;
import com.rbkmoney.swag.claim_management.model.*;
import com.rbkmoney.swag.claim_management.model.ModificationUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.rbkmoney.dark.api.converter.claimmanagement.data.ThriftClaimStatus.*;
import static com.rbkmoney.swag.claim_management.model.CommentModification.CommentModificationTypeEnum.COMMENTCREATED;
import static com.rbkmoney.swag.claim_management.model.Contractor.ContractorTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.DocumentModification.DocumentModificationTypeEnum.DOCUMENTCREATED;
import static com.rbkmoney.swag.claim_management.model.FileModification.FileModificationTypeEnum.FILECREATED;
import static com.rbkmoney.swag.claim_management.model.LegalEntity.LegalEntityTypeEnum.INTERNATIONALLEGALENTITY;
import static com.rbkmoney.swag.claim_management.model.LegalEntity.LegalEntityTypeEnum.RUSSIANLEGALENTITY;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.PARTYMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.RepresentativeDocument.DocumentTypeEnum.*;

@Slf4j
@Component
public class ClaimManagementToThriftConverterImpl implements ClaimManagementToThriftConverter {

    @Override
    public List<Modification> convertChangesetToThrift(ClaimChangeset changeset) {
        List<Modification> modificationList = new ArrayList<>();
        for (ModificationUnit unit : changeset) {
            modificationList.add(convertModificationToThrift(unit.getModification()));
        }
        return modificationList;
    }

    @Override
    public List<Modification> convertModificationUnitToThrift(
            List<com.rbkmoney.swag.claim_management.model.Modification> unitModifications
    ) {
        List<Modification> modificationList = new ArrayList<>();
        for (com.rbkmoney.swag.claim_management.model.Modification unitModification : unitModifications) {
            modificationList.add(convertModificationToThrift(unitModification));
        }
        return modificationList;
    }

    private Modification convertModificationToThrift(com.rbkmoney.swag.claim_management.model.Modification swagModification) {
        switch (swagModification.getModificationType()) {
            case CLAIMMODIFICATION:
                var claimModification = (com.rbkmoney.swag.claim_management.model.ClaimModification) swagModification;
                return convertClaimModificationToThrift(claimModification);
            case PARTYMODIFICATION:
                var swagPartyModification = (com.rbkmoney.swag.claim_management.model.PartyModification) swagModification;
                return convertPartyModificationToThrift(swagPartyModification);
            default:
                throw new IllegalArgumentException("Unknown claim management modification type: " + swagModification);
        }
    }

    private Modification convertPartyModificationToThrift(
            com.rbkmoney.swag.claim_management.model.PartyModification swagPartyModification
    ) {
        switch (swagPartyModification.getPartyModificationType()) {
            case CONTRACTMODIFICATIONUNIT:
                var swagContractModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ContractModificationUnit) swagPartyModification;
                return convertContractModificationUnitToThrift(swagContractModificationUnit);
            case CONTRACTORMODIFICATIONUNIT:
                var swagContractorModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ContractorModificationUnit) swagPartyModification;
                return convertContractorModificationUnitToThrift(swagContractorModificationUnit);
            case SHOPMODIFICATIONUNIT:
                var swagShopModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ShopModificationUnit) swagPartyModification;
                return convertShopModificationUnitToThrift(swagShopModificationUnit);
            default:
                throw new IllegalArgumentException("Unknown claim management party modification type: " + swagPartyModification);
        }
    }

    private Modification convertContractModificationUnitToThrift(
            com.rbkmoney.swag.claim_management.model.ContractModificationUnit swagContractModificationUnit
    ) {
        Modification modification = new Modification();
        PartyModification partyModification = new PartyModification();
        ContractModificationUnit contractModificationUnit = new ContractModificationUnit();
        contractModificationUnit.setId(swagContractModificationUnit.getId());
        ContractModification contractModification = new ContractModification();
        var swagContractModification = swagContractModificationUnit.getModification();

        switch (swagContractModification.getContractModificationType()) {
            case CONTRACTPARAMS:
                var swagContractParams = (com.rbkmoney.swag.claim_management.model.ContractParams) swagContractModification;
                contractModification.setCreation(convertContractParamstoThrift(swagContractParams));
                break;
            case CONTRACTORID:
                var swagContractorId = (com.rbkmoney.swag.claim_management.model.ContractorID) swagContractModification;
                contractModification.setContractorModification(swagContractorId.getContractorID());
                break;
            case LEGALAGREEMENT:
                var swagLegalAgreement = (com.rbkmoney.swag.claim_management.model.LegalAgreement) swagContractModification;
                contractModification.setLegalAgreementBinding(new LegalAgreement()
                        .setLegalAgreementId(swagLegalAgreement.getLegalAgreementID())
                        .setSignedAt(swagLegalAgreement.getSignedAt())
                        .setValidUntil(swagLegalAgreement.getValidUntil()));
                break;
            case REPORTPREFERENCES:
                var swagReportPreferences = (com.rbkmoney.swag.claim_management.model.ReportPreferences) swagContractModification;
                contractModification.setReportPreferencesModification(convertReportPreferencesToThrift(swagReportPreferences));
                break;
            case CONTRACTTERMINATION:
                var swagContractTerm = (com.rbkmoney.swag.claim_management.model.ContractTermination) swagContractModification;

                contractModification.setTermination(
                        new ContractTermination()
                                .setReason(swagContractTerm.getReason())
                );
                break;
            case PAYOUTTOOLMODIFICATIONUNIT:
                var swagPayoutToolModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit) swagContractModification;
                PayoutToolModificationUnit payoutToolModificationUnit =
                        convertPayoutToolModificationUnitToThrift(swagPayoutToolModificationUnit);
                contractModification.setPayoutToolModification(payoutToolModificationUnit);
                break;
            case CONTRACTADJUSTMENTMODIFICATIONUNIT:
                var swagContractAdjustmentModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit) swagContractModification;
                ContractAdjustmentModificationUnit adjustmentModificationUnit =
                        convertContractAdjustmentModificationUnitToThrift(swagContractAdjustmentModificationUnit);
                contractModification.setAdjustmentModification(adjustmentModificationUnit);
                break;
            default:

        }

        contractModificationUnit.setModification(contractModification);
        partyModification.setContractModification(contractModificationUnit);
        modification.setPartyModification(partyModification);
        return modification;
    }

    private ContractAdjustmentModificationUnit convertContractAdjustmentModificationUnitToThrift(
            com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit swagContractAdjustmentModificationUnit
    ) {
        ContractAdjustmentModificationUnit adjustmentModificationUnit = new ContractAdjustmentModificationUnit();
        adjustmentModificationUnit.setAdjustmentId(swagContractAdjustmentModificationUnit.getAdjustmentID());
        ContractAdjustmentModification adjustmentModification = new ContractAdjustmentModification();
        ContractAdjustmentParams contractAdjustmentParams = new ContractAdjustmentParams();

        contractAdjustmentParams.setTemplate(
                new ContractTemplateRef()
                        .setId(swagContractAdjustmentModificationUnit.getModification().getCreation().getTemplate().getId())
        );

        adjustmentModification.setCreation(contractAdjustmentParams);
        return adjustmentModificationUnit;
    }

    private PayoutToolModificationUnit convertPayoutToolModificationUnitToThrift(
            com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit swagPayoutToolModificationUnit
    ) {
        PayoutToolModificationUnit payoutToolModificationUnit = new PayoutToolModificationUnit();
        payoutToolModificationUnit.setPayoutToolId(swagPayoutToolModificationUnit.getPayoutToolID());
        PayoutToolModification payoutToolModification = new PayoutToolModification();
        PayoutToolParams creation = new PayoutToolParams();
        var swagCreation = swagPayoutToolModificationUnit.getModification().getCreation();

        creation.setCurrency(
                new CurrencyRef()
                        .setSymbolicCode(swagCreation.getCurrency().getSymbolicCode())
        );

        PayoutToolInfo infoModification = new PayoutToolInfo();

        switch (swagCreation.getToolInfo().getPayoutToolType()) {
            case RUSSIANBANKACCOUNT:
                var swagRussianBankAccount =
                        (com.rbkmoney.swag.claim_management.model.RussianBankAccount) swagCreation.getToolInfo();

                infoModification.setRussianBankAccount(
                        new RussianBankAccount()
                                .setBankName(swagRussianBankAccount.getBankName())
                                .setBankBik(swagRussianBankAccount.getBankBik())
                                .setBankPostAccount(swagRussianBankAccount.getBankPostAccount())
                                .setAccount(swagRussianBankAccount.getAccount())
                );
                break;
            case INTERNATIONALBANKACCOUNT:
                var swagInternationalBankAccount =
                        (com.rbkmoney.swag.claim_management.model.InternationalBankAccount) swagCreation.getToolInfo();
                InternationalBankAccount internationalBankAccount =
                        convertInternationalBankAccountToThrift(swagInternationalBankAccount);
                infoModification.setInternationalBankAccount(internationalBankAccount);
                break;
            case WALLETINFO:
                var swagWalletInfo =
                        (com.rbkmoney.swag.claim_management.model.WalletInfo) swagCreation.getToolInfo();
                infoModification.setWalletInfo(
                        new WalletInfo()
                                .setWalletId(swagWalletInfo.getWalletID())
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

    private InternationalBankAccount convertInternationalBankAccountToThrift(
            com.rbkmoney.swag.claim_management.model.InternationalBankAccount swagInternationalBankAccount
            ) {
        InternationalBankAccount internationalBankAccount = new InternationalBankAccount()
                .setAccountHolder(swagInternationalBankAccount.getAccountHolder())
                .setIban(swagInternationalBankAccount.getIban())
                .setNumber(swagInternationalBankAccount.getNumber());

        var swagBankDetails = swagInternationalBankAccount.getBank();
        internationalBankAccount.setBank(new InternationalBankDetails()
                .setAbaRtn(swagBankDetails.getAbaRtn())
                .setAddress(swagBankDetails.getAddress())
                .setBic(swagBankDetails.getBic())
                .setName(swagBankDetails.getName())
                .setCountry(Residence.valueOf(swagBankDetails.getCountry())));

        var swagCorrespondentAccount = swagInternationalBankAccount.getCorrespondentAccount();
        InternationalBankAccount correspondentAccount = new InternationalBankAccount()
                .setAccountHolder(swagCorrespondentAccount.getAccountHolder())
                .setIban(swagCorrespondentAccount.getIban())
                .setNumber(swagCorrespondentAccount.getNumber());

        var swagCorrespondentAccountBank = swagCorrespondentAccount.getBank();
        correspondentAccount.setBank(new InternationalBankDetails()
                .setAbaRtn(swagCorrespondentAccountBank.getAbaRtn())
                .setAddress(swagCorrespondentAccountBank.getAddress())
                .setBic(swagCorrespondentAccountBank.getBic())
                .setName(swagCorrespondentAccountBank.getName())
                .setCountry(Residence.valueOf(swagCorrespondentAccountBank.getCountry())));

        internationalBankAccount.setCorrespondentAccount(correspondentAccount);
        return internationalBankAccount;
    }

    private ReportPreferences convertReportPreferencesToThrift(
            com.rbkmoney.swag.claim_management.model.ReportPreferences swagReportPreferences
    ) {
        ReportPreferences reportPreferencesModification = new ReportPreferences();
        ServiceAcceptanceActPreferences actPreferences = new ServiceAcceptanceActPreferences();

        var swagSigner = swagReportPreferences.getServiceAcceptanceActPreferences().getSigner();
        Representative signer = new Representative()
                .setFullName(swagSigner.getFullName())
                .setPosition(swagSigner.getPosition());
        RepresentativeDocument signerDocument = new RepresentativeDocument();

        switch (swagSigner.getDocument().getDocumentType()) {
            case ARTICLESOFASSOCIATION:
                signerDocument.setArticlesOfAssociation(new ArticlesOfAssociation());
                break;
            case POWEROFATTORNEY:
                var swagPowerOfAttorney = (PowerOfAttorney) swagSigner.getDocument();
                LegalAgreement powerOfAttorney = new LegalAgreement()
                        .setLegalAgreementId(swagPowerOfAttorney.getLegalAgreementID())
                        .setSignedAt(swagPowerOfAttorney.getSignedAt())
                        .setValidUntil(swagPowerOfAttorney.getValidUntil());
                signerDocument.setPowerOfAttorney(powerOfAttorney);
                break;
            default:
                throw new IllegalArgumentException("Unknown report preferences document type: " + swagReportPreferences);
        }

        actPreferences.setSchedule(new BusinessScheduleRef().setId(
                swagReportPreferences.getServiceAcceptanceActPreferences().getSchedule().getId())
        );

        signer.setDocument(signerDocument);
        actPreferences.setSigner(signer);
        reportPreferencesModification.setServiceAcceptanceActPreferences(actPreferences);
        return reportPreferencesModification;
    }

    private ContractParams convertContractParamstoThrift(
            com.rbkmoney.swag.claim_management.model.ContractParams swagContractParams
    ) {
        ContractParams params = new ContractParams();
        params.setContractorId(swagContractParams.getContractorID());

        if (swagContractParams.getTemplate() != null) {
            ContractTemplateRef contractTemplateRef = new ContractTemplateRef();
            contractTemplateRef.setId(swagContractParams.getTemplate().getId());
            params.setTemplate(contractTemplateRef);
        }

        if (swagContractParams.getPaymentInstitution() != null) {
            PaymentInstitutionRef paymentInstitution = new PaymentInstitutionRef();
            paymentInstitution.setId(swagContractParams.getPaymentInstitution().getId());
            params.setPaymentInstitution(paymentInstitution);
        }
        return params;
    }

    private Modification convertContractorModificationUnitToThrift(
            com.rbkmoney.swag.claim_management.model.ContractorModificationUnit swagContractorModificationUnit
    ) {
        Modification modification = new Modification();
        PartyModification partyModification = modification.getPartyModification();
        ContractorModificationUnit contractorModificationUnit = new ContractorModificationUnit();

        contractorModificationUnit.setId(swagContractorModificationUnit.getId());
        ContractorModification contractorModification = new ContractorModification();

        var swagModification = swagContractorModificationUnit.getModification();
        switch (swagModification.getContractorModificationType()) {
            case CONTRACTOR:
                var swagContractor = (com.rbkmoney.swag.claim_management.model.Contractor) swagModification;
                contractorModification.setCreation(convertContractorToThrift(swagContractor));
                break;
            case CONTRACTORIDENTIFICATIONLEVEL:
                var swagContractorIdentificationLevel =
                        (com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel) swagModification;
                contractorModification.setIdentificationLevelModification(
                        ContractorIdentificationLevel.findByValue(
                                swagContractorIdentificationLevel.getContractorIdentificationLevel()
                        )
                );
                break;
            case CONTRACTORIDENTITYDOCUMENTSMODIFICATION:
                var swagContractorIdentityDocumentsModification =
                        (com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification) swagModification;
                List<ByteBuffer> byteBufferList =
                        swagContractorIdentityDocumentsModification.getIdentityDocuments().stream()
                                .map(ByteBuffer::wrap)
                                .collect(Collectors.toList());
                contractorModification.setIdentityDocumentsModification(
                        new ContractorIdentityDocumentsModification().setIdentityDocuments(byteBufferList)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown contractor modification type: " +
                        swagModification.getContractorModificationType());
        }

        partyModification.setContractorModification(contractorModificationUnit);
        modification.setPartyModification(partyModification);
        return modification;
    }

    private Contractor convertContractorToThrift(com.rbkmoney.swag.claim_management.model.Contractor swagContractor) {
        Contractor contractor = new Contractor();
        switch (swagContractor.getContractorType()) {
            case LEGALENTITY:
                var swagLegalEntity = (com.rbkmoney.swag.claim_management.model.LegalEntity) swagContractor;
                contractor.setLegalEntity(convertLegalEntityToThrift(swagLegalEntity));
                break;
            case PRIVATEENTITY:
                var swagPrivateEntity = (com.rbkmoney.swag.claim_management.model.PrivateEntity) swagContractor;
                contractor.setPrivateEntity(convertPrivateEntityToThrift(swagPrivateEntity));
                break;
            case REGISTEREDUSER:
                var swagRegisteredUser = (com.rbkmoney.swag.claim_management.model.RegisteredUser) swagContractor;
                contractor.setRegisteredUser(
                        new RegisteredUser()
                                .setEmail(swagRegisteredUser.getEmail())
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown contractor type: " + swagContractor.getContractorType());
        }
        return contractor;
    }

    private LegalEntity convertLegalEntityToThrift(com.rbkmoney.swag.claim_management.model.LegalEntity swagLegalEntity) {
        LegalEntity legalEntity = new LegalEntity();

        switch (swagLegalEntity.getLegalEntityType()) {
            case RUSSIANLEGALENTITY:
                var swagRussianLegalEntity =
                        (com.rbkmoney.swag.claim_management.model.RussianLegalEntity) swagLegalEntity;
                legalEntity.setRussianLegalEntity(convertRussianLegalEntityToThrift(swagRussianLegalEntity));
                break;
            case INTERNATIONALLEGALENTITY:
                var swagInternationalLegalEntity =
                        (com.rbkmoney.swag.claim_management.model.InternationalLegalEntity) swagLegalEntity;
                legalEntity.setInternationalLegalEntity(
                        convertInternationalLegalEntityToThrift(swagInternationalLegalEntity)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown legal entity type: " + swagLegalEntity.getLegalEntityType());
        }
        return legalEntity;
    }

    private PrivateEntity convertPrivateEntityToThrift(
            com.rbkmoney.swag.claim_management.model.PrivateEntity swagPrivateEntity
    ) {
        PrivateEntity privateEntity = new PrivateEntity();
        ContactInfo contactInfo = new ContactInfo()
                .setEmail(swagPrivateEntity.getContactInfo().getEmail())
                .setPhoneNumber(swagPrivateEntity.getContactInfo().getPhoneNumber());
        RussianPrivateEntity russianPrivateEntity = new RussianPrivateEntity()
                .setFirstName(swagPrivateEntity.getFirstName())
                .setMiddleName(swagPrivateEntity.getMiddleName())
                .setSecondName(swagPrivateEntity.getSecondName())
                .setContactInfo(contactInfo);

        privateEntity.setRussianPrivateEntity(russianPrivateEntity);
        return privateEntity;
    }

    private InternationalLegalEntity convertInternationalLegalEntityToThrift(
            com.rbkmoney.swag.claim_management.model.InternationalLegalEntity swagInternationalLegalEntity
    ) {
        return  new InternationalLegalEntity()
                .setLegalName(swagInternationalLegalEntity.getLegalName())
                .setActualAddress(swagInternationalLegalEntity.getActualAddress())
                .setRegisteredAddress(swagInternationalLegalEntity.getRegisteredAddress())
                .setRegisteredNumber(swagInternationalLegalEntity.getRegisteredNumber())
                .setTradingName(swagInternationalLegalEntity.getTradingName());
    }

    private RussianLegalEntity convertRussianLegalEntityToThrift(
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
                .setRepresentativePosition(swagRussianLegalEntity.getEpresentativePosition())
                .setRussianBankAccount(russianBankAccount);
        return russianLegalEntity;
    }

    private Modification convertShopModificationUnitToThrift(
            com.rbkmoney.swag.claim_management.model.ShopModificationUnit swagShopModificationUnit
    ) {
        Modification modification = new Modification();
        PartyModification partyModification = modification.getPartyModification();
        ShopModificationUnit shopModificationUnit = partyModification.getShopModification();
        shopModificationUnit.setId(swagShopModificationUnit.getId());
        ShopModification shopModification = new ShopModification();

        ShopLocation shopLocation = new ShopLocation();
        var swagModification = swagShopModificationUnit.getModification();
        shopLocation.setUrl(swagModification.getCreation().getLocation().getUrl());

        var swagCreation = swagModification.getCreation();
        ShopParams creation = new ShopParams()
                .setContractId(swagCreation.getContractID())
                .setPayoutToolId(swagCreation.getPayoutToolID())
                .setCategory(new CategoryRef()
                        .setId(swagCreation.getCategory().getId()))
                .setDetails(new ShopDetails()
                        .setDescription(swagCreation.getDetails().getDescription())
                        .setName(swagCreation.getDetails().getName()))
                .setLocation(shopLocation);
        shopModification.setCreation(creation);

        shopModification.setCategoryModification(
                new CategoryRef()
                        .setId(swagModification.getCategoryModification().getId())
        );

        shopModification.setContractModification(
                new ShopContractModification()
                        .setContractId(swagModification.getContractModification().getContractID())
                        .setPayoutToolId(swagModification.getContractModification().getPayoutToolID())
        );

        shopModification.setDetailsModification(
                new ShopDetails()
                        .setDescription(swagModification.getDetailsModification().getDescription())
                        .setName(swagModification.getDetailsModification().getName())
        );

        ShopLocation locationModification = new ShopLocation();
        //TODO: отличие между thrift & swag
        locationModification.setUrl(swagModification.getLocationModification().getUrl());
        shopModification.setLocationModification(locationModification);

        shopModification.setPayoutScheduleModification(
                new ScheduleModification()
                        .setSchedule(
                                new BusinessScheduleRef()
                                        .setId(swagModification.getPayoutScheduleModification().getSchedule().getId())
                        )
        );

        shopModification.setShopAccountCreation(
                new ShopAccountParams()
                        .setCurrency(
                                new CurrencyRef()
                                        .setSymbolicCode(
                                                swagModification.getShopAccountCreation().getCurrency().getSymbolicCode()
                                        )
                        )
        );

        shopModification.setPayoutToolModification(swagModification.getPayoutToolModification());

        shopModificationUnit.setModification(shopModification);
        partyModification.setShopModification(shopModificationUnit);
        modification.setPartyModification(partyModification);
        return modification;
    }

    private Modification convertClaimModificationToThrift(
            com.rbkmoney.swag.claim_management.model.ClaimModification swagClaimModification
    ) {
        switch (swagClaimModification.getClaimModificationType()) {
            case DOCUMENTMODIFICATIONUNIT:
                var swagDocModification = (com.rbkmoney.swag.claim_management.model.DocumentModificationUnit) swagClaimModification;
                return convertDocModificationToThrift(swagDocModification);
            case COMMENTMODIFICATIONUNIT:
                var commentModificationUnit = (com.rbkmoney.swag.claim_management.model.CommentModificationUnit) swagClaimModification;
                return convertCommentModificationToThrift(commentModificationUnit);
            case STATUSMODIFICATIONUNIT:
                var swagStatusModificationUnit = (com.rbkmoney.swag.claim_management.model.StatusModificationUnit) swagClaimModification;
                return convertStatusModificationToThrift(swagStatusModificationUnit);
            case FILEMODIFICATIONUNIT:
                var fileModificationUnit = (com.rbkmoney.swag.claim_management.model.FileModificationUnit) swagClaimModification;
                return convertFileModificationToThrift(fileModificationUnit);
            default:
                throw new IllegalArgumentException("Unknown claim modification type: " +
                        swagClaimModification.getClaimModificationType());
        }
    }

    private Modification convertFileModificationToThrift(
            com.rbkmoney.swag.claim_management.model.FileModificationUnit swagFileModificationUnit
    ) {
        Modification modification = new Modification();
        ClaimModification claimModification = new ClaimModification();
        FileModificationUnit fileModificationUnit = new FileModificationUnit();
        fileModificationUnit.setId(swagFileModificationUnit.getId());

        switch (swagFileModificationUnit.getModification().getFileModificationType()) {
            case FILECREATED:
                FileModification fileModification = new FileModification();
                fileModification.setCreation(new FileCreated());
                fileModificationUnit.setModification(fileModification);
                break;
            default:
                throw new IllegalArgumentException("Unknown file modification type: " +
                        swagFileModificationUnit.getModification().getFileModificationType());
        }

        modification.setClaimModfication(claimModification);
        return modification;
    }

    private Modification convertStatusModificationToThrift(
            com.rbkmoney.swag.claim_management.model.StatusModificationUnit swagStatusModificationUnit
    ) {
        Modification modification = new Modification();
        ClaimModification claimModification = new ClaimModification();
        StatusModificationUnit statusModificationUnit = claimModification.getStatusModification();
        statusModificationUnit.setStatus(convertSwagStatusModificationToThrift(swagStatusModificationUnit));

        if (statusModificationUnit.getModification().isSetChange()) {
            StatusModification statusModification = new StatusModification();
            statusModification.setChange(new StatusChanged());
            statusModificationUnit.setModification(statusModification);
        } else {
            throw new IllegalArgumentException("Chande field for StatusModificationUnit must be set!");
        }

        modification.setClaimModfication(claimModification);
        return modification;
    }

    private ClaimStatus convertSwagStatusModificationToThrift(
            com.rbkmoney.swag.claim_management.model.StatusModificationUnit swagStatusModificationUnit
    ) {
        ClaimStatus status = new ClaimStatus();
        switch (swagStatusModificationUnit.getStatus()) {
            case PENDING:
                status.setPending(new ClaimPending());
                return status;
            case REVIEW:
                status.setReview(new ClaimReview());
                return status;
            case PENDINGACCEPTANCE:
                status.setPendingAcceptance(new ClaimPendingAcceptance());
                return status;
            case ACCEPTED:
                status.setAccepted(new ClaimAccepted());
                return status;
            case DENIED:
                status.setDenied(new ClaimDenied());
                return status;
            case REVOKED:
                status.setRevoked(new ClaimRevoked());
                return status;
            default:
                throw new IllegalArgumentException("Status " + swagStatusModificationUnit.getStatus() +
                        "in swagStatusModificationUnit not found!");
        }
    }

    private Modification convertCommentModificationToThrift(
            com.rbkmoney.swag.claim_management.model.CommentModificationUnit swagCommentModificationUnit
    ) {
        Modification modification = new Modification();
        ClaimModification claimModification = new ClaimModification();
        CommentModificationUnit commentModificationUnit = new CommentModificationUnit();
        commentModificationUnit.setId(swagCommentModificationUnit.getId());

        switch (swagCommentModificationUnit.getModification().getCommentModificationType()) {
            case COMMENTCREATED:
                CommentModification commentModification = new CommentModification();
                commentModification.setCreation(new CommentCreated());
                commentModificationUnit.setModification(commentModification);
                break;
            default:
                throw new IllegalArgumentException("Type " + swagCommentModificationUnit.getModification().getCommentModificationType() +
                        "in swagCommentModificationUnit not found!");
        }

        claimModification.setCommentModification(commentModificationUnit);
        modification.setClaimModfication(claimModification);
        return modification;
    }

    private Modification convertDocModificationToThrift(
            com.rbkmoney.swag.claim_management.model.DocumentModificationUnit swagDocModificationUnit
    ) {
        Modification modification = new Modification();
        ClaimModification claimModification = new ClaimModification();
        DocumentModificationUnit docModificationUnit = new DocumentModificationUnit();
        docModificationUnit.setId(swagDocModificationUnit.getId());

        switch (swagDocModificationUnit.getModification().getDocumentModificationType()) {
            case DOCUMENTCREATED:
                DocumentModification docModification = new DocumentModification();
                docModification.setCreation(new DocumentCreated());
                docModificationUnit.setModification(docModification);
                break;
            default:
                throw new IllegalArgumentException("DocumentModificationType not found: " +
                        swagDocModificationUnit.getModification().getDocumentModificationType());
        }

        claimModification.setDocumentModification(docModificationUnit);
        modification.setClaimModfication(claimModification);
        return modification;
    }

    @Override
    public ClaimSearchQuery convertSearchClaimsToThrift(String partyId,
                                                        Integer limit,
                                                        String continuationToken,
                                                        List<String> claimStatuses) {
        ClaimSearchQuery claimSearchQuery = new ClaimSearchQuery();
        claimSearchQuery.setPartyId(partyId);
        claimSearchQuery.setLimit(limit);
        claimSearchQuery.setToken(continuationToken);
        claimSearchQuery.setStatuses(convertStatusesToThrift(claimStatuses));
        return claimSearchQuery;
    }

    @Override
    public List<ClaimStatus> convertStatusesToThrift(List<String> sourceClaimStatuses) {
        List<ClaimStatus> claimStatuses = new ArrayList<>();
        for (String sourceClaimStatus : sourceClaimStatuses) {
            ClaimStatus status = new ClaimStatus();
            switch (sourceClaimStatus) {
                case PENDING:
                    status.setPending(new ClaimPending());
                    break;
                case REVIEW:
                    status.setReview(new ClaimReview());
                    break;
                case PENDING_ACCEPTANCE:
                    status.setPendingAcceptance(new ClaimPendingAcceptance());
                    break;
                case ACCEPTED:
                    status.setAccepted(new ClaimAccepted());
                    break;
                case DENIED:
                    status.setDenied(new ClaimDenied());
                    break;
                case REVOKED:
                    status.setRevoked(new ClaimRevoked());
                    break;
                default:
                    throw new IllegalArgumentException("Claim status not found: " + sourceClaimStatus);
            }
            claimStatuses.add(status);
        }
        return claimStatuses;
    }

}
