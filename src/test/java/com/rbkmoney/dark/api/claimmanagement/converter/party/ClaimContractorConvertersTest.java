package com.rbkmoney.dark.api.claimmanagement.converter.party;

import com.rbkmoney.damsel.claim_management.ContractorModificationUnit;
import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.dark.api.converter.claimmanagement.party.contractor.*;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;

import java.io.IOException;

import static com.rbkmoney.swag.claim_management.model.ContractorModification.ContractorModificationTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.ContractorType.ContractorTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.LegalEntityType.LegalEntityTypeEnum.INTERNATIONALLEGALENTITY;
import static com.rbkmoney.swag.claim_management.model.LegalEntityType.LegalEntityTypeEnum.RUSSIANLEGALENTITY;
import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.CONTRACTORMODIFICATIONUNIT;
import static org.junit.Assert.assertEquals;

public class ClaimContractorConvertersTest {

    @Test
    public void russianLegalEntityConverterTest() throws IOException {
        RussianLegalEntityConverter converter = new RussianLegalEntityConverter();
        var swagRussianLegalEntity = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.RussianLegalEntity.class);
        swagRussianLegalEntity.setLegalEntityType(RUSSIANLEGALENTITY);
        swagRussianLegalEntity.getRussianBankAccount().setPayoutToolType(null);
        swagRussianLegalEntity.getRussianBankAccount().setPayoutToolModificationType(null);
        var resultSwagRussianLegalEntity = converter.convertToSwag(
                converter.convertToThrift(swagRussianLegalEntity)
        );
        assertEquals("Swag objects 'RussianLegalEntity' not equals",
                swagRussianLegalEntity, resultSwagRussianLegalEntity);

        RussianLegalEntity thriftRussianLegalEntity = new RussianLegalEntity();
        thriftRussianLegalEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftRussianLegalEntity, new TBaseHandler<>(RussianLegalEntity.class));
        RussianLegalEntity resultRussianLegalEntity = converter.convertToThrift(
                converter.convertToSwag(thriftRussianLegalEntity)
        );
        assertEquals("Thrift objects 'RussianLegalEntity' (MockMode.ALL) not equals",
                thriftRussianLegalEntity, resultRussianLegalEntity);

        thriftRussianLegalEntity = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftRussianLegalEntity, new TBaseHandler<>(RussianLegalEntity.class));
        resultRussianLegalEntity = converter.convertToThrift(
                converter.convertToSwag(thriftRussianLegalEntity)
        );
        assertEquals("Thrift objects 'RussianLegalEntity' (MockMode.REQUIRED_ONLY) not equals",
                thriftRussianLegalEntity, resultRussianLegalEntity);
    }

    @Test
    public void internationalLegalEntityConverterTest() throws IOException {
        InternationalLegalEntityConverter converter = new InternationalLegalEntityConverter();
        var swagInternationalLegalEntity =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.InternationalLegalEntity.class);
        swagInternationalLegalEntity.setLegalEntityType(INTERNATIONALLEGALENTITY);

        var resultSwagInternationalLegalEntityConverter = converter.convertToSwag(
                converter.convertToThrift(swagInternationalLegalEntity)
        );
        assertEquals("Swag objects 'InternationalLegalEntity' not equals",
                swagInternationalLegalEntity, resultSwagInternationalLegalEntityConverter);

        InternationalLegalEntity thriftInternationalLegalEntity = new InternationalLegalEntity();
        thriftInternationalLegalEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftInternationalLegalEntity, new TBaseHandler<>(InternationalLegalEntity.class));
        InternationalLegalEntity resultInternationalLegalEntity = converter.convertToThrift(
                converter.convertToSwag(thriftInternationalLegalEntity)
        );
        assertEquals("Thrift objects 'InternationalLegalEntity' (MockMode.ALL) not equals",
                thriftInternationalLegalEntity, resultInternationalLegalEntity);

        thriftInternationalLegalEntity = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftInternationalLegalEntity, new TBaseHandler<>(InternationalLegalEntity.class));
        resultInternationalLegalEntity = converter.convertToThrift(
                converter.convertToSwag(thriftInternationalLegalEntity)
        );
        assertEquals("Thrift objects 'InternationalLegalEntity' (MockMode.REQUIRED_ONLY) not equals",
                thriftInternationalLegalEntity, resultInternationalLegalEntity);
    }

    @Test
    public void legalEntityConverterTest() throws IOException {
        ClaimLegalEntityConverter converter = new ClaimLegalEntityConverter(
                new InternationalLegalEntityConverter(),
                new RussianLegalEntityConverter()
        );
        var swagLegalEntity =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.LegalEntity.class);
        swagLegalEntity.setContractorType(LEGALENTITY);
        switch (swagLegalEntity.getLegalEntityType().getLegalEntityType()) {
            case RUSSIANLEGALENTITY:
                var swagRussianLegalEntity = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.RussianLegalEntity.class);
                swagRussianLegalEntity.setLegalEntityType(RUSSIANLEGALENTITY);
                swagRussianLegalEntity.getRussianBankAccount().setPayoutToolType(null);
                swagRussianLegalEntity.getRussianBankAccount().setPayoutToolModificationType(null);
                swagLegalEntity.setLegalEntityType(swagRussianLegalEntity);
                break;
            case INTERNATIONALLEGALENTITY:
                var swagInternationalLegalEntity =
                        EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.InternationalLegalEntity.class);
                swagInternationalLegalEntity.setLegalEntityType(INTERNATIONALLEGALENTITY);
                swagLegalEntity.setLegalEntityType(swagInternationalLegalEntity);
                break;
            default:
                throw new IllegalArgumentException("Unknown legal entity type!");
        }

        var resultSwagLegalEntity = converter.convertToSwag(converter.convertToThrift(swagLegalEntity));
        assertEquals("Swag objects 'LegalEntity' not equals",
                swagLegalEntity, resultSwagLegalEntity);

        LegalEntity thriftLegalEntity = new LegalEntity();
        thriftLegalEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftLegalEntity, new TBaseHandler<>(LegalEntity.class));
        LegalEntity resultLegalEntity = converter.convertToThrift(converter.convertToSwag(thriftLegalEntity));
        assertEquals("Thrift objects 'LegalEntity' (MockMode.ALL) not equals",
                thriftLegalEntity, resultLegalEntity);

        thriftLegalEntity = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftLegalEntity, new TBaseHandler<>(LegalEntity.class));
        resultLegalEntity = converter.convertToThrift(converter.convertToSwag(thriftLegalEntity));
        assertEquals("Thrift objects 'LegalEntity' (MockMode.REQUIRED_ONLY) not equals",
                thriftLegalEntity, resultLegalEntity);

    }

    @Test
    public void privateEntityConverterTest() throws IOException {
        PrivateEntityConverter converter = new PrivateEntityConverter();
        var swagPrivateEntity = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.PrivateEntity.class);
        swagPrivateEntity.setContractorType(PRIVATEENTITY);
        var resultSwagPrivateEntity = converter.convertToSwag(converter.convertToThrift(swagPrivateEntity));
        assertEquals("Swag objects 'PrivateEntity' not equals", swagPrivateEntity, resultSwagPrivateEntity);

        PrivateEntity thriftPrivateEntity = new PrivateEntity();
        thriftPrivateEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftPrivateEntity, new TBaseHandler<>(PrivateEntity.class));
        PrivateEntity resultPrivateEntity = converter.convertToThrift(converter.convertToSwag(thriftPrivateEntity));
        assertEquals("Thrift objects 'PrivateEntity' (MockMode.ALL) not equals",
                thriftPrivateEntity, resultPrivateEntity);

        thriftPrivateEntity = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftPrivateEntity, new TBaseHandler<>(PrivateEntity.class));
        resultPrivateEntity = converter.convertToThrift(converter.convertToSwag(thriftPrivateEntity));
        assertEquals("Thrift objects 'PrivateEntity' (MockMode.REQUIRED_ONLY) not equals",
                thriftPrivateEntity, resultPrivateEntity);
    }

    @Test
    public void contractorIdentificationLevelConverterTest() {
        ContractorIdentificationLevelConverter converter = new ContractorIdentificationLevelConverter();
        var swagIdentificationLevel = getTestContractorIdentificationLevel();
        var resultSwagIdentificationLevel = converter.convertToSwag(converter.convertToThrift(swagIdentificationLevel));
        assertEquals("Swag objects 'ContractorIdentificationLevel' not equals",
                swagIdentificationLevel, resultSwagIdentificationLevel);

        ContractorIdentificationLevel resultContractorIdentificationLevel = converter.convertToThrift(
                converter.convertToSwag(ContractorIdentificationLevel.full)
        );
        assertEquals("Thrift objects 'ContractorIdentificationLevel' (MockMode.ALL) not equals",
                ContractorIdentificationLevel.full, resultContractorIdentificationLevel);
    }

    @Test
    public void contractorModificationUnitConverterTest() throws IOException {
        ContractorModificationUnitConverter converter = new ContractorModificationUnitConverter(
                new ClaimContractorConverter(
                        new ClaimLegalEntityConverter(
                                new InternationalLegalEntityConverter(),
                                new RussianLegalEntityConverter()
                        ),
                        new PrivateEntityConverter()
                ),
                new ContractorIdentificationLevelConverter()
        );
        var swagContractorModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ContractorModificationUnit.class);
        swagContractorModificationUnit.setPartyModificationType(CONTRACTORMODIFICATIONUNIT);
        swagContractorModificationUnit.setModification(getTestContractorIdentificationLevel());
        var resultContractorModification = converter.convertToSwag(converter.convertToThrift(swagContractorModificationUnit));
        assertEquals("Swag objects 'ContractorModificationUnit' not equals",
                swagContractorModificationUnit, resultContractorModification);

        ContractorModificationUnit thriftContractorModificationUnit = new ContractorModificationUnit();
        thriftContractorModificationUnit = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftContractorModificationUnit, new TBaseHandler<>(ContractorModificationUnit.class));
        ContractorModificationUnit resultContractorModificationUnit = converter.convertToThrift(
                converter.convertToSwag(thriftContractorModificationUnit)
        );
        assertEquals("Thrift objects 'ContractorModificationUnit' (MockMode.ALL) not equals",
                thriftContractorModificationUnit, resultContractorModificationUnit);

        thriftContractorModificationUnit = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftContractorModificationUnit, new TBaseHandler<>(ContractorModificationUnit.class));
        resultContractorModificationUnit = converter.convertToThrift(
                converter.convertToSwag(thriftContractorModificationUnit)
        );
        assertEquals("Thrift objects 'ContractorModificationUnit' (MockMode.REQUIRED_ONLY) not equals",
                thriftContractorModificationUnit, resultContractorModificationUnit);

    }

    @Test
    public void contractorConverterTest() throws IOException {
        ClaimContractorConverter converter = new ClaimContractorConverter(
                new ClaimLegalEntityConverter(new InternationalLegalEntityConverter(), new RussianLegalEntityConverter()),
                new PrivateEntityConverter());
        var swagRegisteredUser = new com.rbkmoney.swag.claim_management.model.RegisteredUser();
        swagRegisteredUser.setContractorType(REGISTEREDUSER);
        swagRegisteredUser.setEmail("some email");
        var swagContractor = new com.rbkmoney.swag.claim_management.model.Contractor();
        swagContractor.setContractorModificationType(CONTRACTOR);
        swagContractor.setContractorType(swagRegisteredUser);

        var resultContractor = converter.convertToSwag(converter.convertToThrift(swagContractor));
        assertEquals("Swag objects 'ContractorIdentificationLevel' not equals",
                swagContractor, resultContractor);

        Contractor thriftContractor = new Contractor();
        thriftContractor = new MockTBaseProcessor(MockMode.ALL).process(thriftContractor, new TBaseHandler<>(Contractor.class));
        Contractor resultThriftContractor = converter.convertToThrift(converter.convertToSwag(thriftContractor));
        assertEquals("Thrift objects 'Contractor' (MockMode.ALL) not equals", thriftContractor, resultThriftContractor);

        thriftContractor = new MockTBaseProcessor(MockMode.REQUIRED_ONLY).process(thriftContractor, new TBaseHandler<>(Contractor.class));
        resultThriftContractor = converter.convertToThrift(converter.convertToSwag(thriftContractor));
        assertEquals("Thrift objects 'Contractor' (MockMode.REQUIRED_ONLY) not equals",
                thriftContractor, resultThriftContractor);
    }

    private static com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel getTestContractorIdentificationLevel() {
        var swagIdentificationLevel =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ContractorIdentificationLevel.class);
        swagIdentificationLevel.setContractorModificationType(CONTRACTORIDENTIFICATIONLEVEL);
        swagIdentificationLevel.setContractorIdentificationLevel(100);
        return swagIdentificationLevel;
    }

}
