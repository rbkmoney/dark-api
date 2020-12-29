package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ShopDetailsModification;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.SHOPDETAILSMODIFICATION;

@Component
public class ClaimShopDetailsConverter
        implements DarkApiConverter<ShopDetails, ShopDetailsModification> {

    @Override
    public ShopDetails convertToThrift(ShopDetailsModification shopDetailsModification) {
        return new ShopDetails()
                .setDescription(shopDetailsModification.getDetails().getDescription())
                .setName(shopDetailsModification.getDetails().getName());
    }

    @Override
    public ShopDetailsModification convertToSwag(ShopDetails detailsModification) {
        ShopDetailsModification shopDetailsModification = new ShopDetailsModification();
        var shopDetails = new com.rbkmoney.swag.claim_management.model.ShopDetails();
        shopDetails.setName(detailsModification.getName());
        shopDetails.setDescription(detailsModification.getDescription());
        shopDetailsModification.setDetails(shopDetails);
        shopDetailsModification.setShopModificationType(SHOPDETAILSMODIFICATION);
        return shopDetailsModification;
    }

}
