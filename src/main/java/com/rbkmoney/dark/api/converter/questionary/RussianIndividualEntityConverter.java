package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.RussianIndividualEntity;
import com.rbkmoney.swag.questionary.model.*;
import org.springframework.stereotype.Component;

@Component
public class RussianIndividualEntityConverter implements
        ThriftConverter<RussianIndividualEntity, com.rbkmoney.swag.questionary.model.RussianIndividualEntity>,
        SwagConverter<com.rbkmoney.swag.questionary.model.RussianIndividualEntity, RussianIndividualEntity> {

    @Override
    public com.rbkmoney.swag.questionary.model.RussianIndividualEntity toSwag(RussianIndividualEntity value, SwagConverterContext ctx) {
        var russianIndividualEntity = new com.rbkmoney.swag.questionary.model.RussianIndividualEntity();
        russianIndividualEntity.setInn(value.getInn());
        russianIndividualEntity.setPropertyInfo(value.getPropertyInfo());
        if (value.isSetRegistrationInfo()) {
            russianIndividualEntity.setRegistrationInfo(ctx.convert(value.getRegistrationInfo(), RegistrationInfo.class));
        }
        if (value.isSetIdentityDocument()) {
            russianIndividualEntity.setIdentityDocument(ctx.convert(value.getIdentityDocument(), IdentityDocument.class));
        }
        if (value.isSetLicenseInfo()) {
            russianIndividualEntity.setLiceneInfo(ctx.convert(value.getLicenseInfo(), LicenseInfo.class));
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

        return russianIndividualEntity;
    }

    @Override
    public RussianIndividualEntity toThrift(com.rbkmoney.swag.questionary.model.RussianIndividualEntity value, ThriftConverterContext ctx) {
        RussianIndividualEntity russianIndividualEntity = new RussianIndividualEntity()
                .setInn(value.getInn())
                .setPropertyInfo(value.getPropertyInfo());
        if (value.getRegistrationInfo() != null) {
            russianIndividualEntity.setRegistrationInfo(ctx.convert(value.getRegistrationInfo(), com.rbkmoney.questionary.RegistrationInfo.class));
        }
        if (value.getIdentityDocument() != null) {
            russianIndividualEntity.setIdentityDocument(ctx.convert(value.getIdentityDocument(), com.rbkmoney.questionary.IdentityDocument.class));
        }
        if (value.getLiceneInfo() != null) {
            russianIndividualEntity.setLicenseInfo(ctx.convert(value.getLiceneInfo(), com.rbkmoney.questionary.LicenseInfo.class));
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

        return russianIndividualEntity;
    }

}
