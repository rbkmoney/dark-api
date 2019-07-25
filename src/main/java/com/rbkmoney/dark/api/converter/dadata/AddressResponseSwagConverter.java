package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.swag.questionary_aggr_proxy.model.AddressResponse;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddressResponseSwagConverter implements SwagConverter<AddressResponse, com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressResponse> {

    @Override
    public AddressResponse toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressResponse value, SwagConverterContext ctx) {
        AddressResponse addressResponse = new AddressResponse();
        if (value.isSetSuggestions()) {
            List<DaDataAddress> daDataAddressList = value.getSuggestions().stream()
                    .map(address -> ctx.convert(address, DaDataAddress.class))
                    .collect(Collectors.toList());
            addressResponse.setSuggestions(daDataAddressList);
        }
        return addressResponse;
    }

}
