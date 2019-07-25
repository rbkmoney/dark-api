package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataRequestException;
import com.rbkmoney.damsel.questionary_proxy_aggr.KonturFocusRequestException;
import com.rbkmoney.damsel.questionary_proxy_aggr.QuestionaryAggrProxyHandlerSrv;
import com.rbkmoney.dark.api.converter.SwagConvertManager;
import com.rbkmoney.dark.api.converter.dadata.DaDataParamToDaDataRequest;
import com.rbkmoney.dark.api.converter.dadata.DaDataRequestHolder;
import com.rbkmoney.dark.api.converter.konturfocus.KonturFocusParamToKonturFocusRequestConverter;
import com.rbkmoney.dark.api.model.KonturFocusRequestHolder;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataParams;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataResponse;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusParams;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionaryAggrProxyService {

    private final QuestionaryAggrProxyHandlerSrv.Iface questionaryAggrProxyClient;

    private final SwagConvertManager swagConvertManager;

    public KonturFocusResponse requestKonturFocus(KonturFocusParams konturFocusParams) {
        try {
            KonturFocusRequestHolder konturFocusRequestHolder = swagConvertManager.convertToThrift(konturFocusParams, KonturFocusRequestHolder.class);
            var thriftKonturFocusResponse = questionaryAggrProxyClient.requestKonturFocus(
                    konturFocusRequestHolder.getKonturFocusRequest(), konturFocusRequestHolder.getKonturFocusEndPoint()
            );
            return swagConvertManager.convertFromThrift(thriftKonturFocusResponse, KonturFocusResponse.class);
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
            DaDataRequestHolder daDataRequestHolder = swagConvertManager.convertToThrift(daDataParams, DaDataRequestHolder.class);
            var thriftDaDataResponse = questionaryAggrProxyClient.requestDaData(daDataRequestHolder.getDaDataRequest(),
                    daDataRequestHolder.getDaDataEndpoint());
            return swagConvertManager.convertFromThrift(thriftDaDataResponse, DaDataResponse.class);
        } catch (DaDataRequestException e) {
            log.error("Exception while sending request to DaData", e);
            throw new IllegalArgumentException(e);
        } catch (TException e) {
            log.error("TException DaData", e);
            throw new RuntimeException(e);
        }
    }
}
