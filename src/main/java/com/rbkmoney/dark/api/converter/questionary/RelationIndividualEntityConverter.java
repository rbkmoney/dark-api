package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.RelationIndividualEntity;
import org.springframework.stereotype.Component;

@Component
public class RelationIndividualEntityConverter implements
        ThriftConverter<RelationIndividualEntity, com.rbkmoney.swag.questionary.model.RelationIndividualEntity>,
        SwagConverter<com.rbkmoney.swag.questionary.model.RelationIndividualEntity, RelationIndividualEntity> {

    @Override
    public com.rbkmoney.swag.questionary.model.RelationIndividualEntity toSwag(RelationIndividualEntity value, SwagConverterContext ctx) {
        switch (value) {
            case liquidation_process:
                return com.rbkmoney.swag.questionary.model.RelationIndividualEntity.LIQUIDATIONPROCESS;
            case insolvency_proceedings:
                return com.rbkmoney.swag.questionary.model.RelationIndividualEntity.INSOLVENCYPROCEEDINGS;
            case bankrupt_judicial_decision:
                return com.rbkmoney.swag.questionary.model.RelationIndividualEntity.BANKRUPTJUDICIALDECISION;
            default:
                throw new IllegalArgumentException("Unknown relationIndividualEntity type: " + value);
        }
    }

    @Override
    public RelationIndividualEntity toThrift(com.rbkmoney.swag.questionary.model.RelationIndividualEntity value, ThriftConverterContext ctx) {
        switch (value) {
            case LIQUIDATIONPROCESS:
                return RelationIndividualEntity.liquidation_process;
            case INSOLVENCYPROCEEDINGS:
                return RelationIndividualEntity.insolvency_proceedings;
            case BANKRUPTJUDICIALDECISION:
                return RelationIndividualEntity.bankrupt_judicial_decision;
            default:
                throw new IllegalArgumentException("Unknown relationIndividualEntity type: " + value);
        }
    }

}
