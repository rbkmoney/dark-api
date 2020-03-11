package com.rbkmoney.dark.api.converter.analytics;

import com.rbkmoney.damsel.analytics.SplitUnit;
import com.rbkmoney.damsel.analytics.*;
import com.rbkmoney.swag.analytics.model.OffsetAmount;
import com.rbkmoney.swag.analytics.model.OffsetCount;
import com.rbkmoney.swag.analytics.model.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@Component
public class AnalyticsConverterImpl implements AnalyticsConverter {

    @Override
    public InlineResponse200 convertAmountResponse(AmountResponse response) {
        InlineResponse200 inlineResponse200 = new InlineResponse200();
        if (response != null && !CollectionUtils.isEmpty(response.getGroupsAmount())) {
            inlineResponse200.setResult(response.getGroupsAmount().stream()
                    .map(this::convertAmountResult)
                    .collect(Collectors.toList()));
        }
        return inlineResponse200;
    }

    @Override
    public InlineResponse2002 convertCountResponse(CountResponse response) {
        InlineResponse2002 inlineResponse2002 = new InlineResponse2002();
        inlineResponse2002.setResult(response.getGroupsCount().stream()
                .map(this::createCountResult)
                .collect(Collectors.toList()));
        return inlineResponse2002;
    }

    @Override
    public InlineResponse2003 convertErrorDistributionsResponse(ErrorDistributionsResponse response) {
        InlineResponse2003 inlineResponse2003 = new InlineResponse2003();
        inlineResponse2003.setResult(
                response.getErrorDistributions().stream()
                        .map(this::createPaymentErrorResult)
                        .collect(Collectors.toList()));
        return inlineResponse2003;
    }

    @Override
    public InlineResponse2004 convertSplitAmountResponse(SplitAmountResponse response) {
        InlineResponse2004 inlineResponse2004 = new InlineResponse2004();
        SplitUnit resultSplitUnit = response.getResultSplitUnit();
        inlineResponse2004.setResult(response.getGroupedCurrencyAmounts().stream()
                .map(groupedCurrencyOffsetAmount ->
                        createSplitAmountResult(groupedCurrencyOffsetAmount, resultSplitUnit))
                .collect(Collectors.toList()));
        return inlineResponse2004;
    }

    @Override
    public InlineResponse2005 convertSplitCountResponse(SplitCountResponse response) {
        InlineResponse2005 inlineResponse2005 = new InlineResponse2005();
        SplitUnit resultSplitUnit = response.getResultSplitUnit();
        inlineResponse2005.setResult(response.getPaymentToolsDestrobutions().stream()
                .map(groupedCurrencyOffsetAmount ->
                        createSplitCountResult(groupedCurrencyOffsetAmount, resultSplitUnit))
                .collect(Collectors.toList()));
        return inlineResponse2005;
    }

    @Override
    public InlineResponse2001 convertPaymentToolDistributionResponse(PaymentToolDistributionResponse response) {
        InlineResponse2001 inlineResponse2001 = new InlineResponse2001();
        inlineResponse2001.setResult(response.getPaymentToolsDistributions().stream()
                .map(this::createPaymentsToolDistributionResult)
                .collect(Collectors.toList()));
        return inlineResponse2001;
    }

    private PaymentsToolDistributionResult createPaymentsToolDistributionResult(NamingDistribution namingDistribution) {
        PaymentsToolDistributionResult paymentsToolDistributionResult = new PaymentsToolDistributionResult();
        paymentsToolDistributionResult.setName(namingDistribution.getName());
        paymentsToolDistributionResult.setPercents((int) namingDistribution.getPercents());
        return paymentsToolDistributionResult;
    }

    private CountResult createCountResult(CurrecyGroupCount currecyGroupCount) {
        CountResult countResult = new CountResult();
        countResult.setCount(currecyGroupCount.getCount());
        countResult.setCurrency(currecyGroupCount.getCurrency());
        return countResult;
    }

    private AmountResult convertAmountResult(CurrencyGroupedAmount currencyGroupedAmount) {
        AmountResult amountResult = new AmountResult();
        amountResult.setAmount(currencyGroupedAmount.getAmount());
        amountResult.setCurrency(currencyGroupedAmount.getCurrency());
        return amountResult;
    }

    private PaymentsErrorsDistributionResult createPaymentErrorResult(NamingDistribution namingDistribution) {
        PaymentsErrorsDistributionResult paymentsErrorsDistributionResult = new PaymentsErrorsDistributionResult();
        paymentsErrorsDistributionResult.setError(namingDistribution.getName());
        paymentsErrorsDistributionResult.setPercents(namingDistribution.getPercents());
        return paymentsErrorsDistributionResult;
    }

    private SplitAmountResult createSplitAmountResult(GroupedCurrencyOffsetAmount groupedCurrencyOffsetAmount, SplitUnit unit) {
        SplitAmountResult splitAmountResult = new SplitAmountResult();
        splitAmountResult.setSplitUnit(SplitAmountResult.SplitUnitEnum.valueOf(unit.name()));
        splitAmountResult.setCurrency(groupedCurrencyOffsetAmount.getCurrency());
        splitAmountResult.setOffsetAmounts(groupedCurrencyOffsetAmount.getOffsetAmounts().stream()
                .map(this::createOffsetAmount)
                .collect(Collectors.toList()));
        return splitAmountResult;
    }

    private SplitCountResult createSplitCountResult(GroupedCurrencyOffsetCount groupedCurrencyOffsetCount, SplitUnit unit) {
        SplitCountResult splitCountResult = new SplitCountResult();
        splitCountResult.setSplitUnit(SplitCountResult.SplitUnitEnum.valueOf(unit.name()));
        splitCountResult.setCurrency(groupedCurrencyOffsetCount.getCurrency());
        splitCountResult.setStatusOffsetCounts(groupedCurrencyOffsetCount.getOffsetAmounts().stream()
                .map(this::createStatusOffsetCount)
                .collect(Collectors.toList()));
        return splitCountResult;
    }

    private StatusOffsetCount createStatusOffsetCount(GroupedStatusOffsetCount groupedStatusOffsetCount) {
        StatusOffsetCount statusOffsetCount = new StatusOffsetCount();
        statusOffsetCount.setStatus(StatusOffsetCount.StatusEnum.valueOf(groupedStatusOffsetCount.getStatus().name()));
        statusOffsetCount.setOffsetCount(groupedStatusOffsetCount.getOffsetCounts().stream()
                .map(this::createOffsetCount)
                .collect(Collectors.toList())
        );
        return statusOffsetCount;
    }

    private OffsetCount createOffsetCount(com.rbkmoney.damsel.analytics.OffsetCount offsetCount) {
        OffsetCount offsetCountResult = new OffsetCount();
        offsetCountResult.setCount(offsetCount.getCount());
        offsetCountResult.setOffset(offsetCount.getOffset());
        return offsetCountResult;
    }

    private OffsetAmount createOffsetAmount(com.rbkmoney.damsel.analytics.OffsetAmount offsetAmount) {
        OffsetAmount result = new OffsetAmount();
        result.setAmount(offsetAmount.getAmount());
        result.setOffset(offsetAmount.getOffset());
        return result;
    }

}
