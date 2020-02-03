package com.rbkmoney.dark.api.controller;

import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataInvalidRequest;
import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataNotFound;
import com.rbkmoney.damsel.questionary_proxy_aggr.KonturFocusInvalidRequest;
import com.rbkmoney.damsel.questionary_proxy_aggr.KonturFocusNotFound;
import com.rbkmoney.dark.api.exceptions.client.NotFoundException;
import com.rbkmoney.dark.api.exceptions.client.badrequest.BadRequestException;
import com.rbkmoney.dark.api.service.PartyManagementService;
import com.rbkmoney.dark.api.service.QuestionaryAggrProxyService;
import com.rbkmoney.swag.questionary_aggr_proxy.api.ProxyApi;
import com.rbkmoney.swag.questionary_aggr_proxy.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionaryAggrProxyController implements ProxyApi {

    private final QuestionaryAggrProxyService questionaryAggrProxyService;
    private final PartyManagementService partyManagementService;

    @Override
    public ResponseEntity<DaDataResponse> requestDaData(@Valid DaDataParams daDataParams) {
        try {
            partyManagementService.checkStatus();

            DaDataResponse daDataResponse = questionaryAggrProxyService.requestDaData(daDataParams);

            return ResponseEntity.ok(daDataResponse);
        } catch (DaDataInvalidRequest ex) {
            String msg = String.format("DaData invalid request, msg=%s", ex.getErrorMsg());
            GeneralError response = new GeneralError().message(msg);
            throw badRequestException(msg, ex, response);
        } catch (DaDataNotFound ex) {
            String msg = "DaData not found";
            GeneralError response = new GeneralError().message(msg);
            throw new NotFoundException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("questionary-aggr", "requestDaData", null, ex);
        }
    }

    @Override
    public ResponseEntity<KonturFocusResponse> requestKonturFocus(@Valid KonturFocusParams konturFocusParams) {
        try {
            partyManagementService.checkStatus();

            KonturFocusResponse konturFocusResponse = questionaryAggrProxyService.requestKonturFocus(konturFocusParams);

            return ResponseEntity.ok(konturFocusResponse);
        } catch (KonturFocusInvalidRequest ex) {
            String msg = String.format("KonturFocus invalid request, msg=%s", ex.getErrorMsg());
            GeneralError response = new GeneralError().message(msg);
            throw badRequestException(msg, ex, response);
        } catch (KonturFocusNotFound ex) {
            String msg = "KonturFocus not found";
            GeneralError response = new GeneralError().message(msg);
            throw new NotFoundException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("questionary-aggr", "requestKonturFocus", null, ex);
        }
    }

    private BadRequestException badRequestException(String msg, Throwable cause, Object response) {
        return new BadRequestException(msg, cause, response);
    }
}
