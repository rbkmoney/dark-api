package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.ShopAccountParams;
import com.rbkmoney.damsel.domain.CurrencyRef;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.dark.api.domain.ShopModificationTypeEnum.SHOPACCOUNTCREATION;

@Component
public class ShopAccountParamsConverter
        implements DarkApiConverter<ShopAccountParams, com.rbkmoney.swag.claim_management.model.ShopAccountParams> {

    @Override
    public ShopAccountParams convertToThrift(
            com.rbkmoney.swag.claim_management.model.ShopAccountParams swagShopAccountParams
    ) {
        return new ShopAccountParams()
                .setCurrency(
                        new CurrencyRef()
                                .setSymbolicCode(
                                        swagShopAccountParams.getCurrency().getSymbolicCode()
                                )
                );
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ShopAccountParams convertToSwag(
            ShopAccountParams shopAccountCreation
    ) {
        var swagShopAccountParams = new com.rbkmoney.swag.claim_management.model.ShopAccountParams();
        var swagCurrencyRef = new com.rbkmoney.swag.claim_management.model.CurrencyRef();
        swagCurrencyRef.setSymbolicCode(shopAccountCreation.getCurrency().getSymbolicCode());

        swagShopAccountParams.setCurrency(swagCurrencyRef);
        swagShopAccountParams.setShopModificationType(SHOPACCOUNTCREATION.getValue());
        return swagShopAccountParams;
    }

}
