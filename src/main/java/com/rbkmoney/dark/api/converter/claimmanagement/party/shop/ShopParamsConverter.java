package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.ShopParams;
import com.rbkmoney.damsel.domain.CategoryRef;
import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.damsel.domain.ShopLocation;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.CREATION;

@Component
public class ShopParamsConverter
        implements DarkApiConverter<ShopParams, com.rbkmoney.swag.claim_management.model.ShopParams> {

    @Override
    public ShopParams convertToThrift(com.rbkmoney.swag.claim_management.model.ShopParams swagCreation) {
        ShopLocation shopLocation = new ShopLocation();
        shopLocation.setUrl(swagCreation.getLocation().getUrl());

        return new ShopParams()
                .setContractId(swagCreation.getContractID())
                .setPayoutToolId(swagCreation.getPayoutToolID())
                .setCategory(new CategoryRef()
                        .setId(swagCreation.getCategory().getId()))
                .setDetails(new ShopDetails()
                        .setDescription(swagCreation.getDetails().getDescription())
                        .setName(swagCreation.getDetails().getName()))
                .setLocation(shopLocation);
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ShopParams convertToSwag(ShopParams shopParams) {
        var swagShopParams = new com.rbkmoney.swag.claim_management.model.ShopParams();
        swagShopParams.setContractID(shopParams.getContractId());
        swagShopParams.setPayoutToolID(shopParams.getPayoutToolId());
        swagShopParams.setShopModificationType(CREATION);

        var swagCategoryRef = new com.rbkmoney.swag.claim_management.model.CategoryRef();
        swagCategoryRef.setId(shopParams.getCategory().getId());
        swagShopParams.setCategory(swagCategoryRef);

        var swagShopParamsDetails = new com.rbkmoney.swag.claim_management.model.ShopDetails();
        swagShopParamsDetails.setName(shopParams.getDetails().getName());
        swagShopParamsDetails.setDescription(shopParams.getDetails().getDescription());
        swagShopParams.setDetails(swagShopParamsDetails);

        var swagShopLocation = new com.rbkmoney.swag.claim_management.model.ShopLocation();
        swagShopLocation.setUrl(shopParams.getLocation().getUrl());
        swagShopParams.setLocation(swagShopLocation);

        return swagShopParams;
    }

}
