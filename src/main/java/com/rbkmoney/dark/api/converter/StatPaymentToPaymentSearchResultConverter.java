package com.rbkmoney.dark.api.converter;

import com.rbkmoney.damsel.base.Content;
import com.rbkmoney.damsel.merch_stat.*;
import com.rbkmoney.swag.dark_api.model.CustomerPayer;
import com.rbkmoney.swag.dark_api.model.Payer;
import com.rbkmoney.swag.dark_api.model.PaymentResourcePayer;
import com.rbkmoney.swag.dark_api.model.RecurrentPayer;
import com.rbkmoney.swag.dark_api.model.*;

import java.time.OffsetDateTime;
import java.util.Map;

import static com.rbkmoney.dark.api.converter.HelperUtils.getCart;

public class StatPaymentToPaymentSearchResultConverter {

    public static PaymentSearchResult convert(StatPayment statPayment, Content invoiceMetadata) {
        return new PaymentSearchResult()
                .invoiceMetadata(invoiceMetadata == null ? Map.of() : new String(invoiceMetadata.getData()))
                .amount(statPayment.amount)
                .cart(getCart(statPayment.cart))
                .createdAt(OffsetDateTime.parse(statPayment.created_at))
                .currency(statPayment.currency_symbolic_code)
                .fee(statPayment.fee)
                .flow(statPayment.flow.isSetHold() ? new PaymentFlowHold() : new PaymentFlowInstant())
                .geoLocationInfo(statPayment.location_info == null ? null : new GeoLocationInfo()
                        .cityGeoID(statPayment.location_info.city_geo_id)
                        .countryGeoID(statPayment.location_info.country_geo_id))
                .id(statPayment.id)
                .invoiceID(statPayment.invoice_id)
                .makeRecurrent(statPayment.make_recurrent)
                .metadata(statPayment.context == null ? Map.of() : new String(statPayment.context.getData()))
                .payer(getPayer(statPayment.payer))
                .shortID(statPayment.short_id)
                .status(getStatus(statPayment.status));
    }

    private static PaymentSearchResult.StatusEnum getStatus(InvoicePaymentStatus status) {
        return PaymentSearchResult.StatusEnum.fromValue(status.getSetField().getFieldName());
    }

    private static Payer getPayer(com.rbkmoney.damsel.merch_stat.Payer payer) {
        if (payer.isSetCustomer()) {
            return new CustomerPayer()
                    .customerID(payer.getCustomer().getCustomerId());
        }
        if (payer.isSetPaymentResource()) {
            com.rbkmoney.damsel.merch_stat.PaymentResourcePayer paymentResource = payer.getPaymentResource();
            PaymentResourcePayer paymentResourcePayer = new PaymentResourcePayer()
                    .contactInfo(new ContactInfo()
                            .email(paymentResource.getEmail())
                            .phoneNumber(paymentResource.getPhoneNumber()));
            if (paymentResource.payment_tool.isSetBankCard()) {
                BankCard bankCard = paymentResource.payment_tool.getBankCard();
                return paymentResourcePayer
                        .paymentToolToken(bankCard.getToken())
                        .paymentToolDetails(new PaymentToolDetailsBankCard()
                                .paymentSystem(PaymentToolDetailsBankCard.PaymentSystemEnum.fromValue(bankCard.payment_system.name()))
                                .bin(bankCard.bin)
                                .cardNumberMask(bankCard.masked_pan)
                                // todo add last digits?
                                .lastDigits(null)
                                .tokenProvider(bankCard.token_provider == null ? null : PaymentToolDetailsBankCard.TokenProviderEnum.fromValue(bankCard.token_provider.name())));
            }
            if (paymentResource.payment_tool.isSetDigitalWallet()) {
                DigitalWallet digitalWallet = paymentResource.payment_tool.getDigitalWallet();
                return paymentResourcePayer
                        .paymentToolDetails(new PaymentToolDetailsDigitalWallet()
                                .detailsType(digitalWallet.getProvider().name()));
            }
            if (paymentResource.payment_tool.isSetPaymentTerminal()) {
                PaymentTerminal paymentTerminal = paymentResource.payment_tool.getPaymentTerminal();
                return paymentResourcePayer
                        .paymentToolDetails(new PaymentToolDetailsPaymentTerminal()
                                .provider(PaymentToolDetailsPaymentTerminal.ProviderEnum.fromValue(paymentTerminal.terminal_type.name())));
            }
        }
        if (payer.isSetRecurrent()) {
            com.rbkmoney.damsel.merch_stat.RecurrentPayer recurrent = payer.getRecurrent();
            return new RecurrentPayer()
                    .contactInfo(new ContactInfo()
                            .email(recurrent.getEmail())
                            .phoneNumber(recurrent.getPhoneNumber()))
                    .recurrentParentPayment(new PaymentRecurrentParent()
                            .invoiceID(recurrent.getRecurrentParent().invoice_id)
                            .paymentID(recurrent.getRecurrentParent().payment_id));
        }
        throw new IllegalArgumentException("Wrong payer type");
    }

}
