package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.ResidencyInfo;
import com.rbkmoney.swag.questionary.model.IndividualResidencyInfo;
import com.rbkmoney.swag.questionary.model.LegalResidencyInfo;
import com.rbkmoney.swag.questionary.model.ResidencyInfo.ResidencyInfoTypeEnum;
import org.springframework.stereotype.Component;

import static com.rbkmoney.dark.api.util.ConverterUtils.safeSetValue;

@Component
public class ResidencyInfoConverter implements
        ThriftConverter<ResidencyInfo, com.rbkmoney.swag.questionary.model.ResidencyInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.ResidencyInfo, ResidencyInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.ResidencyInfo toSwag(ResidencyInfo value, SwagConverterContext ctx) {
        if (value.isSetIndividualResidencyInfo()) {
            return new IndividualResidencyInfo()
                    .usaTaxResident(value.getIndividualResidencyInfo().isUsaTaxResident())
                    .exceptUsaTaxResident(value.getIndividualResidencyInfo().isExceptUsaTaxResident())
                    .residencyInfoType(ResidencyInfoTypeEnum.INDIVIDUALRESIDENCYINFO);
        } else if (value.isSetLegalResidencyInfo()) {
            return new com.rbkmoney.swag.questionary.model.LegalResidencyInfo()
                    .fatca(value.getLegalResidencyInfo().isFatca())
                    .ownerResident(value.getLegalResidencyInfo().isOwnerResident())
                    .taxResident(value.getLegalResidencyInfo().isTaxResident())
                    .residencyInfoType(ResidencyInfoTypeEnum.LEGALRESIDENCYINFO);
        }

        throw new IllegalArgumentException("Unknown residencyInfo type: " + value.getClass().getName());
    }

    @Override
    public ResidencyInfo toThrift(com.rbkmoney.swag.questionary.model.ResidencyInfo value, ThriftConverterContext ctx) {
        switch (value.getResidencyInfoType()) {
            case INDIVIDUALRESIDENCYINFO:
                var swagIndividualResidencyInfo = (IndividualResidencyInfo) value;
                var individualResidencyInfo = new com.rbkmoney.questionary.IndividualResidencyInfo()
                        .setExceptUsaTaxResident(safeSetValue(swagIndividualResidencyInfo.isExceptUsaTaxResident()))
                        .setUsaTaxResident(safeSetValue(swagIndividualResidencyInfo.isUsaTaxResident()));

                return ResidencyInfo.individual_residency_info(individualResidencyInfo);
            case LEGALRESIDENCYINFO:
                var swagLegalResidencyInfo = (LegalResidencyInfo) value;
                var legalResidencyInfo = new com.rbkmoney.questionary.LegalResidencyInfo()
                        .setTaxResident(safeSetValue(swagLegalResidencyInfo.isTaxResident()))
                        .setOwnerResident(safeSetValue(swagLegalResidencyInfo.isOwnerResident()))
                        .setFatca(safeSetValue(swagLegalResidencyInfo.isFatca()));
                return ResidencyInfo.legal_residency_info(legalResidencyInfo);
            default:
                throw new IllegalArgumentException("Unknown residencyInfo type: " + value.getResidencyInfoType());
        }
    }

}
