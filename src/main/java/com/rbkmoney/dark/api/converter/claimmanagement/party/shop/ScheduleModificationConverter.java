package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.ScheduleModification;
import com.rbkmoney.damsel.domain.BusinessScheduleRef;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ShopModification;
import org.springframework.stereotype.Component;

import static com.rbkmoney.dark.api.domain.ShopModificationTypeEnum.PAYOUTSCHEDULEMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.SHOPPAYOUTSCHEDULEMODIFICATION;

@Component
public class ScheduleModificationConverter
        implements DarkApiConverter<ScheduleModification, com.rbkmoney.swag.claim_management.model.ShopPayoutScheduleModification> {

    @Override
    public ScheduleModification convertToThrift(
            com.rbkmoney.swag.claim_management.model.ShopPayoutScheduleModification swagScheduleModification
    ) {
        return new ScheduleModification()
                .setSchedule(
                        new BusinessScheduleRef()
                                .setId(swagScheduleModification.getSchedule().getId())
                );
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ShopPayoutScheduleModification convertToSwag(
            ScheduleModification payoutScheduleModification
    ) {
        var swagScheduleModification = new com.rbkmoney.swag.claim_management.model.ShopPayoutScheduleModification();
        var swagBusinessScheduleRef = new com.rbkmoney.swag.claim_management.model.BusinessScheduleRef();
        swagBusinessScheduleRef.setId(payoutScheduleModification.getSchedule().getId());
        swagScheduleModification.setSchedule(swagBusinessScheduleRef);
        swagScheduleModification.setShopModificationType(SHOPPAYOUTSCHEDULEMODIFICATION);
        return swagScheduleModification;
    }

}
