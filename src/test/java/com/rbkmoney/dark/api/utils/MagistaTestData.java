package com.rbkmoney.dark.api.utils;

import com.rbkmoney.damsel.base.Content;
import com.rbkmoney.damsel.domain.BankCardPaymentSystem;
import com.rbkmoney.damsel.merch_stat.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MagistaTestData {

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
