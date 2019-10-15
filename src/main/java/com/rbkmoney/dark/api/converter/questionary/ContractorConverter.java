package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.Contractor;
import com.rbkmoney.swag.questionary.model.Contractor.ContractorTypeEnum;
import com.rbkmoney.swag.questionary.model.IndividualEntity;
import com.rbkmoney.swag.questionary.model.IndividualEntityContractor;
import com.rbkmoney.swag.questionary.model.LegalEntity;
import com.rbkmoney.swag.questionary.model.LegalEntityContractor;
import org.springframework.stereotype.Component;

@Component
public class ContractorConverter implements
        ThriftConverter<Contractor, com.rbkmoney.swag.questionary.model.Contractor>,
        SwagConverter<com.rbkmoney.swag.questionary.model.Contractor, Contractor> {

    @Override
    public com.rbkmoney.swag.questionary.model.Contractor toSwag(Contractor value, SwagConverterContext ctx) {
        if (value.isSetIndividualEntity()) {
            IndividualEntity individualEntity = ctx.convert(value.getIndividualEntity(), IndividualEntity.class);
            individualEntity.setIndividualEntityType(IndividualEntity.IndividualEntityTypeEnum.RUSSIANINDIVIDUALENTITY);
            return new IndividualEntityContractor().individualEntity(individualEntity)
                    .contractorType(ContractorTypeEnum.INDIVIDUALENTITYCONTRACTOR);
        } else if (value.isSetLegalEntity()) {
            LegalEntity legalEntity = ctx.convert(value.getLegalEntity(), LegalEntity.class);
            legalEntity.setLegalEntityType(LegalEntity.LegalEntityTypeEnum.RUSSIANLEGALENTITY);
            return new LegalEntityContractor().legalEntity(legalEntity)
                    .contractorType(ContractorTypeEnum.LEGALENTITYCONTRACTOR);
        } else {
            throw new IllegalArgumentException("Unknown contractor type: " + value.getClass().getName());
        }
    }

    @Override
    public Contractor toThrift(com.rbkmoney.swag.questionary.model.Contractor value, ThriftConverterContext ctx) {
        switch (value.getContractorType()) {
            case INDIVIDUALENTITYCONTRACTOR:
                var individualEntity = ctx.convert(((IndividualEntityContractor) value).getIndividualEntity(),
                        com.rbkmoney.questionary.IndividualEntity.class);
                return Contractor.individual_entity(individualEntity);
            case LEGALENTITYCONTRACTOR:
                var legalEntity = ctx.convert(((LegalEntityContractor) value).getLegalEntity(),
                        com.rbkmoney.questionary.LegalEntity.class);
                return Contractor.legal_entity(legalEntity);
            default:
                throw new IllegalArgumentException("Unknown contractor type: " + value.getContractorType());
        }
    }

}
