package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.service.ConversationService;
import com.rbkmoney.swag.messages.api.ConversationApi;
import com.rbkmoney.swag.messages.model.ConversationResponse;
import com.rbkmoney.swag.messages.model.GetConversationParams;
import com.rbkmoney.swag.messages.model.SaveConversationParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ConversationController implements ConversationApi {

    private final ConversationService conversationService;

    @Override
    public ResponseEntity<ConversationResponse> getConversations(@Valid GetConversationParams getConversationParams) {
        ConversationResponse conversation = conversationService.getConversation(getConversationParams);
        return ResponseEntity.ok(conversation);
    }

    @Override
    public ResponseEntity<Void> saveConversations(@Valid SaveConversationParams saveConversationParams) {
        conversationService.saveConversation(saveConversationParams);
        return ResponseEntity.ok().build();
    }
}
