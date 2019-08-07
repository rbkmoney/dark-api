package com.rbkmoney.dark.api.utils;

import com.rbkmoney.swag.questionary.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionaryCompareUtil {

    public static void contractorCompare(com.rbkmoney.questionary.Contractor thriftContractor, Contractor swagContractor) {
        if (swagContractor instanceof IndividualEntityContractor) {
            com.rbkmoney.questionary.RussianIndividualEntity thriftRussianIndividualEntity = thriftContractor.getIndividualEntity().getRussianIndividualEntity();
            IndividualEntity swagIndividualEntity = ((IndividualEntityContractor) swagContractor).getIndividualEntity();
            Assert.assertEquals(thriftRussianIndividualEntity.getInn(), ((RussianIndividualEntity) swagIndividualEntity).getInn());
            Assert.assertEquals(thriftRussianIndividualEntity.getInn(), ((RussianIndividualEntity) swagIndividualEntity).getInn());
            identityDocumentCompare(thriftRussianIndividualEntity.getIdentityDocument(),
                    ((RussianIndividualEntity) swagIndividualEntity).getIdentityDocument());
            individualPersonCategoriesCompare(thriftRussianIndividualEntity.getIndividualPersonCategories(),
                    ((RussianIndividualEntity) swagIndividualEntity).getIndividualPersonCategories());
            licenseInfoCompare(thriftRussianIndividualEntity.getLicenseInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getLiceneInfo());
            migrationCardInfoCompare(thriftRussianIndividualEntity.getMigrationCardInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getMigrationCardInfo());
            principalActivityCompare(thriftRussianIndividualEntity.getPrincipalActivity(),
                    ((RussianIndividualEntity) swagIndividualEntity).getPrincipalActivity());
            Assert.assertThat(thriftRussianIndividualEntity.getPropertyInfo(),
                    CoreMatchers.is(((RussianIndividualEntity) swagIndividualEntity).getPropertyInfo()));
            registrationInfoCompare(thriftRussianIndividualEntity.getRegistrationInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getRegistrationInfo());
            residencyInfoCompare(thriftRussianIndividualEntity.getResidencyInfo(),
                    ((RussianIndividualEntity) swagIndividualEntity).getResidencyInfo());
            residencyApproveCompare(thriftRussianIndividualEntity.getResidenceApprove(),
                    ((RussianIndividualEntity) swagIndividualEntity).getResidenceApprove());
            russianPrivateEntityCompare(thriftRussianIndividualEntity.getRussianPrivateEntity(),
                    ((RussianIndividualEntity) swagIndividualEntity).getRussianPrivateEntity());
        } else if (swagContractor instanceof LegalEntityContractor) {
            com.rbkmoney.questionary.LegalEntity legalEntity = thriftContractor.getLegalEntity();
            LegalEntity swagLegalEntity = ((LegalEntityContractor) swagContractor).getLegalEntity();
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getInn(), ((RussianLegalEntity) swagLegalEntity).getInn());
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getAdditionalSpace(), ((RussianLegalEntity) swagLegalEntity).getAdditionalSpace());
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getForeignName(), ((RussianLegalEntity) swagLegalEntity).getForeignName());
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getLegalForm(), ((RussianLegalEntity) swagLegalEntity).getLegalForm());
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getName(), ((RussianLegalEntity) swagLegalEntity).getName());
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getOkatoCode(), ((RussianLegalEntity) swagLegalEntity).getOkatoCode());
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getOkpoCode(), ((RussianLegalEntity) swagLegalEntity).getOkpoCode());
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getPostalAddress(), ((RussianLegalEntity) swagLegalEntity).getPostalAddress());
            Assert.assertEquals(legalEntity.getRussianLegalEntity().getPropertyInfo(), ((RussianLegalEntity) swagLegalEntity).getPropertyInfo());
            residencyInfoCompare(legalEntity.getRussianLegalEntity().getResidencyInfo(), ((RussianLegalEntity) swagLegalEntity).getResidencyInfo());
            registrationInfoCompare(legalEntity.getRussianLegalEntity().getRegistrationInfo(), ((RussianLegalEntity) swagLegalEntity).getRegistrationInfo());
            principalActivityCompare(legalEntity.getRussianLegalEntity().getPrincipalActivity(), ((RussianLegalEntity) swagLegalEntity).getPrincipalActivity());
            for (int i = 0; i < legalEntity.getRussianLegalEntity().getBeneficialOwners().size(); i++) {
                com.rbkmoney.questionary.BeneficialOwner beneficialOwner = legalEntity.getRussianLegalEntity().getBeneficialOwners().get(i);
                BeneficialOwner swagBeneficialOwner = ((RussianLegalEntity) swagLegalEntity).getBeneficialOwner().get(i);
                beneficialOwnerCompare(beneficialOwner, swagBeneficialOwner);
            }
            founderInfoCompare(legalEntity.getRussianLegalEntity().getFoundersInfo(), ((RussianLegalEntity) swagLegalEntity).getFoundersInfo());
        }
    }

    public static void contactInfoCompare(com.rbkmoney.questionary.ContactInfo thriftContactInfo, ContactInfo swagContactInfo) {
        Assert.assertEquals(thriftContactInfo.getEmail(), swagContactInfo.getEmail());
        Assert.assertEquals(thriftContactInfo.getPhoneNumber(), swagContactInfo.getPhoneNumber());
    }

    public static void bankAccountCompare(com.rbkmoney.questionary.BankAccount thriftBankAccount, com.rbkmoney.swag.questionary.model.BankAccount swagBankAccount) {
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
            personAnthroponymCompare(thriftFounder.getIndividualPersonFounder().getFio(), ((IndividualPerson) swagFounder).getFio());
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
        personAnthroponymCompare(thriftIndividualPerson.getFio(), swagIndividualPerson.getFio());
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
        personAnthroponymCompare(thriftRussianPrivateEntity.getFio(), swagRussianPrivateEntity.getPersonAnthroponym());
        Assert.assertEquals(thriftRussianPrivateEntity.getBirthPlace(), swagRussianPrivateEntity.getBirthPlace());
        Assert.assertEquals(thriftRussianPrivateEntity.getResidenceAddress(), swagRussianPrivateEntity.getResidenceAddress());
    }

    private static void personAnthroponymCompare(com.rbkmoney.questionary.PersonAnthroponym thriftPersonAnthroponym, PersonAnthroponym swagPersonAnthroponym) {
        Assert.assertEquals(thriftPersonAnthroponym.getFirstName(), swagPersonAnthroponym.getFirstName());
        Assert.assertEquals(thriftPersonAnthroponym.getMiddleName(), swagPersonAnthroponym.getMiddleName());
        Assert.assertEquals(thriftPersonAnthroponym.getSecondName(), swagPersonAnthroponym.getSecondName());
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
            Assert.assertEquals(thriftResidencyInfo.getIndividualResidencyInfo().isTaxResident(),
                    ((IndividualResidencyInfo) swagResidencyInfo).isTaxResident());
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
                    ((IndividualRegistrationInfo) swagRegistrationInfo).getRegistrationData());
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
            Assert.assertEquals(thriftIdentityDocument.getRussianDomesticPassword().getNumber(),
                    ((RussianDomesticPassport) swagIdentityDocument).getNumber());
            Assert.assertEquals(thriftIdentityDocument.getRussianDomesticPassword().getIssuerCode(),
                    ((RussianDomesticPassport) swagIdentityDocument).getIssuerCode());
            Assert.assertEquals(thriftIdentityDocument.getRussianDomesticPassword().getSeries(),
                    ((RussianDomesticPassport) swagIdentityDocument).getSeries());
        }
    }

}
