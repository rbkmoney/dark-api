package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
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
                .accounting(value.getAccounting())
                .accountingOrg(value.getAccountingOrg())
                .staffCount(value.getStaffCount())
                .hasAccountant(value.isHasAccountant())
                .nkoRelationTarget(value.getNKORelationTarget())
                .relationshipWithNko(value.getRelationshipWithNKO())
                .storageFacilities(value.isStorageFacilities())
                .mainCounterparties(value.getMainCounterparties())
                .benefitThirdParties(value.isBenefitThirdParties());
        if (value.isSetBankAccount()) {
            additionalInfo.setBankAccount(ctx.convert(value.getBankAccount(), BankAccount.class));
        }
        if (value.isSetMonthOperationCount()) {
            additionalInfo.setMontOperationCount(ctx.convert(value.getMonthOperationCount(), MonthOperationCount.class));
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
            additionalInfo.setRelationIndividualEntity(ctx.convert(value.getRelationIndividualEntity(), RelationIndividualEntity.class));
        }
        if (value.isSetBusinessReputation()) {
            additionalInfo.setBusinessReputation(ctx.convert(value.getBusinessReputation(), BusinessReputation.class));
        }

        return additionalInfo;
    }

    @Override
    public AdditionalInfo toThrift(com.rbkmoney.swag.questionary.model.AdditionalInfo value, ThriftConverterContext ctx) {
        AdditionalInfo additionalInfo = new AdditionalInfo()
                .setAccounting(value.getAccounting())
                .setAccountingOrg(value.getAccountingOrg())
                .setStaffCount(value.getStaffCount())
                .setHasAccountant(value.isHasAccountant())
                .setNKORelationTarget(value.getNkoRelationTarget())
                .setRelationshipWithNKO(value.getRelationshipWithNko())
                .setStorageFacilities(value.isStorageFacilities())
                .setMainCounterparties(value.getMainCounterparties())
                .setBenefitThirdParties(value.isBenefitThirdParties());
        if (value.getBankAccount() != null) {
            additionalInfo.setBankAccount(ctx.convert(value.getBankAccount(), com.rbkmoney.questionary.BankAccount.class));
        }
        if (value.getMontOperationCount() != null) {
            additionalInfo.setMonthOperationCount(ctx.convert(value.getMontOperationCount(),
                    com.rbkmoney.questionary.MonthOperationCount.class));
        }
        if (value.getMonthOperationSum() != null) {
            additionalInfo.setMonthOperationSum(ctx.convert(value.getMonthOperationSum(),
                    com.rbkmoney.questionary.MonthOperationSum.class));
        }
        if (value.getFinancialPosition() != null) {
            List<com.rbkmoney.questionary.FinancialPosition> financialPositionList = value.getFinancialPosition().stream()
                    .map(financialPosition -> ctx.convert(financialPosition, com.rbkmoney.questionary.FinancialPosition.class))
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

        return additionalInfo;
    }

}
