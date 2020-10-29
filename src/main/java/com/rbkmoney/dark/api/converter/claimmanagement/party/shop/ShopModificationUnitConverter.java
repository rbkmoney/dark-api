package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.domain.CategoryRef;
import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.damsel.domain.ShopLocation;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.SHOPMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.*;

@Component
@RequiredArgsConstructor
public class ShopModificationUnitConverter
        implements DarkApiConverter<ShopModificationUnit, com.rbkmoney.swag.claim_management.model.ShopModificationUnit> {

    private final DarkApiConverter<ShopParams, com.rbkmoney.swag.claim_management.model.ShopParams> shopParamsConverter;

    private final DarkApiConverter<ScheduleModification,
            com.rbkmoney.swag.claim_management.model.ScheduleModification> scheduleModificationConverter;

    private final DarkApiConverter<ShopDetails,
            com.rbkmoney.swag.claim_management.model.ShopDetails> claimShopDetailsConverter;

    private final DarkApiConverter<ShopAccountParams,
            com.rbkmoney.swag.claim_management.model.ShopAccountParams> shopAccountParamsConverter;

    private final DarkApiConverter<ShopContractModification,
            com.rbkmoney.swag.claim_management.model.ShopContractModification> shopContractModificationConverter;

    @Override
    public ShopModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.ShopModificationUnit swagShopModificationUnit
    ) {
        ShopModificationUnit shopModificationUnit = new ShopModificationUnit();
        shopModificationUnit.setId(swagShopModificationUnit.getId());
        ShopModification shopModification = new ShopModification();
        var swagModification = swagShopModificationUnit.getModification();
        switch (swagModification.getShopModificationType()) {
            case CREATION:
                var swagCreation = (com.rbkmoney.swag.claim_management.model.ShopParams) swagModification;
                shopModification.setCreation(shopParamsConverter.convertToThrift(swagCreation));
                break;
            case CATEGORYMODIFICATION:
                var swagCategoryRef = (com.rbkmoney.swag.claim_management.model.CategoryRef) swagModification;
                shopModification.setCategoryModification(new CategoryRef().setId(swagCategoryRef.getId()));
                break;
            case DETAILSMODIFICATION:
                var swagShopDetails = (com.rbkmoney.swag.claim_management.model.ShopDetails) swagModification;
                shopModification.setDetailsModification(claimShopDetailsConverter.convertToThrift(swagShopDetails));
                break;
            case CONTRACTMODIFICATION:
                var swagShopContractModification =
                        (com.rbkmoney.swag.claim_management.model.ShopContractModification) swagModification;
                shopModification.setContractModification(
                        shopContractModificationConverter.convertToThrift(swagShopContractModification)
                );
                break;
            case PAYOUTTOOLMODIFICATION:
                var swagShopPayoutToolModification =
                        (com.rbkmoney.swag.claim_management.model.ShopPayoutToolModification) swagModification;
                shopModification.setPayoutToolModification(swagShopPayoutToolModification.getPayoutToolModification());
                break;
            case LOCATIONMODIFICATION:
                var swagShopLocation = (com.rbkmoney.swag.claim_management.model.ShopLocation) swagModification;
                ShopLocation locationModification = new ShopLocation();
                locationModification.setUrl(swagShopLocation.getUrl());
                shopModification.setLocationModification(locationModification);
                break;
            case SHOPACCOUNTCREATION:
                var swagShopAccountParams = (com.rbkmoney.swag.claim_management.model.ShopAccountParams) swagModification;
                shopModification.setShopAccountCreation(shopAccountParamsConverter.convertToThrift(swagShopAccountParams));
                break;
            case PAYOUTSCHEDULEMODIFICATION:
                var swagScheduleModification = (com.rbkmoney.swag.claim_management.model.ScheduleModification) swagModification;
                shopModification.setPayoutScheduleModification(scheduleModificationConverter.convertToThrift(swagScheduleModification));
                break;
            default:
                throw new IllegalArgumentException("Unknown shop modification type: " +
                        swagModification.getShopModificationType());
        }

        shopModificationUnit.setModification(shopModification);
        return shopModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ShopModificationUnit convertToSwag(
            ShopModificationUnit shopModificationUnit
    ) {
        var swagShopModificationUnit = new com.rbkmoney.swag.claim_management.model.ShopModificationUnit();
        swagShopModificationUnit.setPartyModificationType(SHOPMODIFICATIONUNIT);
        swagShopModificationUnit.setId(shopModificationUnit.getId());

        ShopModification shopModification = shopModificationUnit.getModification();
        if (shopModification.isSetCreation()) {
            swagShopModificationUnit.setModification(shopParamsConverter.convertToSwag(shopModification.getCreation()));
        } else if (shopModification.isSetContractModification()) {
            ShopContractModification shopContractModification = shopModification.getContractModification();
            swagShopModificationUnit.setModification(shopContractModificationConverter.convertToSwag(shopContractModification));
        } else if (shopModification.isSetCategoryModification()) {
            CategoryRef swagCategoryModification = shopModification.getCategoryModification();
            var swagCategoryRef = new com.rbkmoney.swag.claim_management.model.CategoryRef();
            swagCategoryRef.setId(swagCategoryModification.getId());
            swagCategoryRef.setShopModificationType(CATEGORYMODIFICATION);
            swagShopModificationUnit.setModification(swagCategoryRef);
        } else if (shopModification.isSetDetailsModification()) {
            ShopDetails detailsModification = shopModification.getDetailsModification();
            swagShopModificationUnit.setModification(claimShopDetailsConverter.convertToSwag(detailsModification));
        } else if (shopModification.isSetLocationModification()) {
            ShopLocation shopLocation = shopModification.getLocationModification();
            var swagShopLocation = new com.rbkmoney.swag.claim_management.model.ShopLocation();
            swagShopLocation.setUrl(shopLocation.getUrl());
            swagShopLocation.setShopModificationType(LOCATIONMODIFICATION);
            swagShopModificationUnit.setModification(swagShopLocation);
        } else if (shopModification.isSetShopAccountCreation()) {
            ShopAccountParams shopAccountCreation = shopModification.getShopAccountCreation();
            swagShopModificationUnit.setModification(shopAccountParamsConverter.convertToSwag(shopAccountCreation));
        } else if (shopModification.isSetPayoutScheduleModification()) {
            ScheduleModification payoutScheduleModification = shopModification.getPayoutScheduleModification();
            swagShopModificationUnit.setModification(scheduleModificationConverter.convertToSwag(payoutScheduleModification));
        } else if (shopModification.isSetPayoutToolModification()) {
            var swagShopPayoutToolModification = new com.rbkmoney.swag.claim_management.model.ShopPayoutToolModification();
            swagShopPayoutToolModification.setPayoutToolModification(shopModification.getPayoutToolModification());
            swagShopPayoutToolModification.setShopModificationType(PAYOUTTOOLMODIFICATION);
            swagShopModificationUnit.setModification(swagShopPayoutToolModification);
        } else if (shopModification.isSetCashRegisterModificationUnit()) {
            //todo
        } else {
            throw new IllegalArgumentException("Unknown party modification type!");
        }

        return swagShopModificationUnit;
    }

}
