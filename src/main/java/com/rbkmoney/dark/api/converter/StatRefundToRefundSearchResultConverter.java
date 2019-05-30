package com.rbkmoney.dark.api.converter;

import com.rbkmoney.damsel.merch_stat.StatRefund;
import com.rbkmoney.swag.dark_api.model.RefundSearchResult;

import java.time.OffsetDateTime;

import static com.rbkmoney.dark.api.converter.HelperUtils.getCart;

public class StatRefundToRefundSearchResultConverter {

    public static RefundSearchResult convert(StatRefund statRefund) {
        return new RefundSearchResult()
                .amount(statRefund.amount)
                .cart(getCart(statRefund.cart))
                .createdAt(OffsetDateTime.parse(statRefund.created_at))
                .currency(statRefund.currency_symbolic_code)
                .id(statRefund.id)
                .invoiceID(statRefund.invoice_id)
                .paymentID(statRefund.payment_id)
                .reason(statRefund.reason)
                .status(RefundSearchResult.StatusEnum.fromValue(statRefund.status.getSetField().getFieldName()));
    }

}
