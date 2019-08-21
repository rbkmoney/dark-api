package com.rbkmoney.dark.api.claimmanagement.converter.party;

import com.rbkmoney.damsel.claim_management.ContractAdjustmentModificationUnit;
import com.rbkmoney.damsel.claim_management.ContractModificationUnit;
import com.rbkmoney.damsel.claim_management.ContractParams;
import com.rbkmoney.damsel.claim_management.PayoutToolModificationUnit;
import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.dark.api.converter.claimmanagement.party.contract.*;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;

import java.io.IOException;

import static com.rbkmoney.dark.api.claimmanagement.converter.party.data.TestContractData.*;
import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.PayoutToolInfo.PayoutToolTypeEnum.RUSSIANBANKACCOUNT;
import static com.rbkmoney.swag.claim_management.model.RepresentativeDocument.DocumentTypeEnum.ARTICLESOFASSOCIATION;
import static org.junit.Assert.assertEquals;

public class ContractConvertersTest {

    @Test
    public void russianBankAccountConverterTest() throws IOException {
        RussianBankAccountConverter converter = new RussianBankAccountConverter();
        var swagRussianBankAccount = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.RussianBankAccount.class);
        swagRussianBankAccount.setPayoutToolType(RUSSIANBANKACCOUNT);
        swagRussianBankAccount.setPayoutToolModificationType(null);
        var resultSwagRussianBankAccount = converter.convertToSwag(
                converter.convertToThrift(swagRussianBankAccount));
        assertEquals("Swag objects 'RussianBankAccount' not equals",
                swagRussianBankAccount, resultSwagRussianBankAccount);

        RussianBankAccount thriftRussianBankAccount = new RussianBankAccount();
        thriftRussianBankAccount = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftRussianBankAccount, new TBaseHandler<>(RussianBankAccount.class));
        RussianBankAccount resultRussianBankAccount = converter.convertToThrift(
                converter.convertToSwag(thriftRussianBankAccount));
        assertEquals("Thrift objects 'RussianBankAccount' (MockMode.ALL) not equals",
                thriftRussianBankAccount, resultRussianBankAccount);

        thriftRussianBankAccount = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftRussianBankAccount, new TBaseHandler<>(RussianBankAccount.class));
        resultRussianBankAccount = converter.convertToThrift(
                converter.convertToSwag(thriftRussianBankAccount));
        assertEquals("Thrift objects 'RussianBankAccount' (MockMode.REQUIRED_ONLY) not equals",
                thriftRussianBankAccount, resultRussianBankAccount);
    }

    @Test
    public void internationalBankAccountConverterTest() throws IOException {
        InternationalBankAccountConverter converter = new InternationalBankAccountConverter();
        var swagInternationalBankAccount = getTestSwagInternationalBankAccount();
        var resultSwagInternationalBankAccount = converter.convertToSwag(
                converter.convertToThrift(swagInternationalBankAccount));
        assertEquals("Swag objects 'InternationalBankAccountConverter' not equals",
                swagInternationalBankAccount, resultSwagInternationalBankAccount);

        InternationalBankAccount thriftInternationalBankAccount = new InternationalBankAccount();
        thriftInternationalBankAccount = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftInternationalBankAccount, new TBaseHandler<>(InternationalBankAccount.class));
        InternationalBankAccount resultInternationalBankAccount = converter.convertToThrift(
                converter.convertToSwag(thriftInternationalBankAccount));
        assertEquals("Thrift objects 'InternationalBankAccountConverter' (MockMode.REQUIRED_ONLY) not equals",
                thriftInternationalBankAccount, resultInternationalBankAccount);

        resultInternationalBankAccount = converter.convertToThrift(
                converter.convertToSwag(getTestInternationalBankAccount()));
        assertEquals("Thrift objects 'InternationalBankAccountConverter' (MockMode.ALL) not equals",
                getTestInternationalBankAccount(), resultInternationalBankAccount);
    }

    @Test
    public void representativeDocumentConverterTest() throws IOException {
        RepresentativeDocumentConverter converter = new RepresentativeDocumentConverter();
        var swagRepresentativeDocument = prepareDocument(
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.RepresentativeDocument.class));
        var resultSwagRepresentativeDocument = converter.convertToSwag(
                converter.convertToThrift(swagRepresentativeDocument)
        );
        assertEquals("Swag objects 'RepresentativeDocument' not equals",
                swagRepresentativeDocument, resultSwagRepresentativeDocument);

        RepresentativeDocument thriftRepresentativeDocument = new RepresentativeDocument();
        thriftRepresentativeDocument = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftRepresentativeDocument, new TBaseHandler<>(RepresentativeDocument.class));
        RepresentativeDocument resultRepresentativeDocumentAllFields = converter.convertToThrift(
                converter.convertToSwag(thriftRepresentativeDocument)
        );
        assertEquals("Thrift objects 'RepresentativeDocument' (MockMode.ALL) not equals",
                thriftRepresentativeDocument, resultRepresentativeDocumentAllFields);

        thriftRepresentativeDocument = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftRepresentativeDocument, new TBaseHandler<>(RepresentativeDocument.class));
        RepresentativeDocument resultRepresentativeDocumentReqFields = converter.convertToThrift(
                converter.convertToSwag(thriftRepresentativeDocument)
        );
        assertEquals("Thrift objects 'RepresentativeDocument' (MockMode.ALL) not equals",
                thriftRepresentativeDocument, resultRepresentativeDocumentReqFields);

    }

    @Test
    public void legalAgreementConverterTest() throws IOException {
        LegalAgreementConverter converter = new LegalAgreementConverter();
        var swagLegalAgreement = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.LegalAgreement.class);
        swagLegalAgreement.setContractModificationType(LEGALAGREEMENT);
        var resultSwagLegalAgreement = converter.convertToSwag(
                converter.convertToThrift(swagLegalAgreement)
        );
        assertEquals("Swag objects 'LegalAgreement' not equals", swagLegalAgreement, resultSwagLegalAgreement);

        LegalAgreement thriftLegalAgreement = new LegalAgreement();
        thriftLegalAgreement = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftLegalAgreement, new TBaseHandler<>(LegalAgreement.class));
        LegalAgreement resultLegalAgreement = converter.convertToThrift(
                converter.convertToSwag(thriftLegalAgreement)
        );

        assertEquals("Thrift objects 'LegalAgreement' (MockMode.ALL) not equals", thriftLegalAgreement, resultLegalAgreement);

        thriftLegalAgreement = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftLegalAgreement, new TBaseHandler<>(LegalAgreement.class));
        LegalAgreement resultLegalAgreementReq = converter.convertToThrift(
                converter.convertToSwag(thriftLegalAgreement)
        );

        assertEquals("Thrift objects 'LegalAgreement' (MockMode.REQUIRED_ONLY) not equals", thriftLegalAgreement, resultLegalAgreementReq);
    }

    @Test
    public void reportPreferencesConverterTest() throws IOException {
        ReportPreferencesConverter converter = new ReportPreferencesConverter(new RepresentativeDocumentConverter());
        var swagReportPreferences = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ReportPreferences.class);
        swagReportPreferences.setContractModificationType(REPORTPREFERENCES);
        var articlesOfAssociation = new com.rbkmoney.swag.claim_management.model.ArticlesOfAssociation();
        articlesOfAssociation.setDocumentType(ARTICLESOFASSOCIATION);
        swagReportPreferences.getServiceAcceptanceActPreferences().getSigner().setDocument(articlesOfAssociation);

        var resultSwagReportPreferences = converter.convertToSwag(
                converter.convertToThrift(swagReportPreferences)
        );
        assertEquals("Swag objects 'ReportPreferences' not equals",
                swagReportPreferences, resultSwagReportPreferences);

        ReportPreferences thriftReportPreferences = new ReportPreferences();
        thriftReportPreferences = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftReportPreferences, new TBaseHandler<>(ReportPreferences.class));
        ReportPreferences resultReportPreferences = converter.convertToThrift(
                converter.convertToSwag(thriftReportPreferences)
        );
        assertEquals("Thrift objects 'ReportPreferences' (MockMode.ALL) not equals",
                thriftReportPreferences, resultReportPreferences);

        thriftReportPreferences = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftReportPreferences, new TBaseHandler<>(ReportPreferences.class));
        ReportPreferences resultReportPreferencesReq = converter.convertToThrift(
                converter.convertToSwag(thriftReportPreferences)
        );
        assertEquals("Thrift objects 'ReportPreferences' (MockMode.ALL) not equals",
                thriftReportPreferences, resultReportPreferencesReq);
    }

    @Test
    public void payoutToolModificationUnitConverterTest() throws IOException {
        PayoutToolModificationUnitConverter converter = new PayoutToolModificationUnitConverter(
                new PayoutToolInfoConverter(
                        new InternationalBankAccountConverter(),
                        new RussianBankAccountConverter()
                )
        );

        var swagPayoutToolModificationUnit = getTestSwagPayoutToolModificationUnit();
        PayoutToolModificationUnit tmpThriftPayoutToolModificationUnit = converter.convertToThrift(swagPayoutToolModificationUnit);
        var resultSwagPayoutToolModificationUnit = converter.convertToSwag(tmpThriftPayoutToolModificationUnit);
        assertEquals("Swag objects 'PayoutToolModificationUnit' not equals",
                swagPayoutToolModificationUnit, resultSwagPayoutToolModificationUnit);


        PayoutToolModificationUnit thriftPayoutToolModificationUnit = new PayoutToolModificationUnit();
        thriftPayoutToolModificationUnit = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftPayoutToolModificationUnit, new TBaseHandler<>(PayoutToolModificationUnit.class));
        var tmpSwagPayoutToolModificationUnit = converter.convertToSwag(thriftPayoutToolModificationUnit);
        PayoutToolModificationUnit resultPayoutToolModificationUnit = converter.convertToThrift(
                tmpSwagPayoutToolModificationUnit
        );
        assertEquals("Thrift objects 'PayoutToolModificationUnit' (MockMode.REQUIRED_ONLY) not equals",
                thriftPayoutToolModificationUnit, resultPayoutToolModificationUnit);

        var tmpSwagPayoutToolModificationUnitAll = converter.convertToSwag(getTestPayoutToolModificationUnit());
        PayoutToolModificationUnit resultPayoutToolModificationUnitAll = converter.convertToThrift(
                tmpSwagPayoutToolModificationUnitAll
        );
        assertEquals("Thrift objects 'PayoutToolModificationUnit' (MockMode.ALL) not equals",
                getTestPayoutToolModificationUnit(), resultPayoutToolModificationUnitAll);

    }


    private static PayoutToolModificationUnit getTestPayoutToolModificationUnit() {
        PayoutToolInfo payoutToolInfo = new PayoutToolInfo();
        payoutToolInfo.setInternationalBankAccount(getTestInternationalBankAccount());

        var payoutToolModification = new com.rbkmoney.damsel.claim_management.PayoutToolModification();
        payoutToolModification.setCreation(new com.rbkmoney.damsel.claim_management.PayoutToolParams()
                .setCurrency(new CurrencyRef().setSymbolicCode("RUB"))
                .setToolInfo(payoutToolInfo));

        PayoutToolModificationUnit payoutToolModificationUnit = new PayoutToolModificationUnit()
                .setPayoutToolId("toolID")
                .setModification(payoutToolModification);

        return payoutToolModificationUnit;
    }

    @Test
    public void contractModificationUnitConverterTest() {
        ContractModificationUnitConverter converter = new ContractModificationUnitConverter(
                new ContractModificationCreationConverter(),
                new ReportPreferencesConverter(new RepresentativeDocumentConverter()),
                new PayoutToolModificationUnitConverter(
                        new PayoutToolInfoConverter(
                                new InternationalBankAccountConverter(),
                                new RussianBankAccountConverter())),
                new ContractAdjustmentModificationUnitConverter(),
                new LegalAgreementConverter()
        );

        var swagContractModificationUnit = getTestSwagContractModificationUnit();
        ContractModificationUnit tmpThriftContractModificationUnit = converter.convertToThrift(swagContractModificationUnit);
        var resultSwagContractModificationUnit = converter.convertToSwag(tmpThriftContractModificationUnit);
        assertEquals("Swag objects 'ContractParams' not equals",
                swagContractModificationUnit, resultSwagContractModificationUnit);

        ContractModificationUnit resultContractParams = converter.convertToThrift(
                converter.convertToSwag(getTestThriftContractModificationUnit()));
        assertEquals("Thrift objects 'ContractModificationUnit' not equals",
                getTestThriftContractModificationUnit(), resultContractParams);
    }

    @Test
    public void contractModificationCreationConverterTest() throws IOException {
        ContractModificationCreationConverter converter = new ContractModificationCreationConverter();
        var swagContractParams = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ContractParams.class);
        swagContractParams.setContractModificationType(CONTRACTPARAMS);
        var resultSwagContractParams = converter.convertToSwag(
                converter.convertToThrift(swagContractParams)
        );
        assertEquals("Swag objects 'ContractParams' not equals", swagContractParams, resultSwagContractParams);

        ContractParams thriftContractParams = new ContractParams();
        thriftContractParams = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftContractParams, new TBaseHandler<>(ContractParams.class));
        ContractParams resultContractParams = converter.convertToThrift(
                converter.convertToSwag(thriftContractParams)
        );
        assertEquals("Thrift objects 'ContractParams' (MockMode.ALL) not equals",
                thriftContractParams, resultContractParams);

        thriftContractParams = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftContractParams, new TBaseHandler<>(ContractParams.class));
        ContractParams resultContractParamsReq = converter.convertToThrift(
                converter.convertToSwag(thriftContractParams)
        );
        assertEquals("Thrift objects 'ContractParams' (MockMode.REQUIRED_ONLY) not equals",
                thriftContractParams, resultContractParamsReq);
    }

    @Test
    public void contractAdjustmentModificationUnitConverterTest() throws IOException {
        var converter = new ContractAdjustmentModificationUnitConverter();
        var swagContractAdjustmentModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit.class);
        swagContractAdjustmentModificationUnit.setContractModificationType(CONTRACTADJUSTMENTMODIFICATIONUNIT);
        ContractAdjustmentModificationUnit tmpThriftContractAdjustmentModificationUnit =
                converter.convertToThrift(swagContractAdjustmentModificationUnit);
        var resultSwagContractAdjustmentModificationUnit = converter.convertToSwag(
                tmpThriftContractAdjustmentModificationUnit
        );
        assertEquals("Swag objects 'ContractAdjustmentModificationUnit' not equals",
                swagContractAdjustmentModificationUnit, resultSwagContractAdjustmentModificationUnit);

        ContractAdjustmentModificationUnit thriftContractAdjustmentModificationUnit = new ContractAdjustmentModificationUnit();
        thriftContractAdjustmentModificationUnit = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftContractAdjustmentModificationUnit, new TBaseHandler<>(ContractAdjustmentModificationUnit.class));
        ContractAdjustmentModificationUnit resultContractAdjustmentModificationUnit = converter.convertToThrift(
                converter.convertToSwag(thriftContractAdjustmentModificationUnit)
        );
        assertEquals("Thrift objects 'ContractAdjustmentModificationUnit' (MockMode.ALL) not equals",
                thriftContractAdjustmentModificationUnit, resultContractAdjustmentModificationUnit);

        thriftContractAdjustmentModificationUnit = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(thriftContractAdjustmentModificationUnit, new TBaseHandler<>(ContractAdjustmentModificationUnit.class));
        ContractAdjustmentModificationUnit resultContractAdjustmentModificationUnitReq = converter.convertToThrift(
                converter.convertToSwag(thriftContractAdjustmentModificationUnit)
        );
        assertEquals("Thrift objects 'ContractAdjustmentModificationUnit' (MockMode.REQUIRED_ONLY) not equals",
                thriftContractAdjustmentModificationUnit, resultContractAdjustmentModificationUnitReq);

    }

}
