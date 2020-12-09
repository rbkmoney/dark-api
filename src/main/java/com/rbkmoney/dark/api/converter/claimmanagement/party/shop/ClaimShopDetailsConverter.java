package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.dark.api.domain.ShopModificationTypeEnum.DETAILSMODIFICATION;

@Component
public class ClaimShopDetailsConverter
        implements DarkApiConverter<ShopDetails, com.rbkmoney.swag.claim_management.model.ShopDetails> {

    @Override
    public ShopDetails convertToThrift(com.rbkmoney.swag.claim_management.model.ShopDetails swagShopDetails) {
        return new ShopDetails()
                .setDescription(swagShopDetails.getDescription())
                .setName(swagShopDetails.getName());
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ShopDetails convertToSwag(ShopDetails detailsModification) {
        var swagShopDetails = new com.rbkmoney.swag.claim_management.model.ShopDetails();
        swagShopDetails.setName(detailsModification.getName());
        swagShopDetails.setDescription(detailsModification.getDescription());
        swagShopDetails.setShopModificationType(DETAILSMODIFICATION.getValue());
        return swagShopDetails;
    }

}
