package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.BusinessReputation;
import org.springframework.stereotype.Component;

@Component
public class BusinessReputationConverter implements
        ThriftConverter<BusinessReputation, com.rbkmoney.swag.questionary.model.BusinessReputation>,
        SwagConverter<com.rbkmoney.swag.questionary.model.BusinessReputation, BusinessReputation> {

    @Override
    public com.rbkmoney.swag.questionary.model.BusinessReputation toSwag(BusinessReputation value, SwagConverterContext ctx) {
        switch (value) {
            case no_reviews:
                return com.rbkmoney.swag.questionary.model.BusinessReputation.NOREVIEWS;
            case provide_reviews:
                return com.rbkmoney.swag.questionary.model.BusinessReputation.PROVIDEREVIEWS;
            default:
                throw new IllegalArgumentException("Unknown businessReputation type: " + value);
        }
    }

    @Override
    public BusinessReputation toThrift(com.rbkmoney.swag.questionary.model.BusinessReputation value, ThriftConverterContext ctx) {
        switch (value) {
            case PROVIDEREVIEWS:
                return BusinessReputation.provide_reviews;
            case NOREVIEWS:
                return BusinessReputation.no_reviews;
            default:
                throw new IllegalArgumentException("Unknown businessReputation type: " + value);
        }
    }

}
