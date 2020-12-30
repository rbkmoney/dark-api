package com.rbkmoney.dark.api.claimmanagement.converter.party;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.domain.ShopDetails;
import com.rbkmoney.dark.api.converter.claimmanagement.party.shop.*;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.swag.claim_management.model.ShopAccountCreationModification;
import com.rbkmoney.swag.claim_management.model.ShopDetailsModification;
import com.rbkmoney.swag.claim_management.model.ShopPayoutScheduleModification;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;

import java.io.IOException;

import static com.rbkmoney.dark.api.claimmanagement.converter.party.data.TestShopData.getTestShopParams;
import static com.rbkmoney.dark.api.claimmanagement.converter.party.data.TestShopData.getTestSwagShopModificationUnit;
import static com.rbkmoney.swag.claim_management.model.ShopModification.ShopModificationTypeEnum.*;
import static org.junit.Assert.assertEquals;

public class ShopConvertersTest {

    @Test
    @Repeat(10)
    public void shopParamsConverterTest() throws IOException {
        ShopCreationModificationConverter converter = new ShopCreationModificationConverter();
        var swagShopParams = getTestShopParams();
        var resultShopParams = converter.convertToSwag(converter.convertToThrift(swagShopParams));
        assertEquals("Swag objects 'ShopParams' not equals", swagShopParams, resultShopParams);

        ShopParams thriftShopParams = new ShopParams();
        thriftShopParams = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftShopParams, new TBaseHandler<>(ShopParams.class));
        ShopParams resultThriftShopParams = converter.convertToThrift(converter.convertToSwag(thriftShopParams));

        assertEquals("Thrift objects 'ShopParams' not equals", thriftShopParams, resultThriftShopParams);
    }

    @Test
    public void shopAccountParamsConverterTest() throws IOException {
        ShopAccountCreationModificationConverter converter = new ShopAccountCreationModificationConverter();
        var swagShopAccountParams = EnhancedRandom.random(ShopAccountCreationModification.class);
        swagShopAccountParams.setShopModificationType(SHOPACCOUNTCREATIONMODIFICATION);

        var resultShopAccountParams = converter.convertToSwag(converter.convertToThrift(swagShopAccountParams));
        assertEquals("Swag objects 'ShopAccountParams' not equals", swagShopAccountParams,
                resultShopAccountParams);

        ShopAccountParams thriftShopAccountParams = new ShopAccountParams();
        thriftShopAccountParams = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftShopAccountParams, new TBaseHandler<>(ShopAccountParams.class));
        ShopAccountParams resultThriftShopAccountParams = converter.convertToThrift(converter.convertToSwag(thriftShopAccountParams));

        assertEquals("Thrift objects 'ShopAccountParams' not equals", thriftShopAccountParams, resultThriftShopAccountParams);
    }

    @Test
    public void scheduleModificationConverterTest() throws IOException {
        ShopPayoutScheduleModificationConverter converter = new ShopPayoutScheduleModificationConverter();
        var swagScheduleModification =
                EnhancedRandom.random(ShopPayoutScheduleModification.class);
        swagScheduleModification.setShopModificationType(SHOPPAYOUTSCHEDULEMODIFICATION);

        var resultScheduleModification = converter.convertToSwag(converter.convertToThrift(swagScheduleModification));
        assertEquals("Swag objects 'ScheduleModification' not equals",
                swagScheduleModification, resultScheduleModification);

        ScheduleModification thriftScheduleModification = new ScheduleModification();
        thriftScheduleModification = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftScheduleModification, new TBaseHandler<>(ScheduleModification.class));
        ScheduleModification resultThriftScheduleModification = converter.convertToThrift(
                converter.convertToSwag(thriftScheduleModification)
        );
        assertEquals("Thrift objects 'ScheduleModification' not equals",
                thriftScheduleModification, resultThriftScheduleModification);
    }

    @Test
    public void shopContractModificationConverterTest() throws IOException {
        ShopContractModificationConverter converter = new ShopContractModificationConverter();
        var swagShopContractModification =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ShopContractModification.class);
        swagShopContractModification.setShopModificationType(SHOPCONTRACTMODIFICATION);

        var resultShopContractModificationConverter = converter.convertToSwag(
                converter.convertToThrift(swagShopContractModification)
        );
        assertEquals("Swag objects 'ShopContractModification' not equals",
                swagShopContractModification, resultShopContractModificationConverter);

        ShopContractModification thriftShopContractModification = new ShopContractModification();
        thriftShopContractModification = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftShopContractModification, new TBaseHandler<>(ShopContractModification.class));
        ShopContractModification resultThriftShopContractModification = converter.convertToThrift(
                converter.convertToSwag(thriftShopContractModification)
        );
        assertEquals("Thrift objects 'ShopContractModification' not equals",
                thriftShopContractModification, resultThriftShopContractModification);
    }

    @Test
    public void shopDetailsConverterTest() throws IOException {
        ShopDetailsModificationConverter converter = new ShopDetailsModificationConverter();
        var swagShopDetails = EnhancedRandom.random(ShopDetailsModification.class);
        swagShopDetails.setShopModificationType(SHOPDETAILSMODIFICATION);
        var resultShopContractModificationConverter = converter.convertToSwag(converter.convertToThrift(swagShopDetails));
        assertEquals("Swag objects 'ShopDetails' not equals",
                swagShopDetails, resultShopContractModificationConverter);

        ShopDetails thriftShopDetails = new ShopDetails();
        thriftShopDetails = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftShopDetails, new TBaseHandler<>(ShopDetails.class));
        ShopDetails resultThriftShopDetails = converter.convertToThrift(
                converter.convertToSwag(thriftShopDetails)
        );
        assertEquals("Thrift objects 'ShopDetails' not equals", thriftShopDetails, resultThriftShopDetails);
    }

    @Test
    @Repeat(10)
    public void shopModificationUnitConverterTest() throws IOException {
        ShopModificationUnitConverter converter = new ShopModificationUnitConverter(
                new ShopCreationModificationConverter(),
                new ShopPayoutScheduleModificationConverter(),
                new ShopDetailsModificationConverter(),
                new ShopAccountCreationModificationConverter(),
                new ShopContractModificationConverter()
        );
        var swagShopModificationUnit = getTestSwagShopModificationUnit();
        var resultShopModificationUnit = converter.convertToSwag(converter.convertToThrift(swagShopModificationUnit));
        assertEquals("Swag objects 'ShopModificationUnit' not equals",
                swagShopModificationUnit, resultShopModificationUnit);

        ShopModificationUnit thriftShopModificationUnit = new ShopModificationUnit();
        thriftShopModificationUnit = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftShopModificationUnit, new TBaseHandler<>(ShopModificationUnit.class));
        ShopModificationUnit resultThriftShopModificationUnit = converter.convertToThrift(
                converter.convertToSwag(thriftShopModificationUnit));
        assertEquals("Thrift objects 'ShopModificationUnit' not equals",
                thriftShopModificationUnit, resultThriftShopModificationUnit);
    }

}
