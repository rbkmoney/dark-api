package com.rbkmoney.dark.api.service;

import com.rbkmoney.dark.api.converter.SwagConvertManager;
import com.rbkmoney.questionary.manage.*;
import com.rbkmoney.swag.questionary.model.QuestionaryParams;
import com.rbkmoney.swag.questionary.model.Snapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionaryService {

    private final QuestionaryManagerSrv.Iface questionaryManagerSrv;

    private final SwagConvertManager swagConvertManager;

    public Snapshot getQuestionary(String questionaryId, String version) {
        Reference reference = new Reference();
        if (version == null) {
            reference.setHead(new Head());
        } else {
            reference.setVersion(Long.parseLong(version));
        }
        try {
            com.rbkmoney.questionary.manage.Snapshot snapshot = questionaryManagerSrv.get(questionaryId, reference);
            return swagConvertManager.convertFromThrift(snapshot, Snapshot.class);
        } catch (QuestionaryNotFound e) {
            log.error("Questionary not found", e);
            throw new IllegalArgumentException(e);
        } catch (TException e) {
            log.error("Get questionary failed", e);
            throw new RuntimeException(e);
        }
    }

    public Long saveQuestionary(QuestionaryParams questionaryParams, Long version) {
        var thriftQuestionaryParams = swagConvertManager.convertToThrift(questionaryParams,
                com.rbkmoney.questionary.manage.QuestionaryParams.class);
        try {
            if (version == null) {
                version = 0L;
            }

            return questionaryManagerSrv.save(thriftQuestionaryParams, version);
        } catch (QuestionaryNotValidException e) {
            log.error("Invalid questionary", e);
            throw new IllegalArgumentException(e);
        } catch (QuestionaryNotFound e) {
            log.error("Questionary not found", e);
            throw new IllegalArgumentException(e);
        } catch (TException e) {
            log.error("Get questionary failed", e);
            throw new RuntimeException(e);
        }
    }

}
