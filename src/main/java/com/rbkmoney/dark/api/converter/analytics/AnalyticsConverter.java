package com.rbkmoney.dark.api.converter.analytics;

import com.rbkmoney.damsel.analytics.*;
import com.rbkmoney.swag.analytics.model.*;

public interface AnalyticsConverter {

    InlineResponse200 convertAmountResponse(AmountResponse response);

    InlineResponse2002 convertCountResponse(CountResponse response);

    InlineResponse2003 convertErrorDistributionsResponse(ErrorDistributionsResponse response);

    InlineResponse2004 convertSplitAmountResponse(SplitAmountResponse response);

    InlineResponse2005 convertSplitCountResponse(SplitCountResponse response);

    InlineResponse2001 convertPaymentToolDistributionResponse(PaymentToolDistributionResponse response);


}
