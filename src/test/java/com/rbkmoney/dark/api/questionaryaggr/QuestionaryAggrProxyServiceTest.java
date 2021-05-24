package com.rbkmoney.dark.api.questionaryaggr;

import com.rbkmoney.damsel.questionary_proxy_aggr.QuestionaryAggrProxyHandlerSrv;
import com.rbkmoney.dark.api.questionaryaggr.utils.DaDataCompareUtil;
import com.rbkmoney.dark.api.questionaryaggr.utils.KonturFocusCompareUtil;
import com.rbkmoney.dark.api.service.QuestionaryAggrProxyService;
import com.rbkmoney.dark.api.utils.QuestionaryAggrTestData;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.Address;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataEndpoint;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataRequest;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusEndPoint;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusRequest;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.apache.thrift.TException;
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

import static com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataRequest.DaDataRequestTypeEnum;
import static com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusRequest.KonturFocusRequestTypeEnum;
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
        var thriftKonturFocusReqResponse = QuestionaryAggrTestData.createThriftKonturFocusReqResponse();
        when(questionaryAggrProxyHandler
                .requestKonturFocus(any(KonturFocusRequest.class), any(KonturFocusEndPoint.class)))
                .thenReturn(thriftKonturFocusReqResponse);

        ReqQuery reqQuery = EnhancedRandom.random(ReqQuery.class);
        reqQuery.setKonturFocusRequestType(KonturFocusRequestTypeEnum.REQQUERY);
        KonturFocusParams konturFocusParams = new KonturFocusParams();
        konturFocusParams.setRequest(reqQuery);

        ReqResponses reqResponses = (ReqResponses) questionaryAggrProxyService.requestKonturFocus(konturFocusParams);

        Assert.assertEquals(thriftKonturFocusReqResponse.getReqResponses().getReqResponses().size(),
                reqResponses.getResponses().size());
        for (int i = 0; i < thriftKonturFocusReqResponse.getReqResponses().getReqResponses().size(); i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponse reqResponse =
                    thriftKonturFocusReqResponse.getReqResponses().getReqResponses().get(i);
            ReqResponse swagReqResponse = reqResponses.getResponses().get(i);
            KonturFocusCompareUtil.reqResponseCompare(reqResponse, swagReqResponse);
        }
    }

    @Test
    public void requestKonturFocusLicensesTest() throws IOException, TException {
        var thriftKonturFocusLicensesResponse = QuestionaryAggrTestData.createThriftKonturFocusLicensesResponse();
        when(questionaryAggrProxyHandler
                .requestKonturFocus(any(KonturFocusRequest.class), any(KonturFocusEndPoint.class)))
                .thenReturn(thriftKonturFocusLicensesResponse);

        LicencesQuery licencesQuery = EnhancedRandom.random(LicencesQuery.class);
        licencesQuery.setKonturFocusRequestType(KonturFocusRequestTypeEnum.LICENCESQUERY);
        KonturFocusParams konturFocusParams = new KonturFocusParams();
        konturFocusParams.setRequest(licencesQuery);

        LicencesResponses licencesResponses =
                (LicencesResponses) questionaryAggrProxyService.requestKonturFocus(konturFocusParams);

        int thriftLicensesSize = thriftKonturFocusLicensesResponse.getLicencesResponses().getLicenseResponses().size();
        Assert.assertEquals(thriftLicensesSize, licencesResponses.getResponses().size());
        for (int i = 0; i < thriftLicensesSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponse licencesResponse =
                    thriftKonturFocusLicensesResponse.getLicencesResponses().getLicenseResponses().get(i);
            LicencesResponse swagLicencesResponse = licencesResponses.getResponses().get(i);
            KonturFocusCompareUtil.licencesCompare(licencesResponse, swagLicencesResponse);
        }
    }

    @Test
    public void requestKonturFocusEgrDetailsTest() throws IOException, TException {
        var thriftKonturFocusEgrDetailsResponse = QuestionaryAggrTestData.createThriftKonturFocusEgrDetailsResponse();
        when(questionaryAggrProxyHandler
                .requestKonturFocus(any(KonturFocusRequest.class), any(KonturFocusEndPoint.class)))
                .thenReturn(thriftKonturFocusEgrDetailsResponse);

        EgrDetailsQuery egrDetailsQuery = EnhancedRandom.random(EgrDetailsQuery.class);
        egrDetailsQuery.setKonturFocusRequestType(KonturFocusRequestTypeEnum.EGRDETAILSQUERY);
        KonturFocusParams konturFocusParams = new KonturFocusParams();
        konturFocusParams.setRequest(egrDetailsQuery);

        EgrDetailsResponses egrDetailsResponses =
                (EgrDetailsResponses) questionaryAggrProxyService.requestKonturFocus(konturFocusParams);

        int thriftEgrDetailsSize =
                thriftKonturFocusEgrDetailsResponse.getEgrDetailsResponses().getEgrDetailsResponses().size();
        Assert.assertEquals(thriftEgrDetailsSize, egrDetailsResponses.getResponses().size());
        for (int i = 0; i < thriftEgrDetailsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponse egrDetailsResponse =
                    thriftKonturFocusEgrDetailsResponse.getEgrDetailsResponses().getEgrDetailsResponses().get(i);
            EgrDetailsResponse swagEgrDetailsResponse = egrDetailsResponses.getResponses().get(i);
            KonturFocusCompareUtil.egrDetailsCompare(egrDetailsResponse, swagEgrDetailsResponse);
        }
    }

    @Test
    public void requestKonturFocusBeneficialOwnersTest() throws IOException, TException {
        var thriftKonturFocusBeneficialOwnerResponse =
                QuestionaryAggrTestData.createThriftKonturFocusBeneficialOwnerResponse();
        when(questionaryAggrProxyHandler
                .requestKonturFocus(any(KonturFocusRequest.class), any(KonturFocusEndPoint.class)))
                .thenReturn(thriftKonturFocusBeneficialOwnerResponse);

        BeneficialOwnerQuery beneficialOwnerQuery = EnhancedRandom.random(BeneficialOwnerQuery.class);
        beneficialOwnerQuery.setKonturFocusRequestType(KonturFocusRequestTypeEnum.BENEFICIALOWNERQUERY);
        KonturFocusParams konturFocusParams = new KonturFocusParams();
        konturFocusParams.setRequest(beneficialOwnerQuery);

        BeneficialOwnerResponses beneficialOwnerResponses =
                ((BeneficialOwnerResponses) questionaryAggrProxyService.requestKonturFocus(konturFocusParams));

        int thriftBeneficialOwnersSize =
                thriftKonturFocusBeneficialOwnerResponse.getBeneficialOwnerResponses().getBeneficialOwnerResponses()
                        .size();
        Assert.assertEquals(thriftBeneficialOwnersSize, beneficialOwnerResponses.getResponses().size());
        for (int i = 0; i < thriftBeneficialOwnersSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerResponse
                    beneficialOwnerResponse =
                    thriftKonturFocusBeneficialOwnerResponse.getBeneficialOwnerResponses().getBeneficialOwnerResponses()
                            .get(i);
            BeneficialOwnerResponse swagBeneficialOwnerResponse = beneficialOwnerResponses.getResponses().get(i);
            KonturFocusCompareUtil.beneficialOwnerResponseCompare(beneficialOwnerResponse, swagBeneficialOwnerResponse);
        }
    }

    @Test
    public void requestDaDataAddressTest() throws IOException, TException {
        DaDataResponse thriftDaDataAddressResponse = QuestionaryAggrTestData.createThriftDaDataAddressResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataAddressResponse);

        AddressQuery addressQuery = EnhancedRandom.random(AddressQuery.class);
        addressQuery.setDaDataRequestType(DaDataRequestTypeEnum.ADDRESSQUERY);
        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setRequest(addressQuery);

        AddressResponse addressResponse = (AddressResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDataAddressResponse.getAddressResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, addressResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            Address address = thriftDaDataAddressResponse.getAddressResponse().getSuggestions().get(i);
            DaDataAddress daDataAddress = addressResponse.getSuggestions().get(i);
            DaDataCompareUtil.addressCompare(address, daDataAddress);
        }
    }

    @Test
    public void requestDaDataPartyTest() throws TException, IOException {
        DaDataResponse thriftDaDataPartyResponse = QuestionaryAggrTestData.createThriftDaDataPartyResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataPartyResponse);

        PartyQuery partyQuery = EnhancedRandom.random(PartyQuery.class);
        partyQuery.setDaDataRequestType(DaDataRequestTypeEnum.PARTYQUERY);
        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setRequest(partyQuery);

        PartyResponse partyResponse = (PartyResponse) questionaryAggrProxyService.requestDaData(daDataParams);
        int thriftSuggestionsSize = thriftDaDataPartyResponse.getPartyResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, partyResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyContent partyContent =
                    thriftDaDataPartyResponse.getPartyResponse().getSuggestions().get(i);
            PartyContent swagPartyContent = partyResponse.getSuggestions().get(i);
            DaDataCompareUtil.partyCompare(partyContent, swagPartyContent);
        }
    }

    @Test
    public void requestDaDataOkvedTest() throws TException, IOException {
        DaDataResponse thriftDaDataOkvedResponse = QuestionaryAggrTestData.createThriftDaDataOkvedResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataOkvedResponse);

        OkvedQuery okvedQuery = EnhancedRandom.random(OkvedQuery.class);
        okvedQuery.setDaDataRequestType(DaDataRequestTypeEnum.OKVEDQUERY);
        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setRequest(okvedQuery);

        OkvedResponse okvedResponse = (OkvedResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDataOkvedResponse.getOkvedResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, okvedResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedContent okvedContent =
                    thriftDaDataOkvedResponse.getOkvedResponse().getSuggestions().get(i);
            OkvedContent swagOkvedContent = okvedResponse.getSuggestions().get(i);
            DaDataCompareUtil.okvedCompare(okvedContent, swagOkvedContent);
        }
    }

    @Test
    public void requestDaDataBankTest() throws TException, IOException {
        DaDataResponse thriftDaDataBankResponse = QuestionaryAggrTestData.createThriftDaDataBankResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataBankResponse);

        BankQuery bankQuery = EnhancedRandom.random(BankQuery.class);
        bankQuery.setDaDataRequestType(DaDataRequestTypeEnum.BANKQUERY);
        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setRequest(bankQuery);

        BankResponse bankResponse = (BankResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDataBankResponse.getBankResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, bankResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankContent bankContent =
                    thriftDaDataBankResponse.getBankResponse().getSuggestions().get(i);
            BankContent swagBankContent = bankResponse.getSuggestions().get(i);
            DaDataCompareUtil.bankCompare(bankContent, swagBankContent);
        }
    }

    @Test
    public void requestDaDataFioTest() throws TException, IOException {
        DaDataResponse thriftDaDataFioResponse = QuestionaryAggrTestData.createThriftDaDataFioResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDataFioResponse);

        FioQuery fioQuery = EnhancedRandom.random(FioQuery.class);
        fioQuery.setDaDataRequestType(DaDataRequestTypeEnum.FIOQUERY);
        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setRequest(fioQuery);

        FioResponse fioResponse = (FioResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDataFioResponse.getFioResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, fioResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioContent fioContent =
                    thriftDaDataFioResponse.getFioResponse().getSuggestions().get(i);
            FioContent swagFioContent = fioResponse.getSuggestions().get(i);
            DaDataCompareUtil.fioCompare(fioContent, swagFioContent);
        }
    }

    @Test
    public void requestDaDataFmsUnitTest() throws TException, IOException {
        DaDataResponse thriftDaDateFmsUnitResponse = QuestionaryAggrTestData.createThriftDaDateFmsUnitResponse();
        when(questionaryAggrProxyHandler.requestDaData(any(DaDataRequest.class), any(DaDataEndpoint.class)))
                .thenReturn(thriftDaDateFmsUnitResponse);

        FmsUnitQuery fmsUnitQuery = EnhancedRandom.random(FmsUnitQuery.class);
        fmsUnitQuery.setDaDataRequestType(DaDataRequestTypeEnum.FMSUNITQUERY);
        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setRequest(fmsUnitQuery);

        FmsUnitResponse fmsUnitResponse = (FmsUnitResponse) questionaryAggrProxyService.requestDaData(daDataParams);

        int thriftSuggestionsSize = thriftDaDateFmsUnitResponse.getFmsUnitResponse().getSuggestions().size();
        Assert.assertEquals(thriftSuggestionsSize, fmsUnitResponse.getSuggestions().size());
        for (int i = 0; i < thriftSuggestionsSize; i++) {
            com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitContent fmsUnitContent =
                    thriftDaDateFmsUnitResponse.getFmsUnitResponse().getSuggestions().get(i);
            FmsUnitContent swagFmsUnitContent = fmsUnitResponse.getSuggestions().get(i);
            DaDataCompareUtil.fmsUnitCompare(fmsUnitContent, swagFmsUnitContent);
        }
    }

}
