package com.rbkmoney.dark.api.claimmanagement.converter.party.data;

import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.PARTYMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.PartyModification.PartyModificationTypeEnum.SHOPMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestShopData {

    public static com.rbkmoney.swag.claim_management.model.ShopParams getTestShopParams() {
        var swagShopParams =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopParams.class);
        swagShopParams.setShopModificationType(CREATION);
        swagShopParams.getCategory().setShopModificationType(null);
        swagShopParams.getDetails().setShopModificationType(null);
        swagShopParams.getLocation().setShopModificationType(null);
        return swagShopParams;
    }

    public static com.rbkmoney.swag.claim_management.model.ShopModificationUnit getTestSwagShopModificationUnit() {
        var swagShopModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopModificationUnit.class);
        swagShopModificationUnit.setPartyModificationType(SHOPMODIFICATIONUNIT);
        swagShopModificationUnit.setModificationType(PARTYMODIFICATION);

        switch (swagShopModificationUnit.getModification().getShopModificationType()) {
            case CREATION:
                var shopParams = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopParams.class);
                shopParams.setShopModificationType(CREATION);
                shopParams.getLocation().setShopModificationType(null);
                shopParams.getDetails().setShopModificationType(null);
                shopParams.getCategory().setShopModificationType(null);
                swagShopModificationUnit.setModification(shopParams);
                break;
            case PAYOUTSCHEDULEMODIFICATION:
                var scheduleModification = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ScheduleModification.class);
                scheduleModification.setShopModificationType(PAYOUTSCHEDULEMODIFICATION);
                swagShopModificationUnit.setModification(scheduleModification);
                break;
            case SHOPACCOUNTCREATION:
                var shopAccountParams = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopAccountParams.class);
                shopAccountParams.setShopModificationType(SHOPACCOUNTCREATION);
                swagShopModificationUnit.setModification(shopAccountParams);
                break;
            case LOCATIONMODIFICATION:
                var shopLocation = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopLocation.class);
                shopLocation.setShopModificationType(LOCATIONMODIFICATION);
                swagShopModificationUnit.setModification(shopLocation);
                break;
            case PAYOUTTOOLMODIFICATION:
                var shopPayoutToolModification = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopPayoutToolModification.class);
                shopPayoutToolModification.setShopModificationType(PAYOUTTOOLMODIFICATION);
                swagShopModificationUnit.setModification(shopPayoutToolModification);
                break;
            case CONTRACTMODIFICATION:
                var shopContractModification = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopContractModification.class);
                shopContractModification.setShopModificationType(CONTRACTMODIFICATION);
                swagShopModificationUnit.setModification(shopContractModification);
                break;
            case DETAILSMODIFICATION:
                var shopDetails = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopDetails.class);
                shopDetails.setShopModificationType(DETAILSMODIFICATION);
                swagShopModificationUnit.setModification(shopDetails);
                break;
            case CATEGORYMODIFICATION:
                var categoryRef = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.CategoryRef.class);
                categoryRef.setShopModificationType(CATEGORYMODIFICATION);
                swagShopModificationUnit.setModification(categoryRef);
                break;
            default:
                throw new IllegalArgumentException("Unknown shop modification type!");
        }
        return swagShopModificationUnit;
    }

}
