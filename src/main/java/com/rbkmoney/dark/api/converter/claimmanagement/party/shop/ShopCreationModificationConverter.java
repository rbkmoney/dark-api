package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.ShopParams;
import com.rbkmoney.damsel.domain.CategoryRef;
import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.damsel.domain.ShopLocation;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ShopCreationModification;
import com.rbkmoney.swag.claim_management.model.ShopLocationUrl;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ShopLocation.LocationTypeEnum.SHOPLOCATIONURL;
import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.SHOPCREATIONMODIFICATION;

@Component
public class ShopCreationModificationConverter implements DarkApiConverter<ShopParams, ShopCreationModification> {

    @Override
    public ShopParams convertToThrift(ShopCreationModification swagCreation) {
        ShopLocation shopLocation = new ShopLocation();
        if (swagCreation.getLocation().getLocationType() == SHOPLOCATIONURL) {
            shopLocation.setUrl(((ShopLocationUrl) swagCreation.getLocation()).getUrl());
        }

        return new ShopParams()
                .setContractId(swagCreation.getContractID())
                .setPayoutToolId(swagCreation.getPayoutToolID())
                .setCategory(new CategoryRef()
                        .setId(swagCreation.getCategory().getCategoryID()))
                .setDetails(new ShopDetails()
                        .setDescription(swagCreation.getDetails().getDescription())
                        .setName(swagCreation.getDetails().getName()))
                .setLocation(shopLocation);
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ShopCreationModification convertToSwag(ShopParams shopParams) {
        var swagShopParams = new com.rbkmoney.swag.claim_management.model.ShopCreationModification();
        swagShopParams.setContractID(shopParams.getContractId());
        swagShopParams.setPayoutToolID(shopParams.getPayoutToolId());
        swagShopParams.setShopModificationType(SHOPCREATIONMODIFICATION);

        var swagCategoryRef = new com.rbkmoney.swag.claim_management.model.CategoryRef();
        swagCategoryRef.setCategoryID(shopParams.getCategory().getId());
        swagShopParams.setCategory(swagCategoryRef);

        var swagShopParamsDetails = new com.rbkmoney.swag.claim_management.model.ShopDetails();
        swagShopParamsDetails.setName(shopParams.getDetails().getName());
        swagShopParamsDetails.setDescription(shopParams.getDetails().getDescription());
        swagShopParams.setDetails(swagShopParamsDetails);

        if (shopParams.getLocation().isSetUrl()) {
            var swagShopLocation = new ShopLocationUrl();
            swagShopLocation.setLocationType(SHOPLOCATIONURL);
            swagShopLocation.setUrl(shopParams.getLocation().getUrl());
            swagShopParams.setLocation(swagShopLocation);
        }

        return swagShopParams;
    }

}
