package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.service.QuestionaryService;
import com.rbkmoney.swag.questionary.api.QuestionaryApi;
import com.rbkmoney.swag.questionary.model.QuestionaryGetParams;
import com.rbkmoney.swag.questionary.model.QuestionaryParams;
import com.rbkmoney.swag.questionary.model.Snapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionaryController implements QuestionaryApi {

    private final QuestionaryService questionaryService;

    @Override
    public ResponseEntity<Snapshot> getQuestionary(String questionaryId, @Valid String version) {
        Snapshot snapshot = questionaryService.getQuestionary(questionaryId, version);
        return ResponseEntity.ok(snapshot);
    }

    @Override
    public ResponseEntity<String> saveQuestionary(@Valid QuestionaryParams questionaryParams) {
        try {
            Long version = questionaryService.saveQuestionary(questionaryParams, Long.parseLong(questionaryParams.getVersion()));
            return ResponseEntity.ok(String.valueOf(version));
        } catch (NumberFormatException e) {
            log.error("Not valid version format: " + questionaryParams.getVersion(), e);
            throw new IllegalArgumentException(e);
        }
    }
}
