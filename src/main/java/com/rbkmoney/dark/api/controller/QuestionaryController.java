package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.exceptions.client.NotFoundException;
import com.rbkmoney.dark.api.exceptions.client.badrequest.BadRequestException;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.dark.api.service.PartyManagementService;
import com.rbkmoney.dark.api.service.QuestionaryService;
import com.rbkmoney.questionary.manage.QuestionaryNotFound;
import com.rbkmoney.questionary.manage.QuestionaryNotValid;
import com.rbkmoney.questionary.manage.QuestionaryVersionConflict;
import com.rbkmoney.swag.questionary.api.QuestionaryApi;
import com.rbkmoney.swag.questionary.model.GeneralError;
import com.rbkmoney.swag.questionary.model.InlineResponse400;
import com.rbkmoney.swag.questionary.model.QuestionaryParams;
import com.rbkmoney.swag.questionary.model.Snapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rbkmoney.dark.api.util.ExceptionUtils.darkApi5xxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class QuestionaryController implements QuestionaryApi {

    private final QuestionaryService questionaryService;
    private final PartyManagementService partyManagementService;
    private final KeycloakService keycloakService;

    @Override
    public ResponseEntity<Snapshot> getQuestionary(String questionaryId, @Valid String version) {
        try {
            partyManagementService.checkStatus();

            String partyId = keycloakService.getPartyId();

            Snapshot snapshot = questionaryService.getQuestionary(questionaryId, partyId, version);

            return ResponseEntity.ok(snapshot);
        } catch (QuestionaryNotFound ex) {
            String msg = "Questionary not found";
            GeneralError response = new GeneralError().message(msg);
            throw new NotFoundException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("questionary", "getQuestionary", null, ex);
        }
    }

    @Override
    public ResponseEntity<String> saveQuestionary(@Valid QuestionaryParams questionaryParams) {
        try {
            partyManagementService.checkStatus();

            Long ver = questionaryParams.getVersion() != null ? Long.parseLong(questionaryParams.getVersion()) : null;

            Long version = questionaryService.saveQuestionary(questionaryParams, ver);
            return ResponseEntity.ok(version.toString());
        } catch (QuestionaryNotValid ex) {
            String msg = "Questionary not valid";
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.QUESTIONARYNOTVALIDEXCEPTION)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (QuestionaryVersionConflict ex) {
            String msg = "Questionary version conflict";
            InlineResponse400 response = new InlineResponse400()
                    .code(InlineResponse400.CodeEnum.QUESTIONARYVERSIONCONFLICT)
                    .message(msg);
            throw new BadRequestException(msg, ex, response);
        } catch (TException ex) {
            throw darkApi5xxException("questionary", "saveQuestionary", null, ex);
        }
    }
}
