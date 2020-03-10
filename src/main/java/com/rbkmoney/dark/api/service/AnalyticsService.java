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

    private final AnalyticsServiceSrv.Iface iface;

    private final AnalyticsConverter analyticsConverter;

    public InlineResponse200 getAveragePayment(OffsetDateTime fromTime,
                                               OffsetDateTime toTime,
                                               String partyId,
                                               String shopId) throws TException {
        AmountResponse averagePayment = iface.getAveragePayment(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(List.of(shopId)))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.BASIC_ISO_DATE))
                        .setToTime(toTime.format(DateTimeFormatter.BASIC_ISO_DATE)))
        );
        return analyticsConverter.convertAmountResponse(averagePayment);
    }


    public InlineResponse200 getCurrentBalances(String partyId, String shopID) throws TException {
        return null;
    }

    public InlineResponse200 getPaymentsAmount(OffsetDateTime fromTime, OffsetDateTime toTime, String partyId, String shopId) throws TException {
        AmountResponse paymentsAmount = iface.getPaymentsAmount(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(List.of(shopId)))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.BASIC_ISO_DATE))
                        .setToTime(toTime.format(DateTimeFormatter.BASIC_ISO_DATE)))
        );
        return analyticsConverter.convertAmountResponse(paymentsAmount);
    }

    public InlineResponse2002 getPaymentsCount(OffsetDateTime fromTime, OffsetDateTime toTime,
                                               String partyId, String shopId) throws TException {
        CountResponse countResponse = iface.getPaymentsCount(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(List.of(shopId)))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.BASIC_ISO_DATE))
                        .setToTime(toTime.format(DateTimeFormatter.BASIC_ISO_DATE)))
        );
        return analyticsConverter.convertCountResponse(countResponse);
    }

    public InlineResponse2003 getPaymentsErrorDistribution(OffsetDateTime fromTime, OffsetDateTime toTime,
                                                           String partyId, String shopId) throws TException {
        ErrorDistributionsResponse countResponse = iface.getPaymentsErrorDistribution(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(List.of(shopId)))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.BASIC_ISO_DATE))
                        .setToTime(toTime.format(DateTimeFormatter.BASIC_ISO_DATE)))
        );
        return analyticsConverter.convertErrorDistributionsResponse(countResponse);
    }

    public InlineResponse2004 getPaymentsSplitAmount(OffsetDateTime fromTime, OffsetDateTime toTime,
                                                     String splitUnit, String partyId, String shopId) throws TException {
        SplitAmountResponse response = iface.getPaymentsSplitAmount(new SplitFilterRequest()
                .setSplitUnit(SplitUnit.valueOf(splitUnit))
                .setFilterRequest(new FilterRequest()
                        .setMerchantFilter(new MerchantFilter()
                                .setPartyId(partyId)
                                .setShopIds(List.of(shopId)))
                        .setTimeFilter(new TimeFilter()
                                .setFromTime(fromTime.format(DateTimeFormatter.BASIC_ISO_DATE))
                                .setToTime(toTime.format(DateTimeFormatter.BASIC_ISO_DATE))))
        );
        return analyticsConverter.convertSplitAmountResponse(response);
    }

    public InlineResponse2005 getPaymentsSplitCount(OffsetDateTime fromTime, OffsetDateTime toTime,
                                                    String splitUnit, String partyId, String shopId) throws TException {
        SplitCountResponse response = iface.getPaymentsSplitCount(new SplitFilterRequest()
                .setSplitUnit(SplitUnit.valueOf(splitUnit))
                .setFilterRequest(new FilterRequest()
                        .setMerchantFilter(new MerchantFilter()
                                .setPartyId(partyId)
                                .setShopIds(List.of(shopId)))
                        .setTimeFilter(new TimeFilter()
                                .setFromTime(fromTime.format(DateTimeFormatter.BASIC_ISO_DATE))
                                .setToTime(toTime.format(DateTimeFormatter.BASIC_ISO_DATE))))
        );
        return analyticsConverter.convertSplitCountResponse(response);
    }

    public InlineResponse2001 getPaymentsToolDistribution(OffsetDateTime fromTime, OffsetDateTime toTime, String partyId, String shopId) throws TException {
        PaymentToolDistributionResponse response = iface.getPaymentsToolDistribution(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(List.of(shopId)))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.BASIC_ISO_DATE))
                        .setToTime(toTime.format(DateTimeFormatter.BASIC_ISO_DATE)))
        );
        return analyticsConverter.convertPaymentToolDistributionResponse(response);
    }

    public InlineResponse200 getRefundsAmount(OffsetDateTime fromTime, OffsetDateTime toTime, String partyId, String shopId) throws TException {
        AmountResponse response = iface.getRefundsAmount(new FilterRequest()
                .setMerchantFilter(new MerchantFilter()
                        .setPartyId(partyId)
                        .setShopIds(List.of(shopId)))
                .setTimeFilter(new TimeFilter()
                        .setFromTime(fromTime.format(DateTimeFormatter.BASIC_ISO_DATE))
                        .setToTime(toTime.format(DateTimeFormatter.BASIC_ISO_DATE)))
        );
        return analyticsConverter.convertAmountResponse(response);
    }
}
