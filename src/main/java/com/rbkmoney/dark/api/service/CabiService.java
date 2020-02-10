package com.rbkmoney.dark.api.service;

import com.rbkmoney.cabi.CheckCurrencyExchangeParams;
import com.rbkmoney.cabi.CryptoApiSrv;
import com.rbkmoney.cabi.CurrencyExchange;
import com.rbkmoney.dark.api.converter.SwagConvertManager;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyRequestHolder;
import com.rbkmoney.swag.cabi.model.ExchangeAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CabiService {

    private final CryptoApiSrv.Iface cryptoApiService;

    private final SwagConvertManager swagConvertManager;

    public com.rbkmoney.swag.cabi.model.CurrencyExchange checkCurrencyExchange(String from,
                                                                               String to,
                                                                               ExchangeAction action,
                                                                               Long amount) throws TException {
        CabiCheckCurrencyRequestHolder checkCurrencyRequestHolder
                = new CabiCheckCurrencyRequestHolder(from, to, action, amount);
        CheckCurrencyExchangeParams checkCurrencyExchangeParams
                = swagConvertManager.convertFromThrift(checkCurrencyRequestHolder, CheckCurrencyExchangeParams.class);
        CurrencyExchange currencyExchange = cryptoApiService.checkCurrencyExchange(checkCurrencyExchangeParams);

        return swagConvertManager.convertFromThrift(
                currencyExchange, com.rbkmoney.swag.cabi.model.CurrencyExchange.class);
    }

}
