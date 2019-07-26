package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DaDataParamToDaDataRequest implements ThriftConverter<DaDataRequestHolder, DaDataParams> {

    @Override
    public DaDataRequestHolder toThrift(DaDataParams daDataParams, ThriftConverterContext thriftConverterContext) {
        DaDataRequestHolder daDataRequestHolder = new DaDataRequestHolder();
        DaDataRequest daDataRequest = new DaDataRequest();
        daDataRequestHolder.setDaDataRequest(daDataRequest);
        if (daDataParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.OKVED2) {
            daDataRequestHolder.setDaDataEndpoint(com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint.okved2);
            OkvedQuery swagOkvedQuery = ((OkvedQuery) daDataParams.getRequest());
            var thriftOkvedQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedQuery();
            thriftOkvedQuery.setQuery(swagOkvedQuery.getQuery());
            thriftOkvedQuery.setQueryType(convertQueryType(swagOkvedQuery.getQueryType()));
            daDataRequest.setOkvedQuery(thriftOkvedQuery);
        } else if (daDataParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTFIO) {
            daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_fio);
            FioQuery swagFioQuery = (FioQuery) daDataParams.getRequest();
            var thriftFioQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioQuery();
            thriftFioQuery.setQuery(swagFioQuery.getQuery());
            thriftFioQuery.setCount(swagFioQuery.getCount().byteValue());
            thriftFioQuery.setParts(swagFioQuery.getParts());
            if (swagFioQuery.getGender() == Gender.FEMALE) {
                thriftFioQuery.setGender(com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.FEMALE);
            } else if (swagFioQuery.getGender() == Gender.MALE) {
                thriftFioQuery.setGender(com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.MALE);
            } else if (swagFioQuery.getGender() == Gender.UNKNOWN) {
                thriftFioQuery.setGender(com.rbkmoney.questionary_proxy_aggr.base_dadata.Gender.UNKNOWN);
            }
            daDataRequest.setFioQuery(thriftFioQuery);
        } else if (daDataParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTBANK) {
            daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_bank);
            BankQuery swagBankQuery = (BankQuery) daDataParams.getRequest();
            daDataRequest.setBankQuery(convertBankQuery(swagBankQuery));
        } else if (daDataParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTPARTY) {
            daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_party);
            PartyQuery swagPartyQuery = (PartyQuery) daDataParams.getRequest();
            daDataRequest.setPartyQuery(convertPartyQuery(swagPartyQuery));
        } else if (daDataParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTFMSUNIT) {
            daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_fms_unit);
            FmsUnitQuery swagFmsUnitQuery = (FmsUnitQuery) daDataParams.getRequest();
            daDataRequest.setFmsUnitQuery(convertFmsUnitQuery(swagFmsUnitQuery));
        } else if (daDataParams.getEndpoint() == com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTADDRESS) {
            daDataRequestHolder.setDaDataEndpoint(DaDataEndpoint.suggest_address);
            AddressQuery swagAddressQuery = (AddressQuery) daDataParams.getRequest();
            daDataRequest.setAddressQuery(convertAddressQuery(swagAddressQuery));
        }
        return daDataRequestHolder;
    }

    private com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus convertOrgStatus(OrgStatus orgStatus) {
        if (orgStatus == OrgStatus.ACTIVE) {
            return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.ACTIVE;
        } else if (orgStatus == OrgStatus.LIQUIDATED) {
            return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.LIQUIDATED;
        } else if (orgStatus == OrgStatus.LIQUIDATING) {
            return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.LIQUIDATING;
        } else if (orgStatus == OrgStatus.REORGANIZING) {
            return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus.REORGANIZING;
        }
        throw new IllegalArgumentException("Unknown orgStatus: " + orgStatus);
    }

    private com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgType convertOrgType(OrgType orgType) {
        if (orgType == OrgType.LEGAL) {
            return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgType.LEGAL;
        } else if (orgType == OrgType.INDIVIDUAL) {
            return com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgType.INDIVIDUAL;
        }
        throw new IllegalArgumentException("Unknown orgType: " + orgType);
    }

    private com.rbkmoney.questionary_proxy_aggr.base_dadata.QueryType convertQueryType(QueryType queryType) {
        if (queryType == QueryType.BYIDENTIFIRE) {
            return com.rbkmoney.questionary_proxy_aggr.base_dadata.QueryType.BY_INDENTIFIRE;
        } else if (queryType == QueryType.FULLTEXTSEARCH) {
            return com.rbkmoney.questionary_proxy_aggr.base_dadata.QueryType.FULL_TEXT_SEARCH;
        }
        throw new IllegalArgumentException("Unknown queryType: " + queryType);
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType convertBoundType(BoundType boundType) {
        if (boundType == BoundType.AREA) {
            return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.area;
        } else if (boundType == BoundType.CITY) {
            return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.city;
        } else if (boundType == BoundType.HOUSE) {
            return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.house;
        } else if (boundType == BoundType.REGION) {
            return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.region;
        } else if (boundType == BoundType.SETTLEMENT) {
            return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.settlement;
        } else if (boundType == BoundType.STREET) {
            return com.rbkmoney.questionary_proxy_aggr.dadata_address.BoundType.street;
        }
        throw new IllegalArgumentException("Unknown boundType: " + boundType);
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankQuery convertBankQuery(BankQuery swagBankQuery) {
        var thriftBankQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankQuery();
        thriftBankQuery.setQuery(swagBankQuery.getQuery());
        thriftBankQuery.setCount(swagBankQuery.getCount().byteValue());
        List<com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgStatus> thriftOrgStatusList = swagBankQuery.getStatus().stream()
                .map(this::convertOrgStatus)
                .filter(Objects::nonNull)
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
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        thriftPartyQuery.setStatus(thriftOrgStatusList);
        thriftPartyQuery.setType(convertOrgType(swagPartyQuery.getType()));
        List<com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationBoostFilter> thriftLocationBoostFilters = swagPartyQuery.getLocationsBoost().stream()
                .map(locationBoostFilter -> {
                    var thriftLocationBoostFilter = new com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationBoostFilter();
                    thriftLocationBoostFilter.setKladrId(locationBoostFilter.getKladrId());
                    return thriftLocationBoostFilter;
                })
                .collect(Collectors.toList());
        thriftPartyQuery.setLocationsBoost(thriftLocationBoostFilters);
        List<com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationFilter> thriftLocationFilters = swagPartyQuery.getLocations().stream()
                .map(locationFilter -> {
                    var thriftLocationFilter = new com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationFilter();
                    thriftLocationFilter.setKladrId(locationFilter.getKladrId());
                    return thriftLocationFilter;
                })
                .collect(Collectors.toList());
        thriftPartyQuery.setLocations(thriftLocationFilters);
        return thriftPartyQuery;
    }

    private com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQuery convertFmsUnitQuery(FmsUnitQuery swagFmsUnitQuery) {
        var thriftFmsUnitQuery = new com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQuery();
        thriftFmsUnitQuery.setQuery(swagFmsUnitQuery.getQuery());
        thriftFmsUnitQuery.setQueryType(convertQueryType(swagFmsUnitQuery.getQueryType()));
        if (swagFmsUnitQuery.getFilters() != null && !swagFmsUnitQuery.getFilters().isEmpty()) {
            List<com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQueryFilter> thriftFmsUnitQueryFilters = swagFmsUnitQuery.getFilters().stream()
                    .map(fmsUnitQueryFilter -> {
                        var thriftFmsUnitQueryFilter = new com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitQueryFilter();
                        thriftFmsUnitQueryFilter.setRegionCode(fmsUnitQueryFilter.getRegionCode());
                        thriftFmsUnitQueryFilter.setType(fmsUnitQueryFilter.getType());
                        return thriftFmsUnitQueryFilter;
                    })
                    .collect(Collectors.toList());
            thriftFmsUnitQuery.setFilters(thriftFmsUnitQueryFilters);
        }
        return thriftFmsUnitQuery;
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
                    .map(addressLocationFilter -> {
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
                    })
                    .collect(Collectors.toList());
            thriftAddressQuery.setLocations(thriftAddressLocationFilters);
        }
        if (swagAddressQuery.getLocationsBoost() != null && !swagAddressQuery.getLocationsBoost().isEmpty()) {
            List<com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationBoostFilter> thriftLocationBoostFilters = swagAddressQuery.getLocationsBoost().stream()
                    .map(locationBoostFilter -> {
                        var thriftLocationBoostFilter = new com.rbkmoney.questionary_proxy_aggr.base_dadata.LocationBoostFilter();
                        thriftLocationBoostFilter.setKladrId(locationBoostFilter.getKladrId());
                        return thriftLocationBoostFilter;
                    })
                    .collect(Collectors.toList());
            thriftAddressQuery.setLocationsBoost(thriftLocationBoostFilters);
        }
        return thriftAddressQuery;
    }

}
