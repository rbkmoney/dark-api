package com.rbkmoney.dark.api.converter.mesages;

import com.rbkmoney.dark.api.converter.SwagConverter;
import com.rbkmoney.dark.api.converter.SwagConverterContext;
import com.rbkmoney.dark.api.converter.ThriftConverter;
import com.rbkmoney.dark.api.converter.ThriftConverterContext;
import com.rbkmoney.swag.messages.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements SwagConverter<User, com.rbkmoney.damsel.messages.User>,
        ThriftConverter<com.rbkmoney.damsel.messages.User, User> {

    @Override
    public User toSwag(com.rbkmoney.damsel.messages.User value, SwagConverterContext ctx) {
        return new User()
                .userId(value.getUserId())
                .fullName(value.getFullname())
                .email(value.getEmail());
    }

    @Override
    public com.rbkmoney.damsel.messages.User toThrift(User value, ThriftConverterContext ctx) {
        return new com.rbkmoney.damsel.messages.User()
                .setUserId(value.getUserId())
                .setFullname(value.getFullName())
                .setEmail(value.getEmail());
    }

}
