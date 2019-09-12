package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.IndividualEntity;
import com.rbkmoney.questionary.RussianIndividualEntity;
import com.rbkmoney.swag.questionary.model.IndividualEntity.IndividualEntityTypeEnum;
import org.springframework.stereotype.Component;

@Component
public class IndividualEntityConverter implements
        ThriftConverter<IndividualEntity, com.rbkmoney.swag.questionary.model.IndividualEntity>,
        SwagConverter<com.rbkmoney.swag.questionary.model.IndividualEntity, IndividualEntity> {

    @Override
    public com.rbkmoney.swag.questionary.model.IndividualEntity toSwag(IndividualEntity value, SwagConverterContext ctx) {
        if (value.isSetRussianIndividualEntity()) {
            return ctx.convert(value.getRussianIndividualEntity(), com.rbkmoney.swag.questionary.model.RussianIndividualEntity.class);
        } else {
            throw new IllegalArgumentException("Unknown individualEntity type: " + value.getClass().getName());
        }
    }

    @Override
    public IndividualEntity toThrift(com.rbkmoney.swag.questionary.model.IndividualEntity value, ThriftConverterContext ctx) {
        IndividualEntity individualEntity = new IndividualEntity();
        if (value.getIndividualEntityType() == IndividualEntityTypeEnum.RUSSIANINDIVIDUALENTITY) {
            individualEntity.setRussianIndividualEntity(ctx.convert(value, RussianIndividualEntity.class));
        } else {
            throw new IllegalArgumentException("Unknown individualEntity type: " + value.getIndividualEntityType());
        }

        return individualEntity;
    }

}
