package com.rbkmoney.dark.api.converter.claimmanagement.party.shop;

import com.rbkmoney.damsel.claim_management.ScheduleModification;
import com.rbkmoney.damsel.domain.BusinessScheduleRef;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.PAYOUTSCHEDULEMODIFICATION;

@Component
public class ScheduleModificationConverter
        implements DarkApiConverter<ScheduleModification, com.rbkmoney.swag.claim_management.model.ScheduleModification> {

    @Override
    public ScheduleModification convertToThrift(
            com.rbkmoney.swag.claim_management.model.ScheduleModification swagScheduleModification
    ) {
        return new ScheduleModification()
                .setSchedule(
                        new BusinessScheduleRef()
                                .setId(swagScheduleModification.getSchedule().getId())
                );
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ScheduleModification convertToSwag(
            ScheduleModification payoutScheduleModification
    ) {
        var swagScheduleModification = new com.rbkmoney.swag.claim_management.model.ScheduleModification();
        var swagBusinessScheduleRef = new com.rbkmoney.swag.claim_management.model.BusinessScheduleRef();
        swagBusinessScheduleRef.setId(payoutScheduleModification.getSchedule().getId());
        swagScheduleModification.setSchedule(swagBusinessScheduleRef);
        swagScheduleModification.setShopModificationType(PAYOUTSCHEDULEMODIFICATION);
        return swagScheduleModification;
    }

}
