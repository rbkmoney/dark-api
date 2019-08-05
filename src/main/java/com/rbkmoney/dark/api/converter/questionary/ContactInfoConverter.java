package com.rbkmoney.dark.api.converter.questionary;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.questionary.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoConverter implements
        ThriftConverter<ContactInfo, com.rbkmoney.swag.questionary.model.ContactInfo>,
        SwagConverter<com.rbkmoney.swag.questionary.model.ContactInfo, ContactInfo> {

    @Override
    public com.rbkmoney.swag.questionary.model.ContactInfo toSwag(ContactInfo value, SwagConverterContext ctx) {
        return new com.rbkmoney.swag.questionary.model.ContactInfo()
                .email(value.getEmail())
                .phoneNumber(value.getPhoneNumber());
    }

    @Override
    public ContactInfo toThrift(com.rbkmoney.swag.questionary.model.ContactInfo value, ThriftConverterContext ctx) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPhoneNumber(value.getPhoneNumber());
        contactInfo.setEmail(value.getEmail());
        return contactInfo;
    }
}
