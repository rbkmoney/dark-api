package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataRequestException;
import com.rbkmoney.damsel.questionary_proxy_aggr.KonturFocusRequestException;
import com.rbkmoney.damsel.questionary_proxy_aggr.QuestionaryAggrProxyHandlerSrv;
import com.rbkmoney.dark.api.converter.SwagConvertManager;
import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.dadata.*;
import com.rbkmoney.dark.api.converter.konturfocus.*;
import com.rbkmoney.dark.api.model.KonturFocusRequestHolder;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QuestionaryAggrProxyService {

    private final QuestionaryAggrProxyHandlerSrv.Iface questionaryAggrProxyClient;

    private final SwagConvertManager swagConvertManager;

    @Autowired
    public QuestionaryAggrProxyService(QuestionaryAggrProxyHandlerSrv.Iface questionaryAggrProxyClient,
                                       SwagConvertManager swagConvertManager) {
        this.questionaryAggrProxyClient = questionaryAggrProxyClient;
        this.swagConvertManager = swagConvertManager;
    }

    public KonturFocusResponse requestKonturFocus(KonturFocusParams konturFocusParams) {
        try {
            KonturFocusRequestHolder konturFocusRequestHolder = KonturFocusParamToKonturFocusRequestConverter.convert(konturFocusParams);
            var thriftKonturFocusResponse = questionaryAggrProxyClient.requestKonturFocus(
                    konturFocusRequestHolder.getKonturFocusRequest(), konturFocusRequestHolder.getKonturFocusEndPoint()
            );
            return swagConvertManager.convert(thriftKonturFocusResponse, KonturFocusResponse.class);
        } catch (KonturFocusRequestException e) {
            log.error("Exception while sending request to KonturFocus", e);
            throw new IllegalArgumentException(e);
        } catch (TException e) {
            log.error("TException KonturFocus", e);
            throw new RuntimeException(e);
        }
    }

    public DaDataResponse requestDaData(DaDataParams daDataParams) {
        try {
            DaDataRequestHolder daDataRequestHolder = DaDataParamToDaDataRequest.convert(daDataParams);
            var thriftDaDataResponse = questionaryAggrProxyClient.requestDaData(daDataRequestHolder.getDaDataRequest(),
                    daDataRequestHolder.getDaDataEndpoint());
            return swagConvertManager.convert(thriftDaDataResponse, DaDataResponse.class);
        } catch (DaDataRequestException e) {
            log.error("Exception while sending request to DaData", e);
            throw new IllegalArgumentException(e);
        } catch (TException e) {
            log.error("TException DaData", e);
            throw new RuntimeException(e);
        }
    }
}
