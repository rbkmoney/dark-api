package com.rbkmoney.dark.api.converter.dadata;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.Address;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DaDataAddressSwagConverter implements SwagConverter<DaDataAddress, Address> {

    @Override
    public DaDataAddress toSwag(Address value, SwagConverterContext ctx) {
        DaDataAddress daDataAddress = new DaDataAddress()
                .beltwayDistance(value.getBeltwayDistance())
                .beltwayHit(value.getBeltwayHit())
                .capitalMarker((int) value.getCapitalMarker())
                .contry(value.getCountry())
                .federalDistrict(value.getFederalDistrict())
                .fiasActualityState((int) value.getFiasActualityState())
                .fiasId(value.getFiasId())
                .fiasCode(value.getFiasCode())
                .fiasLevel((int) value.getFiasLevel())
                .qcGeo((int) value.getQcGeo())
                .geoLat(value.getGeoLat())
                .geoLon(value.getGeoLon())
                .geonameId(value.getGeonameId())
                .kladrId(value.getKladrId())
                .okato(value.getOkato())
                .oktmo(value.getOktmo())
                .taxOffice(value.getTaxOffice())
                .taxOfficeLegal(value.getTaxOfficeLegal())
                .postalBox(value.getPostalBox())
                .postalCode(value.getPostalCode())
                .source(value.getSource())
                .timezone(value.getTimezone())
                .value(value.getValue())
                .unrestrictedValue(value.getUnrestrictedValue())
                .historyValues(value.getHistoryValues())
                .squareMeterPrice(value.getSquareMeterPrice());

        if (value.isSetArea()) {
            daDataAddress.setArea(convertAddressAreaData(value.getArea()));
        }

        if (value.isSetBlock()) {
            daDataAddress.setBlock(convertAddressBlock(value.getBlock()));
        }

        if (value.isSetCity()) {
            daDataAddress.setCity(convertAddressCityData(value.getCity()));
        }

        if (value.isSetFlatData()) {
            daDataAddress.setFlatData(convertAddressFlatData(value.getFlatData()));
        }

        if (value.isSetCityDistrict()) {
            daDataAddress.setCityDistrict(convertAddressCityDistrictData(value.getCityDistrict()));
        }

        if (value.isSetHouse()) {
            daDataAddress.setHouse(convertAddressHouseData(value.getHouse()));
        }

        if (value.isSetRegion()) {
            daDataAddress.setRegion(convertAddressRegionData(value.getRegion()));
        }

        if (value.isSetSettlement()) {
            daDataAddress.setSettlement(convertAddressSettlementData(value.getSettlement()));
        }

        if (value.isSetMetroList()) {
            List<AddressMetro> swagAddressMetroList = value.getMetroList().stream()
                    .map(this::convertAddressMetro)
                    .collect(Collectors.toList());
            daDataAddress.setMetroList(swagAddressMetroList);
        }

        return daDataAddress;
    }

    private AddressAreaData convertAddressAreaData(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressAreaData addressAreaData) {
        AddressAreaData swagAddressAreaData = new AddressAreaData();
        swagAddressAreaData.setArea(addressAreaData.getArea());
        swagAddressAreaData.setAreaFiasId(addressAreaData.getAreaFiasId());
        swagAddressAreaData.setAreaKladrId(addressAreaData.getAreaKladrId());
        swagAddressAreaData.setAreaType(addressAreaData.getAreaType());
        swagAddressAreaData.setAreaTypeFull(addressAreaData.getAreaTypeFull());
        swagAddressAreaData.setAreaWithType(addressAreaData.getAreaWithType());
        return swagAddressAreaData;
    }

    private AddressBlockData convertAddressBlock(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressBlockData addressBlockData) {
        AddressBlockData swagAddressBlockData = new AddressBlockData();
        swagAddressBlockData.setBlockTypeFull(addressBlockData.getBlockTypeFull());
        swagAddressBlockData.setBlockType(addressBlockData.getBlockType());
        swagAddressBlockData.setBlock(addressBlockData.getBlock());
        return swagAddressBlockData;
    }

    private AddressCityData convertAddressCityData(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressCityData addressCityData) {
        AddressCityData swagAddressCityData = new AddressCityData();
        swagAddressCityData.setCity(addressCityData.getCity());
        swagAddressCityData.setCityArea(addressCityData.getCityArea());
        swagAddressCityData.setCityFiasId(addressCityData.getCityFiasId());
        swagAddressCityData.setCityKladrId(addressCityData.getCityKladrId());
        swagAddressCityData.setCityType(addressCityData.getCityType());
        swagAddressCityData.setCityTypeFull(addressCityData.getCityTypeFull());
        swagAddressCityData.setCityWithType(addressCityData.getCityWithType());
        return swagAddressCityData;
    }

    private AddressCityDistrictData convertAddressCityDistrictData(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressCityDistrictData addressCityDistrictData) {
        AddressCityDistrictData swagAddressCityDistrictData = new AddressCityDistrictData();
        swagAddressCityDistrictData.setCityDistrict(addressCityDistrictData.getCityDistrict());
        swagAddressCityDistrictData.setCityDistrictFiasId(addressCityDistrictData.getCityDistrictFiasId());
        swagAddressCityDistrictData.setCityDistrictKladrId(addressCityDistrictData.getCityDistrictKladrId());
        swagAddressCityDistrictData.setCityDistrictType(addressCityDistrictData.getCityDistrictType());
        swagAddressCityDistrictData.setCityDistrictTypeFull(addressCityDistrictData.getCityDistrictTypeFull());
        swagAddressCityDistrictData.setCityDistrictWithType(addressCityDistrictData.getCityDistrictWithType());
        return swagAddressCityDistrictData;
    }

    private AddressFlatData convertAddressFlatData(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressFlatData addressFlatData) {
        AddressFlatData swagAddressFlatData = new AddressFlatData();
        swagAddressFlatData.setFlatType(addressFlatData.getFlatType());
        swagAddressFlatData.setFlat(addressFlatData.getFlat());
        swagAddressFlatData.setFlatPrice(addressFlatData.getFlatPrice());
        swagAddressFlatData.setFlatArea(addressFlatData.getFlatArea());
        swagAddressFlatData.setFlatTypeFull(addressFlatData.getFlatTypeFull());
        return swagAddressFlatData;
    }

    private AddressHouseData convertAddressHouseData(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressHouseData addressHouseData) {
        AddressHouseData swagAddressHouseData = new AddressHouseData();
        swagAddressHouseData.setHouse(addressHouseData.getHouse());
        swagAddressHouseData.setHouseFiasId(addressHouseData.getHouseFiasId());
        swagAddressHouseData.setHouseKladrId(addressHouseData.getHouseKladrId());
        swagAddressHouseData.setHouseType(addressHouseData.getHouseType());
        swagAddressHouseData.setHouseTypeFull(addressHouseData.getHouseTypeFull());
        return swagAddressHouseData;
    }

    private AddressRegionData convertAddressRegionData(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressRegionData addressRegionData) {
        AddressRegionData swagAddressRegionData = new AddressRegionData();
        swagAddressRegionData.setRegion(addressRegionData.getRegion());
        swagAddressRegionData.setRegionFiasId(addressRegionData.getRegionFiasId());
        swagAddressRegionData.setRegionKladrId(addressRegionData.getRegionKladrId());
        swagAddressRegionData.setRegionType(addressRegionData.getRegionType());
        swagAddressRegionData.setRegionWithType(addressRegionData.getRegionWithType());
        return swagAddressRegionData;
    }

    private AddressSettlementData convertAddressSettlementData(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressSettlementData addressSettlementData) {
        AddressSettlementData swagAddressSettlementData = new AddressSettlementData();
        swagAddressSettlementData.setSettlement(addressSettlementData.getSettlement());
        swagAddressSettlementData.setSettlementFiasId(addressSettlementData.getSettlementFiasId());
        swagAddressSettlementData.setSettlementKladrId(addressSettlementData.getSettlementKladrId());
        swagAddressSettlementData.setSettlementType(addressSettlementData.getSettlementType());
        swagAddressSettlementData.setSettlementTypeFull(addressSettlementData.getSettlementTypeFull());
        swagAddressSettlementData.settlementWithType(addressSettlementData.getSettlementWithType());
        return swagAddressSettlementData;
    }

    private AddressMetro convertAddressMetro(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressMetro addressMetro) {
        AddressMetro swagAddressMetro = new AddressMetro();
        swagAddressMetro.setName(addressMetro.getName());
        swagAddressMetro.setDistance(addressMetro.getDistance());
        swagAddressMetro.setLine(addressMetro.getLine());
        return swagAddressMetro;
    }



}
