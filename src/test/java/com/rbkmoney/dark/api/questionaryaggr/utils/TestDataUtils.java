package com.rbkmoney.dark.api.questionaryaggr.utils;

import com.rbkmoney.damsel.base.Content;
import com.rbkmoney.damsel.domain.BankCardPaymentSystem;
import com.rbkmoney.damsel.merch_stat.*;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponses;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponses;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponses;
import com.rbkmoney.swag.questionary.model.*;
import com.rbkmoney.swag.questionary.model.RussianBankAccount;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusParams;
import io.github.benas.randombeans.api.EnhancedRandom;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestDataUtils {

    public static KonturFocusParams getKonturFocusParams() {
        return EnhancedRandom.random(KonturFocusParams.class);
    }

    public static KonturFocusResponse createThriftKonturFocusReqResponse() throws IOException {
        KonturFocusResponse konturFocusResponse = new KonturFocusResponse();
        ReqResponses reqResponses = new ReqResponses();
        reqResponses = new MockTBaseProcessor(MockMode.ALL).process(reqResponses, new TBaseHandler<>(ReqResponses.class));
        konturFocusResponse.setReqResponses(reqResponses);
        return konturFocusResponse;
    }

    public static KonturFocusResponse createThriftKonturFocusLicensesResponse() throws IOException {
        KonturFocusResponse konturFocusResponse = new KonturFocusResponse();
        LicencesResponses licencesResponses = new LicencesResponses();
        licencesResponses = new MockTBaseProcessor(MockMode.ALL).process(licencesResponses, new TBaseHandler<>(LicencesResponses.class));
        konturFocusResponse.setLicencesResponses(licencesResponses);
        return konturFocusResponse;
    }

    public static KonturFocusResponse createThriftKonturFocusEgrDetailsResponse() throws IOException {
        KonturFocusResponse konturFocusResponse = new KonturFocusResponse();
        EgrDetailsResponses egrDetailsResponses = new EgrDetailsResponses();
        egrDetailsResponses = new MockTBaseProcessor(MockMode.ALL).process(egrDetailsResponses, new TBaseHandler<>(EgrDetailsResponses.class));
        konturFocusResponse.setEgrDetailsResponses(egrDetailsResponses);
        return konturFocusResponse;
    }

    public static DaDataResponse createThriftDaDataAddressResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        AddressResponse addressResponse = new AddressResponse();
        addressResponse = new MockTBaseProcessor(MockMode.ALL).process(addressResponse, new TBaseHandler<>(AddressResponse.class));
        daDataResponse.setAddressResponse(addressResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDataPartyResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        PartyResponse partyResponse = new PartyResponse();
        partyResponse = new MockTBaseProcessor(MockMode.ALL).process(partyResponse, new TBaseHandler<>(PartyResponse.class));
        daDataResponse.setPartyResponse(partyResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDataOkvedResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        OkvedResponse okvedResponse = new OkvedResponse();
        okvedResponse = new MockTBaseProcessor(MockMode.ALL).process(okvedResponse, new TBaseHandler<>(OkvedResponse.class));
        daDataResponse.setOkvedResponse(okvedResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDataBankResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        BankResponse bankResponse = new BankResponse();
        bankResponse = new MockTBaseProcessor(MockMode.ALL).process(bankResponse, new TBaseHandler<>(BankResponse.class));
        daDataResponse.setBankResponse(bankResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDataFioResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        FioResponse fioResponse = new FioResponse();
        fioResponse = new MockTBaseProcessor(MockMode.ALL).process(fioResponse, new TBaseHandler<>(FioResponse.class));
        daDataResponse.setFioResponse(fioResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDateFmsUnitResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        FmsUnitResponse fmsUnitResponse = new FmsUnitResponse();
        fmsUnitResponse = new MockTBaseProcessor(MockMode.ALL).process(fmsUnitResponse, new TBaseHandler<>(FmsUnitResponse.class));
        daDataResponse.setFmsUnitResponse(fmsUnitResponse);
        return daDataResponse;
    }

    public static QuestionaryParams createIndividualEntityQuestionarySwag() {
        QuestionaryParams questionaryParams = EnhancedRandom.random(QuestionaryParams.class);
        questionaryParams.setVersion("0");
        IndividualEntityContractor individualEntityContractor = new IndividualEntityContractor();
        RussianIndividualEntity russianIndividualEntity = EnhancedRandom.random(RussianIndividualEntity.class);
        russianIndividualEntity.setIdentityDocument(EnhancedRandom.random(RussianDomesticPassport.class));
        russianIndividualEntity.setRegistrationInfo(EnhancedRandom.random(IndividualRegistrationInfo.class));
        russianIndividualEntity.setResidencyInfo(EnhancedRandom.random(IndividualResidencyInfo.class));
        russianIndividualEntity.getAdditionalInfo().setBankAccount(EnhancedRandom.random(com.rbkmoney.swag.questionary.model.RussianBankAccount.class));
        russianIndividualEntity.getAdditionalInfo().setFinancialPosition(Collections.singletonList(new AnnualFinancialStatements()));
        russianIndividualEntity.getAdditionalInfo().setBusinessInfo(Collections.singletonList(new AnotherBusiness().description("test")));
        individualEntityContractor.setIndividualEntity(russianIndividualEntity);
        questionaryParams.getData().setContractor(individualEntityContractor);
        questionaryParams.getData().setBankAccount(EnhancedRandom.random(com.rbkmoney.swag.questionary.model.RussianBankAccount.class));
        return questionaryParams;
    }

    public static com.rbkmoney.questionary.manage.QuestionaryParams createIndividualEntityQuestionaryThrift() throws IOException {
        var questionaryParams = new com.rbkmoney.questionary.manage.QuestionaryParams();
        questionaryParams.setId("123456");
        questionaryParams.setOwnerId("12413");
        var questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();
        questionaryData = new MockTBaseProcessor(MockMode.ALL)
                .process(questionaryData, new TBaseHandler<>(com.rbkmoney.questionary.manage.QuestionaryData.class));
        var individualEntity = new com.rbkmoney.questionary.IndividualEntity();
        individualEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(individualEntity, new TBaseHandler<>(com.rbkmoney.questionary.IndividualEntity.class));
        questionaryData.setContractor(com.rbkmoney.questionary.Contractor.individual_entity(individualEntity));
        questionaryParams.setData(questionaryData);
        return questionaryParams;
    }

    public static QuestionaryParams createLegalEntityQuestionarySwag() {
        QuestionaryParams questionaryParams = EnhancedRandom.random(QuestionaryParams.class);
        questionaryParams.setVersion("0");
        LegalEntityContractor legalEntityContractor = new LegalEntityContractor();
        RussianLegalEntity russianLegalEntity = EnhancedRandom.random(RussianLegalEntity.class);
        russianLegalEntity.setRegistrationInfo(EnhancedRandom.random(LegalRegistrationInfo.class));
        russianLegalEntity.setResidencyInfo(EnhancedRandom.random(LegalResidencyInfo.class));
        russianLegalEntity.getLegalOwnerInfo().setIdentityDocument(EnhancedRandom.random(RussianDomesticPassport.class));
        russianLegalEntity.getAdditionalInfo().setBankAccount(EnhancedRandom.random(com.rbkmoney.swag.questionary.model.RussianBankAccount.class));
        russianLegalEntity.getAdditionalInfo().setFinancialPosition(Collections.singletonList(new AnnualTaxReturnWithMark()));
        russianLegalEntity.getAdditionalInfo().setBusinessInfo(Collections.singletonList(new RetailTradeBusiness()));
        LegalOwnerInfo legalOwnerInfo = EnhancedRandom.random(LegalOwnerInfo.class);
        legalOwnerInfo.setIdentityDocument(EnhancedRandom.random(RussianDomesticPassport.class));
        russianLegalEntity.setLegalOwnerInfo(legalOwnerInfo);
        BeneficialOwner beneficialOwner = EnhancedRandom.random(BeneficialOwner.class);
        beneficialOwner.setIdentityDocument(EnhancedRandom.random(RussianDomesticPassport.class));
        russianLegalEntity.setBeneficialOwner(Collections.singletonList(beneficialOwner));
        FoundersInfo foundersInfo = new FoundersInfo();
        foundersInfo.setFounders(Collections.singletonList(EnhancedRandom.random(RussianLegalEntityFounder.class)));
        foundersInfo.setHeads(Collections.singletonList(EnhancedRandom.random(FounderHead.class)));
        foundersInfo.setLegalOwner(EnhancedRandom.random(FounderHead.class));
        russianLegalEntity.setFoundersInfo(foundersInfo);
        legalEntityContractor.setLegalEntity(russianLegalEntity);
        questionaryParams.getData().setContractor(legalEntityContractor);
        questionaryParams.getData().setBankAccount(EnhancedRandom.random(RussianBankAccount.class));
        return questionaryParams;
    }

    public static com.rbkmoney.questionary.manage.QuestionaryParams createLegalEntityQuestionaryThrift() throws IOException {
        var questionaryParams = new com.rbkmoney.questionary.manage.QuestionaryParams();
        questionaryParams.setId("123456");
        questionaryParams.setOwnerId("12413");
        var questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();
        questionaryData = new MockTBaseProcessor(MockMode.ALL)
                .process(questionaryData, new TBaseHandler<>(com.rbkmoney.questionary.manage.QuestionaryData.class));
        var legalEntity = new com.rbkmoney.questionary.LegalEntity();
        legalEntity = new MockTBaseProcessor(MockMode.ALL)
                .process(legalEntity, new TBaseHandler<>(com.rbkmoney.questionary.LegalEntity.class));
        questionaryData.setContractor(com.rbkmoney.questionary.Contractor.legal_entity(legalEntity));
        questionaryParams.setData(questionaryData);
        return questionaryParams;
    }

    public static StatResponse createStatResponse() {
        return new StatResponse()
                .setData(createStatResponseData())
                .setContinuationToken("1");
    }

    private static StatResponseData createStatResponseData() {
        StatResponseData statResponseData = new StatResponseData();
        statResponseData.setEnrichedInvoices(List.of(createEnrichedStatInvoiceWithRefund(), createEnrichedStatInvoiceWithoutRefund()));
        return statResponseData;
    }

    private static EnrichedStatInvoice createEnrichedStatInvoiceWithRefund() {
        return new EnrichedStatInvoice(getStatInvoice(),
                List.of(getStatPayment()),
                List.of(new StatRefund(null,null,null,null,null, InvoicePaymentRefundStatus.succeeded(new InvoicePaymentRefundSucceeded()), OffsetDateTime.now().toString(),0L,0L,null)));
    }


    private static EnrichedStatInvoice createEnrichedStatInvoiceWithoutRefund() {
        return new EnrichedStatInvoice(getStatInvoice(),
                List.of(getStatPayment()),
                List.of());
    }

    private static StatInvoice getStatInvoice() {
        StatInvoice invoice = new StatInvoice(null, null, null, OffsetDateTime.now().toString(), null, null, null, 0L, null);
        invoice.setContext(new Content("test", ByteBuffer.wrap("{'test_invoice':'test'}".getBytes())));
        return invoice;
    }

    private static StatPayment getStatPayment() {
        StatPayment statPayment = new StatPayment(null, null, null, null, OffsetDateTime.now().toString(), InvoicePaymentStatus.captured(new InvoicePaymentCaptured()), 0L, 0L, null, getPayer(), InvoicePaymentFlow.hold(new InvoicePaymentFlowHold()), 0L);
        statPayment.setContext(new Content("test", ByteBuffer.wrap("{'test_payment':'test'}".getBytes())));
        return statPayment;
    }

    private static Payer getPayer() {
        PaymentResourcePayer paymentResourcePayer = new PaymentResourcePayer();
        PaymentTool paymentTool = new PaymentTool();
        paymentTool.setBankCard(new BankCard("token", BankCardPaymentSystem.dankort, "123", "12*3"));
        paymentResourcePayer.setPaymentTool(paymentTool);
        return Payer.payment_resource(paymentResourcePayer);
    }

}
