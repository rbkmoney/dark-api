package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.AuthorityConfirmingDocument;
import org.springframework.stereotype.Component;

@Component
public class AuthorityConfirmDocumentConverter implements
        ThriftConverter<AuthorityConfirmingDocument, com.rbkmoney.swag.questionary.model.AuthorityConfirmingDocument>,
        SwagConverter<com.rbkmoney.swag.questionary.model.AuthorityConfirmingDocument, AuthorityConfirmingDocument> {

    @Override
    public com.rbkmoney.swag.questionary.model.AuthorityConfirmingDocument toSwag(AuthorityConfirmingDocument value,
                                                                                  SwagConverterContext ctx) {
        var authorityConfirmingDocument = new com.rbkmoney.swag.questionary.model.AuthorityConfirmingDocument();
        authorityConfirmingDocument.setDate(value.getDate());
        authorityConfirmingDocument.setNumber(value.getNumber());
        authorityConfirmingDocument.setType(value.getType());

        return authorityConfirmingDocument;
    }

    @Override
    public AuthorityConfirmingDocument toThrift(com.rbkmoney.swag.questionary.model.AuthorityConfirmingDocument value,
                                                ThriftConverterContext ctx) {
        AuthorityConfirmingDocument authorityConfirmingDocument = new AuthorityConfirmingDocument();
        authorityConfirmingDocument.setDate(value.getDate());
        authorityConfirmingDocument.setType(value.getType());
        authorityConfirmingDocument.setNumber(value.getNumber());

        return authorityConfirmingDocument;
    }

}
