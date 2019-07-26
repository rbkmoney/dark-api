package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DaDataParamToDaDataRequest implements ThriftConverter<DaDataRequestHolder, DaDataParams> {

    @Override
    public DaDataRequestHolder toThrift(DaDataParams daDataParams, ThriftConverterContext thriftConverterContext) {
        DaDataRequestHolder daDataRequestHolder = new DaDataRequestHolder();
        DaDataRequest daDataRequest = new DaDataRequest();
        daDataRequestHolder.setDaDataRequest(daDataRequest);
        switch (daDataParams.getEndpoint()) {
            case OKVED2:
                daDataRequestHolder.setDaDataEndpoint(com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint.okved2);
                OkvedQuery swagOkvedQuery = ((OkvedQuery) daDataParams.getRequest());
                daDataRequest.setOkvedQuery(convertOkvedQuery(swagOkvedQuery));
                break;
            case SUGGESTFIO:
                daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_fio);
                FioQuery swagFioQuery = (FioQuery) daDataParams.getRequest();
                daDataRequest.setFioQuery(convertFioQuery(swagFioQuery));
                break;
            case SUGGESTBANK:
                daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_bank);
                BankQuery swagBankQuery = (BankQuery) daDataParams.getRequest();
                daDataRequest.setBankQuery(convertBankQuery(swagBankQuery));
                break;
            case SUGGESTPARTY:
                daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_party);
                PartyQuery swagPartyQuery = (PartyQuery) daDataParams.getRequest();
                daDataRequest.setPartyQuery(convertPartyQuery(swagPartyQuery));
                break;
            case SUGGESTFMSUNIT:
                daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_fms_unit);
                FmsUnitQuery swagFmsUnitQuery = (FmsUnitQuery) daDataParams.getRequest();
                daDataRequest.setFmsUnitQuery(convertFmsUnitQuery(swagFmsUnitQuery));
                break;
            case SUGGESTADDRESS:
                daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_address);
                AddressQuery swagAddressQuery = (AddressQuery) daDataParams.getRequest();
                daDataRequest.setAddressQuery(convertAddressQuery(swagAddressQuery));
                break;
            default:
                throw new IllegalArgumentException("Unknown endpoint: " + daDataParams.getEndpoint().name());
        }

        return daDataRequestHolder;
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioQuery convertFioQuery(FioQuery fioQuery) {
        var thriftFioQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioQuery();
        thriftFioQuery.setQuery(fioQuery.getQuery());
        thriftFioQuery.setCount(fioQuery.getCount().byteValue());
        thriftFioQuery.setParts(fioQuery.getParts());
        if (fioQuery.getGender() == Gender.FEMALE) {
            thriftFioQuery.setGender(com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.FEMALE);
        } else if (fioQuery.getGender() == Gender.MALE) {
            thriftFioQuery.setGender(com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.MALE);
        } else if (fioQuery.getGender() == Gender.UNKNOWN) {
            thriftFioQuery.setGender(com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.UNKNOWN);
        }
        return thriftFioQuery;
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedQuery convertOkvedQuery(OkvedQuery okvedQuery) {
        var thriftOkvedQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedQuery();
        thriftOkvedQuery.setQuery(okvedQuery.getQuery());
        thriftOkvedQuery.setQueryType(convertQueryType(okvedQuery.getQueryType()));
        return thriftOkvedQuery;
    }

    private com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus convertOrgStatus(OrgStatus orgStatus) {
        switch (orgStatus) {
            case ACTIVE:
                return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.ACTIVE;
            case LIQUIDATED:
                return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.LIQUIDATED;
            case REORGANIZING:
                return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.REORGANIZING;
            case LIQUIDATING:
                return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.LIQUIDATING;
            default:
                throw new IllegalArgumentException("Unknown orgStatus: " + orgStatus);
        }
    }

    private com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgType convertOrgType(OrgType orgType) {
        switch (orgType) {
            case LEGAL:
                return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgType.LEGAL;
            case INDIVIDUAL:
                return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgType.INDIVIDUAL;
            default:
                throw new IllegalArgumentException("Unknown orgType: " + orgType);
        }
    }

    private com.rbkmoney.questionary_proxy_aggr.base_dadata.QueryType convertQueryType(QueryType queryType) {
        switch (queryType) {
            case BYIDENTIFIRE:
                return com.rbkmoney.questionary_proxy_aggr.base_dadata.QueryType.BY_INDENTIFIRE;
            case FULLTEXTSEARCH:
                return com.rbkmoney.questionary_proxy_aggr.base_dadata.QueryType.FULL_TEXT_SEARCH;
            default:
                throw new IllegalArgumentException("Unknown queryType: " + queryType);
        }
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType convertBoundType(BoundType boundType) {
        switch (boundType) {
            case AREA:
                return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.area;
            case CITY:
                return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.city;
            case HOUSE:
                return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.house;
            case REGION:
                return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.region;
            case SETTLEMENT:
                return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.settlement;
            case STREET:
                return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.street;
            default:
                throw new IllegalArgumentException("Unknown boundType: " + boundType);
        }
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankQuery convertBankQuery(BankQuery swagBankQuery) {
        var thriftBankQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankQuery();
        thriftBankQuery.setQuery(swagBankQuery.getQuery());
        thriftBankQuery.setCount(swagBankQuery.getCount().byteValue());
        List<com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus> thriftOrgStatusList = swagBankQuery.getStatus().stream()
                .map(this::convertOrgStatus)
                .collect(Collectors.toList());
        thriftBankQuery.setStatus(thriftOrgStatusList);
        thriftBankQuery.setType(convertOrgType(swagBankQuery.getType()));
        return thriftBankQuery;
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyQuery convertPartyQuery(PartyQuery swagPartyQuery) {
        var thriftPartyQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyQuery();
        thriftPartyQuery.setQuery(swagPartyQuery.getQuery());
        thriftPartyQuery.setCount(swagPartyQuery.getCount().byteValue());
        List<com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus> thriftOrgStatusList = swagPartyQuery.getStatus().stream()
                .map(this::convertOrgStatus)
                .collect(Collectors.toList());
        thriftPartyQuery.setStatus(thriftOrgStatusList);
        thriftPartyQuery.setType(convertOrgType(swagPartyQuery.getType()));
        List<com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationBoostFilter> thriftLocationBoostFilters = swagPartyQuery.getLocationsBoost().stream()
                .map(this::convertLocationBoostFilter)
                .collect(Collectors.toList());
        thriftPartyQuery.setLocationsBoost(thriftLocationBoostFilters);
        List<com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationFilter> thriftLocationFilters = swagPartyQuery.getLocations().stream()
                .map(this::convertLocationFilter)
                .collect(Collectors.toList());
        thriftPartyQuery.setLocations(thriftLocationFilters);
        return thriftPartyQuery;
    }

    private com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationFilter convertLocationFilter(LocationFilter locationFilter) {
        var thriftLocationFilter = new com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationFilter();
        thriftLocationFilter.setKladrId(locationFilter.getKladrId());
        return thriftLocationFilter;
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQuery convertFmsUnitQuery(FmsUnitQuery swagFmsUnitQuery) {
        var thriftFmsUnitQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQuery();
        thriftFmsUnitQuery.setQuery(swagFmsUnitQuery.getQuery());
        thriftFmsUnitQuery.setQueryType(convertQueryType(swagFmsUnitQuery.getQueryType()));
        if (swagFmsUnitQuery.getFilters() != null && !swagFmsUnitQuery.getFilters().isEmpty()) {
            List<com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQueryFilter> thriftFmsUnitQueryFilters = swagFmsUnitQuery.getFilters().stream()
                    .map(this::convertFmsUnitQuery)
                    .collect(Collectors.toList());
            thriftFmsUnitQuery.setFilters(thriftFmsUnitQueryFilters);
        }
        return thriftFmsUnitQuery;
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQueryFilter convertFmsUnitQuery(FmsUnitQueryFilter fmsUnitQueryFilter) {
        var thriftFmsUnitQueryFilter = new com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQueryFilter();
        thriftFmsUnitQueryFilter.setRegionCode(fmsUnitQueryFilter.getRegionCode());
        thriftFmsUnitQueryFilter.setType(fmsUnitQueryFilter.getType());
        return thriftFmsUnitQueryFilter;
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressQuery convertAddressQuery(AddressQuery swagAddressQuery) {
        var thriftAddressQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressQuery();
        thriftAddressQuery.setQuery(swagAddressQuery.getQuery());
        thriftAddressQuery.setRestrictValue(swagAddressQuery.isRestrictValue());
        thriftAddressQuery.setToBound(convertBoundType(swagAddressQuery.getToBound()));
        thriftAddressQuery.setFromBound(convertBoundType(swagAddressQuery.getFromBound()));
        thriftAddressQuery.setCount(swagAddressQuery.getCount().byteValue());
        if (swagAddressQuery.getLocations() != null && !swagAddressQuery.getLocations().isEmpty()) {
            List<com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressLocationFilter> thriftAddressLocationFilters = swagAddressQuery.getLocations().stream()
                    .map(this::convertAddressLocationFilter)
                    .collect(Collectors.toList());
            thriftAddressQuery.setLocations(thriftAddressLocationFilters);
        }
        if (swagAddressQuery.getLocationsBoost() != null && !swagAddressQuery.getLocationsBoost().isEmpty()) {
            List<com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationBoostFilter> thriftLocationBoostFilters = swagAddressQuery.getLocationsBoost().stream()
                    .map(this::convertLocationBoostFilter)
                    .collect(Collectors.toList());
            thriftAddressQuery.setLocationsBoost(thriftLocationBoostFilters);
        }
        return thriftAddressQuery;
    }

    private com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationBoostFilter convertLocationBoostFilter(LocationBoostFilter locationBoostFilter) {
        var thriftLocationBoostFilter = new com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationBoostFilter();
        thriftLocationBoostFilter.setKladrId(locationBoostFilter.getKladrId());
        return thriftLocationBoostFilter;
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressLocationFilter convertAddressLocationFilter(AddressLocationFilter addressLocationFilter) {
        var thriftAddressLocationFilter = new com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressLocationFilter();
        thriftAddressLocationFilter.setCityFiasId(addressLocationFilter.getCityFiasId());
        thriftAddressLocationFilter.setCity(addressLocationFilter.getCity());
        thriftAddressLocationFilter.setKladrId(addressLocationFilter.getKladrId());
        thriftAddressLocationFilter.setAreaFiasId(addressLocationFilter.getAreaFiasId());
        thriftAddressLocationFilter.setRegion(addressLocationFilter.getRegion());
        thriftAddressLocationFilter.setRegionFiasId(addressLocationFilter.getRegionFiasId());
        thriftAddressLocationFilter.setSettlementFiasId(addressLocationFilter.getSettlementFiasId());
        thriftAddressLocationFilter.setStreetFiasId(addressLocationFilter.getStreetFiasId());
        return thriftAddressLocationFilter;
    }

}
