package com.rbkmoney.dark.api.controller;

import com.rbkmoney.cabi.CurrencyRequestFail;
import com.rbkmoney.dark.api.domain.ErrorResponse;
import com.rbkmoney.dark.api.exceptions.client.badrequest.BadRequestException;
import com.rbkmoney.dark.api.service.CabiService;
import com.rbkmoney.swag.cabi.api.CurrencyApi;
import com.rbkmoney.swag.cabi.model.CurrencyExchange;
import com.rbkmoney.swag.cabi.model.ExchangeAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CabiController implements CurrencyApi {

    private final CabiService cabiService;

    @Override
    public ResponseEntity<CurrencyExchange> currencyExchange(
            @NotNull @Valid String from,
            @NotNull @Valid String to,
            @NotNull @Valid ExchangeAction action,
            @NotNull @Valid Long amount) {
        CurrencyExchange currencyExchange = null;
        try {
            currencyExchange = cabiService.checkCurrencyExchange(from, to, action, amount);
        } catch (CurrencyRequestFail e) {
            String msg = "CABI 'checkCurrencyExchange' invalid request";
            throw new BadRequestException(msg, e, new ErrorResponse("400", msg));
        } catch (TException e) {
            throw darkApi5xxException("swag-cabi", "checkCurrencyExchange", null, e);
        }
        return ResponseEntity.ok(currencyExchange);
    }

}
