package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.ShopDetails;
import org.springframework.stereotype.Component;

@Component
public class ShopDetailsConverter implements
        ThriftConverter<ShopDetails, com.rbkmoney.swag.questionary.model.ShopDetails>,
        SwagConverter<com.rbkmoney.swag.questionary.model.ShopDetails, ShopDetails> {

    @Override
    public com.rbkmoney.swag.questionary.model.ShopDetails toSwag(ShopDetails value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.ShopDetails()
                .description(value.getDescription())
                .name(value.getName());
    }

    @Override
    public ShopDetails toThrift(com.rbkmoney.swag.questionary.model.ShopDetails value, ThriftConverterContext ctx) {
        return new ShopDetails()
                .setName(value.getName())
                .setDescription(value.getDescription());
    }

}
