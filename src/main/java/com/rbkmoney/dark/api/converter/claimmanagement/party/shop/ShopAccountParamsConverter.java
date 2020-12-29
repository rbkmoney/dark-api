package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.ShopAccountParams;
import com.rbkmoney.damsel.domain.CurrencyRef;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ShopAccountCreationModification;
import com.rbkmoney.swag.claim_management.model.ShopModification;
import org.springframework.stereotype.Component;

@Component
public class ShopAccountParamsConverter
        implements DarkApiConverter<ShopAccountParams, com.rbkmoney.swag.claim_management.model.ShopAccountCreationModification> {

    @Override
    public ShopAccountParams convertToThrift(ShopAccountCreationModification shopAccountCreationModification) {
        return new ShopAccountParams()
                .setCurrency(
                        new CurrencyRef()
                                .setSymbolicCode(
                                        shopAccountCreationModification.getCurrency().getSymbolicCode()
                                )
                );
    }

    @Override
    public ShopAccountCreationModification convertToSwag(ShopAccountParams shopAccountCreation) {
        var swagShopAccountParams = new ShopAccountCreationModification();
        var swagCurrencyRef = new com.rbkmoney.swag.claim_management.model.CurrencyRef();
        swagCurrencyRef.setSymbolicCode(shopAccountCreation.getCurrency().getSymbolicCode());

        swagShopAccountParams.setCurrency(swagCurrencyRef);
        swagShopAccountParams.setShopModificationType(ShopModification.ShopModificationTypeEnum.SHOPACCOUNTCREATIONMODIFICATION);
        return swagShopAccountParams;
    }

}
