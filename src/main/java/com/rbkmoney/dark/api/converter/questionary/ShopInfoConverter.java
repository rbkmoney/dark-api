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
        var shopInfo = new com.rbkmoney.swag.questionary.model.ShopInfo();
        if (value.isSetLocation()) {
            shopInfo.setLocation(
                    ctx.convert(value.getLocation(), com.rbkmoney.swag.questionary.model.ShopLocation.class));
        }
        if (value.isSetDetails()) {
            shopInfo.setDetails(ctx.convert(value.getDetails(), com.rbkmoney.swag.questionary.model.ShopDetails.class));
        }
        return shopInfo;
    }

    @Override
    public ShopInfo toThrift(com.rbkmoney.swag.questionary.model.ShopInfo value, ThriftConverterContext ctx) {
        ShopInfo shopInfo = new ShopInfo();
        if (value.getLocation() != null) {
            shopInfo.setLocation(ctx.convert(value.getLocation(), ShopLocation.class));
        }
        if (value.getDetails() != null) {
            shopInfo.setDetails(ctx.convert(value.getDetails(), ShopDetails.class));
        }
        return shopInfo;
    }
}
