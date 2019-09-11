package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.RussianIndividualEntity;
import com.rbkmoney.swag.questionary.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RussianIndividualEntityConverter implements
        ThriftConverter<RussianIndividualEntity, com.rbkmoney.swag.questionary.model.RussianIndividualEntity>,
        SwagConverter<com.rbkmoney.swag.questionary.model.RussianIndividualEntity, RussianIndividualEntity> {

    @Override
    public com.rbkmoney.swag.questionary.model.RussianIndividualEntity toSwag(RussianIndividualEntity value, SwagConverterContext ctx) {
        var russianIndividualEntity = new com.rbkmoney.swag.questionary.model.RussianIndividualEntity()
                .inn(value.getInn())
                .snils(value.getSnils());
        if (value.isSetRegistrationInfo()) {
            russianIndividualEntity.setRegistrationInfo(ctx.convert(value.getRegistrationInfo(), RegistrationInfo.class));
        }
        if (value.isSetIdentityDocument()) {
            russianIndividualEntity.setIdentityDocument(ctx.convert(value.getIdentityDocument(), IdentityDocument.class));
        }
        if (value.isSetLicenseInfo()) {
            russianIndividualEntity.setLicenseInfo(ctx.convert(value.getLicenseInfo(), LicenseInfo.class));
        }
        if (value.isSetMigrationCardInfo()) {
            russianIndividualEntity.setMigrationCardInfo(ctx.convert(value.getMigrationCardInfo(), MigrationCardInfo.class));
        }
        if (value.isSetResidenceApprove()) {
            russianIndividualEntity.setResidenceApprove(ctx.convert(value.getResidenceApprove(), ResidenceApprove.class));
        }
        if (value.isSetIndividualPersonCategories()) {
            russianIndividualEntity.setIndividualPersonCategories(ctx.convert(value.getIndividualPersonCategories(), IndividualPersonCategories.class));
        }
        if (value.isSetResidencyInfo()) {
            russianIndividualEntity.setResidencyInfo(ctx.convert(value.getResidencyInfo(), ResidencyInfo.class));
        }
        if (value.isSetRussianPrivateEntity()) {
            russianIndividualEntity.setRussianPrivateEntity(ctx.convert(value.getRussianPrivateEntity(), RussianPrivateEntity.class));
        }
        if (value.isSetPrincipalActivity()) {
            russianIndividualEntity.setPrincipalActivity(ctx.convert(value.getPrincipalActivity(), Activity.class));
        }
        if (value.isSetAdditionalInfo()) {
            russianIndividualEntity.setAdditionalInfo(ctx.convert(value.getAdditionalInfo(), AdditionalInfo.class));
        }
        if (value.isSetPropertyInfoDocumentType()) {
            russianIndividualEntity.setPropertyInfoDocumentType(
                    ctx.convert(value.getPropertyInfoDocumentType(), PropertyInfoDocumentType.class));
        }
        if (value.isSetBeneficialOwners()) {
            List<BeneficialOwner> beneficialOwnerList = value.getBeneficialOwners().stream()
                    .map(beneficialOwner -> ctx.convert(beneficialOwner, BeneficialOwner.class))
                    .collect(Collectors.toList());
            russianIndividualEntity.setBeneficialOwners(beneficialOwnerList);
        }

        return russianIndividualEntity;
    }

    @Override
    public RussianIndividualEntity toThrift(com.rbkmoney.swag.questionary.model.RussianIndividualEntity value, ThriftConverterContext ctx) {
        RussianIndividualEntity russianIndividualEntity = new RussianIndividualEntity()
                .setInn(value.getInn());
        if (value.getRegistrationInfo() != null) {
            russianIndividualEntity.setRegistrationInfo(ctx.convert(value.getRegistrationInfo(), com.rbkmoney.questionary.RegistrationInfo.class));
        }
        if (value.getIdentityDocument() != null) {
            russianIndividualEntity.setIdentityDocument(ctx.convert(value.getIdentityDocument(), com.rbkmoney.questionary.IdentityDocument.class));
        }
        if (value.getLicenseInfo() != null) {
            russianIndividualEntity.setLicenseInfo(ctx.convert(value.getLicenseInfo(), com.rbkmoney.questionary.LicenseInfo.class));
        }
        if (value.getMigrationCardInfo() != null) {
            russianIndividualEntity.setMigrationCardInfo(ctx.convert(value.getMigrationCardInfo(), com.rbkmoney.questionary.MigrationCardInfo.class));
        }
        if (value.getResidenceApprove() != null) {
            russianIndividualEntity.setResidenceApprove(ctx.convert(value.getResidenceApprove(), com.rbkmoney.questionary.ResidenceApprove.class));
        }
        if (value.getIndividualPersonCategories() != null) {
            russianIndividualEntity.setIndividualPersonCategories(ctx.convert(value.getIndividualPersonCategories(), com.rbkmoney.questionary.IndividualPersonCategories.class));
        }
        if (value.getResidencyInfo() != null) {
            russianIndividualEntity.setResidencyInfo(ctx.convert(value.getResidencyInfo(), com.rbkmoney.questionary.ResidencyInfo.class));
        }
        if (value.getRussianPrivateEntity() != null) {
            russianIndividualEntity.setRussianPrivateEntity(ctx.convert(value.getRussianPrivateEntity(), com.rbkmoney.questionary.RussianPrivateEntity.class));
        }
        if (value.getPrincipalActivity() != null) {
            russianIndividualEntity.setPrincipalActivity(ctx.convert(value.getPrincipalActivity(), com.rbkmoney.questionary.Activity.class));
        }
        if (value.getAdditionalInfo() != null) {
            russianIndividualEntity.setAdditionalInfo(ctx.convert(value.getAdditionalInfo(), com.rbkmoney.questionary.AdditionalInfo.class));
        }
        if (value.getPropertyInfoDocumentType() != null) {
            russianIndividualEntity.setPropertyInfoDocumentType(
                    ctx.convert(value.getPropertyInfoDocumentType(), com.rbkmoney.questionary.PropertyInfoDocumentType.class));
        }
        if (value.getBeneficialOwners() != null) {
            List<com.rbkmoney.questionary.BeneficialOwner> beneficialOwnerList = value.getBeneficialOwners().stream()
                    .map(beneficialOwner -> ctx.convert(beneficialOwner, com.rbkmoney.questionary.BeneficialOwner.class))
                    .collect(Collectors.toList());
            russianIndividualEntity.setBeneficialOwners(beneficialOwnerList);
        }

        return russianIndividualEntity;
    }

}
