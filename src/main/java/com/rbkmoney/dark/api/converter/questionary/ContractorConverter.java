package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.Contractor;
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
        com.rbkmoney.swag.questionary.model.Contractor contractor = null;
        if (value.isSetIndividualEntity()) {
            IndividualEntity individualEntity = ctx.convert(value.getIndividualEntity(), IndividualEntity.class);
            contractor = new IndividualEntityContractor().individualEntity(individualEntity);
        } else if (value.isSetLegalEntity()) {
            LegalEntity legalEntity = ctx.convert(value.getLegalEntity(), LegalEntity.class);
            contractor = new LegalEntityContractor().legalEntity(legalEntity);
        } else {
            throw new IllegalArgumentException("Unknown contractor type: " + value.getClass().getName());
        }
        return contractor;
    }

    @Override
    public Contractor toThrift(com.rbkmoney.swag.questionary.model.Contractor value, ThriftConverterContext ctx) {
        if (value instanceof IndividualEntityContractor) {
            var individualEntity = ctx.convert(((IndividualEntityContractor) value).getIndividualEntity(),
                    com.rbkmoney.questionary.IndividualEntity.class);
            return Contractor.individual_entity(individualEntity);
        } else if (value instanceof LegalEntityContractor) {
            var legalEntity = ctx.convert(((LegalEntityContractor) value).getLegalEntity(),
                    com.rbkmoney.questionary.LegalEntity.class);
            return Contractor.legal_entity(legalEntity);
        }

        throw new IllegalArgumentException("Unknown contractor type: " + value.getClass().getName());
    }

}
