package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.ShopDetails;
import com.rbkmoney.questionary.ShopInfo;
import com.rbkmoney.questionary.ShopLocation;
import org.springframework.stereotype.Component;

@Component
public class ShopInfoConverter implements
        ThriftConverter<ShopInfo, com.rbkmoney.swag.questionary.model.ShopInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.ShopInfo, ShopInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.ShopInfo toSwag(ShopInfo value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.ShopInfo()
                .location(ctx.convert(value.getLocation(), com.rbkmoney.swag.questionary.model.ShopLocation.class))
                .details(ctx.convert(value.getDetails(), com.rbkmoney.swag.questionary.model.ShopDetails.class));
    }

    @Override
    public ShopInfo toThrift(com.rbkmoney.swag.questionary.model.ShopInfo value, ThriftConverterContext ctx) {
        return new ShopInfo()
                .setLocation(ctx.convert(value.getLocation(), ShopLocation.class))
                .setDetails(ctx.convert(value.getDetails(), ShopDetails.class));
    }
}
