package com.rbkmoney.dark.api.utils;

import com.rbkmoney.questionary_proxy_aggr.base_dadata.Registration;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.Address;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DaDataCompareUtil {

    public static void fmsUnitCompare(com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent thriftFmsUnitContent, FmsUnitContent swagFmsUnitContent) {
        Assert.assertEquals(thriftFmsUnitContent.getCode(), swagFmsUnitContent.getCode());
        Assert.assertEquals(thriftFmsUnitContent.getName(), swagFmsUnitContent.getName());
        Assert.assertEquals(thriftFmsUnitContent.getRegionCode(), swagFmsUnitContent.getRegionCode());
        Assert.assertEquals(thriftFmsUnitContent.getValue(), swagFmsUnitContent.getValue());
        Assert.assertEquals(thriftFmsUnitContent.getUnrestrictdValue(), swagFmsUnitContent.getUnrestrictedValue());
        Assert.assertEquals(thriftFmsUnitContent.getType(), swagFmsUnitContent.getType());
    }

    public static void fioCompare(com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioContent thriftFioContent, FioContent swagFioContent) {
        Assert.assertEquals(thriftFioContent.getName(), swagFioContent.getName());
        Assert.assertEquals(thriftFioContent.getPatronymic(), swagFioContent.getPatronymic());
        Assert.assertEquals(thriftFioContent.getQc(), swagFioContent.getQc().byteValue());
        Assert.assertEquals(thriftFioContent.getSurname(), swagFioContent.getSurname());
        Assert.assertEquals(thriftFioContent.getValue(), swagFioContent.getValue());
        Assert.assertEquals(thriftFioContent.getUnrestrictedValue(), swagFioContent.getUnrestrictedValue());
    }

    public static void bankCompare(com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankContent thriftBankContent, BankContent swagBankContent) {
        Assert.assertEquals(thriftBankContent.getPayment().getFullName(), swagBankContent.getPayment().getFullName());
        Assert.assertEquals(thriftBankContent.getPayment().getName(), swagBankContent.getPayment().getName());
        Assert.assertEquals(thriftBankContent.getPayment().getShortName(), swagBankContent.getPayment().getShortName());
        addressCompare(thriftBankContent.getAddress(), swagBankContent.getAddress());
        Assert.assertEquals(thriftBankContent.getBic(), swagBankContent.getBic());
        Assert.assertEquals(thriftBankContent.getCorrespondentAccount(), swagBankContent.getCorrespondentAccount());
        Assert.assertEquals(thriftBankContent.getOkpo(), swagBankContent.getOkpo());
        opfCompare(thriftBankContent.getOpf(), swagBankContent.getOpf());
        Assert.assertEquals(thriftBankContent.getPhone(), swagBankContent.getPhone());
        Assert.assertEquals(thriftBankContent.getRegistrationNumber(), swagBankContent.getRegistrationNumber());
        Assert.assertEquals(thriftBankContent.getValue(), swagBankContent.getValue());
        Assert.assertEquals(thriftBankContent.getUnrestrictedValue(), swagBankContent.getUnrestrictedValue());
        Assert.assertEquals(thriftBankContent.getSwift(), swagBankContent.getSwift());
        Assert.assertEquals(thriftBankContent.getRkc(), swagBankContent.getRkc());
    }

    public static void okvedCompare(com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedContent thriftOkvedContent, OkvedContent swagOkvedContent) {
        Assert.assertEquals(thriftOkvedContent.getCode(), swagOkvedContent.getCode());
        Assert.assertEquals(thriftOkvedContent.getIdx(), swagOkvedContent.getIdx());
        Assert.assertEquals(thriftOkvedContent.getName(), swagOkvedContent.getName());
        Assert.assertEquals(thriftOkvedContent.getSection(), swagOkvedContent.getSection());
        Assert.assertEquals(thriftOkvedContent.getValue(), swagOkvedContent.getValue());
    }

    public static void partyCompare(com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyContent thriftPartyContent, PartyContent swagPartyContent) {
        addressCompare(thriftPartyContent.getAddress(), swagPartyContent.getAddress());
        authoritiesCompare(thriftPartyContent.getAuthorities().getFtsRegistration(), swagPartyContent.getAuthorities().getFtsRegistration());
        authoritiesCompare(thriftPartyContent.getAuthorities().getFtsReport(), swagPartyContent.getAuthorities().getFtsReport());
        authoritiesCompare(thriftPartyContent.getAuthorities().getPf(), swagPartyContent.getAuthorities().getPf());
        authoritiesCompare(thriftPartyContent.getAuthorities().getSif(), swagPartyContent.getAuthorities().getSif());
        registrationCompare(thriftPartyContent.getDocuments().getFtsRegistration(), swagPartyContent.getDocuments().getFtsRegistration());
        registrationCompare(thriftPartyContent.getDocuments().getPfRegistration(), swagPartyContent.getDocuments().getPfRegistration());
        registrationCompare(thriftPartyContent.getDocuments().getSifRegistration(), swagPartyContent.getDocuments().getSifRegistration());
        Assert.assertEquals(thriftPartyContent.getInn(), swagPartyContent.getInn());
        Assert.assertEquals(thriftPartyContent.getKpp(), swagPartyContent.getKpp());
        for (int i = 0; i < thriftPartyContent.getLicenses().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.base_dadata.License license = thriftPartyContent.getLicenses().get(i);
            DaDataLicense swagLicense = swagPartyContent.getLicenses().get(i);
            daDataLicenseCompare(license, swagLicense);
        }
        orgNameCompare(thriftPartyContent.getName(), swagPartyContent.getName());
        Assert.assertEquals(thriftPartyContent.getOgrn(), swagPartyContent.getOgrn());
        Assert.assertEquals(thriftPartyContent.getOkpo(), swagPartyContent.getOkpo());
        opfCompare(thriftPartyContent.getOpf(), swagPartyContent.getOpf());
        Assert.assertEquals(thriftPartyContent.getValue(), swagPartyContent.getValue());
        Assert.assertEquals(thriftPartyContent.getUnrestrictedValue(), swagPartyContent.getUnrestrictedValue());
        Assert.assertEquals(thriftPartyContent.getCapital().getType(), swagPartyContent.getCapital().getType());
        Assert.assertEquals(thriftPartyContent.getCapital().getValue(), swagPartyContent.getCapital().getValue());
        Assert.assertEquals(thriftPartyContent.getCitizenship().getAlpha3(), swagPartyContent.getCitizenship().getAplha3());
        Assert.assertEquals(thriftPartyContent.getCitizenship().getCountryFullName(), swagPartyContent.getCitizenship().getCountryFullName());
        Assert.assertEquals(thriftPartyContent.getCitizenship().getCountryShortName(), swagPartyContent.getCitizenship().getCountryShortName());
        Assert.assertEquals(thriftPartyContent.getHid(), swagPartyContent.getHid().getHid());
        Assert.assertEquals(thriftPartyContent.getManagement().getName(), swagPartyContent.getManagement().getName());
        Assert.assertEquals(thriftPartyContent.getManagement().getPost(), swagPartyContent.getManagement().getPost());
        Assert.assertEquals(thriftPartyContent.getOgrnDate(), swagPartyContent.getOgrnDate());
        Assert.assertEquals(thriftPartyContent.getBranchCount(), ((int) swagPartyContent.getBranchCount()));
        Assert.assertEquals(thriftPartyContent.getOkved(), swagPartyContent.getOkved());
        for (int i = 0; i < thriftPartyContent.getOkveds().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyOkved partyOkved = thriftPartyContent.getOkveds().get(i);
            PartyOkved swagPartyOkved = swagPartyContent.getOkveds().get(i);
            Assert.assertEquals(partyOkved.getCode(), swagPartyOkved.getCode());
            Assert.assertEquals(partyOkved.getName(), swagPartyOkved.getName());
        }
    }

    private static void opfCompare(com.rbkmoney.questionary_proxy_aggr.base_dadata.Opf thriftOpf, Opf swagOpf) {
        Assert.assertEquals(thriftOpf.getCode(), swagOpf.getCode());
        Assert.assertEquals(thriftOpf.getFullName(), swagOpf.getFullName());
        Assert.assertEquals(thriftOpf.getShortName(), swagOpf.getShortName());
        Assert.assertEquals(thriftOpf.getType(), swagOpf.getType());
    }

    private static void orgNameCompare(com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgName thriftOrgName, OrgName swagOrgName) {
        Assert.assertEquals(thriftOrgName.getFullName(), swagOrgName.getFullName());
        Assert.assertEquals(thriftOrgName.getFullWithOpf(), swagOrgName.getFullWithOpf());
        Assert.assertEquals(thriftOrgName.getLatin(), swagOrgName.getLatin());
        Assert.assertEquals(thriftOrgName.getShortName(), swagOrgName.getShortName());
        Assert.assertEquals(thriftOrgName.getShortWithOpf(), swagOrgName.getShortWithOpf());
    }

    private static void daDataLicenseCompare(com.rbkmoney.questionary_proxy_aggr.base_dadata.License thriftLicense, DaDataLicense swagLicense) {
        Assert.assertThat(thriftLicense.getActivities(), CoreMatchers.is(swagLicense.getActivities()));
        Assert.assertThat(thriftLicense.getAddresses(), CoreMatchers.is(swagLicense.getAddresses()));
        Assert.assertEquals(thriftLicense.getIssueAuthority(), swagLicense.getIssueAuthority());
        Assert.assertEquals(thriftLicense.getIssueDate(), swagLicense.getIssueDate());
        Assert.assertEquals(thriftLicense.getSeries(), swagLicense.getSeries());
        Assert.assertEquals(thriftLicense.getNumber(), swagLicense.getNumber());
        Assert.assertEquals(thriftLicense.getSuspendAuthority(), swagLicense.getSuspendAuthority());
        Assert.assertEquals(thriftLicense.getSuspendDate(), swagLicense.getSuspendDate());
        Assert.assertEquals(thriftLicense.getValidFrom(), swagLicense.getValidFrom());
        Assert.assertEquals(thriftLicense.getValidTo(), swagLicense.getValidTo());
    }

    private static void registrationCompare(Registration thriftRegistration, IFNCRegistration swagRegistration) {
        Assert.assertEquals(thriftRegistration.getIssueAuthority(), swagRegistration.getIssueAuthority());
        Assert.assertEquals(thriftRegistration.getIssueDate(), swagRegistration.getIssueDate());
    }

    private static void authoritiesCompare(com.rbkmoney.questionary_proxy_aggr.base_dadata.Authorities thriftAuthorities, com.rbkmoney.swag.questionary_aggr_proxy.model.Authorities swagAuthorities) {
        Assert.assertEquals(thriftAuthorities.getAddress(), swagAuthorities.getAddress());
        Assert.assertEquals(thriftAuthorities.getCode(), swagAuthorities.getCode());
        Assert.assertEquals(thriftAuthorities.getName(), swagAuthorities.getName());
        Assert.assertEquals(thriftAuthorities.getType(), swagAuthorities.getType());
    }

    public static void addressCompare(Address thriftAddress, DaDataAddress swagDaDataAddress) {
        addressCityDataCompare(thriftAddress.getCity(), swagDaDataAddress.getCity());
        addressAreaDataCompare(thriftAddress.getArea(), swagDaDataAddress.getArea());
        Assert.assertThat(thriftAddress.getHistoryValues(), CoreMatchers.is(thriftAddress.getHistoryValues()));
        addressBlockDataCompare(thriftAddress.getBlock(), swagDaDataAddress.getBlock());
        addressCityDistrictCompare(thriftAddress.getCityDistrict(), swagDaDataAddress.getCityDistrict());
        addressFlatDataCompare(thriftAddress.getFlatData(), swagDaDataAddress.getFlatData());
        addressRegionCompare(thriftAddress.getRegion(), swagDaDataAddress.getRegion());
        addressSettlement(thriftAddress.getSettlement(), swagDaDataAddress.getSettlement());
        addressHouseCompare(thriftAddress.getHouse(), swagDaDataAddress.getHouse());
        Assert.assertEquals(thriftAddress.getValue(), swagDaDataAddress.getValue());
        Assert.assertEquals(thriftAddress.getUnrestrictedValue(), swagDaDataAddress.getUnrestrictedValue());
        Assert.assertEquals(thriftAddress.getBeltwayDistance(), swagDaDataAddress.getBeltwayDistance());
        Assert.assertEquals(thriftAddress.getBeltwayHit(), swagDaDataAddress.getBeltwayHit());
        Assert.assertEquals(thriftAddress.getFederalDistrict(), swagDaDataAddress.getFederalDistrict());
        Assert.assertEquals(thriftAddress.getCapitalMarker(), swagDaDataAddress.getCapitalMarker().byteValue());
        Assert.assertEquals(thriftAddress.getCountry(), swagDaDataAddress.getContry());
        Assert.assertEquals(thriftAddress.getFiasActualityState(), swagDaDataAddress.getFiasActualityState().byteValue());
        Assert.assertEquals(thriftAddress.getFiasCode(), swagDaDataAddress.getFiasCode());
        Assert.assertEquals(thriftAddress.getFiasId(), swagDaDataAddress.getFiasId());
        Assert.assertEquals(thriftAddress.getFiasLevel(), swagDaDataAddress.getFiasLevel().byteValue());
        Assert.assertEquals(thriftAddress.getGeoLat(), swagDaDataAddress.getGeoLat());
        Assert.assertEquals(thriftAddress.getGeoLon(), swagDaDataAddress.getGeoLon());
        Assert.assertEquals(thriftAddress.getGeonameId(), swagDaDataAddress.getGeonameId());
        Assert.assertEquals(thriftAddress.getQcGeo(), swagDaDataAddress.getQcGeo().byteValue());
        Assert.assertEquals(thriftAddress.getKladrId(), swagDaDataAddress.getKladrId());
        for (int i = 0; i < thriftAddress.getMetroList().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressMetro addressMetro = thriftAddress.getMetroList().get(i);
            AddressMetro swagAddressMetro = swagDaDataAddress.getMetroList().get(i);
            addressMetroCompare(addressMetro, swagAddressMetro);
        }
        Assert.assertEquals(thriftAddress.getOkato(), swagDaDataAddress.getOkato());
        Assert.assertEquals(thriftAddress.getOktmo(), swagDaDataAddress.getOktmo());
        Assert.assertEquals(thriftAddress.getPostalBox(), swagDaDataAddress.getPostalBox());
        Assert.assertEquals(thriftAddress.getPostalCode(), swagDaDataAddress.getPostalCode());
        Assert.assertEquals(thriftAddress.getSource(), swagDaDataAddress.getSource());
        Assert.assertEquals(thriftAddress.getSquareMeterPrice(), swagDaDataAddress.getSquareMeterPrice());
        Assert.assertEquals(thriftAddress.getTaxOffice(), swagDaDataAddress.getTaxOffice());
        Assert.assertEquals(thriftAddress.getTaxOfficeLegal(), swagDaDataAddress.getTaxOfficeLegal());
        Assert.assertEquals(thriftAddress.getTimezone(), swagDaDataAddress.getTimezone());
    }

    private static void addressMetroCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressMetro thriftAddressMetro, AddressMetro swagAddressMetro) {
        Assert.assertEquals(thriftAddressMetro.getDistance(), swagAddressMetro.getDistance());
        Assert.assertEquals(thriftAddressMetro.getLine(), swagAddressMetro.getLine());
        Assert.assertEquals(thriftAddressMetro.getName(), swagAddressMetro.getName());
    }

    private static void addressHouseCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressHouseData thriftHouse, AddressHouseData swagHouse) {
        Assert.assertEquals(thriftHouse.getHouse(), swagHouse.getHouse());
        Assert.assertEquals(thriftHouse.getHouseFiasId(), swagHouse.getHouseFiasId());
        Assert.assertEquals(thriftHouse.getHouseKladrId(), swagHouse.getHouseKladrId());
        Assert.assertEquals(thriftHouse.getHouseType(), swagHouse.getHouseType());
        Assert.assertEquals(thriftHouse.getHouseTypeFull(), swagHouse.getHouseTypeFull());
    }

    private static void addressSettlement(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressSettlementData thriftSettlement, AddressSettlementData swagSettlement) {
        Assert.assertEquals(thriftSettlement.getSettlement(), swagSettlement.getSettlement());
        Assert.assertEquals(thriftSettlement.getSettlementFiasId(), swagSettlement.getSettlementFiasId());
        Assert.assertEquals(thriftSettlement.getSettlementKladrId(), swagSettlement.getSettlementKladrId());
        Assert.assertEquals(thriftSettlement.getSettlementType(), swagSettlement.getSettlementType());
        Assert.assertEquals(thriftSettlement.getSettlementTypeFull(), swagSettlement.getSettlementTypeFull());
        Assert.assertEquals(thriftSettlement.getSettlementWithType(), swagSettlement.getSettlementWithType());
    }

    private static void addressRegionCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressRegionData thriftRegion, AddressRegionData swagRegion) {
        Assert.assertEquals(thriftRegion.getRegion(), swagRegion.getRegion());
        Assert.assertEquals(thriftRegion.getRegionFiasId(), swagRegion.getRegionFiasId());
        Assert.assertEquals(thriftRegion.getRegionKladrId(), swagRegion.getRegionKladrId());
        Assert.assertEquals(thriftRegion.getRegionType(), swagRegion.getRegionType());
        Assert.assertEquals(thriftRegion.getRegionWithType(), swagRegion.getRegionWithType());
    }

    private static void addressFlatDataCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressFlatData thriftFlatData, AddressFlatData swagFlatData) {
        Assert.assertEquals(thriftFlatData.getFlat(), swagFlatData.getFlat());
        Assert.assertEquals(thriftFlatData.getFlatArea(), swagFlatData.getFlatArea());
        Assert.assertEquals(thriftFlatData.getFlatPrice(), swagFlatData.getFlatPrice());
        Assert.assertEquals(thriftFlatData.getFlatType(), swagFlatData.getFlatType());
        Assert.assertEquals(thriftFlatData.getFlatTypeFull(), swagFlatData.getFlatTypeFull());
    }

    private static void addressCityDistrictCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressCityDistrictData thriftCityDistrict, AddressCityDistrictData swagCityDistrict) {
        Assert.assertEquals(thriftCityDistrict.getCityDistrict(), swagCityDistrict.getCityDistrict());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictFiasId(), swagCityDistrict.getCityDistrictFiasId());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictKladrId(), swagCityDistrict.getCityDistrictKladrId());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictType(), swagCityDistrict.getCityDistrictType());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictTypeFull(), swagCityDistrict.getCityDistrictTypeFull());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictWithType(), swagCityDistrict.getCityDistrictWithType());
    }

    private static void addressBlockDataCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressBlockData thriftBlock, AddressBlockData swagBlock) {
        Assert.assertEquals(thriftBlock.getBlock(), swagBlock.getBlock());
        Assert.assertEquals(thriftBlock.getBlockType(), swagBlock.getBlockType());
        Assert.assertEquals(thriftBlock.getBlockTypeFull(), swagBlock.getBlockTypeFull());
    }

    private static void addressCityDataCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressCityData thriftAddressCityData, AddressCityData swagAddressCityData) {
        Assert.assertEquals(thriftAddressCityData.getCity(), swagAddressCityData.getCity());
        Assert.assertEquals(thriftAddressCityData.getCityArea(), swagAddressCityData.getCityArea());
        Assert.assertEquals(thriftAddressCityData.getCityFiasId(), swagAddressCityData.getCityFiasId());
        Assert.assertEquals(thriftAddressCityData.getCityKladrId(), swagAddressCityData.getCityKladrId());
        Assert.assertEquals(thriftAddressCityData.getCityType(), swagAddressCityData.getCityType());
        Assert.assertEquals(thriftAddressCityData.getCityTypeFull(), swagAddressCityData.getCityTypeFull());
        Assert.assertEquals(thriftAddressCityData.getCityWithType(), swagAddressCityData.getCityWithType());
    }

    private static void addressAreaDataCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressAreaData thriftAddressAreaData, AddressAreaData swagAddressAreaData) {
        Assert.assertEquals(thriftAddressAreaData.getArea(), swagAddressAreaData.getArea());
        Assert.assertEquals(thriftAddressAreaData.getAreaFiasId(), swagAddressAreaData.getAreaFiasId());
        Assert.assertEquals(thriftAddressAreaData.getAreaKladrId(), swagAddressAreaData.getAreaKladrId());
        Assert.assertEquals(thriftAddressAreaData.getAreaType(), swagAddressAreaData.getAreaType());
        Assert.assertEquals(thriftAddressAreaData.getAreaTypeFull(), swagAddressAreaData.getAreaTypeFull());
        Assert.assertEquals(thriftAddressAreaData.getAreaWithType(), swagAddressAreaData.getAreaWithType());
    }

    private void nalogRegBodyCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.NalogRegBody thriftNalogRegBody, NalogRegBody swagNalogRegBody) {
        Assert.assertEquals(thriftNalogRegBody.getDate(), swagNalogRegBody.getDate());
        Assert.assertEquals(thriftNalogRegBody.getKpp(), swagNalogRegBody.getKpp());
        Assert.assertEquals(thriftNalogRegBody.getNalogCode(), swagNalogRegBody.getNalogCode());
        Assert.assertEquals(thriftNalogRegBody.getNalogName(), swagNalogRegBody.getNalogName());
        Assert.assertEquals(thriftNalogRegBody.getNalogRegDate(), swagNalogRegBody.getNalogRegDate());
    }

}
