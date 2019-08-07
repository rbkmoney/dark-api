package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.ResidencyInfo;
import com.rbkmoney.swag.questionary.model.IndividualResidencyInfo;
import com.rbkmoney.swag.questionary.model.LegalResidencyInfo;
import org.springframework.stereotype.Component;

@Component
public class ResidencyInfoConverter implements
        ThriftConverter<ResidencyInfo, com.rbkmoney.swag.questionary.model.ResidencyInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.ResidencyInfo, ResidencyInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.ResidencyInfo toSwag(ResidencyInfo value, SwagConverterContext ctx) {
        if (value.isSetIndividualResidencyInfo()) {
            return new IndividualResidencyInfo()
                    .taxResident(value.getIndividualResidencyInfo().isTaxResident());
        } else if (value.isSetLegalResidencyInfo()) {
            return new com.rbkmoney.swag.questionary.model.LegalResidencyInfo()
                    .fatca(value.getLegalResidencyInfo().isFatca())
                    .ownerResident(value.getLegalResidencyInfo().isOwnerResident())
                    .taxResident(value.getLegalResidencyInfo().isTaxResident());
        }

        throw new IllegalArgumentException("Unknown residencyInfo type: " + value.getClass().getName());
    }

    @Override
    public ResidencyInfo toThrift(com.rbkmoney.swag.questionary.model.ResidencyInfo value, ThriftConverterContext ctx) {
        if (value instanceof IndividualResidencyInfo) {
            var individualResidencyInfo = new com.rbkmoney.questionary.IndividualResidencyInfo()
                    .setTaxResident(((IndividualResidencyInfo) value).isTaxResident());

            return ResidencyInfo.individual_residency_info(individualResidencyInfo);
        } else if (value instanceof LegalResidencyInfo) {
            var legalResidencyInfo = new com.rbkmoney.questionary.LegalResidencyInfo()
                    .setTaxResident(((LegalResidencyInfo) value).isTaxResident())
                    .setOwnerResident(((LegalResidencyInfo) value).isOwnerResident())
                    .setFatca(((LegalResidencyInfo) value).isFatca());

            return ResidencyInfo.legal_residency_info(legalResidencyInfo);
        }

        throw new IllegalArgumentException("Unknown residencyInfo type: " + value.getClass().getName());
    }

}
