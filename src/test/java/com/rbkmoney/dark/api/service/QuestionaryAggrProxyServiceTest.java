package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.questionary_proxy_aggr.QuestionaryAggrProxyHandlerSrv;
import com.rbkmoney.dark.api.utils.TestDataUtils;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.Authorities;
import com.rbkmoney.questionary_proxy_aggr.base_dadata.Registration;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderFL;
import com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderUL;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.Address;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderUL;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.License;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.apache.thrift.TException;
import org.eclipse.jetty.util.IO;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionaryAggrProxyServiceTest {

    @MockBean
    private QuestionaryAggrProxyHandlerSrv.Iface questionaryAggrProxyHandler;

    @Autowired
    private QuestionaryAggrProxyService questionaryAggrProxyService;

    @Before
    public void setUp() throws Exception {
        Mockito.reset(questionaryAggrProxyHandler);
    }

    @Test
    public void requestKonturFocusReqQueryTest() throws TException, IOException {
        var thriftKonturFocusReqResponse = TestDataUtils.createThriftKonturFocusReqResponse();
        when(questionaryAggrProxyHandler.requestKonturFocus(any(KonturFocusRequest.class), any(KonturFocusEndPoint.class)))
                .thenReturn(thriftKonturFocusReqResponse);

        KonturFocusParams konturFocusParams = new KonturFocusParams();
        konturFocusParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusEndPoint.REQ);
        konturFocusParams.setRequest(EnhancedRandom.random(ReqQuery.class));

        ReqResponses reqResponses = (ReqResponses) questionaryAggrProxyService.requestKonturFocus(konturFocusParams);

        Assert.assertEquals(thriftKonturFocusReqResponse.getReqResponses().getReqResponses().size(), reqResponses.getResponses().size());
        for (int i = 0; i < thriftKonturFocusReqResponse.getReqResponses().getReqResponses().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponse reqResponse = thriftKonturFocusReqResponse.getReqResponses().getReqResponses().get(i);
            ReqResponse swagReqResponse = reqResponses.getResponses().get(i);
            reqResponseCompare(reqResponse, swagReqResponse);
        }
    }

    @Test
    public void requestKonturFocusLicensesTest() throws IOException, TException {
        var thriftKonturFocusLicensesResponse = TestDataUtils.createThriftKonturFocusLicensesResponse();
        when(questionaryAggrProxyHandler.requestKonturFocus(any(KonturFocusRequest.class), any(KonturFocusEndPoint.class)))
                .thenReturn(thriftKonturFocusLicensesResponse);

        KonturFocusParams konturFocusParams = new KonturFocusParams();
        konturFocusParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusEndPoint.LICENCES);
        konturFocusParams.setRequest(EnhancedRandom.random(LicencesQuery.class));

        LicencesResponses licencesResponses = (LicencesResponses) questionaryAggrProxyService.requestKonturFocus(konturFocusParams);

        int thriftLicensesSize = thriftKonturFocusLicensesResponse.getLicencesResponses().getLicenseResponses().size();
        Assert.assertEquals(thriftLicensesSize, licencesResponses.getResponses().size());
        for (int i = 0; i < thriftLicensesSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse licencesResponse = thriftKonturFocusLicensesResponse.getLicencesResponses().getLicenseResponses().get(i);
            LicencesResponse swagLicencesResponse = licencesResponses.getResponses().get(i);
            licencesCompare(licencesResponse, swagLicencesResponse);
        }
    }

    @Test
    public void requestKonturFocusEgrDetailsTest() throws IOException, TException {
        var thriftKonturFocusEgrDetailsResponse = TestDataUtils.createThriftKonturFocusEgrDetailsResponse();
        when(questionaryAggrProxyHandler.requestKonturFocus(any(KonturFocusRequest.class), any(KonturFocusEndPoint.class)))
                .thenReturn(thriftKonturFocusEgrDetailsResponse);

        KonturFocusParams konturFocusParams = new KonturFocusParams();
        konturFocusParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusEndPoint.EGRDETAILS);
        konturFocusParams.setRequest(EnhancedRandom.random(EgrDetailsQuery.class));

        EgrDetailsResponses egrDetailsResponses = (EgrDetailsResponses) questionaryAggrProxyService.requestKonturFocus(konturFocusParams);

        int thriftEgrDetailsSize = thriftKonturFocusEgrDetailsResponse.getEgrDetailsResponses().getEgrDetailsResponses().size();
        Assert.assertEquals(thriftEgrDetailsSize, egrDetailsResponses.getResponses().size());
        for (int i = 0; i < thriftEgrDetailsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse egrDetailsResponse = thriftKonturFocusEgrDetailsResponse.getEgrDetailsResponses().getEgrDetailsResponses().get(i);
            EgrDetailsResponse swagEgrDetailsResponse = egrDetailsResponses.getResponses().get(i);
            egrDetailsCompare(egrDetailsResponse, swagEgrDetailsResponse);
        }
    }

    @Test
    public void requestDaDataAddressTest() throws IOException, TException {
        DaDataResponse thriftDaDataAddressResponse = TestDataUtils.createThriftDaDataAddressResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataAddressResponse);

        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTADDRESS);
        daDataParams.setRequest(EnhancedRandom.random(com.rbkmoney.swag.questionary_aggr_proxy.model.AddressQuery.class));

        AddressResponse addressResponse = (AddressResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDataAddressResponse.getAddressResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, addressResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            Address address = thriftDaDataAddressResponse.getAddressResponse().getSuggestions().get(i);
            DaDataAddress daDataAddress = addressResponse.getSuggestions().get(i);
            addressCompare(address, daDataAddress);
        }
    }

    @Test
    public void requestDaDataPartyTest() throws TException, IOException {
        DaDataResponse thriftDaDataPartyResponse = TestDataUtils.createThriftDaDataPartyResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataPartyResponse);

        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTPARTY);
        daDataParams.setRequest(EnhancedRandom.random(PartyQuery.class));

        PartyResponse partyResponse = (PartyResponse) questionaryAggrProxyService.requestDaData(daDataParams);
        int thriftSuggestionsSize = thriftDaDataPartyResponse.getPartyResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, partyResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyContent partyContent = thriftDaDataPartyResponse.getPartyResponse().getSuggestions().get(i);
            PartyContent swagPartyContent = partyResponse.getSuggestions().get(i);
            partyCompare(partyContent, swagPartyContent);
        }
    }

    @Test
    public void requestDaDataOkvedTest() throws TException, IOException {
        DaDataResponse thriftDaDataOkvedResponse = TestDataUtils.createThriftDaDataOkvedResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataOkvedResponse);

        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.OKVED2);
        daDataParams.setRequest(EnhancedRandom.random(OkvedQuery.class));

        OkvedResponse okvedResponse = (OkvedResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDataOkvedResponse.getOkvedResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, okvedResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedContent okvedContent = thriftDaDataOkvedResponse.getOkvedResponse().getSuggestions().get(i);
            OkvedContent swagOkvedContent = okvedResponse.getSuggestions().get(i);
            okvedCompare(okvedContent, swagOkvedContent);
        }
    }

    @Test
    public void requestDaDataBankTest() throws TException, IOException {
        DaDataResponse thriftDaDataBankResponse = TestDataUtils.createThriftDaDataBankResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataBankResponse);

        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTBANK);
        daDataParams.setRequest(EnhancedRandom.random(BankQuery.class));

        BankResponse bankResponse = (BankResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDataBankResponse.getBankResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, bankResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankContent bankContent = thriftDaDataBankResponse.getBankResponse().getSuggestions().get(i);
            BankContent swagBankContent = bankResponse.getSuggestions().get(i);
            bankCompare(bankContent, swagBankContent);
        }
    }

    @Test
    public void requestDaDataFioTest() throws TException, IOException {
        DaDataResponse thriftDaDataFioResponse = TestDataUtils.createThriftDaDataFioResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataFioResponse);

        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTFIO);
        daDataParams.setRequest(EnhancedRandom.random(FioQuery.class));

        FioResponse fioResponse = (FioResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDataFioResponse.getFioResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, fioResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioContent fioContent = thriftDaDataFioResponse.getFioResponse().getSuggestions().get(i);
            FioContent swagFioContent = fioResponse.getSuggestions().get(i);
            fioCompare(fioContent, swagFioContent);
        }
    }

    @Test
    public void requestDaDataFmsUnitTest() throws TException, IOException {
        DaDataResponse thriftDaDateFmsUnitResponse = TestDataUtils.createThriftDaDateFmsUnitResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDateFmsUnitResponse);

        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setEndpoint(com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataEndpoint.SUGGESTFMSUNIT);
        daDataParams.setRequest(EnhancedRandom.random(FmsUnitQuery.class));

        FmsUnitResponse fmsUnitResponse = (FmsUnitResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDateFmsUnitResponse.getFmsUnitResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, fmsUnitResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent fmsUnitContent = thriftDaDateFmsUnitResponse.getFmsUnitResponse().getSuggestions().get(i);
            FmsUnitContent swagFmsUnitContent = fmsUnitResponse.getSuggestions().get(i);
            fmsUnitCompare(fmsUnitContent, swagFmsUnitContent);
        }
    }

    private void fmsUnitCompare(com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent thriftFmsUnitContent, FmsUnitContent swagFmsUnitContent) {
        Assert.assertEquals(thriftFmsUnitContent.getCode(), swagFmsUnitContent.getCode());
        Assert.assertEquals(thriftFmsUnitContent.getName(), swagFmsUnitContent.getName());
        Assert.assertEquals(thriftFmsUnitContent.getRegionCode(), swagFmsUnitContent.getRegionCode());
        Assert.assertEquals(thriftFmsUnitContent.getValue(), swagFmsUnitContent.getValue());
        Assert.assertEquals(thriftFmsUnitContent.getUnrestrictdValue(), swagFmsUnitContent.getUnrestrictedValue());
        Assert.assertEquals(thriftFmsUnitContent.getType(), swagFmsUnitContent.getType());
    }

    private void fioCompare(com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioContent thriftFioContent, FioContent swagFioContent) {
        Assert.assertEquals(thriftFioContent.getName(), swagFioContent.getName());
        Assert.assertEquals(thriftFioContent.getPatronymic(), swagFioContent.getPatronymic());
        Assert.assertEquals(thriftFioContent.getQc(), swagFioContent.getQc().byteValue());
        Assert.assertEquals(thriftFioContent.getSurname(), swagFioContent.getSurname());
        Assert.assertEquals(thriftFioContent.getValue(), swagFioContent.getValue());
        Assert.assertEquals(thriftFioContent.getUnrestrictedValue(), swagFioContent.getUnrestrictedValue());
    }

    private void bankCompare(com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankContent thriftBankContent, BankContent swagBankContent) {
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

    private void okvedCompare(com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedContent thriftOkvedContent, OkvedContent swagOkvedContent) {
        Assert.assertEquals(thriftOkvedContent.getCode(), swagOkvedContent.getCode());
        Assert.assertEquals(thriftOkvedContent.getIdx(), swagOkvedContent.getIdx());
        Assert.assertEquals(thriftOkvedContent.getName(), swagOkvedContent.getName());
        Assert.assertEquals(thriftOkvedContent.getSection(), swagOkvedContent.getSection());
        Assert.assertEquals(thriftOkvedContent.getValue(), swagOkvedContent.getValue());
    }

    private void partyCompare(com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyContent thriftPartyContent, PartyContent swagPartyContent) {
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

    private void opfCompare(com.rbkmoney.questionary_proxy_aggr.base_dadata.Opf thriftOpf, Opf swagOpf) {
        Assert.assertEquals(thriftOpf.getCode(), swagOpf.getCode());
        Assert.assertEquals(thriftOpf.getFullName(), swagOpf.getFullName());
        Assert.assertEquals(thriftOpf.getShortName(), swagOpf.getShortName());
        Assert.assertEquals(thriftOpf.getType(), swagOpf.getType());
    }

    private void orgNameCompare(com.rbkmoney.questionary_proxy_aggr.base_dadata.OrgName thriftOrgName, OrgName swagOrgName) {
        Assert.assertEquals(thriftOrgName.getFullName(), swagOrgName.getFullName());
        Assert.assertEquals(thriftOrgName.getFullWithOpf(), swagOrgName.getFullWithOpf());
        Assert.assertEquals(thriftOrgName.getLatin(), swagOrgName.getLatin());
        Assert.assertEquals(thriftOrgName.getShortName(), swagOrgName.getShortName());
        Assert.assertEquals(thriftOrgName.getShortWithOpf(), swagOrgName.getShortWithOpf());
    }

    private void daDataLicenseCompare(com.rbkmoney.questionary_proxy_aggr.base_dadata.License thriftLicense, DaDataLicense swagLicense) {
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

    private void registrationCompare(Registration thriftRegistration, IFNCRegistration swagRegistration) {
        Assert.assertEquals(thriftRegistration.getIssueAuthority(), swagRegistration.getIssueAuthority());
        Assert.assertEquals(thriftRegistration.getIssueDate(), swagRegistration.getIssueDate());
    }

    private void authoritiesCompare(Authorities thriftAuthorities, com.rbkmoney.swag.questionary_aggr_proxy.model.Authorities swagAuthorities) {
        Assert.assertEquals(thriftAuthorities.getAddress(), swagAuthorities.getAddress());
        Assert.assertEquals(thriftAuthorities.getCode(), swagAuthorities.getCode());
        Assert.assertEquals(thriftAuthorities.getName(), swagAuthorities.getName());
        Assert.assertEquals(thriftAuthorities.getType(), swagAuthorities.getType());
    }

    private void addressCompare(Address thriftAddress, DaDataAddress swagDaDataAddress) {
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

    private void addressMetroCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressMetro thriftAddressMetro, AddressMetro swagAddressMetro) {
        Assert.assertEquals(thriftAddressMetro.getDistance(), swagAddressMetro.getDistance());
        Assert.assertEquals(thriftAddressMetro.getLine(), swagAddressMetro.getLine());
        Assert.assertEquals(thriftAddressMetro.getName(), swagAddressMetro.getName());
    }

    private void addressHouseCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressHouseData thriftHouse, AddressHouseData swagHouse) {
        Assert.assertEquals(thriftHouse.getHouse(), swagHouse.getHouse());
        Assert.assertEquals(thriftHouse.getHouseFiasId(), swagHouse.getHouseFiasId());
        Assert.assertEquals(thriftHouse.getHouseKladrId(), swagHouse.getHouseKladrId());
        Assert.assertEquals(thriftHouse.getHouseType(), swagHouse.getHouseType());
        Assert.assertEquals(thriftHouse.getHouseTypeFull(), swagHouse.getHouseTypeFull());
    }

    private void addressSettlement(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressSettlementData thriftSettlement, AddressSettlementData swagSettlement) {
        Assert.assertEquals(thriftSettlement.getSettlement(), swagSettlement.getSettlement());
        Assert.assertEquals(thriftSettlement.getSettlementFiasId(), swagSettlement.getSettlementFiasId());
        Assert.assertEquals(thriftSettlement.getSettlementKladrId(), swagSettlement.getSettlementKladrId());
        Assert.assertEquals(thriftSettlement.getSettlementType(), swagSettlement.getSettlementType());
        Assert.assertEquals(thriftSettlement.getSettlementTypeFull(), swagSettlement.getSettlementTypeFull());
        Assert.assertEquals(thriftSettlement.getSettlementWithType(), swagSettlement.getSettlementWithType());
    }

    private void addressRegionCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressRegionData thriftRegion, AddressRegionData swagRegion) {
        Assert.assertEquals(thriftRegion.getRegion(), swagRegion.getRegion());
        Assert.assertEquals(thriftRegion.getRegionFiasId(), swagRegion.getRegionFiasId());
        Assert.assertEquals(thriftRegion.getRegionKladrId(), swagRegion.getRegionKladrId());
        Assert.assertEquals(thriftRegion.getRegionType(), swagRegion.getRegionType());
        Assert.assertEquals(thriftRegion.getRegionWithType(), swagRegion.getRegionWithType());
    }

    private void addressFlatDataCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressFlatData thriftFlatData, AddressFlatData swagFlatData) {
        Assert.assertEquals(thriftFlatData.getFlat(), swagFlatData.getFlat());
        Assert.assertEquals(thriftFlatData.getFlatArea(), swagFlatData.getFlatArea());
        Assert.assertEquals(thriftFlatData.getFlatPrice(), swagFlatData.getFlatPrice());
        Assert.assertEquals(thriftFlatData.getFlatType(), swagFlatData.getFlatType());
        Assert.assertEquals(thriftFlatData.getFlatTypeFull(), swagFlatData.getFlatTypeFull());
    }

    private void addressCityDistrictCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressCityDistrictData thriftCityDistrict, AddressCityDistrictData swagCityDistrict) {
        Assert.assertEquals(thriftCityDistrict.getCityDistrict(), swagCityDistrict.getCityDistrict());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictFiasId(), swagCityDistrict.getCityDistrictFiasId());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictKladrId(), swagCityDistrict.getCityDistrictKladrId());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictType(), swagCityDistrict.getCityDistrictType());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictTypeFull(), swagCityDistrict.getCityDistrictTypeFull());
        Assert.assertEquals(thriftCityDistrict.getCityDistrictWithType(), swagCityDistrict.getCityDistrictWithType());
    }

    private void addressBlockDataCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressBlockData thriftBlock, AddressBlockData swagBlock) {
        Assert.assertEquals(thriftBlock.getBlock(), swagBlock.getBlock());
        Assert.assertEquals(thriftBlock.getBlockType(), swagBlock.getBlockType());
        Assert.assertEquals(thriftBlock.getBlockTypeFull(), swagBlock.getBlockTypeFull());
    }

    private void addressCityDataCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressCityData thriftAddressCityData, AddressCityData swagAddressCityData) {
        Assert.assertEquals(thriftAddressCityData.getCity(), swagAddressCityData.getCity());
        Assert.assertEquals(thriftAddressCityData.getCityArea(), swagAddressCityData.getCityArea());
        Assert.assertEquals(thriftAddressCityData.getCityFiasId(), swagAddressCityData.getCityFiasId());
        Assert.assertEquals(thriftAddressCityData.getCityKladrId(), swagAddressCityData.getCityKladrId());
        Assert.assertEquals(thriftAddressCityData.getCityType(), swagAddressCityData.getCityType());
        Assert.assertEquals(thriftAddressCityData.getCityTypeFull(), swagAddressCityData.getCityTypeFull());
        Assert.assertEquals(thriftAddressCityData.getCityWithType(), swagAddressCityData.getCityWithType());
    }

    private void addressAreaDataCompare(com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressAreaData thriftAddressAreaData, AddressAreaData swagAddressAreaData) {
        Assert.assertEquals(thriftAddressAreaData.getArea(), swagAddressAreaData.getArea());
        Assert.assertEquals(thriftAddressAreaData.getAreaFiasId(), swagAddressAreaData.getAreaFiasId());
        Assert.assertEquals(thriftAddressAreaData.getAreaKladrId(), swagAddressAreaData.getAreaKladrId());
        Assert.assertEquals(thriftAddressAreaData.getAreaType(), swagAddressAreaData.getAreaType());
        Assert.assertEquals(thriftAddressAreaData.getAreaTypeFull(), swagAddressAreaData.getAreaTypeFull());
        Assert.assertEquals(thriftAddressAreaData.getAreaWithType(), swagAddressAreaData.getAreaWithType());
    }

    private void egrDetailsCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse thriftEgrDetailsResponse, EgrDetailsResponse swagEgrDetailsResponse) {
        Assert.assertEquals(thriftEgrDetailsResponse.getInn(), swagEgrDetailsResponse.getInn());
        Assert.assertEquals(thriftEgrDetailsResponse.getOgrn(), swagEgrDetailsResponse.getOgrn());
        Assert.assertEquals(thriftEgrDetailsResponse.getFocusHref(), swagEgrDetailsResponse.getFocusHref());
        if (thriftEgrDetailsResponse.getContractor().isSetIndividualEntity()) {
            var thriftIndividualEntity = thriftEgrDetailsResponse.getContractor().getIndividualEntity();
            EgrDetailsIndividualEntity swagEgrDetailsIndividualEntity = (EgrDetailsIndividualEntity) swagEgrDetailsResponse.getContractor();
            shortenedAddressCompare(thriftIndividualEntity.getShortenedAddress(), swagEgrDetailsIndividualEntity.getShortenedAddress());
            activitiesCompare(thriftIndividualEntity.getActivities(), swagEgrDetailsIndividualEntity.getActivities());
            for (int i = 0; i < thriftIndividualEntity.getEgrRecords().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrRecord egrRecord = thriftIndividualEntity.getEgrRecords().get(i);
                EgrRecord swagEgrRecord = swagEgrDetailsIndividualEntity.getEgrRecords().get(i);
                egrRecordCompare(egrRecord, swagEgrRecord);
            }
            Assert.assertEquals(thriftIndividualEntity.getFomsRegNumber(), swagEgrDetailsIndividualEntity.getFomsRegNumber());
            Assert.assertEquals(thriftIndividualEntity.getFssRegNumber(), swagEgrDetailsIndividualEntity.getFssRegNumber());
            nalogRegBodyCompare(thriftIndividualEntity.getNalogRegBody(), swagEgrDetailsIndividualEntity.getNalogRegBody());
            Assert.assertEquals(thriftIndividualEntity.getOkato(), swagEgrDetailsIndividualEntity.getOkato());
            Assert.assertEquals(thriftIndividualEntity.getOkpo(), swagEgrDetailsIndividualEntity.getOkpo());
            regBodyCompare(thriftIndividualEntity.getRegBody(), swagEgrDetailsIndividualEntity.getRegBody());
            regInfoCompare(thriftIndividualEntity.getRegInfo(), swagEgrDetailsIndividualEntity.getRegInfo());
            Assert.assertEquals(thriftIndividualEntity.getPfrRegNumber(), swagEgrDetailsIndividualEntity.getPfrRegNumber());
        } else if (thriftEgrDetailsResponse.getContractor().isSetLegalEntity()) {
            var thriftLegalEntity = thriftEgrDetailsResponse.getContractor().getLegalEntity();
            EgrDetailsLegalEntity swagLegalEntity = (EgrDetailsLegalEntity) swagEgrDetailsResponse.getContractor();
            activitiesCompare(thriftLegalEntity.getActivities(), swagLegalEntity.getActivities());
            for (int i = 0; i < thriftLegalEntity.getEgrRecords().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrRecord egrRecord = thriftLegalEntity.getEgrRecords().get(i);
                EgrRecord swagEgrRecord = swagLegalEntity.getEgrRecords().get(i);
                egrRecordCompare(egrRecord, swagEgrRecord);
            }
            for (int i = 0; i < thriftLegalEntity.getPredecessors().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Predecessor predecessor = thriftLegalEntity.getPredecessors().get(i);
                Predecessor swagPredecessor = swagLegalEntity.getPredecessor().get(i);
                predecessorCompare(predecessor, swagPredecessor);
            }
            for (int i = 0; i < thriftLegalEntity.getSuccessors().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Successor successor = thriftLegalEntity.getSuccessors().get(i);
                Successor swagSuccessor = swagLegalEntity.getSuccessor().get(i);
                successorCompare(successor, swagSuccessor);
            }
            for (int i = 0; i < thriftLegalEntity.getShareHolders().getShareHoldersOther().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther shareHolderOther = thriftLegalEntity.getShareHolders().getShareHoldersOther().get(i);
                ShareHolderOther swagShareHolderOther = swagLegalEntity.getShareHolders().getShareHoldersOther().get(i);
                shareHolderOtherCompare(shareHolderOther, swagShareHolderOther);
            }
            for (int i = 0; i < thriftLegalEntity.getShareHolders().getShareHoldersUl().size(); i++) {
                ShareHolderUL shareHolderUL = thriftLegalEntity.getShareHolders().getShareHoldersUl().get(i);
                ShareHolderUl swagShareHolderUl = swagLegalEntity.getShareHolders().getShareHoldersUl().get(i);
                shareHolderULCompare(shareHolderUL, swagShareHolderUl);
            }
            for (int i = 0; i < thriftLegalEntity.getShareHolders().getShareHoldersFl().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl shareHolderFL = thriftLegalEntity.getShareHolders().getShareHoldersFl().get(i);
                ShareHolderFl swagShareHolderUl = swagLegalEntity.getShareHolders().getShareHoldersFl().get(i);
                shareHolderFlCompare(shareHolderFL, swagShareHolderUl);
            }
            for (int i = 0; i < thriftLegalEntity.getFoundersForeign().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderForeign founderForeign = thriftLegalEntity.getFoundersForeign().get(i);
                FounderForeign swagFounderForeign = swagLegalEntity.getFoundersForeign().get(i);
                founderForeignCompare(founderForeign, swagFounderForeign);
            }
            Assert.assertEquals(thriftLegalEntity.getOkpo(), swagLegalEntity.getOkpo());
            Assert.assertEquals(thriftLegalEntity.getPfrRegNumber(), swagLegalEntity.getPfrRegNumber());
            Assert.assertEquals(thriftLegalEntity.getStatedCapital().getDate(), swagLegalEntity.getStatedCapital().getDate());
            Assert.assertEquals(thriftLegalEntity.getStatedCapital().getSum(), swagLegalEntity.getStatedCapital().getSum().longValue());
            nalogRegBodyCompare(thriftLegalEntity.getNalogRegBody(), swagLegalEntity.getNalogRegBody());
            regInfoCompare(thriftLegalEntity.getRegInfo(), swagLegalEntity.getRegInfo());
            regBodyCompare(thriftLegalEntity.getRegBody(), swagLegalEntity.getRegBody());
            for (int i = 0; i < thriftLegalEntity.getFoundersFl().size(); i++) {
                FounderFL founderFL = thriftLegalEntity.getFoundersFl().get(i);
                FounderFl swagFounderFl = swagLegalEntity.getFoundersFl().get(i);
                founderFlCompare(founderFL, swagFounderFl);
            }
            for (int i = 0; i < thriftLegalEntity.getFoundersUl().size(); i++) {
                FounderUL founderUL = thriftLegalEntity.getFoundersUl().get(i);
                FounderUl swagFounderUl = swagLegalEntity.getFoundersUl().get(i);
                founderUlCompare(founderUL, swagFounderUl);
            }
        }
    }

    private void egrDetailsHistoryCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsHistory thriftEgrDetailsHistory, EgrDetailsHistory swagEgrDetailsHistory) {
        for (int i = 0; i < thriftEgrDetailsHistory.getShareholdersOther().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther shareHolderOther = thriftEgrDetailsHistory.getShareholdersOther().get(i);
            ShareHolderOther swagShareHolderOther = swagEgrDetailsHistory.getShareholdersOther().get(i);
            shareHolderOtherCompare(shareHolderOther, swagShareHolderOther);
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getShareholdersUl().size(); i++) {
            ShareHolderUL shareHolderUL = thriftEgrDetailsHistory.getShareholdersUl().get(i);
            ShareHolderUl swagShareHolderUl = swagEgrDetailsHistory.getShareHoldersUl().get(i);
            shareHolderULCompare(shareHolderUL, swagShareHolderUl);
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getShareholdersFl().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl shareHolderFl = thriftEgrDetailsHistory.getShareholdersFl().get(i);
            ShareHolderFl swagShareHolderFl = swagEgrDetailsHistory.getShareHoldersFl().get(i);
            shareHolderFlCompare(shareHolderFl, swagShareHolderFl);
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getStatedCapitals().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.StatedCapital statedCapital = thriftEgrDetailsHistory.getStatedCapitals().get(i);
            StatedCapital swagStatedCapital = swagEgrDetailsHistory.getStatedCapitals().get(i);
            Assert.assertEquals(statedCapital.getSum(), swagStatedCapital.getSum().longValue());
            Assert.assertEquals(statedCapital.getDate(), swagStatedCapital.getDate());
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getFoundersUl().size(); i++) {
            FounderFL founderFL = thriftEgrDetailsHistory.getFoundersFl().get(i);
            FounderFl swagFounderFl = swagEgrDetailsHistory.getFoundersFl().get(i);
            founderFlCompare(founderFL, swagFounderFl);
        }
        for (int i = 0; i < thriftEgrDetailsHistory.getFoundersUl().size(); i++) {
            FounderUL founderUL = thriftEgrDetailsHistory.getFoundersUl().get(i);
            FounderUl swagFounderUl = swagEgrDetailsHistory.getFoundersUl().get(i);
            founderUlCompare(founderUL, swagFounderUl);
        }
    }

    private void founderFlCompare(FounderFL thriftFounderFL, FounderFl swagFounderFl) {
        Assert.assertEquals(thriftFounderFL.getDate(), swagFounderFl.getDate());
        Assert.assertEquals(thriftFounderFL.getFio(), swagFounderFl.getFio());
        Assert.assertEquals(thriftFounderFL.getFirstDate(), swagFounderFl.getFirstDate());
        Assert.assertEquals(thriftFounderFL.getInnfl(), swagFounderFl.getInnfl());
        shareCompare(thriftFounderFL.getShare(), swagFounderFl.getShare());
    }

    private void founderUlCompare(FounderUL thriftFounderUL, FounderUl swagFounderUl) {
        Assert.assertEquals(thriftFounderUL.getDate(), swagFounderUl.getDate());
        Assert.assertEquals(thriftFounderUL.getFirstDate(), swagFounderUl.getFirstDate());
        Assert.assertEquals(thriftFounderUL.getInn(), swagFounderUl.getInn());
        Assert.assertEquals(thriftFounderUL.getOgrn(), swagFounderUl.getOgrn());
        shareCompare(thriftFounderUL.getShare(), swagFounderUl.getShare());
    }

    private void founderForeignCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.FounderForeign thriftFounderForeign, FounderForeign swagFounderForeign) {
        Assert.assertEquals(thriftFounderForeign.getCountry(), swagFounderForeign.getCountry());
        Assert.assertEquals(thriftFounderForeign.getDate(), swagFounderForeign.getDate());
        Assert.assertEquals(thriftFounderForeign.getFirstDate(), swagFounderForeign.getFirstDate());
        Assert.assertEquals(thriftFounderForeign.getFullName(), swagFounderForeign.getFullName());
        shareCompare(thriftFounderForeign.getShare(), swagFounderForeign.getShare());
    }

    private void shareCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Share thriftShare, Share swagShare) {
        Assert.assertEquals(thriftShare.getPercentageDenominator(), ((int) swagShare.getPercentageDenominator()));
        Assert.assertEquals(thriftShare.getPercentageNominator(), ((int) swagShare.getPercentageNominator()));
        Assert.assertEquals(thriftShare.getPercentagePlain(), ((int) swagShare.getPercentagePlain()));
        Assert.assertEquals(thriftShare.getSum(), swagShare.getSum().longValue());
    }

    private void shareHolderFlCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderFl thriftShareHolderFL, ShareHolderFl swagShareFolderFl) {
        Assert.assertEquals(thriftShareHolderFL.getAddress(), swagShareFolderFl.getAddress());
        Assert.assertEquals(thriftShareHolderFL.getCapitalSharesPercent(), ((int) swagShareFolderFl.getCapitalSharesPercent()));
        Assert.assertEquals(thriftShareHolderFL.getVotingSharesPercent(), ((int) swagShareFolderFl.getVotingSharesPercent()));
        Assert.assertEquals(thriftShareHolderFL.getDate(), swagShareFolderFl.getDate());
    }

    private void shareHolderULCompare(ShareHolderUL thriftShareHolderUL, ShareHolderUl swagShareHolderUl) {
        Assert.assertEquals(thriftShareHolderUL.getAddress(), swagShareHolderUl.getAddress());
        Assert.assertEquals(thriftShareHolderUL.getCapitalSharesPercent(), ((int) swagShareHolderUl.getCapitalSharesPercent()));
        Assert.assertEquals(thriftShareHolderUL.getVotingSharesPercent(), ((int) swagShareHolderUl.getVotingSharesPercent()));
        Assert.assertEquals(thriftShareHolderUL.getDate(), swagShareHolderUl.getDate());
        Assert.assertEquals(thriftShareHolderUL.getFullName(), swagShareHolderUl.getFullName());
    }

    private void shareHolderOtherCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShareHolderOther thriftShareHolderOther, ShareHolderOther swagShareHolderOther) {
        Assert.assertEquals(thriftShareHolderOther.getAddress(), swagShareHolderOther.getAddress());
        Assert.assertEquals(thriftShareHolderOther.getCapitalSharesPercent(), ((int) swagShareHolderOther.getCapitalSharesPercent()));
        Assert.assertEquals(thriftShareHolderOther.getVotingSharesPercent(), ((int) swagShareHolderOther.getVotingSharesPercent()));
        Assert.assertEquals(thriftShareHolderOther.getDate(), swagShareHolderOther.getDate());
        Assert.assertEquals(thriftShareHolderOther.getFullName(), swagShareHolderOther.getFullName());
    }

    private void successorCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Successor thriftSuccessor, Successor swagSuccessor) {
        Assert.assertEquals(thriftSuccessor.getDate(), swagSuccessor.getDate());
        Assert.assertEquals(thriftSuccessor.getInn(), swagSuccessor.getInn());
        Assert.assertEquals(thriftSuccessor.getName(), swagSuccessor.getName());
        Assert.assertEquals(thriftSuccessor.getOgrn(), swagSuccessor.getOgrn());
    }

    private void predecessorCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Predecessor thriftPredecessor, Predecessor swagPredecessor) {
        Assert.assertEquals(thriftPredecessor.getDate(), swagPredecessor.getDate());
        Assert.assertEquals(thriftPredecessor.getInn(), swagPredecessor.getInn());
        Assert.assertEquals(thriftPredecessor.getName(), swagPredecessor.getName());
        Assert.assertEquals(thriftPredecessor.getOgrn(), swagPredecessor.getOgrn());
    }

    private void activitiesCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Activity thriftActivity, Activity swagAcitivty) {
        Assert.assertEquals(thriftActivity.getPrincipalActivity().getCode(), swagAcitivty.getPrincipalActivity().getCode());
        Assert.assertEquals(thriftActivity.getPrincipalActivity().getText(), swagAcitivty.getPrincipalActivity().getText());
        Assert.assertEquals(thriftActivity.getPrincipalActivity().getDate(), swagAcitivty.getPrincipalActivity().getDate());
        for (int i = 0; i < thriftActivity.getComplementaryActivities().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ComplementaryActivity complementaryActivity = thriftActivity.getComplementaryActivities().get(i);
            ComplementaryActivity swagComplementaryActivity = swagAcitivty.getComplementaryActivities().get(i);
            Assert.assertEquals(complementaryActivity.getCode(), swagComplementaryActivity.getCode());
            Assert.assertEquals(complementaryActivity.getDate(), swagComplementaryActivity.getDate());
            Assert.assertEquals(complementaryActivity.getText(), swagComplementaryActivity.getText());
        }
    }

    private void regInfoCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.RegInfo thriftRegInfo, RegInfo swagRegInfo) {
        Assert.assertEquals(thriftRegInfo.getOgrnDate(), swagRegInfo.getOgrnDate());
        Assert.assertEquals(thriftRegInfo.getRegName(), swagRegInfo.getRegName());
    }

    private void regBodyCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.RegBody thriftRegBody, RegBody swagRegBody) {
        Assert.assertEquals(thriftRegBody.getDate(), swagRegBody.getDate());
        Assert.assertEquals(thriftRegBody.getKpp(), swagRegBody.getKpp());
        Assert.assertEquals(thriftRegBody.getNalogCode(), swagRegBody.getNalogCode());
        Assert.assertEquals(thriftRegBody.getNalogName(), swagRegBody.getNalogName());
        Assert.assertEquals(thriftRegBody.getNalogRegDate(), swagRegBody.getNalogRegDate());
    }

    private void nalogRegBodyCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.NalogRegBody thriftNalogRegBody, NalogRegBody swagNalogRegBody) {
        Assert.assertEquals(thriftNalogRegBody.getDate(), swagNalogRegBody.getDate());
        Assert.assertEquals(thriftNalogRegBody.getKpp(), swagNalogRegBody.getKpp());
        Assert.assertEquals(thriftNalogRegBody.getNalogCode(), swagNalogRegBody.getNalogCode());
        Assert.assertEquals(thriftNalogRegBody.getNalogName(), swagNalogRegBody.getNalogName());
        Assert.assertEquals(thriftNalogRegBody.getNalogRegDate(), swagNalogRegBody.getNalogRegDate());
    }

    private void egrRecordCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrRecord thriftEgrRecord, EgrRecord swagEgrRecord) {
        Assert.assertEquals(thriftEgrRecord.getDate(), swagEgrRecord.getDate());
        Assert.assertEquals(thriftEgrRecord.getGrn(), swagEgrRecord.getGrn());
        Assert.assertEquals(thriftEgrRecord.getName(), swagEgrRecord.getName());
        Assert.assertEquals(thriftEgrRecord.getRegCode(), swagEgrRecord.getRegCode());
        Assert.assertEquals(thriftEgrRecord.getRegName(), swagEgrRecord.getRegName());
        for (int i = 0; i < thriftEgrRecord.getCertificates().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.Certificate certificate = thriftEgrRecord.getCertificates().get(i);
            Certificate swagCertificate = swagEgrRecord.getCertificates().get(i);
            Assert.assertEquals(certificate.getDate(), swagCertificate.getDate());
            Assert.assertEquals(certificate.getSerialNumber(), swagCertificate.getSerialNumber());
        }
        for (int i = 0; i < thriftEgrRecord.getDocuments().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.RecordDocument recordDocument = thriftEgrRecord.getDocuments().get(i);
            RecordDocument swagRecordDocument = swagEgrRecord.getDocuments().get(i);
            Assert.assertEquals(recordDocument.getDate(), swagRecordDocument.getDate());
            Assert.assertEquals(recordDocument.getName(), swagRecordDocument.getName());
        }
    }


    private void shortenedAddressCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.ShortenedAddress thriftShortenedAddress, ShortenedAddress swagShortenedAddress) {
        toponimCompare(thriftShortenedAddress.getCity(), swagShortenedAddress.getCity());
        toponimCompare(thriftShortenedAddress.getDistrict(), swagShortenedAddress.getDistrict());
        toponimCompare(thriftShortenedAddress.getRegionName(), swagShortenedAddress.getRegionName());
        toponimCompare(thriftShortenedAddress.getSettlement(), swagShortenedAddress.getSettlement());
        Assert.assertEquals(thriftShortenedAddress.getRegionCode(), thriftShortenedAddress.getRegionCode());
    }

    private void licencesCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse thriftLicencesResponse,
                                 LicencesResponse swagLicencesResponse) {
        Assert.assertEquals(thriftLicencesResponse.getInn(), swagLicencesResponse.getInn());
        Assert.assertEquals(thriftLicencesResponse.getFocusHref(), swagLicencesResponse.getFocusHref());
        Assert.assertEquals(thriftLicencesResponse.getOgrn(), swagLicencesResponse.getOgrn());
        for (int i = 0; i < thriftLicencesResponse.getLicenses().size(); i++) {
            License license = thriftLicencesResponse.getLicenses().get(i);
            KonturFocusLicense swagLicense = swagLicencesResponse.getLicenses().get(i);
            licenseCompare(license, swagLicense);
        }
    }

    private void licenseCompare(License license, KonturFocusLicense swagLicense) {
        Assert.assertEquals(license.getActivity(), swagLicense.getActivity());
        Assert.assertThat(license.getAddresses(), CoreMatchers.is(swagLicense.getAddresses()));
        Assert.assertEquals(license.getOfficialNum(), swagLicense.getOfficialNum());
        Assert.assertEquals(license.getDate(), swagLicense.getDate());
        Assert.assertEquals(license.getDateEnd(), swagLicense.getDateEnd());
        Assert.assertEquals(license.getDateStart(), swagLicense.getDateStart());
        Assert.assertEquals(license.getIssuerName(), swagLicense.getIssuerNamer());
        Assert.assertThat(license.getServices(), CoreMatchers.is(swagLicense.getServices()));
        Assert.assertEquals(license.getStatusDescription(), swagLicense.getStatusDescription());
    }

    private void reqResponseCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponse thriftReqResponse,
                                    ReqResponse swagReqResponse) {
        Assert.assertEquals(thriftReqResponse.getInn(), swagReqResponse.getInn());
        Assert.assertEquals(thriftReqResponse.getOgrn(), swagReqResponse.getOgrn());
        Assert.assertEquals(thriftReqResponse.getBriefReport().getSummary().isGreenStatements(),
                swagReqResponse.getBriefReport().getSummary().isGreenStatements());
        Assert.assertEquals(thriftReqResponse.getBriefReport().getSummary().isRedStatements(),
                swagReqResponse.getBriefReport().getSummary().isRedStatements());
        Assert.assertEquals(thriftReqResponse.getBriefReport().getSummary().isYellowStatements(),
                swagReqResponse.getBriefReport().getSummary().isYellowStatements());
        Assert.assertEquals(thriftReqResponse.getBriefReport().getHref(), swagReqResponse.getBriefReport().getHref());
        Assert.assertEquals(thriftReqResponse.getContactPhones().getCount(), swagReqResponse.getContactPhones().getCount().longValue());
        Assert.assertThat(thriftReqResponse.getContactPhones().getPhones(), CoreMatchers.is(swagReqResponse.getContactPhones().getPhones()));
        if (thriftReqResponse.getPrivateEntity().isSetIndividualEntity()) {
            var reqIndividualEntity = thriftReqResponse.getPrivateEntity().getIndividualEntity();
            ReqIndividualEntity swagReqIndividualEntity = (ReqIndividualEntity) swagReqResponse.getContractor();
            Assert.assertEquals(reqIndividualEntity.getDissolutionDate(), swagReqIndividualEntity.getDissolutionDate());
            Assert.assertEquals(reqIndividualEntity.getFio(), swagReqIndividualEntity.getFio());
            Assert.assertEquals(reqIndividualEntity.getOkato(), swagReqIndividualEntity.getOkato());
            Assert.assertEquals(reqIndividualEntity.getOkfs(), swagReqIndividualEntity.getOkfs());
            Assert.assertEquals(reqIndividualEntity.getOkogu(), swagReqIndividualEntity.getOkogu());
            Assert.assertEquals(reqIndividualEntity.getOkopf(), swagReqIndividualEntity.getOkopf());
            Assert.assertEquals(reqIndividualEntity.getOkpo(), swagReqIndividualEntity.getOkpo());
            Assert.assertEquals(reqIndividualEntity.getOktmo(), swagReqIndividualEntity.getOktmo());
            Assert.assertEquals(reqIndividualEntity.getRegistrationDate(), swagReqIndividualEntity.getRegistrationDate());
            Assert.assertEquals(reqIndividualEntity.getStatusDetail().getDate(), swagReqIndividualEntity.getStatusDetail().getDate());
            Assert.assertEquals(reqIndividualEntity.getStatusDetail().getStatus(), swagReqIndividualEntity.getStatusDetail().getStatus());
        } else if (thriftReqResponse.getPrivateEntity().isSetLegalEntity()) {
            var reqLegalEntity = thriftReqResponse.getPrivateEntity().getLegalEntity();
            ReqLegalEntity swagReqLegalEntity = (ReqLegalEntity) swagReqResponse.getContractor();
            for (int i = 0; i < swagReqLegalEntity.getBranches().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch branch = reqLegalEntity.getBranches().get(i);
                Branch swagBranch = swagReqLegalEntity.getBranches().get(i);
                branchCompare(branch, swagBranch);
            }
            Assert.assertEquals(reqLegalEntity.getStatus().getDate(), swagReqLegalEntity.getStatus().getDate());
            Assert.assertEquals(reqLegalEntity.getStatus().getStatus(), swagReqLegalEntity.getStatus().getStatus());
            for (int i = 0; i < swagReqLegalEntity.getHeads().size(); i++) {
                Head swagHead = swagReqLegalEntity.getHeads().get(i);
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Head head = reqLegalEntity.getHeads().get(i);
                headCompare(head, swagHead);
            }
            legalAddressCompare(reqLegalEntity.getLegalAddress(), swagReqLegalEntity.getLegalAddress());
            legalNameCompare(reqLegalEntity.getLegalName(), swagReqLegalEntity.getLegalName());
            for (int i = 0; i < reqLegalEntity.getManagementCompanies().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ManagementCompany managementCompany = reqLegalEntity.getManagementCompanies().get(i);
                ManagementCompany swagManagementCompany = swagReqLegalEntity.getManagementCompanies().get(i);
                managementCompanyCompare(managementCompany, swagManagementCompany);
            }
            Assert.assertEquals(reqLegalEntity.getOkato(), swagReqLegalEntity.getOkato());
            Assert.assertEquals(reqLegalEntity.getOkfs(), swagReqLegalEntity.getOkfs());
            Assert.assertEquals(reqLegalEntity.getOkogu(), swagReqLegalEntity.getOkogu());
            Assert.assertEquals(reqLegalEntity.getOkopf(), swagReqLegalEntity.getOkopf());
            Assert.assertEquals(reqLegalEntity.getOkpo(), swagReqLegalEntity.getOkpo());
            Assert.assertEquals(reqLegalEntity.getOktmo(), swagReqLegalEntity.getOktmo());
            Assert.assertEquals(reqLegalEntity.getOpf(), swagReqLegalEntity.getOpf());
            Assert.assertEquals(reqLegalEntity.getDissolutionDate(), swagReqLegalEntity.getDissolutionDate());
            Assert.assertEquals(reqLegalEntity.getRegistrationDate(), swagReqLegalEntity.getRegistrationDate());
            Assert.assertEquals(reqLegalEntity.getKpp(), swagReqLegalEntity.getKpp());
            for (int i = 0; i < reqLegalEntity.getHistory().getLegalAddresses().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalAddress legalAddress = reqLegalEntity.getHistory().getLegalAddresses().get(i);
                LegalAddress swagLegalAddresses = swagReqLegalEntity.getHistory().getLegalAddresses().get(i);
                legalAddressCompare(legalAddress, swagLegalAddresses);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getLegalNames().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalName legalName = reqLegalEntity.getHistory().getLegalNames().get(i);
                LegalName swagLegalName = swagReqLegalEntity.getHistory().getLegalNames().get(i);
                legalNameCompare(legalName, swagLegalName);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getKpps().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqKppHistory reqKppHistory = reqLegalEntity.getHistory().getKpps().get(i);
                ReqKppHistory swagReqKppHistory = swagReqLegalEntity.getHistory().getKpps().get(i);
                kppsNameCompare(reqKppHistory, swagReqKppHistory);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getManagementCompanies().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ManagementCompany managementCompany = reqLegalEntity.getHistory().getManagementCompanies().get(i);
                ManagementCompany swagManagementCompany = swagReqLegalEntity.getHistory().getManagementCompanies().get(i);
                managementCompanyCompare(managementCompany, swagManagementCompany);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getHeads().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Head head = reqLegalEntity.getHistory().getHeads().get(i);
                Head swagHead = swagReqLegalEntity.getHistory().getHeads().get(i);
                headCompare(head, swagHead);
            }
            for (int i = 0; i < reqLegalEntity.getHistory().getBranches().size(); i++) {
                com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch branch = reqLegalEntity.getHistory().getBranches().get(i);
                Branch swagBranch = swagReqLegalEntity.getHistory().getBranches().get(i);
                branchCompare(branch, swagBranch);
            }
        }
    }

    private void kppsNameCompare(com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqKppHistory thriftReqKppHistory, ReqKppHistory swagReqKppHistory) {
        Assert.assertEquals(thriftReqKppHistory.getDate(), swagReqKppHistory.getDate());
        Assert.assertEquals(thriftReqKppHistory.getKpp(), swagReqKppHistory.getKpp());
    }

    private void legalNameCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalName thriftLegalName, LegalName swagLegalName) {
        Assert.assertEquals(thriftLegalName.getDate(), swagLegalName.getDate());
        Assert.assertEquals(thriftLegalName.getFullName(), swagLegalName.getFullName());
        Assert.assertEquals(thriftLegalName.getShortName(), swagLegalName.getShortName());
    }

    private void branchCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Branch thriftBranch, Branch swagBranch) {
        Assert.assertEquals(thriftBranch.getDate(), swagBranch.getDate());
        Assert.assertEquals(thriftBranch.getName(), swagBranch.getName());
        Assert.assertEquals(thriftBranch.getAddressRf().getBulkRaw(), swagBranch.getAddressRf().getBulkRaw());
        Assert.assertEquals(thriftBranch.getAddressRf().getFlatRaw(), swagBranch.getAddressRf().getFlatRaw());
        Assert.assertEquals(thriftBranch.getAddressRf().getHouseRaw(), swagBranch.getAddressRf().getHouseRaw());
        Assert.assertEquals(thriftBranch.getAddressRf().getKladrCode(), swagBranch.getAddressRf().getKladrCode());
        Assert.assertEquals(thriftBranch.getAddressRf().getRegionCode(), swagBranch.getAddressRf().getRegionCode());
        Assert.assertEquals(thriftBranch.getAddressRf().getZipCode(), swagBranch.getAddressRf().getZipCode());
        addressRFCompare(thriftBranch.getAddressRf(), swagBranch.getAddressRf());
    }

    private void addressRFCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ParsedAddressRF thriftParsedAddressRF,
                                  ParsedAddressRF swagParsedAddressRF) {
        toponimCompare(thriftParsedAddressRF.getBulk(), swagParsedAddressRF.getBulk());
        toponimCompare(thriftParsedAddressRF.getCity(), swagParsedAddressRF.getCity());
        toponimCompare(thriftParsedAddressRF.getDistrict(), swagParsedAddressRF.getDistrict());
        toponimCompare(thriftParsedAddressRF.getFlat(), swagParsedAddressRF.getFlat());
        toponimCompare(thriftParsedAddressRF.getHouse(), swagParsedAddressRF.getHouse());
        toponimCompare(thriftParsedAddressRF.getRegionName(), swagParsedAddressRF.getRegionName());
        toponimCompare(thriftParsedAddressRF.getSettlement(), swagParsedAddressRF.getSettlement());
    }

    private void toponimCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Toponim thriftToponim, Toponim swagToponim) {
        Assert.assertEquals(thriftToponim.getTopoFullName(), swagToponim.getTopoFullName());
        Assert.assertEquals(thriftToponim.getTopoShortName(), swagToponim.getTopoShortName());
        Assert.assertEquals(thriftToponim.getTopoValue(), swagToponim.getTopoValue());
    }

    private void headCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.Head thriftHead, Head swagHead) {
        Assert.assertEquals(thriftHead.getDate(), swagHead.getDate());
        Assert.assertEquals(thriftHead.getFio(), swagHead.getFio());
        Assert.assertEquals(thriftHead.getFirstDate(), swagHead.getFirstDate());
        Assert.assertEquals(thriftHead.getInnfl(), swagHead.getInnfl());
        Assert.assertEquals(thriftHead.getPosition(), swagHead.getPosition());
    }

    private void managementCompanyCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.ManagementCompany thriftManagementCompany,
                                          ManagementCompany swagManagementCompany) {
        Assert.assertEquals(thriftManagementCompany.getDate(), swagManagementCompany.getDate());
        Assert.assertEquals(thriftManagementCompany.getFirstDate(), swagManagementCompany.getFirstDate());
        Assert.assertEquals(thriftManagementCompany.getInn(), swagManagementCompany.getInn());
        Assert.assertEquals(thriftManagementCompany.getName(), swagManagementCompany.getName());
        Assert.assertEquals(thriftManagementCompany.getOgrn(), swagManagementCompany.getOgrn());
    }

    private void legalAddressCompare(com.rbkmoney.questionary_proxy_aggr.base_kontur_focus.LegalAddress thriftLegalAddress,
                                     LegalAddress swagLegalAddress) {
        Assert.assertEquals(thriftLegalAddress.getFirstDate(), swagLegalAddress.getFirstDate());
        Assert.assertEquals(thriftLegalAddress.getDate(), swagLegalAddress.getDate());
        addressRFCompare(thriftLegalAddress.getAddressRf(), swagLegalAddress.getAddressRf());
    }

}
