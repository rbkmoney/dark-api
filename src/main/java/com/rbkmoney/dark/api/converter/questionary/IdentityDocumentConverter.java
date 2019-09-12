package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.IdentityDocument;
import com.rbkmoney.swag.questionary.model.IdentityDocument.IdentityDocumentTypeEnum;
import com.rbkmoney.swag.questionary.model.RussianDomesticPassport;
import org.springframework.stereotype.Component;

@Component
public class IdentityDocumentConverter implements
        ThriftConverter<IdentityDocument, com.rbkmoney.swag.questionary.model.IdentityDocument>,
        SwagConverter<com.rbkmoney.swag.questionary.model.IdentityDocument, IdentityDocument> {

    @Override
    public com.rbkmoney.swag.questionary.model.IdentityDocument toSwag(IdentityDocument value, SwagConverterContext ctx) {
        if (value.isSetRussianDomesticPassword()) {
            return ctx.convert(value.getRussianDomesticPassword(), RussianDomesticPassport.class);
        } else {
            throw new IllegalArgumentException("Unknown identityDocument type: " + value.getClass().getName());
        }
    }

    @Override
    public IdentityDocument toThrift(com.rbkmoney.swag.questionary.model.IdentityDocument value, ThriftConverterContext ctx) {
        IdentityDocument identityDocument = new IdentityDocument();
        if (value.getIdentityDocumentType() == IdentityDocumentTypeEnum.RUSSIANDOMESTICPASSPORT) {
            identityDocument.setRussianDomesticPassword(ctx.convert(value, com.rbkmoney.questionary.RussianDomesticPassport.class));
        } else {
            throw new IllegalArgumentException("Unknown identityDocument type: " + value.getIdentityDocumentType());
        }
        return identityDocument;
    }

}
