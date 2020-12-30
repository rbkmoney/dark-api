package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.claim_management.ShopContractModification;
import com.rbkmoney.damsel.claim_management.ShopModification;
import com.rbkmoney.damsel.claim_management.ShopModificationUnit;
import com.rbkmoney.damsel.domain.CategoryRef;
import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.damsel.domain.ShopLocation;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.SHOPMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.*;

@Component
@RequiredArgsConstructor
public class ShopModificationUnitConverter
        implements DarkApiConverter<ShopModificationUnit, com.rbkmoney.swag.claim_management.model.ShopModificationUnit> {

    private final DarkApiConverter<ShopParams, ShopCreationModification> shopParamsConverter;

    private final DarkApiConverter<ScheduleModification, ShopPayoutScheduleModification> scheduleModificationConverter;

    private final DarkApiConverter<ShopDetails, ShopDetailsModification> claimShopDetailsConverter;

    private final DarkApiConverter<ShopAccountParams, ShopAccountCreationModification> shopAccountParamsConverter;

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
            case SHOPCREATIONMODIFICATION:
                var swagCreation = (com.rbkmoney.swag.claim_management.model.ShopCreationModification) swagModification;
                shopModification.setCreation(shopParamsConverter.convertToThrift(swagCreation));
                break;
            case SHOPCATEGORYMODIFICATION:
                var swagShopCategoryModification = (com.rbkmoney.swag.claim_management.model.ShopCategoryModification) swagModification;
                shopModification.setCategoryModification(new CategoryRef().setId(swagShopCategoryModification.getCategory().getCategoryID()));
                break;
            case SHOPDETAILSMODIFICATION:
                var swagShopDetails = (com.rbkmoney.swag.claim_management.model.ShopDetailsModification) swagModification;
                shopModification.setDetailsModification(claimShopDetailsConverter.convertToThrift(swagShopDetails));
                break;
            case SHOPCONTRACTMODIFICATION:
                var swagShopContractModification =
                        (com.rbkmoney.swag.claim_management.model.ShopContractModification) swagModification;
                shopModification.setContractModification(
                        shopContractModificationConverter.convertToThrift(swagShopContractModification)
                );
                break;
            case SHOPPAYOUTTOOLMODIFICATION:
                var swagShopPayoutToolModification =
                        (com.rbkmoney.swag.claim_management.model.ShopPayoutToolModification) swagModification;
                shopModification.setPayoutToolModification(swagShopPayoutToolModification.getPayoutToolModification());
                break;
            case SHOPLOCATIONMODIFICATION:
                var swagShopLocation = (com.rbkmoney.swag.claim_management.model.ShopLocationModification) swagModification;
                ShopLocation locationModification = new ShopLocation();
                locationModification.setUrl(((ShopLocationUrl) swagShopLocation.getLocation()).getUrl());
                shopModification.setLocationModification(locationModification);
                break;
            case SHOPACCOUNTCREATIONMODIFICATION:
                var swagShopAccountParams = (com.rbkmoney.swag.claim_management.model.ShopAccountCreationModification) swagModification;
                shopModification.setShopAccountCreation(shopAccountParamsConverter.convertToThrift(swagShopAccountParams));
                break;
            case SHOPPAYOUTSCHEDULEMODIFICATION:
                var swagScheduleModification = (com.rbkmoney.swag.claim_management.model.ShopPayoutScheduleModification) swagModification;
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
            CategoryRef categoryModification = shopModification.getCategoryModification();
            var swagCategoryRef = new com.rbkmoney.swag.claim_management.model.CategoryRef();
            swagCategoryRef.setCategoryID(categoryModification.getId());
            swagShopModificationUnit.setModification(
                    new ShopCategoryModification()
                            .category(swagCategoryRef)
                            .shopModificationType(SHOPCATEGORYMODIFICATION)
            );
        } else if (shopModification.isSetDetailsModification()) {
            ShopDetails detailsModification = shopModification.getDetailsModification();
            swagShopModificationUnit.setModification(claimShopDetailsConverter.convertToSwag(detailsModification));
        } else if (shopModification.isSetLocationModification()) {
            ShopLocation shopLocation = shopModification.getLocationModification();
            var swagShopLocation = new com.rbkmoney.swag.claim_management.model.ShopLocationUrl();
            swagShopLocation.setUrl(shopLocation.getUrl());
            swagShopModificationUnit.setModification(
                    new ShopLocationModification()
                            .location(swagShopLocation)
                            .shopModificationType(SHOPLOCATIONMODIFICATION)
            );
        } else if (shopModification.isSetShopAccountCreation()) {
            ShopAccountParams shopAccountCreation = shopModification.getShopAccountCreation();
            swagShopModificationUnit.setModification(shopAccountParamsConverter.convertToSwag(shopAccountCreation));
        } else if (shopModification.isSetPayoutScheduleModification()) {
            ScheduleModification payoutScheduleModification = shopModification.getPayoutScheduleModification();
            swagShopModificationUnit.setModification(scheduleModificationConverter.convertToSwag(payoutScheduleModification));
        } else if (shopModification.isSetPayoutToolModification()) {
            var swagShopPayoutToolModification = new com.rbkmoney.swag.claim_management.model.ShopPayoutToolModification();
            swagShopPayoutToolModification.setPayoutToolModification(shopModification.getPayoutToolModification());
            swagShopPayoutToolModification.setShopModificationType(SHOPPAYOUTTOOLMODIFICATION);
            swagShopModificationUnit.setModification(swagShopPayoutToolModification);
        } else if (shopModification.isSetCashRegisterModificationUnit()) {
            //todo
        } else {
            throw new IllegalArgumentException("Unknown party modification type!");
        }

        return swagShopModificationUnit;
    }

}
