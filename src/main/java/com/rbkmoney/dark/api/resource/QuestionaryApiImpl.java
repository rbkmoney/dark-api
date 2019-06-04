package com.rbkmoney.dark.api.resource;

import com.rbkmoney.dark.api.service.questionary.QuestionaryManagerService;
import com.rbkmoney.swag.dark_api.api.QuestionaryApi;
import com.rbkmoney.swag.dark_api.model.QuestionaryGetParams;
import com.rbkmoney.swag.dark_api.model.QuestionaryParams;
import com.rbkmoney.swag.dark_api.model.Snapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.rbkmoney.dark.api.service.questionary.utils.QuestionaryUtils.*;

@Slf4j
@Service
@RestController
@RequiredArgsConstructor
public class QuestionaryApiImpl implements QuestionaryApi {

    private final QuestionaryManagerService questionaryManagerService;

    @Override
    public ResponseEntity<Snapshot> getQuestionary(
            String xRequestID, String authorization,
            @Valid @RequestBody QuestionaryGetParams questionaryParams
    ) {
        try {
            com.rbkmoney.questionary.manage.Snapshot snapshotResponse = questionaryManagerService.get(
                    questionaryParams.getQuestionaryId(), prepareReference(questionaryParams)
            );
            return new ResponseEntity<>(prepareSnapshot(snapshotResponse), HttpStatus.OK);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Integer> saveQuestionary(
            String xRequestID, String authorization,
            @Valid @RequestBody QuestionaryParams questionaryParams
    ) {
        try {
            Long version = questionaryManagerService.save(
                    prepareQuestionaryParams(questionaryParams), questionaryParams.getVersion()
            );
            return new ResponseEntity<>(version.intValue(), HttpStatus.OK);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

}
