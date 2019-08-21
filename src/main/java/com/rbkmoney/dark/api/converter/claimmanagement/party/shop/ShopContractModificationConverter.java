package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.ShopContractModification;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.CONTRACTMODIFICATION;

@Component
public class ShopContractModificationConverter
        implements DarkApiConverter<ShopContractModification, com.rbkmoney.swag.claim_management.model.ShopContractModification> {

    @Override
    public ShopContractModification convertToThrift(
            com.rbkmoney.swag.claim_management.model.ShopContractModification swagShopContractModification
    ) {
        return new ShopContractModification()
                .setContractId(swagShopContractModification.getContractID())
                .setPayoutToolId(swagShopContractModification.getPayoutToolID());
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ShopContractModification convertToSwag(
            ShopContractModification shopContractModification
    ) {
        var swagShopContractModification = new com.rbkmoney.swag.claim_management.model.ShopContractModification();
        swagShopContractModification.setContractID(shopContractModification.getContractId());
        swagShopContractModification.setPayoutToolID(shopContractModification.getPayoutToolId());
        swagShopContractModification.setShopModificationType(CONTRACTMODIFICATION);
        return swagShopContractModification;
    }

}
