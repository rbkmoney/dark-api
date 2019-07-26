package com.rbkmoney.dark.api.utils;

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
import com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent;
import com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponses;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponses;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponses;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusParams;
import com.rbkmoney.swag.questionary_aggr_proxy.model.OkvedContent;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.eclipse.jetty.util.IO;
import org.mockito.Mock;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
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
        Payer payer = Payer.payment_resource(paymentResourcePayer);
        return payer;
    }

}
