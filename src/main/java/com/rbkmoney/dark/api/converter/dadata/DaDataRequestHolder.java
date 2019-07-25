package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaDataRequestHolder {

    private DaDataRequest daDataRequest;

    private DaDataEndpoint daDataEndpoint;

}
