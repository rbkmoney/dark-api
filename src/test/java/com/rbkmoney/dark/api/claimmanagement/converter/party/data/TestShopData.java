package com.rbkmoney.dark.api.claimmanagement.converter.party.data;

import com.rbkmoney.swag.claim_management.model.*;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.SHOPMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.ShopLocation.LocationTypeEnum.SHOPLOCATIONURL;
import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestShopData {

    public static ShopCreationModification getTestShopParams() {
        var swagShopParams =
                EnhancedRandom.random(ShopCreationModification.class);
        swagShopParams.setLocation(
                new ShopLocationUrl()
                        .url("http://example.com")
                        .locationType(SHOPLOCATIONURL)
        );
        swagShopParams.setShopModificationType(SHOPCREATIONMODIFICATION);
        return swagShopParams;
    }

    public static com.rbkmoney.swag.claim_management.model.ShopModificationUnit getTestSwagShopModificationUnit() {
        var swagShopModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopModificationUnit.class);
        swagShopModificationUnit.setPartyModificationType(SHOPMODIFICATIONUNIT);
        swagShopModificationUnit.getModification().setShopModificationType(SHOPCREATIONMODIFICATION);

        switch (swagShopModificationUnit.getModification().getShopModificationType()) {
            case SHOPCREATIONMODIFICATION:
                var shopParams = EnhancedRandom.random(ShopCreationModification.class);
                shopParams.setShopModificationType(SHOPCREATIONMODIFICATION);
                shopParams.setLocation(new ShopLocationUrl().url("http://example.com/").locationType(SHOPLOCATIONURL));
                swagShopModificationUnit.setModification(shopParams);
                break;
            case SHOPPAYOUTSCHEDULEMODIFICATION:
                var scheduleModification = EnhancedRandom.random(ShopPayoutScheduleModification.class);
                scheduleModification.setShopModificationType(SHOPPAYOUTSCHEDULEMODIFICATION);
                swagShopModificationUnit.setModification(scheduleModification);
                break;
            case SHOPACCOUNTCREATIONMODIFICATION:
                var shopAccountParams = EnhancedRandom.random(ShopAccountCreationModification.class);
                shopAccountParams.setShopModificationType(SHOPACCOUNTCREATIONMODIFICATION);
                swagShopModificationUnit.setModification(shopAccountParams);
                break;
            case SHOPLOCATIONMODIFICATION:
                var shopLocationType = EnhancedRandom.random(ShopLocationUrl.class);
                swagShopModificationUnit.setModification(
                        new ShopLocationModification()
                                .location(shopLocationType)
                                .shopModificationType(SHOPLOCATIONMODIFICATION)
                );
                break;
            case SHOPPAYOUTTOOLMODIFICATION:
                var shopPayoutToolModification = EnhancedRandom.random(ShopPayoutToolModification.class);
                shopPayoutToolModification.setShopModificationType(SHOPPAYOUTTOOLMODIFICATION);
                swagShopModificationUnit.setModification(shopPayoutToolModification);
                break;
            case SHOPCONTRACTMODIFICATION:
                var shopContractModification = EnhancedRandom.random(ShopContractModification.class);
                shopContractModification.setShopModificationType(SHOPCONTRACTMODIFICATION);
                swagShopModificationUnit.setModification(shopContractModification);
                break;
            case SHOPDETAILSMODIFICATION:
                var shopDetails = EnhancedRandom.random(ShopDetailsModification.class);
                shopDetails.setShopModificationType(SHOPDETAILSMODIFICATION);
                swagShopModificationUnit.setModification(shopDetails);
                break;
            case SHOPCATEGORYMODIFICATION:
                var categoryRef = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.CategoryRef.class);
                swagShopModificationUnit.setModification(
                        new ShopCategoryModification()
                                .category(categoryRef)
                                .shopModificationType(SHOPCATEGORYMODIFICATION)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown shop modification type!");
        }
        return swagShopModificationUnit;
    }

}
