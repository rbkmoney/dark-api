package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.dark.api.util.ConverterUtils;
import com.rbkmoney.swag.questionary.model.*;
import org.springframework.stereotype.Component;

@Component
public class LegalOwnerInfoConverter implements
        ThriftConverter<com.rbkmoney.questionary.LegalOwnerInfo, LegalOwnerInfo>,
        SwagConverter<LegalOwnerInfo, com.rbkmoney.questionary.LegalOwnerInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.LegalOwnerInfo toSwag(com.rbkmoney.questionary.LegalOwnerInfo value,
                                                                     SwagConverterContext ctx) {
        LegalOwnerInfo legalOwnerInfo = new LegalOwnerInfo()
                .inn(value.getInn())
                .pdlCategory(value.isPdlCategory())
                .pdlRelationDegree(value.getPdlRelationDegree())
                .snils(value.getSnils())
                .termOfOffice(value.getTermOfOffice())
                .headPosition(value.getHeadPosition());
        if (value.isSetIdentityDocument()) {
            legalOwnerInfo.setIdentityDocument(ctx.convert(value.getIdentityDocument(), IdentityDocument.class));
        }
        if (value.isSetResidenceApprove()) {
            legalOwnerInfo.setResidenceApprove(ctx.convert(value.getResidenceApprove(), ResidenceApprove.class));
        }
        if (value.isSetMigrationCardInfo()) {
            legalOwnerInfo.setMigrationCardInfo(ctx.convert(value.getMigrationCardInfo(), MigrationCardInfo.class));
        }
        if (value.isSetRussianPrivateEntity()) {
            legalOwnerInfo.setRussianPrivateEntity(ctx.convert(value.getRussianPrivateEntity(), RussianPrivateEntity.class));
        }
        if (value.isSetAuthorityConfirmingDocument()) {
            legalOwnerInfo.setAuthorityConfirmingDocument(
                    ctx.convert(value.getAuthorityConfirmingDocument(), AuthorityConfirmingDocument.class));
        }

        return legalOwnerInfo;
    }

    @Override
    public com.rbkmoney.questionary.LegalOwnerInfo toThrift(LegalOwnerInfo value, ThriftConverterContext ctx) {
        var legalOwnerInfo = new com.rbkmoney.questionary.LegalOwnerInfo()
                .setInn(value.getInn())
                .setPdlCategory(ConverterUtils.safeSetValue(value.isPdlCategory()))
                .setPdlRelationDegree(value.getPdlRelationDegree())
                .setSnils(value.getSnils())
                .setTermOfOffice(value.getTermOfOffice())
                .setHeadPosition(value.getHeadPosition());
        if (value.getIdentityDocument() != null) {
            legalOwnerInfo.setIdentityDocument(
                    ctx.convert(value.getIdentityDocument(), com.rbkmoney.questionary.IdentityDocument.class));
        }
        if (value.getResidenceApprove() != null) {
            legalOwnerInfo.setResidenceApprove(
                    ctx.convert(value.getResidenceApprove(), com.rbkmoney.questionary.ResidenceApprove.class));
        }
        if (value.getMigrationCardInfo() != null) {
            legalOwnerInfo.setMigrationCardInfo(
                    ctx.convert(value.getMigrationCardInfo(), com.rbkmoney.questionary.MigrationCardInfo.class));
        }
        if (value.getRussianPrivateEntity() != null) {
            legalOwnerInfo.setRussianPrivateEntity(
                    ctx.convert(value.getRussianPrivateEntity(), com.rbkmoney.questionary.RussianPrivateEntity.class));
        }
        if (value.getAuthorityConfirmingDocument() != null) {
            legalOwnerInfo.setAuthorityConfirmingDocument(
                    ctx.convert(value.getAuthorityConfirmingDocument(), com.rbkmoney.questionary.AuthorityConfirmingDocument.class));
        }

        return legalOwnerInfo;

    }

}
