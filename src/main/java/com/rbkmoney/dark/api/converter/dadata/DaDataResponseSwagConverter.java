package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyResponse;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DaDataResponseSwagConverter implements SwagConverter<DaDataResponse, com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse> {
    @Override
    public DaDataResponse toSwag(com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse value, SwagConverterContext ctx) {
        DaDataResponse daDataResponse = null;
        if (value.isSetAddressResponse()) {
            var swagAddressResponse = new com.rbkmoney.swag.questionary_aggr_proxy.model.AddressResponse();
            AddressResponse addressResponse = value.getAddressResponse();
            List<DaDataAddress> daDataAddressList = addressResponse.getSuggestions().stream()
                    .map(address -> {
                        return ctx.convert(address, DaDataAddress.class);
                    })
                    .collect(Collectors.toList());
            swagAddressResponse.setSuggestions(daDataAddressList);
            daDataResponse = swagAddressResponse;
        } else if (value.isSetBankResponse()) {
            var swagBankResponse = new com.rbkmoney.swag.questionary_aggr_proxy.model.BankResponse();
            BankResponse bankResponse = value.getBankResponse();
            List<BankContent> bankContentList = bankResponse.getSuggestions().stream()
                    .map(bankContent -> {
                        return ctx.convert(bankContent, BankContent.class);
                    })
                    .collect(Collectors.toList());
            swagBankResponse.setSuggestions(bankContentList);
            daDataResponse = swagBankResponse;
        } else if (value.isSetFioResponse()) {
            var swagFioResponse = new com.rbkmoney.swag.questionary_aggr_proxy.model.FioResponse();
            FioResponse fioResponse = value.getFioResponse();
            List<FioContent> fioContentList = fioResponse.getSuggestions().stream()
                    .map(fioContent -> {
                        return ctx.convert(fioContent, FioContent.class);
                    })
                    .collect(Collectors.toList());
            swagFioResponse.setSuggestions(fioContentList);
            daDataResponse = swagFioResponse;
        } else if (value.isSetOkvedResponse()) {
            var swagOkvedResponse = new com.rbkmoney.swag.questionary_aggr_proxy.model.OkvedResponse();
            OkvedResponse okvedResponse = value.getOkvedResponse();
            List<OkvedContent> okvedContentList = okvedResponse.getSuggestions().stream()
                    .map(okvedContent -> {
                        return ctx.convert(okvedContent, OkvedContent.class);
                    })
                    .collect(Collectors.toList());
            swagOkvedResponse.setSuggestions(okvedContentList);
            daDataResponse = swagOkvedResponse;
        } else if (value.isSetFmsUnitResponse()) {
            var swagFmsUnitResponse = new com.rbkmoney.swag.questionary_aggr_proxy.model.FmsUnitResponse();
            FmsUnitResponse fmsUnitResponse = value.getFmsUnitResponse();
            List<FmsUnitContent> fmsUnitContentList = fmsUnitResponse.getSuggestions().stream()
                    .map(fmsUnitContent -> {
                        return ctx.convert(fmsUnitContent, FmsUnitContent.class);
                    })
                    .collect(Collectors.toList());
            swagFmsUnitResponse.setSuggestions(fmsUnitContentList);
            daDataResponse = swagFmsUnitResponse;
        } else if (value.isSetPartyResponse()) {
            var swagPartyResponse = new com.rbkmoney.swag.questionary_aggr_proxy.model.PartyResponse();
            PartyResponse partyResponse = value.getPartyResponse();
            List<PartyContent> partyContentList = partyResponse.getSuggestions().stream()
                    .map(partyContent -> {
                        return ctx.convert(partyContent, PartyContent.class);
                    })
                    .collect(Collectors.toList());
            swagPartyResponse.setSuggestions(partyContentList);
            daDataResponse = swagPartyResponse;
        }

        if (daDataResponse == null) {
            throw new IllegalArgumentException("Unknown response type");
        }

        return daDataResponse;
    }
}
