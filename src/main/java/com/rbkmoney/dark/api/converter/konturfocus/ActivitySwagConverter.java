package com.rbkmoney.dark.api.converter.konturfocus;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.Activity;
import com.rbkmoney.swag.questionary_aggr_proxy.model.ComplementaryActivity;
import com.rbkmoney.swag.questionary_aggr_proxy.model.PrincipalActivity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivitySwagConverter implements SwagConverter<Activity, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Activity> {

    @Override
    public Activity toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Activity value, SwagConverterContext ctx) {
        Activity swagActivity = new Activity();
        if (value.isSetComplementaryActivities()) {
            List<ComplementaryActivity> swagComplementaryActivityList = value.getComplementaryActivities().stream()
                    .map(complementaryActivity -> {
                        ComplementaryActivity swagComplementaryActivity = new ComplementaryActivity();
                        swagComplementaryActivity.setCode(complementaryActivity.getCode());
                        swagComplementaryActivity.setDate(complementaryActivity.getDate());
                        swagComplementaryActivity.setText(complementaryActivity.getText());
                        return swagComplementaryActivity;
                    })
                    .collect(Collectors.toList());
            swagActivity.setComplementaryActivities(swagComplementaryActivityList);
        }

        if (value.isSetPrincipalActivity()) {
            PrincipalActivity principalActivity = new PrincipalActivity();
            principalActivity.setCode(value.getPrincipalActivity().getCode());
            principalActivity.setDate(value.getPrincipalActivity().getDate());
            principalActivity.setText(value.getPrincipalActivity().getText());
            swagActivity.setPrincipalActivity(principalActivity);
        }

        return swagActivity;
    }

}
