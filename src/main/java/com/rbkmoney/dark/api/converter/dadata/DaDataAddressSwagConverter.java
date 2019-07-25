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
        DaDataAddress daDataAddress = new DaDataAddress();
        daDataAddress.setBeltwayDistance(value.getBeltwayDistance());
        daDataAddress.setBeltwayHit(value.getBeltwayHit());
        daDataAddress.setCapitalMarker((int) value.getCapitalMarker());
        daDataAddress.setContry(value.getCountry());
        daDataAddress.setFederalDistrict(value.getFederalDistrict());
        daDataAddress.setFiasActualityState((int) value.getFiasActualityState());
        daDataAddress.setFiasId(value.getFiasId());
        daDataAddress.setFiasCode(value.getFiasCode());
        daDataAddress.setFiasLevel((int) value.getFiasLevel());
        daDataAddress.setQcGeo((int) value.getQcGeo());
        daDataAddress.setGeoLat(value.getGeoLat());
        daDataAddress.setGeoLon(value.getGeoLon());
        daDataAddress.setGeonameId(value.getGeonameId());
        daDataAddress.setKladrId(value.getKladrId());
        daDataAddress.setOkato(value.getOkato());
        daDataAddress.setOktmo(value.getOktmo());
        daDataAddress.setTaxOffice(value.getTaxOffice());
        daDataAddress.setTaxOfficeLegal(value.getTaxOfficeLegal());
        daDataAddress.setPostalBox(value.getPostalBox());
        daDataAddress.setPostalCode(value.getPostalCode());
        daDataAddress.setSource(value.getSource());
        daDataAddress.setTimezone(value.getTimezone());
        daDataAddress.setValue(value.getValue());
        daDataAddress.setUnrestrictedValue(value.getUnrestrictedValue());
        daDataAddress.setHistoryValues(value.getHistoryValues());
        daDataAddress.setSquareMeterPrice(value.getSquareMeterPrice());
        daDataAddress.setHistoryValues(value.getHistoryValues());

        if (value.isSetArea()) {
            AddressAreaData addressAreaData = new AddressAreaData();
            addressAreaData.setArea(value.getArea().getArea());
            addressAreaData.setAreaFiasId(value.getArea().getAreaFiasId());
            addressAreaData.setAreaKladrId(value.getArea().getAreaKladrId());
            addressAreaData.setAreaType(value.getArea().getAreaType());
            addressAreaData.setAreaTypeFull(value.getArea().getAreaTypeFull());
            addressAreaData.setAreaWithType(value.getArea().getAreaWithType());
            daDataAddress.setArea(addressAreaData);
        }

        if (value.isSetBlock()) {
            AddressBlockData swagAddressBlockData = new AddressBlockData();
            swagAddressBlockData.setBlock(value.getBlock().getBlock());
            swagAddressBlockData.setBlockType(value.getBlock().getBlockType());
            swagAddressBlockData.setBlockTypeFull(value.getBlock().getBlockTypeFull());
            daDataAddress.setBlock(swagAddressBlockData);
        }

        if (value.isSetCity()) {
            AddressCityData addressCityData = new AddressCityData();
            addressCityData.setCity(value.getCity().getCity());
            addressCityData.setCityArea(value.getCity().getCityArea());
            addressCityData.setCityFiasId(value.getCity().getCityFiasId());
            addressCityData.setCityKladrId(value.getCity().getCityKladrId());
            addressCityData.setCityType(value.getCity().getCityType());
            addressCityData.setCityTypeFull(value.getCity().getCityTypeFull());
            addressCityData.setCityWithType(value.getCity().getCityWithType());
            daDataAddress.setCity(addressCityData);
        }

        if (value.isSetFlatData()) {
            AddressFlatData addressFlatData = new AddressFlatData();
            addressFlatData.setFlatType(value.getFlatData().getFlatType());
            addressFlatData.setFlat(value.getFlatData().getFlat());
            addressFlatData.setFlatPrice(value.getFlatData().getFlatPrice());
            addressFlatData.setFlatArea(value.getFlatData().getFlatArea());
            addressFlatData.setFlatTypeFull(value.getFlatData().getFlatTypeFull());
            daDataAddress.setFlatData(addressFlatData);
        }

        if (value.isSetCityDistrict()) {
            AddressCityDistrictData addressCityDistrictData = new AddressCityDistrictData();
            addressCityDistrictData.setCityDistrict(value.getCityDistrict().getCityDistrict());
            addressCityDistrictData.setCityDistrictFiasId(value.getCityDistrict().getCityDistrictFiasId());
            addressCityDistrictData.setCityDistrictKladrId(value.getCityDistrict().getCityDistrictKladrId());
            addressCityDistrictData.setCityDistrictType(value.getCityDistrict().getCityDistrictType());
            addressCityDistrictData.setCityDistrictTypeFull(value.getCityDistrict().getCityDistrictTypeFull());
            addressCityDistrictData.setCityDistrictWithType(value.getCityDistrict().getCityDistrictWithType());
            daDataAddress.setCityDistrict(addressCityDistrictData);
        }

        if (value.isSetHouse()) {
            AddressHouseData addressHouseData = new AddressHouseData();
            addressHouseData.setHouse(value.getHouse().getHouse());
            addressHouseData.setHouseFiasId(value.getHouse().getHouseFiasId());
            addressHouseData.setHouseKladrId(value.getHouse().getHouseKladrId());
            addressHouseData.setHouseType(value.getHouse().getHouseType());
            addressHouseData.setHouseTypeFull(value.getHouse().getHouseTypeFull());
            daDataAddress.setHouse(addressHouseData);
        }

        if (value.isSetRegion()) {
            AddressRegionData addressRegionData = new AddressRegionData();
            addressRegionData.setRegion(value.getRegion().getRegion());
            addressRegionData.setRegionFiasId(value.getRegion().getRegionFiasId());
            addressRegionData.setRegionKladrId(value.getRegion().getRegionKladrId());
            addressRegionData.setRegionType(value.getRegion().getRegionType());
            addressRegionData.setRegionWithType(value.getRegion().getRegionWithType());
            daDataAddress.setRegion(addressRegionData);
        }

        if (value.isSetSettlement()) {
            AddressSettlementData addressSettlementData = new AddressSettlementData();
            addressSettlementData.setSettlement(value.getSettlement().getSettlement());
            addressSettlementData.setSettlementFiasId(value.getSettlement().getSettlementFiasId());
            addressSettlementData.setSettlementKladrId(value.getSettlement().getSettlementKladrId());
            addressSettlementData.setSettlementType(value.getSettlement().getSettlementType());
            addressSettlementData.setSettlementTypeFull(value.getSettlement().getSettlementTypeFull());
            addressSettlementData.settlementWithType(value.getSettlement().getSettlementWithType());
            daDataAddress.setSettlement(addressSettlementData);
        }

        if (value.isSetMetroList()) {
            List<AddressMetro> swagAddressMetroList = value.getMetroList().stream()
                    .map(addressMetro -> {
                        AddressMetro swagAddressMetro = new AddressMetro();
                        swagAddressMetro.setName(addressMetro.getName());
                        swagAddressMetro.setDistance(addressMetro.getDistance());
                        swagAddressMetro.setLine(addressMetro.getLine());
                        return swagAddressMetro;
                    })
                    .collect(Collectors.toList());
            daDataAddress.setMetroList(swagAddressMetroList);
        }

        return daDataAddress;
    }

}
