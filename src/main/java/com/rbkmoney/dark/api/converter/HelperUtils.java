package com.rbkmoney.dark.api.converter;

import com.rbkmoney.swag.dark_api.model.InvoiceCart;
import com.rbkmoney.swag.dark_api.model.InvoiceLine;

import java.util.List;
import java.util.stream.Collectors;

public class HelperUtils {

    static InvoiceCart getCart(com.rbkmoney.damsel.domain.InvoiceCart cart) {
        InvoiceCart invoiceCart = new InvoiceCart();
        List<InvoiceLine> invoiceLines = cart.lines.stream()
                .map(invoiceLine -> new InvoiceLine()
                        .cost(invoiceLine.price.amount * invoiceLine.quantity)
                        .price(invoiceLine.price.amount)
                        .quantity((long) invoiceLine.quantity)
                        .product(invoiceLine.product)
                )
                .collect(Collectors.toList());
        invoiceCart.addAll(invoiceLines);
        return invoiceCart;
    }

}
