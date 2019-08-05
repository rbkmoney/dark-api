package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityConverter implements
        ThriftConverter<Activity, com.rbkmoney.swag.questionary.model.Activity>,
        SwagConverter<com.rbkmoney.swag.questionary.model.Activity, Activity> {

    @Override
    public com.rbkmoney.swag.questionary.model.Activity toSwag(Activity value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.Activity()
                .code(value.getCode())
                .description(value.getDescription());
    }

    @Override
    public Activity toThrift(com.rbkmoney.swag.questionary.model.Activity value, ThriftConverterContext ctx) {
        Activity activity = new Activity();
        activity.setCode(value.getCode());
        activity.setDescription(value.getDescription());
        return activity;
    }

}
