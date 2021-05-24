package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.analytics.SplitUnit;
import com.rbkmoney.damsel.analytics.*;
import com.rbkmoney.dark.api.converter.analytics.AnalyticsConverter;
import com.rbkmoney.swag.analytics.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final AnalyticsServiceSrv.Iface analyticsClient;

    private final AnalyticsConverter analyticsConverter;

    public InlineResponse200 getAveragePayment(OffsetDateTime fromTime,
                                               OffsetDateTime toTime,
                                               String partyId,
                                               List<String> shopIds) throws TException {
        AmountResponse averagePayment = analyticsClient.getAveragePayment(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(shopIds))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.ISO_INSTANT))
                        .setToTime(toTime.format(DateTimeFormatter.ISO_INSTANT)))
        );
        return analyticsConverter.convertAmountResponse(averagePayment);
    }

    public InlineResponse200 getPaymentsAmount(OffsetDateTime fromTime,
                                               OffsetDateTime toTime,
                                               String partyId,
                                               List<String> shopIds) throws TException {
        AmountResponse paymentsAmount = analyticsClient.getPaymentsAmount(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(shopIds))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.ISO_INSTANT))
                        .setToTime(toTime.format(DateTimeFormatter.ISO_INSTANT)))
        );
        return analyticsConverter.convertAmountResponse(paymentsAmount);
    }

    public InlineResponse2002 getPaymentsCount(OffsetDateTime fromTime,
                                               OffsetDateTime toTime,
                                               String partyId,
                                               List<String> shopIds) throws TException {
        CountResponse countResponse = analyticsClient.getPaymentsCount(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(shopIds))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.ISO_INSTANT))
                        .setToTime(toTime.format(DateTimeFormatter.ISO_INSTANT)))
        );
        return analyticsConverter.convertCountResponse(countResponse);
    }

    public InlineResponse2003 getPaymentsErrorDistribution(OffsetDateTime fromTime,
                                                           OffsetDateTime toTime,
                                                           String partyId,
                                                           List<String> shopIds) throws TException {
        ErrorDistributionsResponse countResponse = analyticsClient.getPaymentsErrorDistribution(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(shopIds))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.ISO_INSTANT))
                        .setToTime(toTime.format(DateTimeFormatter.ISO_INSTANT)))
        );
        return analyticsConverter.convertErrorDistributionsResponse(countResponse);
    }

    public InlineResponse2004 getPaymentsSplitAmount(OffsetDateTime fromTime,
                                                     OffsetDateTime toTime,
                                                     String splitUnit,
                                                     String partyId,
                                                     List<String> shopIds)
            throws TException {
        SplitAmountResponse response = analyticsClient.getPaymentsSplitAmount(new SplitFilterRequest()
                .setSplitUnit(SplitUnit.valueOf(splitUnit))
                .setFilterRequest(new FilterRequest()
                        .setMerchantFilter(new MerchantFilter()
                                .setPartyId(partyId)
                                .setShopIds(shopIds))
                        .setTimeFilter(new TimeFilter()
                                .setFromTime(fromTime.format(DateTimeFormatter.ISO_INSTANT))
                                .setToTime(toTime.format(DateTimeFormatter.ISO_INSTANT))))
        );
        return analyticsConverter.convertSplitAmountResponse(response);
    }

    public InlineResponse2005 getPaymentsSplitCount(OffsetDateTime fromTime,
                                                    OffsetDateTime toTime,
                                                    String splitUnit,
                                                    String partyId,
                                                    List<String> shopIds)
            throws TException {
        SplitCountResponse response = analyticsClient.getPaymentsSplitCount(new SplitFilterRequest()
                .setSplitUnit(SplitUnit.valueOf(splitUnit))
                .setFilterRequest(new FilterRequest()
                        .setMerchantFilter(new MerchantFilter()
                                .setPartyId(partyId)
                                .setShopIds(shopIds))
                        .setTimeFilter(new TimeFilter()
                                .setFromTime(fromTime.format(DateTimeFormatter.ISO_INSTANT))
                                .setToTime(toTime.format(DateTimeFormatter.ISO_INSTANT))))
        );
        return analyticsConverter.convertSplitCountResponse(response);
    }

    public InlineResponse2001 getPaymentsToolDistribution(OffsetDateTime fromTime,
                                                          OffsetDateTime toTime,
                                                          String partyId,
                                                          List<String> shopIds) throws TException {
        PaymentToolDistributionResponse response = analyticsClient.getPaymentsToolDistribution(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(shopIds))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.ISO_INSTANT))
                        .setToTime(toTime.format(DateTimeFormatter.ISO_INSTANT)))
        );
        return analyticsConverter.convertPaymentToolDistributionResponse(response);
    }

    public InlineResponse200 getRefundsAmount(OffsetDateTime fromTime,
                                              OffsetDateTime toTime,
                                              String partyId,
                                              List<String> shopIds) throws TException {
        AmountResponse response = analyticsClient.getRefundsAmount(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(shopIds))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.ISO_INSTANT))
                        .setToTime(toTime.format(DateTimeFormatter.ISO_INSTANT)))
        );
        return analyticsConverter.convertAmountResponse(response);
    }
}
