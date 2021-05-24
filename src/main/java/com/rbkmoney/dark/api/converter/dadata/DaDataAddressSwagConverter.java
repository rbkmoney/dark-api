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

    private AddressAreaData convertAddressAreaData(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressAreaData addressAreaData) {
        return new AddressAreaData()
                .area(addressAreaData.getArea())
                .areaFiasId(addressAreaData.getAreaFiasId())
                .areaKladrId(addressAreaData.getAreaKladrId())
                .areaType(addressAreaData.getAreaType())
                .areaTypeFull(addressAreaData.getAreaTypeFull())
                .areaWithType(addressAreaData.getAreaWithType());
    }

    private AddressBlockData convertAddressBlock(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressBlockData addressBlockData) {
        return new AddressBlockData()
                .block(addressBlockData.getBlock())
                .blockTypeFull(addressBlockData.getBlockTypeFull())
                .blockType(addressBlockData.getBlockType());
    }

    private AddressCityData convertAddressCityData(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressCityData addressCityData) {
        return new AddressCityData()
                .city(addressCityData.getCity())
                .cityArea(addressCityData.getCityArea())
                .cityFiasId(addressCityData.getCityFiasId())
                .cityKladrId(addressCityData.getCityKladrId())
                .cityType(addressCityData.getCityType())
                .cityTypeFull(addressCityData.getCityTypeFull())
                .cityWithType(addressCityData.getCityWithType());
    }

    private AddressCityDistrictData convertAddressCityDistrictData(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressCityDistrictData addressCityDistrictData) {
        return new AddressCityDistrictData()
                .cityDistrict(addressCityDistrictData.getCityDistrict())
                .cityDistrictFiasId(addressCityDistrictData.getCityDistrictFiasId())
                .cityDistrictKladrId(addressCityDistrictData.getCityDistrictKladrId())
                .cityDistrictType(addressCityDistrictData.getCityDistrictType())
                .cityDistrictTypeFull(addressCityDistrictData.getCityDistrictTypeFull())
                .cityDistrictWithType(addressCityDistrictData.getCityDistrictWithType());
    }

    private AddressFlatData convertAddressFlatData(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressFlatData addressFlatData) {
        return new AddressFlatData()
                .flatType(addressFlatData.getFlatType())
                .flat(addressFlatData.getFlat())
                .flatPrice(addressFlatData.getFlatPrice())
                .flatArea(addressFlatData.getFlatArea())
                .flatTypeFull(addressFlatData.getFlatTypeFull());
    }

    private AddressHouseData convertAddressHouseData(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressHouseData addressHouseData) {
        return new AddressHouseData()
                .house(addressHouseData.getHouse())
                .houseFiasId(addressHouseData.getHouseFiasId())
                .houseKladrId(addressHouseData.getHouseKladrId())
                .houseType(addressHouseData.getHouseType())
                .houseTypeFull(addressHouseData.getHouseTypeFull());
    }

    private AddressRegionData convertAddressRegionData(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressRegionData addressRegionData) {
        return new AddressRegionData()
                .region(addressRegionData.getRegion())
                .regionFiasId(addressRegionData.getRegionFiasId())
                .regionKladrId(addressRegionData.getRegionKladrId())
                .regionType(addressRegionData.getRegionType())
                .regionWithType(addressRegionData.getRegionWithType());
    }

    private AddressSettlementData convertAddressSettlementData(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressSettlementData addressSettlementData) {
        return new AddressSettlementData()
                .settlement(addressSettlementData.getSettlement())
                .settlementFiasId(addressSettlementData.getSettlementFiasId())
                .settlementKladrId(addressSettlementData.getSettlementKladrId())
                .settlementType(addressSettlementData.getSettlementType())
                .settlementTypeFull(addressSettlementData.getSettlementTypeFull())
                .settlementWithType(addressSettlementData.getSettlementWithType());
    }

    private AddressMetro convertAddressMetro(
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressMetro addressMetro) {
        return new AddressMetro()
                .name(addressMetro.getName())
                .distance(addressMetro.getDistance())
                .line(addressMetro.getLine());
    }

}
