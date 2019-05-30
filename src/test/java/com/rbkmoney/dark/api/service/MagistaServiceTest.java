package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.merch_stat.DarkMessiahStatisticsSrv;
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

import static com.rbkmoney.dark.api.utils.TestDataUtils.createStatResponse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MagistaServiceTest.Config.class)
public class MagistaServiceTest {

    private MagistaService magistaService;
    @Autowired DarkMessiahStatisticsSrv.Iface magistaClient;

    @Before
    public void init() {
        magistaService = new MagistaService(magistaClient);
    }

    @Test
    public void testPaymentsByQuery() throws TException {
        when(magistaClient.getByQuery(any()))
                .thenReturn(createStatResponse());

        InlineResponse200 paymentsByQuery = magistaService.getPaymentsByQuery(null,
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
    public void testRefundsByQuery() throws TException {
        when(magistaClient.getByQuery(any()))
                .thenReturn(createStatResponse());

        InlineResponse200 paymentsByQuery = magistaService.getRefundsByQuery(null,
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

    @Configuration
    static class Config {

        @Bean
        public DarkMessiahStatisticsSrv.Iface magistaClient() {
            return mock(DarkMessiahStatisticsSrv.Iface.class);
        }

    }
}
