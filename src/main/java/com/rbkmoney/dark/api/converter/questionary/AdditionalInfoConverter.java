package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.dark.api.util.ConverterUtils;
import com.rbkmoney.questionary.AdditionalInfo;
import com.rbkmoney.swag.questionary.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AdditionalInfoConverter implements
        ThriftConverter<AdditionalInfo, com.rbkmoney.swag.questionary.model.AdditionalInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.AdditionalInfo, AdditionalInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.AdditionalInfo toSwag(AdditionalInfo value, SwagConverterContext ctx) {
        var additionalInfo = new com.rbkmoney.swag.questionary.model.AdditionalInfo()
                .staffCount(value.getStaffCount())
                .nkoRelationTarget(value.getNKORelationTarget())
                .relationshipWithNko(value.getRelationshipWithNKO())
                .mainCounterparties(value.getMainCounterparties())
                .benefitThirdParties(value.isBenefitThirdParties())
                .hasBeneficiary(value.isHasBeneficiary())
                .hasLiquidationProcess(value.isHasLiquidationProcess());
        if (value.isSetMonthOperationCount()) {
            additionalInfo
                    .setMonthOperationCount(ctx.convert(value.getMonthOperationCount(), MonthOperationCount.class));
        }
        if (value.isSetMonthOperationSum()) {
            additionalInfo.setMonthOperationSum(ctx.convert(value.getMonthOperationSum(), MonthOperationSum.class));
        }
        if (value.isSetFinancialPosition()) {
            List<FinancialPosition> financialPositionList = value.getFinancialPosition().stream()
                    .map(financialPosition -> ctx.convert(financialPosition, FinancialPosition.class))
                    .collect(Collectors.toList());
            additionalInfo.setFinancialPosition(financialPositionList);
        }
        if (value.isSetBusinessInfo()) {
            List<BusinessInfo> businessInfoList = value.getBusinessInfo().stream()
                    .map(businessInfo -> ctx.convert(businessInfo, BusinessInfo.class))
                    .collect(Collectors.toList());
            additionalInfo.setBusinessInfo(businessInfoList);
        }
        if (value.isSetRelationIndividualEntity()) {
            additionalInfo.setRelationIndividualEntity(
                    ctx.convert(value.getRelationIndividualEntity(), RelationIndividualEntity.class));
        }
        if (value.isSetBusinessReputation()) {
            additionalInfo.setBusinessReputation(ctx.convert(value.getBusinessReputation(), BusinessReputation.class));
        }
        if (value.isSetAccountantInfo()) {
            additionalInfo.setAccountantInfo(ctx.convert(value.getAccountantInfo(), AccountantInfo.class));
        }

        return additionalInfo;
    }

    @Override
    public AdditionalInfo toThrift(com.rbkmoney.swag.questionary.model.AdditionalInfo value,
                                   ThriftConverterContext ctx) {
        AdditionalInfo additionalInfo = new AdditionalInfo()
                .setStaffCount(ConverterUtils.safeSetValue(value.getStaffCount()))
                .setNKORelationTarget(value.getNkoRelationTarget())
                .setRelationshipWithNKO(value.getRelationshipWithNko())
                .setMainCounterparties(value.getMainCounterparties())
                .setBenefitThirdParties(ConverterUtils.safeSetValue(value.isBenefitThirdParties()))
                .setHasBeneficiary(ConverterUtils.safeSetValue(value.isHasBeneficiary()))
                .setHasLiquidationProcess(ConverterUtils.safeSetValue(value.isHasLiquidationProcess()));
        if (value.getMonthOperationCount() != null) {
            additionalInfo.setMonthOperationCount(ctx.convert(value.getMonthOperationCount(),
                    com.rbkmoney.questionary.MonthOperationCount.class));
        }
        if (value.getMonthOperationSum() != null) {
            additionalInfo.setMonthOperationSum(ctx.convert(value.getMonthOperationSum(),
                    com.rbkmoney.questionary.MonthOperationSum.class));
        }
        if (value.getFinancialPosition() != null) {
            List<com.rbkmoney.questionary.FinancialPosition> financialPositionList =
                    value.getFinancialPosition().stream()
                            .map(financialPosition -> ctx
                                    .convert(financialPosition, com.rbkmoney.questionary.FinancialPosition.class))
                            .collect(Collectors.toList());
            additionalInfo.setFinancialPosition(financialPositionList);
        }
        if (value.getBusinessInfo() != null) {
            List<com.rbkmoney.questionary.BusinessInfo> businessInfoList = value.getBusinessInfo().stream()
                    .map(businessInfo -> ctx.convert(businessInfo, com.rbkmoney.questionary.BusinessInfo.class))
                    .collect(Collectors.toList());
            additionalInfo.setBusinessInfo(businessInfoList);
        }
        if (value.getRelationIndividualEntity() != null) {
            additionalInfo.setRelationIndividualEntity(ctx.convert(value.getRelationIndividualEntity(),
                    com.rbkmoney.questionary.RelationIndividualEntity.class));
        }
        if (value.getBusinessReputation() != null) {
            additionalInfo.setBusinessReputation(ctx.convert(value.getBusinessReputation(),
                    com.rbkmoney.questionary.BusinessReputation.class));
        }
        if (value.getAccountantInfo() != null) {
            additionalInfo.setAccountantInfo(ctx.convert(value.getAccountantInfo(),
                    com.rbkmoney.questionary.AccountantInfo.class));
        }

        return additionalInfo;
    }

}
