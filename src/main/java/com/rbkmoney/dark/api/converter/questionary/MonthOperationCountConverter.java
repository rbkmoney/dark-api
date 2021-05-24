package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.MonthOperationCount;
import org.springframework.stereotype.Component;

@Component
public class MonthOperationCountConverter implements
        ThriftConverter<MonthOperationCount, com.rbkmoney.swag.questionary.model.MonthOperationCount>,
        SwagConverter<com.rbkmoney.swag.questionary.model.MonthOperationCount, MonthOperationCount> {

    @Override
    public com.rbkmoney.swag.questionary.model.MonthOperationCount toSwag(MonthOperationCount value,
                                                                          SwagConverterContext ctx) {
        switch (value) {
            case lt_ten:
                return com.rbkmoney.swag.questionary.model.MonthOperationCount.LTTEN;
            case gt_fifty:
                return com.rbkmoney.swag.questionary.model.MonthOperationCount.GTFIFTY;
            case btw_ten_to_fifty:
                return com.rbkmoney.swag.questionary.model.MonthOperationCount.BTWTENTOFIFTY;
            default:
                throw new IllegalArgumentException("Unknown monthOperationCount type: " + value);
        }
    }

    @Override
    public MonthOperationCount toThrift(com.rbkmoney.swag.questionary.model.MonthOperationCount value,
                                        ThriftConverterContext ctx) {
        switch (value) {
            case BTWTENTOFIFTY:
                return MonthOperationCount.btw_ten_to_fifty;
            case GTFIFTY:
                return MonthOperationCount.gt_fifty;
            case LTTEN:
                return MonthOperationCount.lt_ten;
            default:
                throw new IllegalArgumentException("Unknown mothOperationCount type: " + value);
        }
    }

}
