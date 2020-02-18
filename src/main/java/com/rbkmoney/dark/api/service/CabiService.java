package com.rbkmoney.dark.api.service;

import com.rbkmoney.cabi.CheckCurrencyExchangeParams;
import com.rbkmoney.cabi.CryptoApiSrv;
import com.rbkmoney.cabi.CurrencyExchange;
import com.rbkmoney.damsel.domain.Currency;
import com.rbkmoney.dark.api.config.property.DominantProperties;
import com.rbkmoney.dark.api.converter.SwagConvertManager;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyRequestDto;
import com.rbkmoney.dark.api.model.CabiCheckCurrencyResponseDto;
import com.rbkmoney.swag.cabi.model.ExchangeAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CabiService {

    private final CryptoApiSrv.Iface cryptoApiService;

    private final DominantService dominantService;

    private final SwagConvertManager swagConvertManager;

    private final DominantProperties dominantProperties;

    public com.rbkmoney.swag.cabi.model.CurrencyExchange checkCurrencyExchange(String from,
                                                                               String to,
                                                                               ExchangeAction action,
                                                                               BigDecimal amount) throws TException {
        Currency fromCurrency = dominantService.getCurrency(from);
        Currency toCurrency = dominantService.getCurrency(to);

        CabiCheckCurrencyRequestDto checkCurrencyRequestHolder
                = new CabiCheckCurrencyRequestDto(fromCurrency, toCurrency, action, amount);
        CheckCurrencyExchangeParams checkCurrencyExchangeParams
                = swagConvertManager.convertToThrift(checkCurrencyRequestHolder, CheckCurrencyExchangeParams.class);

        CurrencyExchange currencyExchange = cryptoApiService.checkCurrencyExchange(checkCurrencyExchangeParams);

        CabiCheckCurrencyResponseDto cabiCheckCurrencyResponseDto = new CabiCheckCurrencyResponseDto();
        cabiCheckCurrencyResponseDto.setAction(currencyExchange.getAction());
        cabiCheckCurrencyResponseDto.setTo(toCurrency);
        cabiCheckCurrencyResponseDto.setFrom(fromCurrency);
        cabiCheckCurrencyResponseDto.setAmount(currencyExchange.getAmount());
        cabiCheckCurrencyResponseDto.setAmountExchanged(currencyExchange.getAmountExchanged());
        cabiCheckCurrencyResponseDto.setAmountExchangedWithFee(currencyExchange.getAmountExchangedWithFee());
        cabiCheckCurrencyResponseDto.setRate(currencyExchange.getRate());

        return swagConvertManager.convertToThrift(
                cabiCheckCurrencyResponseDto, com.rbkmoney.swag.cabi.model.CurrencyExchange.class);
    }

}
