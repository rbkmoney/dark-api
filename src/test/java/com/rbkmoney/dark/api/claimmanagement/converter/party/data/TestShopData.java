package com.rbkmoney.dark.api.claimmanagement.converter.party.data;

import com.rbkmoney.dark.api.domain.ShopModificationTypeEnum;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.rbkmoney.dark.api.domain.ShopModificationTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.SHOPMODIFICATIONUNIT;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestShopData {

    public static com.rbkmoney.swag.claim_management.model.ShopParams getTestShopParams() {
        var swagShopParams =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopParams.class);
        swagShopParams.setShopModificationType(CREATION.getValue());
        swagShopParams.getCategory().setShopModificationType(null);
        swagShopParams.getDetails().setShopModificationType(null);
        swagShopParams.getLocation().setShopModificationType(null);
        return swagShopParams;
    }

    public static com.rbkmoney.swag.claim_management.model.ShopModificationUnit getTestSwagShopModificationUnit() {
        var swagShopModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopModificationUnit.class);
        swagShopModificationUnit.setPartyModificationType(SHOPMODIFICATIONUNIT);
        swagShopModificationUnit.getModification().setShopModificationType(CREATION.getValue());

        switch (ShopModificationTypeEnum.toEnum(swagShopModificationUnit.getModification().getShopModificationType())) {
            case CREATION:
                var shopParams = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopParams.class);
                shopParams.setShopModificationType(CREATION.getValue());
                shopParams.getLocation().setShopModificationType(null);
                shopParams.getDetails().setShopModificationType(null);
                shopParams.getCategory().setShopModificationType(null);
                swagShopModificationUnit.setModification(shopParams);
                break;
            case PAYOUTSCHEDULEMODIFICATION:
                var scheduleModification = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ScheduleModification.class);
                scheduleModification.setShopModificationType(PAYOUTSCHEDULEMODIFICATION.getValue());
                swagShopModificationUnit.setModification(scheduleModification);
                break;
            case SHOPACCOUNTCREATION:
                var shopAccountParams = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopAccountParams.class);
                shopAccountParams.setShopModificationType(SHOPACCOUNTCREATION.getValue());
                swagShopModificationUnit.setModification(shopAccountParams);
                break;
            case LOCATIONMODIFICATION:
                var shopLocation = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopLocation.class);
                shopLocation.setShopModificationType(LOCATIONMODIFICATION.getValue());
                swagShopModificationUnit.setModification(shopLocation);
                break;
            case PAYOUTTOOLMODIFICATION:
                var shopPayoutToolModification = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopPayoutToolModification.class);
                shopPayoutToolModification.setShopModificationType(PAYOUTTOOLMODIFICATION.getValue());
                swagShopModificationUnit.setModification(shopPayoutToolModification);
                break;
            case CONTRACTMODIFICATION:
                var shopContractModification = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopContractModification.class);
                shopContractModification.setShopModificationType(CONTRACTMODIFICATION.getValue());
                swagShopModificationUnit.setModification(shopContractModification);
                break;
            case DETAILSMODIFICATION:
                var shopDetails = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopDetails.class);
                shopDetails.setShopModificationType(DETAILSMODIFICATION.getValue());
                swagShopModificationUnit.setModification(shopDetails);
                break;
            case CATEGORYMODIFICATION:
                var categoryRef = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.CategoryRef.class);
                categoryRef.setShopModificationType(CATEGORYMODIFICATION.getValue());
                swagShopModificationUnit.setModification(categoryRef);
                break;
            default:
                throw new IllegalArgumentException("Unknown shop modification type!");
        }
        return swagShopModificationUnit;
    }

}
