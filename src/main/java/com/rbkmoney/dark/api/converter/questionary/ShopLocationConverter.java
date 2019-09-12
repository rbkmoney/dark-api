package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.ShopLocation;
import com.rbkmoney.swag.questionary.model.ShopLocation.LocationTypeEnum;
import com.rbkmoney.swag.questionary.model.ShopLocationUrl;
import org.springframework.stereotype.Component;

@Component
public class ShopLocationConverter implements
        ThriftConverter<ShopLocation, com.rbkmoney.swag.questionary.model.ShopLocation>,
        SwagConverter<com.rbkmoney.swag.questionary.model.ShopLocation, ShopLocation> {

    @Override
    public com.rbkmoney.swag.questionary.model.ShopLocation toSwag(ShopLocation value, SwagConverterContext ctx) {
        return new ShopLocationUrl()
                .url(value.getUrl());
    }

    @Override
    public ShopLocation toThrift(com.rbkmoney.swag.questionary.model.ShopLocation value, ThriftConverterContext ctx) {
        ShopLocation shopLocation = new ShopLocation();
        if (value.getLocationType() == LocationTypeEnum.URL) {
            shopLocation.setUrl(((ShopLocationUrl) value).getUrl());
            return shopLocation;
        }
        throw new IllegalArgumentException("Unknown shopLocation type: " + shopLocation);
    }

}
