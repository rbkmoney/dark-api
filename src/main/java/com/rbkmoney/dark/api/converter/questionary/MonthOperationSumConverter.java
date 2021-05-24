package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.MonthOperationSum;
import org.springframework.stereotype.Component;

@Component
public class MonthOperationSumConverter implements
        ThriftConverter<MonthOperationSum, com.rbkmoney.swag.questionary.model.MonthOperationSum>,
        SwagConverter<com.rbkmoney.swag.questionary.model.MonthOperationSum, MonthOperationSum> {

    @Override
    public com.rbkmoney.swag.questionary.model.MonthOperationSum toSwag(MonthOperationSum value,
                                                                        SwagConverterContext ctx) {
        switch (value) {
            case gt_one_million:
                return com.rbkmoney.swag.questionary.model.MonthOperationSum.GTONEMILLION;
            case lt_five_hundred_thousand:
                return com.rbkmoney.swag.questionary.model.MonthOperationSum.LTFIVEHUNDREDTHOUSAND;
            case btw_five_hundred_thousand_to_one_million:
                return com.rbkmoney.swag.questionary.model.MonthOperationSum.BTWFIVEHUNDREDTHOUSANDTOONEMILLION;
            default:
                throw new IllegalArgumentException("Unknown monthOperationSum: " + value);
        }
    }

    @Override
    public MonthOperationSum toThrift(com.rbkmoney.swag.questionary.model.MonthOperationSum value,
                                      ThriftConverterContext ctx) {
        switch (value) {
            case GTONEMILLION:
                return MonthOperationSum.gt_one_million;
            case LTFIVEHUNDREDTHOUSAND:
                return MonthOperationSum.lt_five_hundred_thousand;
            case BTWFIVEHUNDREDTHOUSANDTOONEMILLION:
                return MonthOperationSum.btw_five_hundred_thousand_to_one_million;
            default:
                throw new IllegalArgumentException("Unknown monthOperationSum: " + value);
        }
    }

}
