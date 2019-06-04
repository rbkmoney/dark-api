package com.rbkmoney.dark.api.service.questionary.utils;

import com.rbkmoney.swag.dark_api.model.*;

import java.util.ArrayList;
import java.util.List;

public class ConverterManageToSwagModel {

    public static RussianBankAccount prepareRussianBankAccount(com.rbkmoney.questionary.manage.QuestionaryData manageQuestionaryData) {
        com.rbkmoney.questionary.RussianBankAccount manageRussianBankAccount = manageQuestionaryData.getBankAccount().getRussianBankAccount();
        RussianBankAccount russianBankAccount = new RussianBankAccount();
        russianBankAccount.setAccount(manageRussianBankAccount.getAccount());
        russianBankAccount.setBankBik(manageRussianBankAccount.getBankBik());
        russianBankAccount.setBankName(manageRussianBankAccount.getBankName());
        russianBankAccount.setBankPostAccount(manageRussianBankAccount.getBankPostAccount());
        return russianBankAccount;
    }

    public static ContactInfo prepareContactInfo(com.rbkmoney.questionary.ContactInfo manageContactInfo) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail(manageContactInfo.getEmail());
        contactInfo.setPhoneNumber(manageContactInfo.getPhoneNumber());
        return contactInfo;
    }

    public static ShopInfo prepareShopInfo(com.rbkmoney.questionary.manage.QuestionaryData manageQuestionaryData) {
        com.rbkmoney.questionary.ShopInfo manageShopInfo = manageQuestionaryData.getShopInfo();
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setName(manageShopInfo.getDetails().getName());
        shopInfo.setDescription(manageShopInfo.getDetails().getDescription());

        ShopLocationUrl shopLocationUrl = new ShopLocationUrl();
        shopLocationUrl.setUrl(manageShopInfo.getLocation().getUrl());
        shopInfo.setLocation(shopLocationUrl);
        return shopInfo;
    }

    public static Contractor prepareContractor(com.rbkmoney.questionary.manage.QuestionaryData manageQuestionaryData) {
        com.rbkmoney.questionary.Contractor manageContractor = manageQuestionaryData.getContractor();

        if (manageContractor.isSetIndividualEntity()) {
            IndividualEntityContractor individualEntityContractor = new IndividualEntityContractor();

            if (manageContractor.getIndividualEntity().isSetRussianIndividualEntity()) {

                com.rbkmoney.questionary.RussianIndividualEntity manageRussianIndividualEntity = manageContractor.getIndividualEntity().getRussianIndividualEntity();

                RussianIndividualEntity russianIndividualEntity = new RussianIndividualEntity();
                russianIndividualEntity.setInn(manageRussianIndividualEntity.getInn());
                russianIndividualEntity.setLicenseInfo(prepareLicenseInfo(manageRussianIndividualEntity.getLicenseInfo()));
                russianIndividualEntity.setPropertyInfo(manageRussianIndividualEntity.getPropertyInfo());
                russianIndividualEntity.setPrincipalActivity(prepareActivity(manageRussianIndividualEntity.getPrincipalActivity()));
                russianIndividualEntity.setMigrationCardInfo(prepareMigrationCardInfo(manageRussianIndividualEntity.getMigrationCardInfo()));
                russianIndividualEntity.setIndividualPersonCategories(prepareIndividualPersonCategories(manageRussianIndividualEntity.getIndividualPersonCategories()));
                russianIndividualEntity.setResidencyInfo(prepareResidencyInfo(manageRussianIndividualEntity.getResidencyInfo()));


                if (manageRussianIndividualEntity.isSetResidenceApprove()) {
                    russianIndividualEntity.setResidenceApprove(prepareResidenceApprove(manageRussianIndividualEntity.getResidenceApprove()));
                }

                if (manageRussianIndividualEntity.isSetRegistrationInfo()) {
                    russianIndividualEntity.setRegistrationInfo(prepareRegistrationInfo(manageRussianIndividualEntity.getRegistrationInfo()));
                }

                if (manageRussianIndividualEntity.isSetIdentityDocument()) {
                    if (manageRussianIndividualEntity.getIdentityDocument().isSetRussianDomesticPassword()) {
                        RussianDomesticPassport russianDomesticPassport = prepareRussianDomesticPassport(manageRussianIndividualEntity.getIdentityDocument());
                        russianIndividualEntity.setIdentityDocument(russianDomesticPassport);
                    }
                }

                if (manageRussianIndividualEntity.isSetAdditionalInfo()) {
                    AdditionalInfo additionalInfo = prepareAdditionalInfo(manageRussianIndividualEntity.getAdditionalInfo());
                    russianIndividualEntity.setAdditionalInfo(additionalInfo);
                }

                russianIndividualEntity.setRussianPrivateEntity(prepareRussianPrivateEntity(manageRussianIndividualEntity.getRussianPrivateEntity()));
                individualEntityContractor.setIndividualEntity(russianIndividualEntity);
            }

            return individualEntityContractor;
        }

        if (manageContractor.isSetLegalEntity()) {
            LegalEntityContractor legalEntityContractor = new LegalEntityContractor();

            if (manageContractor.getLegalEntity().isSetRussianLegalEntity()) {
                com.rbkmoney.questionary.RussianLegalEntity manageRussianLegalEntity = manageContractor.getLegalEntity().getRussianLegalEntity();

                RussianLegalEntity russianLegalEntity = new RussianLegalEntity();

                russianLegalEntity.setName(manageRussianLegalEntity.getName());
                russianLegalEntity.setForeignName(manageRussianLegalEntity.getForeignName());
                russianLegalEntity.setLegalForm(manageRussianLegalEntity.getLegalForm());
                russianLegalEntity.setInn(manageRussianLegalEntity.getInn());
                russianLegalEntity.setRegistrationInfo(prepareRegistrationInfo(manageRussianLegalEntity.getRegistrationInfo()));
                russianLegalEntity.setAdditionalSpace(manageRussianLegalEntity.getAdditionalSpace());
                russianLegalEntity.setPropertyInfo(manageRussianLegalEntity.getPropertyInfo());
                russianLegalEntity.setOkatoCode(manageRussianLegalEntity.getOkatoCode());
                russianLegalEntity.setOkpoCode(manageRussianLegalEntity.getOkpoCode());
                russianLegalEntity.setPostalAddress(manageRussianLegalEntity.getPostalAddress());


                if (manageRussianLegalEntity.isSetFoundersInfo()) {
                    com.rbkmoney.questionary.FoundersInfo manageFoundersInfo = manageRussianLegalEntity.getFoundersInfo();
                    FoundersInfo foundersInfo = new FoundersInfo();

                    // HEAD LIST
                    List<FounderHead> founderHeadList = new ArrayList<>();
                    List<com.rbkmoney.questionary.Head> manageFounderHeadList = manageFoundersInfo.getHeads();
                    for (com.rbkmoney.questionary.Head manageHead : manageFounderHeadList) {
                        FounderHead founderHead = new FounderHead();
                        founderHead.setIndividualPerson(prepareIndividualPersonFounder(manageHead.getIndividualPerson()));
                        founderHead.setPosition(manageFoundersInfo.getLegalOwner().getPosition());
                        founderHeadList.add(founderHead);
                    }
                    foundersInfo.setHeads(founderHeadList);

                    // FOUNDER LIST
                    List<Founder> founderList = new ArrayList<>();
                    List<com.rbkmoney.questionary.Founder> manageFounderList = manageFoundersInfo.getFounders();
                    for (com.rbkmoney.questionary.Founder manageFounder : manageFounderList) {
                        if (manageFounder.isSetIndividualPersonFounder()) {
                            IndividualPerson individualPerson = prepareIndividualPerson(manageFounder.getIndividualPersonFounder());
                            founderList.add(individualPerson);
                        }
                        if (manageFounder.isSetInternationalLegalEntityFounder()) {
                            InternationalLegalEntityFounder internationalLegalEntityFounder = new InternationalLegalEntityFounder();
                            internationalLegalEntityFounder.setCountry(manageFounder.getInternationalLegalEntityFounder().getCountry());
                            internationalLegalEntityFounder.setFullName(manageFounder.getInternationalLegalEntityFounder().getFullName());
                            founderList.add(internationalLegalEntityFounder);
                        }
                        if (manageFounder.isSetRussianLegalEntityFounder()) {
                            RussianLegalEntityFounder russianLegalEntityFounder = new RussianLegalEntityFounder();
                            russianLegalEntityFounder.setFullName(manageFounder.getRussianLegalEntityFounder().getFullName());
                            russianLegalEntityFounder.setInn(manageFounder.getRussianLegalEntityFounder().getInn());
                            russianLegalEntityFounder.setOgrn(manageFounder.getRussianLegalEntityFounder().getOgrn());
                            founderList.add(russianLegalEntityFounder);
                        }
                    }
                    foundersInfo.setFounders(founderList);

                    if (manageFoundersInfo.isSetLegalOwner()) {
                        FounderHead founderHead = prepareLegalOwner(manageFoundersInfo);
                        foundersInfo.setLegalOwner(founderHead);
                    }

                    russianLegalEntity.setFoundersInfo(foundersInfo);
                }


                russianLegalEntity.setLicenseInfo(prepareLicenseInfo(manageRussianLegalEntity.getLicenseInfo()));
                russianLegalEntity.setPrincipalActivity(prepareActivity(manageRussianLegalEntity.getPrincipalActivity()));
                com.rbkmoney.questionary.LegalOwnerInfo manageLegalOwnerInfo = manageRussianLegalEntity.getLegalOwnerInfo();

                LegalOwnerInfo legalOwnerInfo = new LegalOwnerInfo();

                if (manageLegalOwnerInfo.isSetIdentityDocument()) {
                    if (manageLegalOwnerInfo.getIdentityDocument().isSetRussianDomesticPassword()) {
                        RussianDomesticPassport russianDomesticPassport = prepareRussianDomesticPassport(manageLegalOwnerInfo.getIdentityDocument());
                        legalOwnerInfo.setIdentityDocument(russianDomesticPassport);
                    }
                }

                legalOwnerInfo.setInn(manageLegalOwnerInfo.getInn());
                legalOwnerInfo.setMigrationCardInfo(prepareMigrationCardInfo(manageLegalOwnerInfo.getMigrationCardInfo()));
                legalOwnerInfo.setPdlCategory(manageLegalOwnerInfo.pdl_category);
                legalOwnerInfo.setResidenceApprove(prepareResidenceApprove(manageLegalOwnerInfo.getResidenceApprove()));

                legalOwnerInfo.setRussianPrivateEntity(prepareRussianPrivateEntity(manageLegalOwnerInfo.getRussianPrivateEntity()));

                russianLegalEntity.setLegalOwnerInfo(legalOwnerInfo);

                List<BeneficialOwner> beneficialOwnersList = new ArrayList<>();
                List<com.rbkmoney.questionary.BeneficialOwner> manageBeneficialOwnerList = manageRussianLegalEntity.getBeneficialOwners();
                for (com.rbkmoney.questionary.BeneficialOwner manageBeneficialOwner : manageBeneficialOwnerList) {
                    BeneficialOwner beneficialOwner = new BeneficialOwner();
                    if (manageBeneficialOwner.isSetIdentityDocument()) {
                        if (manageBeneficialOwner.getIdentityDocument().isSetRussianDomesticPassword()) {
                            RussianDomesticPassport russianDomesticPassport = prepareRussianDomesticPassport(manageBeneficialOwner.getIdentityDocument());
                            beneficialOwner.setIdentityDocument(russianDomesticPassport);
                        }
                    }

                    beneficialOwner.setInn(manageBeneficialOwner.getInn());
                    beneficialOwner.setMigrationCardInfo(prepareMigrationCardInfo(manageBeneficialOwner.getMigrationCardInfo()));
                    beneficialOwner.setOwnershipPercentage(Byte.valueOf(manageBeneficialOwner.getOwnershipPercentage()).intValue());
                    beneficialOwner.setPdlCategory(manageBeneficialOwner.pdl_category);
                    beneficialOwner.setResidenceApprove(prepareResidenceApprove(manageBeneficialOwner.getResidenceApprove()));

                    beneficialOwner.setRussianPrivateEntity(prepareRussianPrivateEntity(manageBeneficialOwner.getRussianPrivateEntity()));

                    beneficialOwnersList.add(beneficialOwner);
                }

                russianLegalEntity.setBeneficialOwners(beneficialOwnersList);

                if (manageRussianLegalEntity.isSetAdditionalInfo()) {
                    AdditionalInfo additionalInfo = prepareAdditionalInfo(manageRussianLegalEntity.getAdditionalInfo());
                    russianLegalEntity.setAdditionalInfo(additionalInfo);
                }

                russianLegalEntity.setResidencyInfo(prepareResidencyInfo(manageRussianLegalEntity.getResidencyInfo()));

                legalEntityContractor.setLegalEntity(russianLegalEntity);
            }

            return legalEntityContractor;
        }

        throw new RuntimeException("Unknown Contractor");
    }

    public static RussianPrivateEntity prepareRussianPrivateEntity(com.rbkmoney.questionary.RussianPrivateEntity manageRussianPrivateEntity) {
        RussianPrivateEntity russianPrivateEntity = new RussianPrivateEntity();
        russianPrivateEntity.setActualAddress(manageRussianPrivateEntity.getActualAddress());
        russianPrivateEntity.setBirthDate(manageRussianPrivateEntity.getBirthDate());
        russianPrivateEntity.setBirthPlace(manageRussianPrivateEntity.getBirthPlace());
        russianPrivateEntity.setCitizenship(manageRussianPrivateEntity.getCitizenship());
        russianPrivateEntity.setResidenceAddress(manageRussianPrivateEntity.getResidenceAddress());

        russianPrivateEntity.setPersonAnthroponym(preparePersonAnthroponym(manageRussianPrivateEntity.getFio()));
        russianPrivateEntity.setContactInfo(prepareContactInfo(manageRussianPrivateEntity.getContactInfo()));
        return russianPrivateEntity;
    }

    public static RussianDomesticPassport prepareRussianDomesticPassport(com.rbkmoney.questionary.IdentityDocument identityDocument) {
        com.rbkmoney.questionary.RussianDomesticPassport manageRussianDomesticPassport = identityDocument.getRussianDomesticPassword();
        RussianDomesticPassport russianDomesticPassport = new RussianDomesticPassport();
        russianDomesticPassport.setSeries(manageRussianDomesticPassport.getSeries());
        russianDomesticPassport.setNumber(manageRussianDomesticPassport.getNumber());
        russianDomesticPassport.setIssuer(manageRussianDomesticPassport.getIssuer());
        russianDomesticPassport.setIssuerCode(manageRussianDomesticPassport.getIssuerCode());
        russianDomesticPassport.setIssuedAt(manageRussianDomesticPassport.getIssuedAt());
        return russianDomesticPassport;
    }

    public static IndividualPerson prepareIndividualPerson(com.rbkmoney.questionary.IndividualPerson manageIndividualPerson) {
        IndividualPerson individualPerson = new IndividualPerson();
        individualPerson.setInn(manageIndividualPerson.getInn());
        PersonAnthroponym personAnthroponym = preparePersonAnthroponym(manageIndividualPerson);
        individualPerson.setFio(personAnthroponym);
        return individualPerson;
    }

    public static IndividualPersonFounder prepareIndividualPersonFounder(com.rbkmoney.questionary.IndividualPerson manageIndividualPerson) {
        IndividualPersonFounder individualPersonFounder = new IndividualPersonFounder();
        individualPersonFounder.setInn(manageIndividualPerson.getInn());
        PersonAnthroponym personAnthroponym = preparePersonAnthroponym(manageIndividualPerson);
        individualPersonFounder.setFio(personAnthroponym);
        return individualPersonFounder;
    }

    public static PersonAnthroponym preparePersonAnthroponym(com.rbkmoney.questionary.IndividualPerson manageIndividualPerson) {
        PersonAnthroponym personAnthroponym = new PersonAnthroponym();
        personAnthroponym.setFirstName(manageIndividualPerson.getFio().getFirstName());
        personAnthroponym.setMiddleName(manageIndividualPerson.getFio().getMiddleName());
        personAnthroponym.setSecondName(manageIndividualPerson.getFio().getSecondName());
        return personAnthroponym;
    }

    public static PersonAnthroponym preparePersonAnthroponym(com.rbkmoney.questionary.PersonAnthroponym managePersonAnthroponym) {
        PersonAnthroponym personAnthroponym = new PersonAnthroponym();
        personAnthroponym.setFirstName(managePersonAnthroponym.getFirstName());
        personAnthroponym.setMiddleName(managePersonAnthroponym.getMiddleName());
        personAnthroponym.setSecondName(managePersonAnthroponym.getSecondName());
        return personAnthroponym;
    }

    public static FounderHead prepareLegalOwner(com.rbkmoney.questionary.FoundersInfo manageFoundersInfo) {
        FounderHead founderHead = new FounderHead();
        founderHead.setIndividualPerson(prepareIndividualPersonFounder(manageFoundersInfo.getLegalOwner().getIndividualPerson()));
        founderHead.setPosition(manageFoundersInfo.getLegalOwner().getPosition());
        return founderHead;
    }

    public static AdditionalInfo prepareAdditionalInfo(com.rbkmoney.questionary.AdditionalInfo manageAdditionalInfo) {
        AdditionalInfo additionalInfo = new AdditionalInfo();

        additionalInfo.setStaffCount(manageAdditionalInfo.getStaffCount());
        additionalInfo.setHasAccountant(manageAdditionalInfo.has_accountant);
        additionalInfo.setAccounting(manageAdditionalInfo.getAccounting());
        additionalInfo.setAccountingOrg(manageAdditionalInfo.getAccountingOrg());
        additionalInfo.setNkoRelationTarget(manageAdditionalInfo.getNKORelationTarget());
        additionalInfo.setRelationshipWithNko(manageAdditionalInfo.getRelationshipWithNKO());
        additionalInfo.setMonthOperationCount(MonthOperationCount.fromValue(manageAdditionalInfo.getMonthOperationCount().name()));
        additionalInfo.setMonthOperationSum(MonthOperationSum.fromValue(manageAdditionalInfo.getMonthOperationSum().name()));

        if (manageAdditionalInfo.isSetFinancialPosition()) {
            additionalInfo.setFinancialPosition(prepareFinancialPositions(manageAdditionalInfo));
        }

        additionalInfo.setStorageFacilities(manageAdditionalInfo.storage_facilities);
        additionalInfo.setMainCounterparties(manageAdditionalInfo.getMainCounterparties());
        additionalInfo.setRelationIndividualEntity(RelationIndividualEntity.fromValue(manageAdditionalInfo.getRelationIndividualEntity().name()));
        additionalInfo.setBenefitThirdParties(manageAdditionalInfo.benefit_third_parties);
        additionalInfo.setBusinessReputation(BusinessReputation.fromValue(manageAdditionalInfo.getBusinessReputation().name()));

        if (manageAdditionalInfo.isSetBusinessInfo()) {
            additionalInfo.setBusinessInfo(prepareBusinessInfo(manageAdditionalInfo));
        }

        if (manageAdditionalInfo.isSetBankAccount() && manageAdditionalInfo.getBankAccount().isSetRussianBankAccount()) {
            additionalInfo.setRussianBankAccount(prepareRussianBankAccount(manageAdditionalInfo));
        }
        return additionalInfo;
    }

    public static RussianBankAccount prepareRussianBankAccount(com.rbkmoney.questionary.AdditionalInfo manageAdditionalInfo) {
        com.rbkmoney.questionary.RussianBankAccount manageRussianBankAccount = manageAdditionalInfo.getBankAccount().getRussianBankAccount();

        RussianBankAccount russianBankAccount = new RussianBankAccount();
        russianBankAccount.setAccount(manageRussianBankAccount.getAccount());
        russianBankAccount.setBankName(manageRussianBankAccount.getBankName());
        russianBankAccount.setBankBik(manageRussianBankAccount.getBankBik());
        russianBankAccount.setBankPostAccount(manageRussianBankAccount.getBankPostAccount());
        return russianBankAccount;
    }

    public static List<BusinessInfo> prepareBusinessInfo(com.rbkmoney.questionary.AdditionalInfo manageAdditionalInfo) {
        List<com.rbkmoney.questionary.BusinessInfo> manageBussinessList = manageAdditionalInfo.getBusinessInfo();
        List<BusinessInfo> businessInfo = new ArrayList<>();

        for (com.rbkmoney.questionary.BusinessInfo manageBusinessInfo : manageBussinessList) {
            if (manageBusinessInfo.isSetAnotherBusiness()) {
                BusinessInfo business = new AnotherBusiness();
                ((AnotherBusiness) business).setDescription(manageBusinessInfo.getAnotherBusiness().getDescription());
                businessInfo.add(business);
            }
            if (manageBusinessInfo.isSetBuildingBusiness()) {
                businessInfo.add(new BuildingBusiness());
            }
        }
        return businessInfo;
    }

    public static List<FinancialPosition> prepareFinancialPositions(com.rbkmoney.questionary.AdditionalInfo manageAdditionalInfo) {
        List<com.rbkmoney.questionary.FinancialPosition> managepositionList = manageAdditionalInfo.getFinancialPosition();
        List<FinancialPosition> financialPositions = new ArrayList<>();

        for (com.rbkmoney.questionary.FinancialPosition financialPosition : managepositionList) {
            if (financialPosition.isSetAnnualFinancialStatements()) {
                FinancialPosition finPosition = new AnnualFinancialStatements();
                financialPositions.add(finPosition);
            }
            if (financialPosition.isSetAnnualTaxReturnWithMark()) {
                FinancialPosition finPosition = new AnnualTaxReturnWithMark();
                financialPositions.add(finPosition);
            }
            if (financialPosition.isSetAnnualTaxReturnWithoutMark()) {
                FinancialPosition finPosition = new AnnualTaxReturnWithoutMark();
                financialPositions.add(finPosition);
            }
            if (financialPosition.isSetAnnualTaxReturnWithoutMarkPaper()) {
                FinancialPosition finPosition = new AnnualTaxReturnWithoutMarkPaper();
                financialPositions.add(finPosition);
            }
            if (financialPosition.isSetLetterOfGuarantee()) {
                FinancialPosition finPosition = new LetterOfGuarantee();
                financialPositions.add(finPosition);
            }
            if (financialPosition.isSetQuarterlyTaxReturnWithMark()) {
                FinancialPosition finPosition = new QuarterlyTaxReturnWithMark();
                financialPositions.add(finPosition);
            }
            if (financialPosition.isSetQuarterlyTaxReturnWithoutMark()) {
                FinancialPosition finPosition = new QuarterlyTaxReturnWithoutMark();
                financialPositions.add(finPosition);
            }
            if (financialPosition.isSetStatementOfDuty()) {
                FinancialPosition finPosition = new StatementOfDuty();
                financialPositions.add(finPosition);
            }
        }
        return financialPositions;
    }

    public static RegistrationInfo prepareRegistrationInfo(com.rbkmoney.questionary.RegistrationInfo manageRegistrationInfo) {
        RegistrationInfo registrationInfo = new RegistrationInfo();
        if (manageRegistrationInfo.isSetIndividualRegistrationInfo()) {
            IndividualRegistrationInfo individualRegistrationInfo = new IndividualRegistrationInfo();
            individualRegistrationInfo.setOgrnip(manageRegistrationInfo.getIndividualRegistrationInfo().getOgrnip());
            individualRegistrationInfo.setRegistrationDate(manageRegistrationInfo.getIndividualRegistrationInfo().getRegistrationDate());
            individualRegistrationInfo.setRegistrationPlace(manageRegistrationInfo.getIndividualRegistrationInfo().getRegistrationPlace());
            return individualRegistrationInfo;
        }

        if (manageRegistrationInfo.isSetLegalRegistrationInfo()) {
            LegalRegistrationInfo legalRegistrationInfo = new LegalRegistrationInfo();
            legalRegistrationInfo.setOgrn(manageRegistrationInfo.getLegalRegistrationInfo().getOgrn());
            legalRegistrationInfo.setActualAddress(manageRegistrationInfo.getLegalRegistrationInfo().getActualAddress());
            legalRegistrationInfo.setRegistrationAddress(manageRegistrationInfo.getLegalRegistrationInfo().getRegistrationAddress());
            legalRegistrationInfo.setRegistrationDate(manageRegistrationInfo.getLegalRegistrationInfo().getRegistrationDate());
            legalRegistrationInfo.setRegistrationPlace(manageRegistrationInfo.getLegalRegistrationInfo().getRegistrationPlace());
            return legalRegistrationInfo;
        }
        return registrationInfo;
    }

    public static ResidencyInfo prepareResidencyInfo(com.rbkmoney.questionary.ResidencyInfo manageResidencyInfo) {

        if (manageResidencyInfo.isSetIndividualResidencyInfo()) {
            IndividualResidencyInfo individualResidencyInfo = new IndividualResidencyInfo();
            individualResidencyInfo.setTaxResident(manageResidencyInfo.getIndividualResidencyInfo().tax_resident);
            return individualResidencyInfo;
        }

        if (manageResidencyInfo.isSetLegalResidencyInfo()) {
            LegalResidencyInfo legalResidencyInfo = new LegalResidencyInfo();
            legalResidencyInfo.setFatca(manageResidencyInfo.getLegalResidencyInfo().fatca);
            legalResidencyInfo.setOwnerResident(manageResidencyInfo.getLegalResidencyInfo().owner_resident);
            legalResidencyInfo.setTaxResident(manageResidencyInfo.getLegalResidencyInfo().tax_resident);
            return legalResidencyInfo;
        }
        return new ResidencyInfo();
    }

    public static IndividualPersonCategories prepareIndividualPersonCategories(com.rbkmoney.questionary.IndividualPersonCategories manageIndividualPersonCategories) {
        IndividualPersonCategories individualPersonCategories = new IndividualPersonCategories();
        individualPersonCategories.setBehalfOfForeign(manageIndividualPersonCategories.behalf_of_foreign);
        individualPersonCategories.setBeneficialOwner(manageIndividualPersonCategories.beneficial_owner);
        individualPersonCategories.setForeignPublicPerson(manageIndividualPersonCategories.foreign_public_person);
        individualPersonCategories.setForeignRelativePerson(manageIndividualPersonCategories.foreign_relative_person);
        individualPersonCategories.setHasRepresentative(manageIndividualPersonCategories.has_representative);
        individualPersonCategories.setWorldwideOrgPublicPerson(manageIndividualPersonCategories.worldwide_org_public_person);
        return individualPersonCategories;
    }

    public static ResidenceApprove prepareResidenceApprove(com.rbkmoney.questionary.ResidenceApprove manageResidenceApprove) {
        ResidenceApprove residenceApprove = new ResidenceApprove();
        residenceApprove.setName(manageResidenceApprove.getName());
        residenceApprove.setNumber(manageResidenceApprove.getNumber());
        residenceApprove.setSeries(manageResidenceApprove.getSeries());
        residenceApprove.setBeginningDate(manageResidenceApprove.getBeginningDate());
        residenceApprove.setExpirationDate(manageResidenceApprove.getExpirationDate());
        return residenceApprove;
    }

    public static MigrationCardInfo prepareMigrationCardInfo(com.rbkmoney.questionary.MigrationCardInfo manageMigrationCardInfo) {
        MigrationCardInfo migrationCardInfo = new MigrationCardInfo();
        migrationCardInfo.setBeginningDate(manageMigrationCardInfo.getBeginningDate());
        migrationCardInfo.setExpirationDate(manageMigrationCardInfo.getExpirationDate());
        migrationCardInfo.setCardNumber(manageMigrationCardInfo.getCardNumber());
        return migrationCardInfo;
    }

    public static Activity prepareActivity(com.rbkmoney.questionary.Activity manageActivity) {
        Activity activity = new Activity();
        activity.setCode(manageActivity.getCode());
        activity.setDescription(manageActivity.getDescription());
        return activity;
    }

    public static LicenseInfo prepareLicenseInfo(com.rbkmoney.questionary.LicenseInfo manageLicenseInfo) {
        LicenseInfo licenseInfo = new LicenseInfo();
        licenseInfo.setEffectiveDate(manageLicenseInfo.getEffectiveDate());
        licenseInfo.setExpirationDate(manageLicenseInfo.getExpirationDate());
        licenseInfo.setIssueDate(manageLicenseInfo.getIssueDate());
        licenseInfo.setLicensedActivity(manageLicenseInfo.getLicensedActivity());
        licenseInfo.setOfficialNum(manageLicenseInfo.getOfficialNum());
        licenseInfo.setIssuerName(manageLicenseInfo.getIssuerName());
        return licenseInfo;
    }

}
