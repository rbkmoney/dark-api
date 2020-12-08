package com.rbkmoney.dark.api.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ShopModificationTypeEnum {
    CREATION("creation"),
    CATEGORYMODIFICATION("categorymodification"),
    DETAILSMODIFICATION("detailsmodification"),
    CONTRACTMODIFICATION("contractmodification"),
    PAYOUTTOOLMODIFICATION("payouttoolmodification"),
    LOCATIONMODIFICATION("locationmodification"),
    SHOPACCOUNTCREATION("shopaccountcreation"),
    PAYOUTSCHEDULEMODIFICATION("payoutschedulemodification");

    @Getter
    @JsonValue
    private final String value;

    public static ShopModificationTypeEnum toEnum(String val) {
        switch (val) {
            case "creation":
                return CREATION;
            case "categorymodification":
                return CATEGORYMODIFICATION;
            case "detailsmodification":
                return DETAILSMODIFICATION;
            case "contractmodification":
                return CONTRACTMODIFICATION;
            case "payouttoolmodification":
                return PAYOUTTOOLMODIFICATION;
            case "locationmodification":
                return LOCATIONMODIFICATION;
            case "shopaccountcreation":
                return SHOPACCOUNTCREATION;
            case "payoutschedulemodification":
                return PAYOUTSCHEDULEMODIFICATION;
            default:
                throw new RuntimeException("Can't find enum value");
        }
    }
}
