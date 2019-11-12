package com.rbkmoney.dark.api.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class ConversationUsersNotProvided extends RuntimeException {

    private final List<String> ids;

    public ConversationUsersNotProvided(List<String> ids) {
        this.ids = ids;
    }

    @Override
    public String getMessage() {
        return "Conversation users not provided" + String.join(", ", ids);
    }
}
