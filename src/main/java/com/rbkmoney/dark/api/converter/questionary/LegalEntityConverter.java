package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.dark.api.util.ConverterUtils;
import com.rbkmoney.questionary.LegalEntity;
import com.rbkmoney.swag.questionary.model.*;
import com.rbkmoney.swag.questionary.model.LegalEntity.LegalEntityTypeEnum;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.rbkmoney.swag.questionary.model.LegalEntity.LegalEntityTypeEnum.INTERNATIONALLEGALENTITY;
import static com.rbkmoney.swag.questionary.model.LegalEntity.LegalEntityTypeEnum.RUSSIANLEGALENTITY;

@Component
public class LegalEntityConverter implements
        ThriftConverter<LegalEntity, com.rbkmoney.swag.questionary.model.LegalEntity>,
        SwagConverter<com.rbkmoney.swag.questionary.model.LegalEntity, LegalEntity> {

    @Override
    public com.rbkmoney.swag.questionary.model.LegalEntity toSwag(LegalEntity value, SwagConverterContext ctx) {
        if (value.isSetRussianLegalEntity()) {
            RussianLegalEntity russianLegalEntity = new RussianLegalEntity()
                    .additionalSpace(value.getRussianLegalEntity().getAdditionalSpace())
                    .foreignName(value.getRussianLegalEntity().getForeignName())
                    .inn(value.getRussianLegalEntity().getInn())
                    .legalForm(value.getRussianLegalEntity().getLegalForm())
                    .name(value.getRussianLegalEntity().getName())
                    .okatoCode(value.getRussianLegalEntity().getOkatoCode())
                    .okpoCode(value.getRussianLegalEntity().getOkpoCode())
                    .postalAddress(value.getRussianLegalEntity().getPostalAddress())
                    .hasBeneficialOwners(value.getRussianLegalEntity().isHasBeneficialOwners());
            if (value.getRussianLegalEntity().isSetFoundersInfo()) {
                russianLegalEntity.setFoundersInfo(
                        ctx.convert(value.getRussianLegalEntity().getFoundersInfo(), FoundersInfo.class));
            }

            if (value.getRussianLegalEntity().isSetLicenseInfo()) {
                russianLegalEntity
                        .setLicenseInfo(ctx.convert(value.getRussianLegalEntity().getLicenseInfo(), LicenseInfo.class));
            }

            if (value.getRussianLegalEntity().isSetLegalOwnerInfo()) {
                russianLegalEntity.setLegalOwnerInfo(
                        ctx.convert(value.getRussianLegalEntity().getLegalOwnerInfo(), LegalOwnerInfo.class));
            }

            if (value.getRussianLegalEntity().isSetBeneficialOwners()) {
                List<BeneficialOwner> beneficialOwnerList = value.getRussianLegalEntity().getBeneficialOwners().stream()
                        .map(beneficialOwner -> ctx.convert(beneficialOwner, BeneficialOwner.class))
                        .collect(Collectors.toList());
                russianLegalEntity.setBeneficialOwner(beneficialOwnerList);
            }

            if (value.getRussianLegalEntity().isSetPrincipalActivity()) {
                russianLegalEntity.setPrincipalActivity(
                        ctx.convert(value.getRussianLegalEntity().getPrincipalActivity(), Activity.class));
            }

            if (value.getRussianLegalEntity().isSetRegistrationInfo()) {
                russianLegalEntity.setRegistrationInfo(
                        ctx.convert(value.getRussianLegalEntity().getRegistrationInfo(), RegistrationInfo.class));
            }

            if (value.getRussianLegalEntity().isSetResidencyInfo()) {
                russianLegalEntity.setResidencyInfo(
                        ctx.convert(value.getRussianLegalEntity().getResidencyInfo(), ResidencyInfo.class));
            }

            if (value.getRussianLegalEntity().isSetAdditionalInfo()) {
                russianLegalEntity.setAdditionalInfo(
                        ctx.convert(value.getRussianLegalEntity().getAdditionalInfo(), AdditionalInfo.class));
            }

            if (value.getRussianLegalEntity().isSetPropertyInfoDocumentType()) {
                russianLegalEntity.setPropertyInfoDocumentType(
                        ctx.convert(value.getRussianLegalEntity().getPropertyInfoDocumentType(),
                                PropertyInfoDocumentType.class));
            }
            russianLegalEntity.setLegalEntityType(RUSSIANLEGALENTITY);
            return russianLegalEntity;
        } else if (value.isSetInternationalLegalEntity()) {
            var thriftInternationalLegalEntity = value.getInternationalLegalEntity();
            return new InternationalLegalEntity()
                    .legalName(thriftInternationalLegalEntity.getLegalName())
                    .actualAddress(thriftInternationalLegalEntity.getActualAddress())
                    .registeredAddress(thriftInternationalLegalEntity.getRegisteredAddress())
                    .registeredNumber(thriftInternationalLegalEntity.getRegisteredNumber())
                    .tradingName(thriftInternationalLegalEntity.getTradingName())
                    .legalEntityType(INTERNATIONALLEGALENTITY);
        }

        throw new IllegalArgumentException("Unknown legalEntity type: " + value.getClass().getName());
    }

    @Override
    public LegalEntity toThrift(com.rbkmoney.swag.questionary.model.LegalEntity value, ThriftConverterContext ctx) {
        if (value.getLegalEntityType() == LegalEntityTypeEnum.RUSSIANLEGALENTITY) {
            var russianLegalEntity = new com.rbkmoney.questionary.RussianLegalEntity()
                    .setAdditionalSpace(((RussianLegalEntity) value).getAdditionalSpace())
                    .setForeignName(((RussianLegalEntity) value).getForeignName())
                    .setInn(((RussianLegalEntity) value).getInn())
                    .setLegalForm(((RussianLegalEntity) value).getLegalForm())
                    .setName(((RussianLegalEntity) value).getName())
                    .setOkatoCode(((RussianLegalEntity) value).getOkatoCode())
                    .setOkpoCode(((RussianLegalEntity) value).getOkpoCode())
                    .setPostalAddress(((RussianLegalEntity) value).getPostalAddress())
                    .setHasBeneficialOwners(
                            ConverterUtils.safeSetValue(((RussianLegalEntity) value).isHasBeneficialOwners()));
            if (((RussianLegalEntity) value).getFoundersInfo() != null) {
                russianLegalEntity.setFoundersInfo(ctx.convert(((RussianLegalEntity) value).getFoundersInfo(),
                        com.rbkmoney.questionary.FoundersInfo.class));
            }
            if (((RussianLegalEntity) value).getLicenseInfo() != null) {
                russianLegalEntity.setLicenseInfo(ctx.convert(((RussianLegalEntity) value).getLicenseInfo(),
                        com.rbkmoney.questionary.LicenseInfo.class));
            }
            if (((RussianLegalEntity) value).getLegalOwnerInfo() != null) {
                russianLegalEntity.setLegalOwnerInfo(ctx.convert(((RussianLegalEntity) value).getLegalOwnerInfo(),
                        com.rbkmoney.questionary.LegalOwnerInfo.class));
            }
            if (((RussianLegalEntity) value).getBeneficialOwner() != null) {
                List<com.rbkmoney.questionary.BeneficialOwner> beneficialOwnerList =
                        ((RussianLegalEntity) value).getBeneficialOwner().stream()
                                .map(beneficialOwner -> ctx
                                        .convert(beneficialOwner, com.rbkmoney.questionary.BeneficialOwner.class))
                                .collect(Collectors.toList());
                russianLegalEntity.setBeneficialOwners(beneficialOwnerList);
            }
            if (((RussianLegalEntity) value).getPrincipalActivity() != null) {
                russianLegalEntity.setPrincipalActivity(ctx.convert(((RussianLegalEntity) value).getPrincipalActivity(),
                        com.rbkmoney.questionary.Activity.class));
            }
            if (((RussianLegalEntity) value).getRegistrationInfo() != null) {
                russianLegalEntity.setRegistrationInfo(ctx.convert(((RussianLegalEntity) value).getRegistrationInfo(),
                        com.rbkmoney.questionary.RegistrationInfo.class));
            }
            if (((RussianLegalEntity) value).getResidencyInfo() != null) {
                russianLegalEntity.setResidencyInfo(ctx.convert(((RussianLegalEntity) value).getResidencyInfo(),
                        com.rbkmoney.questionary.ResidencyInfo.class));
            }
            if (((RussianLegalEntity) value).getAdditionalInfo() != null) {
                russianLegalEntity.setAdditionalInfo(ctx.convert(((RussianLegalEntity) value).getAdditionalInfo(),
                        com.rbkmoney.questionary.AdditionalInfo.class));
            }
            if (((RussianLegalEntity) value).getPropertyInfoDocumentType() != null) {
                russianLegalEntity.setPropertyInfoDocumentType(
                        ctx.convert(((RussianLegalEntity) value).getPropertyInfoDocumentType(),
                                com.rbkmoney.questionary.PropertyInfoDocumentType.class));
            }

            return LegalEntity.russian_legal_entity(russianLegalEntity);
        } else if (value.getLegalEntityType() == INTERNATIONALLEGALENTITY) {
            InternationalLegalEntity internationalLegalEntity = (InternationalLegalEntity) value;
            var thriftInternationalLegalEntity = new com.rbkmoney.questionary.InternationalLegalEntity()
                    .setLegalName(internationalLegalEntity.getLegalName())
                    .setActualAddress(internationalLegalEntity.getActualAddress())
                    .setRegisteredAddress(internationalLegalEntity.getRegisteredAddress())
                    .setRegisteredNumber(internationalLegalEntity.getRegisteredNumber())
                    .setTradingName(internationalLegalEntity.getTradingName());
            return LegalEntity.international_legal_entity(thriftInternationalLegalEntity);
        }
        throw new IllegalArgumentException("Unknown legalEntity type: " + value.getLegalEntityType());
    }

}
