package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.MigrationCardInfo;
import org.springframework.stereotype.Component;

@Component
public class MigrationCardInfoConverter implements
        ThriftConverter<MigrationCardInfo, com.rbkmoney.swag.questionary.model.MigrationCardInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.MigrationCardInfo, MigrationCardInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.MigrationCardInfo toSwag(MigrationCardInfo value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.MigrationCardInfo()
                .beginningDate(value.getBeginningDate())
                .cardNumber(value.getCardNumber())
                .expirationDate(value.getExpirationDate());
    }

    @Override
    public MigrationCardInfo toThrift(com.rbkmoney.swag.questionary.model.MigrationCardInfo value, ThriftConverterContext ctx) {
        return new MigrationCardInfo()
                .setExpirationDate(value.getExpirationDate())
                .setBeginningDate(value.getBeginningDate())
                .setCardNumber(value.getCardNumber());
    }

}
