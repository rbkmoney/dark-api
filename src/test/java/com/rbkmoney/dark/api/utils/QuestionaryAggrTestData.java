package com.rbkmoney.dark.api.utils;

import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.questionary_proxy_aggr.dadata_address.AddressResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_api.DaDataResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_bank.BankResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_fio.FioResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_fms_unit.FmsUnitResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_okved2.OkvedResponse;
import com.rbkmoney.questionary_proxy_aggr.dadata_party.PartyResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_api.KonturFocusResponse;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_beneficial_owner.BeneficialOwnerResponses;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_egr_details.EgrDetailsResponses;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_licences.LicencesResponses;
import com.rbkmoney.questionary_proxy_aggr.kontur_focus_req.ReqResponses;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusParams;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QuestionaryAggrTestData {

    public static KonturFocusParams getKonturFocusParams() {
        return EnhancedRandom.random(KonturFocusParams.class);
    }

    public static KonturFocusResponse createThriftKonturFocusReqResponse() throws IOException {
        KonturFocusResponse konturFocusResponse = new KonturFocusResponse();
        ReqResponses reqResponses = new ReqResponses();
        reqResponses =
                new MockTBaseProcessor(MockMode.ALL).process(reqResponses, new TBaseHandler<>(ReqResponses.class));
        konturFocusResponse.setReqResponses(reqResponses);
        return konturFocusResponse;
    }

    public static KonturFocusResponse createThriftKonturFocusLicensesResponse() throws IOException {
        KonturFocusResponse konturFocusResponse = new KonturFocusResponse();
        LicencesResponses licencesResponses = new LicencesResponses();
        licencesResponses = new MockTBaseProcessor(MockMode.ALL)
                .process(licencesResponses, new TBaseHandler<>(LicencesResponses.class));
        konturFocusResponse.setLicencesResponses(licencesResponses);
        return konturFocusResponse;
    }

    public static KonturFocusResponse createThriftKonturFocusEgrDetailsResponse() throws IOException {
        KonturFocusResponse konturFocusResponse = new KonturFocusResponse();
        EgrDetailsResponses egrDetailsResponses = new EgrDetailsResponses();
        egrDetailsResponses = new MockTBaseProcessor(MockMode.ALL)
                .process(egrDetailsResponses, new TBaseHandler<>(EgrDetailsResponses.class));
        konturFocusResponse.setEgrDetailsResponses(egrDetailsResponses);
        return konturFocusResponse;
    }

    public static KonturFocusResponse createThriftKonturFocusBeneficialOwnerResponse() throws IOException {
        KonturFocusResponse konturFocusResponse = new KonturFocusResponse();
        BeneficialOwnerResponses beneficialOwnerResponses = new BeneficialOwnerResponses();
        beneficialOwnerResponses = new MockTBaseProcessor(MockMode.ALL)
                .process(beneficialOwnerResponses, new TBaseHandler<>(BeneficialOwnerResponses.class));
        konturFocusResponse.setBeneficialOwnerResponses(beneficialOwnerResponses);
        return konturFocusResponse;
    }

    public static DaDataResponse createThriftDaDataAddressResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        AddressResponse addressResponse = new AddressResponse();
        addressResponse = new MockTBaseProcessor(MockMode.ALL)
                .process(addressResponse, new TBaseHandler<>(AddressResponse.class));
        daDataResponse.setAddressResponse(addressResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDataPartyResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        PartyResponse partyResponse = new PartyResponse();
        partyResponse =
                new MockTBaseProcessor(MockMode.ALL).process(partyResponse, new TBaseHandler<>(PartyResponse.class));
        daDataResponse.setPartyResponse(partyResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDataOkvedResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        OkvedResponse okvedResponse = new OkvedResponse();
        okvedResponse =
                new MockTBaseProcessor(MockMode.ALL).process(okvedResponse, new TBaseHandler<>(OkvedResponse.class));
        daDataResponse.setOkvedResponse(okvedResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDataBankResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        BankResponse bankResponse = new BankResponse();
        bankResponse =
                new MockTBaseProcessor(MockMode.ALL).process(bankResponse, new TBaseHandler<>(BankResponse.class));
        daDataResponse.setBankResponse(bankResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDataFioResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        FioResponse fioResponse = new FioResponse();
        fioResponse = new MockTBaseProcessor(MockMode.ALL).process(fioResponse, new TBaseHandler<>(FioResponse.class));
        daDataResponse.setFioResponse(fioResponse);
        return daDataResponse;
    }

    public static DaDataResponse createThriftDaDateFmsUnitResponse() throws IOException {
        DaDataResponse daDataResponse = new DaDataResponse();
        FmsUnitResponse fmsUnitResponse = new FmsUnitResponse();
        fmsUnitResponse = new MockTBaseProcessor(MockMode.ALL)
                .process(fmsUnitResponse, new TBaseHandler<>(FmsUnitResponse.class));
        daDataResponse.setFmsUnitResponse(fmsUnitResponse);
        return daDataResponse;
    }

}
