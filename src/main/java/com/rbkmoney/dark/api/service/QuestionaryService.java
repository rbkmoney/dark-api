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

    public Snapshot getQuestionary(String questionaryId, String version) throws QuestionaryNotFound, TException {
        Reference reference = getReference(version);

        var snapshot = questionaryManagerSrv.get(questionaryId, reference);
        return swagConvertManager.convertFromThrift(snapshot, Snapshot.class);
    }

    public Long saveQuestionary(QuestionaryParams questionaryParams, Long version) throws QuestionaryNotValid, QuestionaryVersionConflict, TException {
        var thriftQuestionaryParams = swagConvertManager.convertToThrift(questionaryParams, com.rbkmoney.questionary.manage.QuestionaryParams.class);

        if (version == null) {
            version = 0L;
        }

        return questionaryManagerSrv.save(thriftQuestionaryParams, version);
    }

    private Reference getReference(String version) {
        Reference reference = new Reference();
        if (version == null) {
            reference.setHead(new Head());
        } else {
            reference.setVersion(Long.parseLong(version));
        }
        return reference;
    }
}
