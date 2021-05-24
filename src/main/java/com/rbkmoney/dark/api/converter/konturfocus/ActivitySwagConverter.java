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
public class ActivitySwagConverter
        implements SwagConverter<Activity, com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Activity> {

    @Override
    public Activity toSwag(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Activity value,
                           SwagConverterContext ctx) {
        Activity swagActivity = new Activity();
        if (value.isSetComplementaryActivities()) {
            List<ComplementaryActivity> swagComplementaryActivityList = value.getComplementaryActivities().stream()
                    .map(this::convertComplementaryActivity)
                    .collect(Collectors.toList());
            swagActivity.setComplementaryActivities(swagComplementaryActivityList);
        }

        if (value.isSetPrincipalActivity()) {
            swagActivity.setPrincipalActivity(convertPrincipalActivity(value.getPrincipalActivity()));
        }

        return swagActivity;
    }

    private ComplementaryActivity convertComplementaryActivity(
            com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ComplementaryActivity complementaryActivity) {
        ComplementaryActivity swagComplementaryActivity = new ComplementaryActivity();
        swagComplementaryActivity.setCode(complementaryActivity.getCode());
        swagComplementaryActivity.setDate(complementaryActivity.getDate());
        swagComplementaryActivity.setText(complementaryActivity.getText());
        return swagComplementaryActivity;
    }

    private PrincipalActivity convertPrincipalActivity(
            com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.PrincipalActivity principalActivity) {
        PrincipalActivity swagPrincipalActivity = new PrincipalActivity();
        swagPrincipalActivity.setCode(principalActivity.getCode());
        swagPrincipalActivity.setDate(principalActivity.getDate());
        swagPrincipalActivity.setText(principalActivity.getText());
        return swagPrincipalActivity;
    }

}
