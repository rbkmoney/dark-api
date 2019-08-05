package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.BeneficialOwner;
import com.rbkmoney.swag.questionary.model.IdentityDocument;
import com.rbkmoney.swag.questionary.model.MigrationCardInfo;
import com.rbkmoney.swag.questionary.model.ResidenceApprove;
import com.rbkmoney.swag.questionary.model.RussianPrivateEntity;
import org.springframework.stereotype.Component;

@Component
public class BeneficialOwnerConverter implements
        ThriftConverter<BeneficialOwner, com.rbkmoney.swag.questionary.model.BeneficialOwner>,
        SwagConverter<com.rbkmoney.swag.questionary.model.BeneficialOwner, BeneficialOwner> {

    @Override
    public com.rbkmoney.swag.questionary.model.BeneficialOwner toSwag(BeneficialOwner value, SwagConverterContext ctx) {
        var beneficialOwner = new com.rbkmoney.swag.questionary.model.BeneficialOwner()
                .inn(value.getInn())
                .pdlCategory(value.isPdlCategory())
                .ownershipPercentage((int) value.getOwnershipPercentage());
        if (value.isSetRussianPrivateEntity()) {
            beneficialOwner.setRussianPrivateEntity(ctx.convert(value.getRussianPrivateEntity(), RussianPrivateEntity.class));
        }
        if (value.isSetIdentityDocument()) {
            beneficialOwner.setIdentityDocument(ctx.convert(value.getIdentityDocument(), IdentityDocument.class));
        }
        if (value.isSetMigrationCardInfo()) {
            beneficialOwner.setMigrationCardInfo(ctx.convert(value.getMigrationCardInfo(), MigrationCardInfo.class));
        }
        if (value.isSetResidenceApprove()) {
            beneficialOwner.setResidenceApprove(ctx.convert(value.getResidenceApprove(), ResidenceApprove.class));
        }
        return beneficialOwner;
    }

    @Override
    public BeneficialOwner toThrift(com.rbkmoney.swag.questionary.model.BeneficialOwner value, ThriftConverterContext ctx) {
        BeneficialOwner beneficialOwner = new BeneficialOwner();
        beneficialOwner.setInn(value.getInn());
        beneficialOwner.setPdlCategory(value.isPdlCategory());
        beneficialOwner.setOwnershipPercentage(value.getOwnershipPercentage().byteValue());
        if (value.getRussianPrivateEntity() != null) {
            beneficialOwner.setRussianPrivateEntity(ctx.convert(value.getRussianPrivateEntity(), com.rbkmoney.questionary.RussianPrivateEntity.class));
        }
        if (value.getIdentityDocument() != null) {
            beneficialOwner.setIdentityDocument(ctx.convert(value.getIdentityDocument(), com.rbkmoney.questionary.IdentityDocument.class));
        }
        if (value.getMigrationCardInfo() != null) {
            beneficialOwner.setMigrationCardInfo(ctx.convert(value.getMigrationCardInfo(), com.rbkmoney.questionary.MigrationCardInfo.class));
        }
        if (value.getResidenceApprove() != null) {
            beneficialOwner.setResidenceApprove(ctx.convert(value.getResidenceApprove(), com.rbkmoney.questionary.ResidenceApprove.class));
        }
        return beneficialOwner;
    }

}
