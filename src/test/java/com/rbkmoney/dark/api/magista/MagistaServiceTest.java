package com.rbkmoney.dark.api.magista;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rbkmoney.damsel.merch_stat.DarkMessiahStatisticsSrv;
import com.rbkmoney.dark.api.service.MagistaService;
import com.rbkmoney.swag.dark_api.model.InlineResponse200;
import com.rbkmoney.swag.dark_api.model.PaymentFlowHold;
import com.rbkmoney.swag.dark_api.model.PaymentSearchResult;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;

import static com.rbkmoney.dark.api.utils.MagistaTestData.createStatResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MagistaServiceTest.Config.class)
public class MagistaServiceTest {

    private MagistaService magistaService;
    @Autowired
    DarkMessiahStatisticsSrv.Iface magistaClient;

    @Before
    public void init() {
        magistaService = new MagistaService(magistaClient);
    }

    @Test
    public void testPaymentsByQuery() throws TException, JsonProcessingException {
        when(magistaClient.getByQuery(any()))
                .thenReturn(createStatResponse());

        InlineResponse200 paymentsByQuery = magistaService.getPaymentsByQuery(null,
                null,
                OffsetDateTime.parse("2016-03-22T00:12:00Z"),
                OffsetDateTime.parse("2016-03-22T01:12:00Z"),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertEquals("1", paymentsByQuery.getContinuationToken());
        paymentsByQuery.getResult().forEach(enrichedSearchResult -> {
            PaymentSearchResult payment = enrichedSearchResult.getPayment();
            assertTrue(payment.getInvoiceMetadata().toString().contains("test_invoice"));
            assertTrue(payment.getMetadata().toString().contains("test_payment"));
            assertTrue(payment.getFlow() instanceof PaymentFlowHold);
            assertEquals("CAPTURED", payment.getStatus().name());
            if (enrichedSearchResult.getRefund() != null) {
                assertEquals("SUCCEEDED", enrichedSearchResult.getRefund().getStatus().name());
            }

        });
    }

    @Test
    public void testRefundsByQuery() throws TException, JsonProcessingException {
        when(magistaClient.getByQuery(any()))
                .thenReturn(createStatResponse());

        InlineResponse200 paymentsByQuery = magistaService.getRefundsByQuery(null,
                null,
                OffsetDateTime.parse("2016-03-22T00:12:00Z"),
                OffsetDateTime.parse("2016-03-22T01:12:00Z"),
                null,
                null,
                null,
                null,
                null,
                null);


        assertEquals("1", paymentsByQuery.getContinuationToken());
        paymentsByQuery.getResult().forEach(enrichedSearchResult -> {
            PaymentSearchResult payment = enrichedSearchResult.getPayment();

            assertTrue(payment.getInvoiceMetadata().toString().contains("test_invoice"));
            assertTrue(payment.getMetadata().toString().contains("test_payment"));
            assertTrue(payment.getFlow() instanceof PaymentFlowHold);
            assertEquals("CAPTURED", payment.getStatus().name());
            if (enrichedSearchResult.getRefund() != null) {
                assertEquals("SUCCEEDED", enrichedSearchResult.getRefund().getStatus().name());
            }
        });
    }

    @Configuration
    static class Config {

        @Bean
        public DarkMessiahStatisticsSrv.Iface magistaClient() {
            return mock(DarkMessiahStatisticsSrv.Iface.class);
        }

    }
}
