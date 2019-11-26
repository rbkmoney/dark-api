package com.rbkmoney.dark.api.utils;

import com.rbkmoney.swag.questionary.model.*;
import com.rbkmoney.swag.questionary.model.AccountantInfo.AccountantInfoTypeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionaryCompareUtil {

    public static void contractorCompare(com.rbkmoney.questionary.Contractor thriftContractor, Contractor swagContractor) {
        if (swagContractor instanceof IndividualEntityContractor) {
            com.rbkmoney.questionary.RussianIndividualEntity thriftRussianIndividualEntity = thriftContractor.getIndividualEntity().getRussianIndividualEntity();
            IndividualEntity swagIndividualEntity = ((IndividualEntityContractor) swagContractor).getIndividualEntity();
            Assert.assertEquals(thriftRussianIndividualEntity.getName(), ((RussianIndividualEntity) swagIndividualEntity).getName());
            Assert.assertEquals(thriftRussianIndividualEntity.getInn(), ((RussianIndividualEntity) swagIndividualEntity).getInn());
            Assert.assertEquals(thriftRussianIndividualEntity.getPdlRelationDegree(), ((RussianIndividualEntity) swagIndividualEntity).getPdlRelationDegree());
            Assert.assertEquals(thriftRussianIndividualEntity.isPdlCategory(), ((RussianIndividualEntity) swagIndividualEntity).isPdlCategory());
            Assert.assertEquals(thriftRussianIndividualEntity.isHasBeneficialOwners(), ((RussianIndividualEntity) swagIndividualEntity).isHasBeneficialOwners());
            identityDocumentCompare(thriftRussianIndividualEntity.getIdentityDocument(),
                    ((RussianIndividualEntity) swagIndividualEntity).getIdentityDocument());
            individualPersonCategoriesCompare(thriftRussianIndividualEntity.getIndividualPersonCategories(),
                    ((RussianIndividualEntity) swagIndividualEntity).getIndividualPersonCategories());
            licenseInfoCompare(thriftRussianIndividualEntity.getLicenseInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getLicenseInfo());
            migrationCardInfoCompare(thriftRussianIndividualEntity.getMigrationCardInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getMigrationCardInfo());
            principalActivityCompare(thriftRussianIndividualEntity.getPrincipalActivity(),
                    ((RussianIndividualEntity) swagIndividualEntity).getPrincipalActivity());
            registrationInfoCompare(thriftRussianIndividualEntity.getRegistrationInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getRegistrationInfo());
            residencyInfoCompare(thriftRussianIndividualEntity.getResidencyInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getResidencyInfo());
            residencyApproveCompare(thriftRussianIndividualEntity.getResidenceApprove(),
                    ((RussianIndividualEntity) swagIndividualEntity).getResidenceApprove());
            russianPrivateEntityCompare(thriftRussianIndividualEntity.getRussianPrivateEntity(),
                    ((RussianIndividualEntity) swagIndividualEntity).getRussianPrivateEntity());
            additionalInfoCompare(thriftRussianIndividualEntity.getAdditionalInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getAdditionalInfo());
        } else if (swagContractor instanceof LegalEntityContractor) {
            com.rbkmoney.questionary.LegalEntity thriftLegalEntity = thriftContractor.getLegalEntity();
            LegalEntity swagLegalEntity = ((LegalEntityContractor) swagContractor).getLegalEntity();
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().getInn(), ((RussianLegalEntity) swagLegalEntity).getInn());
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().getAdditionalSpace(), ((RussianLegalEntity) swagLegalEntity).getAdditionalSpace());
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().getForeignName(), ((RussianLegalEntity) swagLegalEntity).getForeignName());
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().getLegalForm(), ((RussianLegalEntity) swagLegalEntity).getLegalForm());
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().getName(), ((RussianLegalEntity) swagLegalEntity).getName());
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().getOkatoCode(), ((RussianLegalEntity) swagLegalEntity).getOkatoCode());
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().getOkpoCode(), ((RussianLegalEntity) swagLegalEntity).getOkpoCode());
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().getPostalAddress(), ((RussianLegalEntity) swagLegalEntity).getPostalAddress());
            Assert.assertEquals(thriftLegalEntity.getRussianLegalEntity().isHasBeneficialOwners(), ((RussianLegalEntity) swagLegalEntity).isHasBeneficialOwners());
            residencyInfoCompare(thriftLegalEntity.getRussianLegalEntity().getResidencyInfo(), ((RussianLegalEntity) swagLegalEntity).getResidencyInfo());
            registrationInfoCompare(thriftLegalEntity.getRussianLegalEntity().getRegistrationInfo(), ((RussianLegalEntity) swagLegalEntity).getRegistrationInfo());
            principalActivityCompare(thriftLegalEntity.getRussianLegalEntity().getPrincipalActivity(), ((RussianLegalEntity) swagLegalEntity).getPrincipalActivity());
            for (int i = 0; i < thriftLegalEntity.getRussianLegalEntity().getBeneficialOwners().size(); i++) {
                com.rbkmoney.questionary.BeneficialOwner beneficialOwner = thriftLegalEntity.getRussianLegalEntity().getBeneficialOwners().get(i);
                BeneficialOwner swagBeneficialOwner = ((RussianLegalEntity) swagLegalEntity).getBeneficialOwner().get(i);
                beneficialOwnerCompare(beneficialOwner, swagBeneficialOwner);
            }
            founderInfoCompare(thriftLegalEntity.getRussianLegalEntity().getFoundersInfo(), ((RussianLegalEntity) swagLegalEntity).getFoundersInfo());
            comparePropertyInfoDocType(thriftLegalEntity.getRussianLegalEntity().getPropertyInfoDocumentType(),
                    ((RussianLegalEntity) swagLegalEntity).getPropertyInfoDocumentType());
            additionalInfoCompare(thriftLegalEntity.getRussianLegalEntity().getAdditionalInfo(), ((RussianLegalEntity) swagLegalEntity).getAdditionalInfo());
        }
    }

    private static void comparePropertyInfoDocType(com.rbkmoney.questionary.PropertyInfoDocumentType thriftPropertyInfoDocumentType,
                                                   PropertyInfoDocumentType swagPropertyInfoDocumentType) {
        if (swagPropertyInfoDocumentType.getDocumentType() == PropertyInfoDocumentType.DocumentTypeEnum.LEASECONTRACT) {
            Assert.assertTrue(thriftPropertyInfoDocumentType.isSetLeaseContract());
        } else if (swagPropertyInfoDocumentType.getDocumentType() == PropertyInfoDocumentType.DocumentTypeEnum.SUBLEASECONTRACT) {
            Assert.assertTrue(thriftPropertyInfoDocumentType.isSetSubleaseContract());
        } else if (swagPropertyInfoDocumentType.getDocumentType() == PropertyInfoDocumentType.DocumentTypeEnum.CERTIFICATEOFOWNERSHIP) {
            Assert.assertTrue(thriftPropertyInfoDocumentType.isSetCertificateOfOwnership());
        } else if (swagPropertyInfoDocumentType.getDocumentType() == PropertyInfoDocumentType.DocumentTypeEnum.OTHERPROPERTYINFODOCUMENTTYPE) {
            Assert.assertTrue(thriftPropertyInfoDocumentType.isSetOtherPropertyInfoDocumentType());
            Assert.assertEquals(thriftPropertyInfoDocumentType.getOtherPropertyInfoDocumentType().getName(),
                    ((OtherPropertyInfoDocumentType) swagPropertyInfoDocumentType).getName());
        }
    }

    private static void additionalInfoCompare(com.rbkmoney.questionary.AdditionalInfo thriftAdditionalInfo, AdditionalInfo swagAdditionalInfo) {
        Assert.assertEquals(thriftAdditionalInfo.getStaffCount(), swagAdditionalInfo.getStaffCount().longValue());
        Assert.assertEquals(thriftAdditionalInfo.getMainCounterparties(), swagAdditionalInfo.getMainCounterparties());
        Assert.assertEquals(thriftAdditionalInfo.getNKORelationTarget(), swagAdditionalInfo.getNkoRelationTarget());
        Assert.assertEquals(thriftAdditionalInfo.getRelationshipWithNKO(), swagAdditionalInfo.getRelationshipWithNko());
        Assert.assertEquals(thriftAdditionalInfo.isHasBeneficiary(), swagAdditionalInfo.isHasBeneficiary());
        Assert.assertEquals(thriftAdditionalInfo.isHasLiquidationProcess(), swagAdditionalInfo.isHasLiquidationProcess());
        for (int i = 0; i < thriftAdditionalInfo.getFinancialPosition().size(); i++) {
            com.rbkmoney.questionary.FinancialPosition thriftFinancialPosition = thriftAdditionalInfo.getFinancialPosition().get(i);
            FinancialPosition swagFinancialPosition = swagAdditionalInfo.getFinancialPosition().get(i);
            compareFinancialPosition(thriftFinancialPosition, swagFinancialPosition);
        }
        for (int i = 0; i < thriftAdditionalInfo.getBusinessInfo().size(); i++) {
            com.rbkmoney.questionary.BusinessInfo thriftBusinessInfo = thriftAdditionalInfo.getBusinessInfo().get(i);
            BusinessInfo swagBusinessInfo = swagAdditionalInfo.getBusinessInfo().get(i);
            compareBusinessInfo(thriftBusinessInfo, swagBusinessInfo);
        }
        compareBusinessReputation(thriftAdditionalInfo.getBusinessReputation(), swagAdditionalInfo.getBusinessReputation());
        compareAccountantInfo(thriftAdditionalInfo.getAccountantInfo(), swagAdditionalInfo.getAccountantInfo());
        compareMonthOperationSum(thriftAdditionalInfo.getMonthOperationSum(), swagAdditionalInfo.getMonthOperationSum());
    }

    private static void compareBusinessReputation(com.rbkmoney.questionary.BusinessReputation thriftBusinessReputation,
                                                  BusinessReputation swagBusinessReputation) {
        if (swagBusinessReputation == BusinessReputation.NOREVIEWS) {
            Assert.assertSame(com.rbkmoney.questionary.BusinessReputation.no_reviews, thriftBusinessReputation);
        } else if (swagBusinessReputation == BusinessReputation.PROVIDEREVIEWS) {
            Assert.assertSame(com.rbkmoney.questionary.BusinessReputation.provide_reviews, thriftBusinessReputation);
        }
    }

    private static void compareBusinessInfo(com.rbkmoney.questionary.BusinessInfo thriftBusinessInfo, BusinessInfo swagBusinessInfo) {
        if (swagBusinessInfo.getBusinessInfoType() == BusinessInfo.BusinessInfoTypeEnum.WHOLESALETRADEBUSINESS) {
            Assert.assertTrue(thriftBusinessInfo.isSetWholesaleTradeBusiness());
        } else if (swagBusinessInfo.getBusinessInfoType() == BusinessInfo.BusinessInfoTypeEnum.TRANSPORTBUSINESS) {
            Assert.assertTrue(thriftBusinessInfo.isSetTransportBusiness());
        } else if (swagBusinessInfo.getBusinessInfoType() == BusinessInfo.BusinessInfoTypeEnum.SECURITIESTRADINGBUSINESS) {
            Assert.assertTrue(thriftBusinessInfo.isSetSecuritiesTradingBusiness());
        } else if (swagBusinessInfo.getBusinessInfoType() == BusinessInfo.BusinessInfoTypeEnum.RETAILTRADEBUSINESS) {
            Assert.assertTrue(thriftBusinessInfo.isSetRetailTradeBusiness());
        } else if (swagBusinessInfo.getBusinessInfoType() == BusinessInfo.BusinessInfoTypeEnum.PRODUCTIONBUSINESS) {
            Assert.assertTrue(thriftBusinessInfo.isSetProductionBusiness());
        } else if (swagBusinessInfo.getBusinessInfoType() == BusinessInfo.BusinessInfoTypeEnum.MEDIATIONINPROPERTYBUSINESS) {
            Assert.assertTrue(thriftBusinessInfo.isSetMediationInPropertyBusiness());
        } else if (swagBusinessInfo.getBusinessInfoType() == BusinessInfo.BusinessInfoTypeEnum.BUILDINGBUSINESS) {
            Assert.assertTrue(thriftBusinessInfo.isSetBuildingBusiness());
        } else if (swagBusinessInfo.getBusinessInfoType() == BusinessInfo.BusinessInfoTypeEnum.ANOTHERBUSINESS) {
            Assert.assertTrue(thriftBusinessInfo.isSetAnotherBusiness());
            Assert.assertEquals(thriftBusinessInfo.getAnotherBusiness().getDescription(),
                    ((AnotherBusiness) swagBusinessInfo).getDescription());
        }
    }

    private static void compareFinancialPosition(com.rbkmoney.questionary.FinancialPosition thriftFinancialPosition, FinancialPosition swagFinancialPosition) {
        if (swagFinancialPosition.getFinancialPositionType() == FinancialPosition.FinancialPositionTypeEnum.STATEMENTOFDUTY) {
            Assert.assertTrue(thriftFinancialPosition.isSetStatementOfDuty());
        } else if (swagFinancialPosition.getFinancialPositionType() == FinancialPosition.FinancialPositionTypeEnum.QUARTERLYTAXRETURNWITHOUTMARK) {
            Assert.assertTrue(thriftFinancialPosition.isSetQuarterlyTaxReturnWithoutMark());
        } else if (swagFinancialPosition.getFinancialPositionType() == FinancialPosition.FinancialPositionTypeEnum.QUARTERLYTAXRETURNWITHMARK) {
            Assert.assertTrue(thriftFinancialPosition.isSetQuarterlyTaxReturnWithMark());
        } else if (swagFinancialPosition.getFinancialPositionType() == FinancialPosition.FinancialPositionTypeEnum.LETTEROFGUARANTEE) {
            Assert.assertTrue(thriftFinancialPosition.isSetLetterOfGuarantee());
        } else if (swagFinancialPosition.getFinancialPositionType() == FinancialPosition.FinancialPositionTypeEnum.ANNUALTAXRETURNWITHOUTMARKPAPER) {
            Assert.assertTrue(thriftFinancialPosition.isSetAnnualTaxReturnWithoutMarkPaper());
        } else if (swagFinancialPosition.getFinancialPositionType() == FinancialPosition.FinancialPositionTypeEnum.ANNUALTAXRETURNWITHMARK) {
            Assert.assertTrue(thriftFinancialPosition.isSetAnnualTaxReturnWithMark());
        } else if (swagFinancialPosition.getFinancialPositionType() == FinancialPosition.FinancialPositionTypeEnum.ANNUALTAXRETURNWITHOUTMARK) {
            Assert.assertTrue(thriftFinancialPosition.isSetAnnualTaxReturnWithoutMark());
        } else if (swagFinancialPosition.getFinancialPositionType() == FinancialPosition.FinancialPositionTypeEnum.ANNUALFINANCIALSTATEMENTS) {
            Assert.assertTrue(thriftFinancialPosition.isSetAnnualFinancialStatements());
        }
    }

    private static void compareMonthOperationSum(com.rbkmoney.questionary.MonthOperationSum thriftMonthOperationSum, MonthOperationSum swagMonthOperationSum) {
        if (swagMonthOperationSum == MonthOperationSum.BTWFIVEHUNDREDTHOUSANDTOONEMILLION) {
            Assert.assertSame(com.rbkmoney.questionary.MonthOperationSum.btw_five_hundred_thousand_to_one_million, thriftMonthOperationSum);
        } else if (swagMonthOperationSum == MonthOperationSum.GTONEMILLION) {
            Assert.assertSame(com.rbkmoney.questionary.MonthOperationSum.gt_one_million, thriftMonthOperationSum);
        } else if (swagMonthOperationSum == MonthOperationSum.LTFIVEHUNDREDTHOUSAND) {
            Assert.assertSame(com.rbkmoney.questionary.MonthOperationSum.lt_five_hundred_thousand, thriftMonthOperationSum);
        }
    }

    private static void compareAccountantInfo(com.rbkmoney.questionary.AccountantInfo thriftAccountantInfo, AccountantInfo swagAccountantInfo) {
        if (swagAccountantInfo.getAccountantInfoType() == AccountantInfoTypeEnum.WITHCHIEFACCOUNTANT) {
            Assert.assertTrue(thriftAccountantInfo.isSetWithChiefAccountant());
        } else if (swagAccountantInfo.getAccountantInfoType() == AccountantInfoTypeEnum.WITHOUTCHIEFHEADACCOUNTING) {
            Assert.assertTrue(thriftAccountantInfo.getWithoutChiefAccountant().isSetHeadAccounting());
        } else if (swagAccountantInfo.getAccountantInfoType() == AccountantInfoTypeEnum.WITHOUTCHIEFINDIVIDUALACCOUNTANT) {
            Assert.assertTrue(thriftAccountantInfo.getWithoutChiefAccountant().isSetIndividualAccountant());
        } else if (swagAccountantInfo.getAccountantInfoType() == AccountantInfoTypeEnum.WITHOUTCHIEFACCOUNTINGORGANIZATION) {
            Assert.assertTrue(thriftAccountantInfo.getWithoutChiefAccountant().isSetAccountingOrganization());
        }
    }

    public static void contactInfoCompare(com.rbkmoney.questionary.ContactInfo thriftContactInfo, ContactInfo swagContactInfo) {
        Assert.assertEquals(thriftContactInfo.getEmail(), swagContactInfo.getEmail());
        Assert.assertEquals(thriftContactInfo.getPhoneNumber(), swagContactInfo.getPhoneNumber());
    }

    public static void bankAccountCompare(com.rbkmoney.questionary.BankAccount thriftBankAccount,
                                          com.rbkmoney.swag.questionary.model.BankAccount swagBankAccount) {
        if (swagBankAccount instanceof RussianBankAccount) {
            Assert.assertEquals(thriftBankAccount.getRussianBankAccount().getAccount(),
                    ((RussianBankAccount) swagBankAccount).getAccount());
            Assert.assertEquals(thriftBankAccount.getRussianBankAccount().getBankBik(),
                    ((RussianBankAccount) swagBankAccount).getBankBik());
            Assert.assertEquals(thriftBankAccount.getRussianBankAccount().getBankName(),
                    ((RussianBankAccount) swagBankAccount).getBankName());
            Assert.assertEquals(thriftBankAccount.getRussianBankAccount().getBankPostAccount(),
                    ((RussianBankAccount) swagBankAccount).getBankPostAccount());
        }
    }

    private static void founderInfoCompare(com.rbkmoney.questionary.FoundersInfo thriftFoundersInfo, FoundersInfo swagFoundersInfo) {
        headCompare(thriftFoundersInfo.getLegalOwner(), swagFoundersInfo.getLegalOwner());
        for (int i = 0; i < thriftFoundersInfo.getHeads().size(); i++) {
            com.rbkmoney.questionary.Head head = thriftFoundersInfo.getHeads().get(i);
            FounderHead swagHead = swagFoundersInfo.getHeads().get(i);
            headCompare(head, swagHead);
        }
        for (int i = 0; i < thriftFoundersInfo.getFounders().size(); i++) {
            com.rbkmoney.questionary.Founder founder = thriftFoundersInfo.getFounders().get(i);
            Founder swagFounder = swagFoundersInfo.getFounders().get(i);
            founderCompare(founder, swagFounder);
        }
    }

    private static void founderCompare(com.rbkmoney.questionary.Founder thriftFounder, Founder swagFounder) {
        if (swagFounder instanceof IndividualPerson) {
            Assert.assertEquals(thriftFounder.getIndividualPersonFounder().getInn(), ((IndividualPerson) swagFounder).getInn());
            Assert.assertEquals(thriftFounder.getIndividualPersonFounder().getFio(), ((IndividualPerson) swagFounder).getFio());
        } else if (swagFounder instanceof RussianLegalEntityFounder) {
            Assert.assertEquals(thriftFounder.getRussianLegalEntityFounder().getFullName(), ((RussianLegalEntityFounder) swagFounder).getFullName());
            Assert.assertEquals(thriftFounder.getRussianLegalEntityFounder().getInn(), ((RussianLegalEntityFounder) swagFounder).getInn());
            Assert.assertEquals(thriftFounder.getRussianLegalEntityFounder().getOgrn(), ((RussianLegalEntityFounder) swagFounder).getOgrn());
        } else if (swagFounder instanceof InternationalLegalEntityFounder) {
            Assert.assertEquals(thriftFounder.getInternationalLegalEntityFounder().getCountry(), ((InternationalLegalEntityFounder) swagFounder).getCountry());
            Assert.assertEquals(thriftFounder.getInternationalLegalEntityFounder().getFullName(), ((InternationalLegalEntityFounder) swagFounder).getFullName());
        }
    }

    private static void headCompare(com.rbkmoney.questionary.Head thriftLegalOwner, FounderHead swagLegalOwner) {
        Assert.assertEquals(thriftLegalOwner.getPosition(), swagLegalOwner.getPosition());
        individualPersonCompare(thriftLegalOwner.getIndividualPerson(), swagLegalOwner.getIndividualPerson());
    }

    private static void individualPersonCompare(com.rbkmoney.questionary.IndividualPerson thriftIndividualPerson, IndividualPerson swagIndividualPerson) {
        Assert.assertEquals(thriftIndividualPerson.getFio(), swagIndividualPerson.getFio());
        Assert.assertEquals(thriftIndividualPerson.getInn(), swagIndividualPerson.getInn());
    }

    private static void beneficialOwnerCompare(com.rbkmoney.questionary.BeneficialOwner thriftBeneficialOwner, BeneficialOwner swagBeneficialOwner) {
        Assert.assertEquals(thriftBeneficialOwner.getInn(), swagBeneficialOwner.getInn());
        identityDocumentCompare(thriftBeneficialOwner.getIdentityDocument(), swagBeneficialOwner.getIdentityDocument());
        migrationCardInfoCompare(thriftBeneficialOwner.getMigrationCardInfo(), swagBeneficialOwner.getMigrationCardInfo());
        Assert.assertEquals(thriftBeneficialOwner.getOwnershipPercentage(), swagBeneficialOwner.getOwnershipPercentage().byteValue());
        residencyApproveCompare(thriftBeneficialOwner.getResidenceApprove(), swagBeneficialOwner.getResidenceApprove());
        russianPrivateEntityCompare(thriftBeneficialOwner.getRussianPrivateEntity(), swagBeneficialOwner.getRussianPrivateEntity());
    }

    private static void russianPrivateEntityCompare(com.rbkmoney.questionary.RussianPrivateEntity thriftRussianPrivateEntity, RussianPrivateEntity swagRussianPrivateEntity) {
        Assert.assertEquals(thriftRussianPrivateEntity.getBirthDate(), swagRussianPrivateEntity.getBirthDate());
        Assert.assertEquals(thriftRussianPrivateEntity.getActualAddress(), swagRussianPrivateEntity.getActualAddress());
        Assert.assertEquals(thriftRussianPrivateEntity.getCitizenship(), swagRussianPrivateEntity.getCitizenship());
        contactInfoCompare(thriftRussianPrivateEntity.getContactInfo(), swagRussianPrivateEntity.getContactInfo());
        Assert.assertEquals(thriftRussianPrivateEntity.getBirthPlace(), swagRussianPrivateEntity.getBirthPlace());
        Assert.assertEquals(thriftRussianPrivateEntity.getResidenceAddress(), swagRussianPrivateEntity.getResidenceAddress());
        Assert.assertEquals(thriftRussianPrivateEntity.getFio(), swagRussianPrivateEntity.getFio());
    }

    private static void residencyApproveCompare(com.rbkmoney.questionary.ResidenceApprove thriftResidenceApprove, ResidenceApprove swagResidenceApprove) {
        Assert.assertEquals(thriftResidenceApprove.getBeginningDate(), swagResidenceApprove.getBeginningDate());
        Assert.assertEquals(thriftResidenceApprove.getExpirationDate(), swagResidenceApprove.getExpirationDate());
        Assert.assertEquals(thriftResidenceApprove.getName(), swagResidenceApprove.getName());
        Assert.assertEquals(thriftResidenceApprove.getNumber(), swagResidenceApprove.getNumber());
        Assert.assertEquals(thriftResidenceApprove.getSeries(), swagResidenceApprove.getSeries());
    }

    private static void residencyInfoCompare(com.rbkmoney.questionary.ResidencyInfo thriftResidencyInfo, ResidencyInfo swagResidencyInfo) {
        if (swagResidencyInfo instanceof IndividualResidencyInfo) {
            Assert.assertEquals(thriftResidencyInfo.getIndividualResidencyInfo().isUsaTaxResident(),
                    ((IndividualResidencyInfo) swagResidencyInfo).isUsaTaxResident());
            Assert.assertEquals(thriftResidencyInfo.getIndividualResidencyInfo().isExceptUsaTaxResident(),
                    ((IndividualResidencyInfo) swagResidencyInfo).isExceptUsaTaxResident());
        } else if (swagResidencyInfo instanceof LegalResidencyInfo) {
            Assert.assertEquals(thriftResidencyInfo.getLegalResidencyInfo().isFatca(),
                    ((LegalResidencyInfo) swagResidencyInfo).isFatca());
            Assert.assertEquals(thriftResidencyInfo.getLegalResidencyInfo().isOwnerResident(),
                    ((LegalResidencyInfo) swagResidencyInfo).isOwnerResident());
            Assert.assertEquals(thriftResidencyInfo.getLegalResidencyInfo().isTaxResident(),
                    ((LegalResidencyInfo) swagResidencyInfo).isTaxResident());
        }
    }

    private static void registrationInfoCompare(com.rbkmoney.questionary.RegistrationInfo thriftRegistrationInfo, RegistrationInfo swagRegistrationInfo) {
        if (swagRegistrationInfo instanceof IndividualRegistrationInfo) {
            Assert.assertEquals(thriftRegistrationInfo.getIndividualRegistrationInfo().getOgrnip(),
                    ((IndividualRegistrationInfo) swagRegistrationInfo).getOgrnip());
            Assert.assertEquals(thriftRegistrationInfo.getIndividualRegistrationInfo().getRegistrationDate(),
                    ((IndividualRegistrationInfo) swagRegistrationInfo).getRegistrationDate());
            Assert.assertEquals(thriftRegistrationInfo.getIndividualRegistrationInfo().getRegistrationPlace(),
                    ((IndividualRegistrationInfo) swagRegistrationInfo).getRegistrationPlace());
        }
    }

    private static void principalActivityCompare(com.rbkmoney.questionary.Activity thriftPrincipalActivity, Activity swagPrincipalActivity) {
        Assert.assertEquals(thriftPrincipalActivity.getCode(), swagPrincipalActivity.getCode());
        Assert.assertEquals(thriftPrincipalActivity.getDescription(), swagPrincipalActivity.getDescription());
    }

    private static void migrationCardInfoCompare(com.rbkmoney.questionary.MigrationCardInfo thriftMigrationCardInfo, MigrationCardInfo swagMigrationCardInfo) {
        Assert.assertEquals(thriftMigrationCardInfo.getBeginningDate(), swagMigrationCardInfo.getBeginningDate());
        Assert.assertEquals(thriftMigrationCardInfo.getCardNumber(), swagMigrationCardInfo.getCardNumber());
        Assert.assertEquals(thriftMigrationCardInfo.getExpirationDate(), swagMigrationCardInfo.getExpirationDate());
    }

    private static void licenseInfoCompare(com.rbkmoney.questionary.LicenseInfo thriftLicenseInfo, LicenseInfo swagLicenseInfo) {
        Assert.assertEquals(thriftLicenseInfo.getEffectiveDate(), swagLicenseInfo.getEffectiveDate());
        Assert.assertEquals(thriftLicenseInfo.getExpirationDate(), swagLicenseInfo.getExpirationDate());
        Assert.assertEquals(thriftLicenseInfo.getIssueDate(), swagLicenseInfo.getIssueDate());
        Assert.assertEquals(thriftLicenseInfo.getIssuerName(), swagLicenseInfo.getIssuerName());
        Assert.assertEquals(thriftLicenseInfo.getLicensedActivity(), swagLicenseInfo.getLicensedActivity());
        Assert.assertEquals(thriftLicenseInfo.getOfficialNum(), swagLicenseInfo.getOfficialNum());
    }

    private static void individualPersonCategoriesCompare(com.rbkmoney.questionary.IndividualPersonCategories thriftIndividualPersonCategories,
                                                          IndividualPersonCategories swagIndividualPersonCategories) {
        Assert.assertEquals(thriftIndividualPersonCategories.isBehalfOfForeign(), swagIndividualPersonCategories.isBehalfOfForeign());
        Assert.assertEquals(thriftIndividualPersonCategories.isBeneficialOwner(), swagIndividualPersonCategories.isBeneficialOwner());
        Assert.assertEquals(thriftIndividualPersonCategories.isForeignPublicPerson(), swagIndividualPersonCategories.isForeignPublicPerson());
        Assert.assertEquals(thriftIndividualPersonCategories.isForeignRelativePerson(), swagIndividualPersonCategories.isForeignRelativePerson());
        Assert.assertEquals(thriftIndividualPersonCategories.isHasRepresentative(), swagIndividualPersonCategories.isHasRepresentative());
        Assert.assertEquals(thriftIndividualPersonCategories.isWorldwideOrgPublicPerson(), swagIndividualPersonCategories.isWorldwideOrgPublicPerson());
    }

    private static void identityDocumentCompare(com.rbkmoney.questionary.IdentityDocument thriftIdentityDocument, IdentityDocument swagIdentityDocument) {
        if (swagIdentityDocument instanceof RussianDomesticPassport) {
            Assert.assertEquals(thriftIdentityDocument.getRussianDomesticPassword().getIssuedAt(),
                    ((RussianDomesticPassport) swagIdentityDocument).getIssuedAt());
            Assert.assertEquals(thriftIdentityDocument.getRussianDomesticPassword().getIssuer(),
                    ((RussianDomesticPassport) swagIdentityDocument).getIssuer());
            Assert.assertEquals(thriftIdentityDocument.getRussianDomesticPassword().getIssuerCode(),
                    ((RussianDomesticPassport) swagIdentityDocument).getIssuerCode());
            Assert.assertEquals(thriftIdentityDocument.getRussianDomesticPassword().getSeriesNumber(),
                    ((RussianDomesticPassport) swagIdentityDocument).getSeriesNumber());
        }
    }

}
