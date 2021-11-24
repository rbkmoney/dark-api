package com.rbkmoney.dark.api.magista.converter;

import com.rbkmoney.damsel.base.Content;
import com.rbkmoney.damsel.merch_stat.*;
import com.rbkmoney.dark.api.converter.StatPaymentToPaymentSearchResultConverter;
import com.rbkmoney.swag.dark_api.model.PaymentSearchResult;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.util.Map;

public class StatPaymentToPaymentSearchResultConverterTest {

    @Test
    public void convert() {
        StatPayment statPayment = new StatPayment("1", "1", "1", "1", OffsetDateTime.now().toString(),
                InvoicePaymentStatus.captured(new InvoicePaymentCaptured().setAt(OffsetDateTime.now().toString())),
                100L, 100L, "KEK",
                Payer.customer(new CustomerPayer()), InvoicePaymentFlow.hold(new InvoicePaymentFlowHold()), 1L);
        statPayment.setContext(new Content("kek", ByteBuffer.wrap("{ \"test\": \"kek\" }".getBytes())));
        PaymentSearchResult convert = StatPaymentToPaymentSearchResultConverter.convert(statPayment, null);
        Assert.assertNotNull(convert.getStatusChangedAt());
        Assert.assertTrue(convert.getInvoiceMetadata() instanceof Map);
        Assert.assertTrue(convert.getMetadata() instanceof Map);
    }

    @Test
    public void convertPendingPayment() {
        StatPayment statPayment = new StatPayment("1", "1", "1", "1", OffsetDateTime.now().toString(),
                InvoicePaymentStatus.pending(new InvoicePaymentPending()), 100L, 100L, "KEK",
                Payer.customer(new CustomerPayer()), InvoicePaymentFlow.hold(new InvoicePaymentFlowHold()), 1L);
        statPayment.setContext(new Content("kek", ByteBuffer.wrap("{ \"test\": \"kek\" }".getBytes())));
        PaymentSearchResult convert = StatPaymentToPaymentSearchResultConverter.convert(statPayment, null);
        Assert.assertNull(convert.getStatusChangedAt());
        Assert.assertTrue(convert.getInvoiceMetadata() instanceof Map);
        Assert.assertTrue(convert.getMetadata() instanceof Map);
    }

}
