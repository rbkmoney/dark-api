package com.rbkmoney.dark.api.controller;

import com.rbkmoney.damsel.analytics.OffsetAmount;
import com.rbkmoney.damsel.analytics.OffsetCount;
import com.rbkmoney.damsel.analytics.PaymentStatus;
import com.rbkmoney.damsel.analytics.SplitUnit;
import com.rbkmoney.damsel.analytics.*;
import com.rbkmoney.dark.api.DarkApiApplication;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.swag.analytics.model.*;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DarkApiApplication.class})
@AutoConfigureMockMvc
public class AnalyticsControllerTest {

    public static final String PARTY_ID = "partyId";
    public static final Long AMOUNT = 100L;
    public static final String CURRENCY = "RUB";
    public static final Long COUNT = 12L;
    public static final String NAME = "Name";
    public static final Long PERCENTS = 89L;
    public static final Long OFFSET = 12L;

    @Autowired
    AnalyticsController analyticsController;

    @MockBean
    KeycloakService keycloakService;

    @MockBean
    AnalyticsServiceSrv.Iface analyticsClient;

    private final String xRequestID = "xRequestID";
    private final String xRequestDeadline = "xRequestID";

    @Before
    public void init() {
        Mockito.when(keycloakService.getPartyId()).thenReturn(PARTY_ID);
    }

    @Test
    public void getAveragePayment() throws TException {
        ArrayList<CurrencyGroupedAmount> groupedAmounts = new ArrayList<>();
        groupedAmounts.add(new CurrencyGroupedAmount()
                .setAmount(AMOUNT)
                .setCurrency(CURRENCY)
        );
        Mockito.when(analyticsClient.getAveragePayment(any()))
                .thenReturn(new AmountResponse().setGroupsAmount(groupedAmounts));

        ResponseEntity<InlineResponse200> averagePayment = analyticsController.getAveragePayment(xRequestID,
                OffsetDateTime.now(), OffsetDateTime.now(), xRequestDeadline, List.of());

        assertTrue(averagePayment.getStatusCode().is2xxSuccessful());

        AmountResult amountResult = averagePayment.getBody().getResult().get(0);

        assertEquals(AMOUNT, amountResult.getAmount());
        assertEquals(CURRENCY, amountResult.getCurrency());
    }

    @Test
    public void getCurrentBalances() {
        //TODO later
    }

    @Test
    public void getPaymentsAmount() throws TException {
        ArrayList<CurrencyGroupedAmount> groupedAmounts = new ArrayList<>();
        groupedAmounts.add(new CurrencyGroupedAmount()
                .setAmount(AMOUNT)
                .setCurrency(CURRENCY)
        );
        Mockito.when(analyticsClient.getPaymentsAmount(any()))
                .thenReturn(new AmountResponse().setGroupsAmount(groupedAmounts));

        ResponseEntity<InlineResponse200> averagePayment = analyticsController.getPaymentsAmount(xRequestID,
                OffsetDateTime.now(), OffsetDateTime.now(), xRequestDeadline, List.of());

        assertTrue(averagePayment.getStatusCode().is2xxSuccessful());

        AmountResult amountResult = averagePayment.getBody().getResult().get(0);

        assertEquals(AMOUNT, amountResult.getAmount());
        assertEquals(CURRENCY, amountResult.getCurrency());
    }

    @Test
    public void getPaymentsCount() throws TException {
        ArrayList<CurrecyGroupCount> groupedAmounts = new ArrayList<>();
        groupedAmounts.add(new CurrecyGroupCount()
                .setCount(COUNT)
                .setCurrency(CURRENCY)
        );
        Mockito.when(analyticsClient.getPaymentsCount(any()))
                .thenReturn(new CountResponse().setGroupsCount(groupedAmounts));

        ResponseEntity<InlineResponse2002> paymentsCount = analyticsController.getPaymentsCount(xRequestID,
                OffsetDateTime.now(), OffsetDateTime.now(), xRequestDeadline, List.of());

        assertTrue(paymentsCount.getStatusCode().is2xxSuccessful());

        CountResult countResult = paymentsCount.getBody().getResult().get(0);

        assertEquals(COUNT, countResult.getCount());
        assertEquals(CURRENCY, countResult.getCurrency());
    }

    @Test
    public void getPaymentsErrorDistribution() throws TException {
        ArrayList<NamingDistribution> namingDistributions = new ArrayList<>();
        namingDistributions.add(new NamingDistribution()
                .setName(NAME)
                .setPercents(PERCENTS)
        );
        Mockito.when(analyticsClient.getPaymentsErrorDistribution(any()))
                .thenReturn(new ErrorDistributionsResponse().setErrorDistributions(namingDistributions));

        ResponseEntity<InlineResponse2003> paymentsErrorDistribution = analyticsController.getPaymentsErrorDistribution(xRequestID,
                OffsetDateTime.now(), OffsetDateTime.now(), xRequestDeadline, List.of());

        assertTrue(paymentsErrorDistribution.getStatusCode().is2xxSuccessful());

        PaymentsErrorsDistributionResult paymentsErrorsDistributionResult = paymentsErrorDistribution.getBody().getResult().get(0);

        assertEquals(NAME, paymentsErrorsDistributionResult.getError());
        assertEquals(PERCENTS, paymentsErrorsDistributionResult.getPercents());
    }

    @Test
    public void getPaymentsSplitAmount() throws TException {
        ArrayList<GroupedCurrencyOffsetAmount> groupedCurrencyOffsetAmounts = new ArrayList<>();
        ArrayList<OffsetAmount> offsetAmounts = new ArrayList<>();
        offsetAmounts.add(new OffsetAmount()
                .setAmount(AMOUNT)
                .setOffset(OFFSET));
        groupedCurrencyOffsetAmounts.add(new GroupedCurrencyOffsetAmount()
                .setCurrency(CURRENCY)
                .setOffsetAmounts(offsetAmounts));
        Mockito.when(analyticsClient.getPaymentsSplitAmount(any()))
                .thenReturn(new SplitAmountResponse()
                        .setGroupedCurrencyAmounts(groupedCurrencyOffsetAmounts)
                        .setResultSplitUnit(SplitUnit.DAY));

        ResponseEntity<InlineResponse2004> paymentsSplitAmount = analyticsController.getPaymentsSplitAmount(xRequestID,
                OffsetDateTime.now(), OffsetDateTime.now(), SplitUnit.DAY.name(), xRequestDeadline, List.of());

        assertTrue(paymentsSplitAmount.getStatusCode().is2xxSuccessful());

        SplitAmountResult splitAmountResult = paymentsSplitAmount.getBody().getResult().get(0);

        assertEquals(CURRENCY, splitAmountResult.getCurrency());
        assertEquals(SplitAmountResult.SplitUnitEnum.DAY, splitAmountResult.getSplitUnit());
        com.rbkmoney.swag.analytics.model.OffsetAmount offsetAmount = splitAmountResult.getOffsetAmounts().get(0);

        assertEquals(AMOUNT, offsetAmount.getAmount());
        assertEquals(OFFSET, offsetAmount.getOffset());
    }

    @Test
    public void getPaymentsSplitCount() throws TException {
        ArrayList<GroupedCurrencyOffsetCount> groupedCurrencyOffsetCounts = new ArrayList<>();
        ArrayList<GroupedStatusOffsetCount> offsetCounts = new ArrayList<>();
        ArrayList<OffsetCount> countArrayList = new ArrayList<>();
        countArrayList.add(new OffsetCount()
                .setCount(COUNT)
                .setOffset(OFFSET));
        offsetCounts.add(new GroupedStatusOffsetCount()
                .setStatus(PaymentStatus.PENDING)
                .setOffsetCounts(countArrayList)
        );
        groupedCurrencyOffsetCounts.add(new GroupedCurrencyOffsetCount()
                .setCurrency(CURRENCY)
                .setOffsetAmounts(offsetCounts));
        Mockito.when(analyticsClient.getPaymentsSplitCount(any()))
                .thenReturn(new SplitCountResponse()
                        .setPaymentToolsDestrobutions(groupedCurrencyOffsetCounts)
                        .setResultSplitUnit(SplitUnit.DAY));

        ResponseEntity<InlineResponse2005> paymentsSplitCount = analyticsController.getPaymentsSplitCount(xRequestID,
                OffsetDateTime.now(), OffsetDateTime.now(), SplitUnit.DAY.name(), xRequestDeadline, List.of());

        assertTrue(paymentsSplitCount.getStatusCode().is2xxSuccessful());

        SplitCountResult splitCountResult = paymentsSplitCount.getBody().getResult().get(0);

        assertEquals(CURRENCY, splitCountResult.getCurrency());
        assertEquals(SplitCountResult.SplitUnitEnum.DAY, splitCountResult.getSplitUnit());
        StatusOffsetCount statusOffsetCount = splitCountResult.getStatusOffsetCounts().get(0);
        assertEquals(StatusOffsetCount.StatusEnum.PENDING, statusOffsetCount.getStatus());
        assertEquals(COUNT, statusOffsetCount.getOffsetCount().get(0).getCount());
        assertEquals(OFFSET, statusOffsetCount.getOffsetCount().get(0).getOffset());
    }

    @Test
    public void getPaymentsToolDistribution() throws TException {
        ArrayList<NamingDistribution> paymentToolsDistributions = new ArrayList<>();
        paymentToolsDistributions.add(new NamingDistribution()
                .setPercents(PERCENTS)
                .setName(NAME)
        );
        Mockito.when(analyticsClient.getPaymentsToolDistribution(any()))
                .thenReturn(new PaymentToolDistributionResponse()
                        .setPaymentToolsDistributions(paymentToolsDistributions));

        ResponseEntity<InlineResponse2001> paymentsCount = analyticsController.getPaymentsToolDistribution(xRequestID,
                OffsetDateTime.now(), OffsetDateTime.now(), xRequestDeadline, List.of());

        assertTrue(paymentsCount.getStatusCode().is2xxSuccessful());

        PaymentsToolDistributionResult paymentsToolDistributionResult = paymentsCount.getBody().getResult().get(0);

        assertEquals(PERCENTS, Long.valueOf(paymentsToolDistributionResult.getPercents()));
        assertEquals(NAME, paymentsToolDistributionResult.getName());
    }

    @Test
    public void getRefundsAmount() throws TException {
        ArrayList<CurrencyGroupedAmount> groupsAmount = new ArrayList<>();
        groupsAmount.add(new CurrencyGroupedAmount()
                .setCurrency(CURRENCY)
                .setAmount(AMOUNT)
        );
        Mockito.when(analyticsClient.getRefundsAmount(any()))
                .thenReturn(new AmountResponse().setGroupsAmount(groupsAmount));

        ResponseEntity<InlineResponse200> refundsAmount = analyticsController.getRefundsAmount(xRequestID,
                OffsetDateTime.now(), OffsetDateTime.now(), xRequestDeadline, List.of());

        assertTrue(refundsAmount.getStatusCode().is2xxSuccessful());

        AmountResult amountResult = refundsAmount.getBody().getResult().get(0);

        assertEquals(AMOUNT, Long.valueOf(amountResult.getAmount()));
        assertEquals(CURRENCY, amountResult.getCurrency());
    }
}