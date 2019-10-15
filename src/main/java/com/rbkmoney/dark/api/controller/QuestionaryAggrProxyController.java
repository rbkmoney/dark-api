package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.service.QuestionaryAggrProxyService;
import com.rbkmoney.swag.questionary_aggr_proxy.api.ProxyApi;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataParams;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataResponse;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusParams;
import com.rbkmoney.swag.questionary_aggr_proxy.model.KonturFocusResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionaryAggrProxyController implements ProxyApi {

    private final QuestionaryAggrProxyService questionaryAggrProxyService;

    @Override
    public ResponseEntity<DaDataResponse> requestDaData(@Valid DaDataParams daDataParams) {
        DaDataResponse daDataResponse = questionaryAggrProxyService.requestDaData(daDataParams);
        return ResponseEntity.ok(daDataResponse);
    }

    @Override
    public ResponseEntity<KonturFocusResponse> requestKonturFocus(@Valid KonturFocusParams konturFocusParams) {
        KonturFocusResponse konturFocusResponse = questionaryAggrProxyService.requestKonturFocus(konturFocusParams);
        return ResponseEntity.ok(konturFocusResponse);
    }
}
