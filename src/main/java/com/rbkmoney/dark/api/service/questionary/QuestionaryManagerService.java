package com.rbkmoney.dark.api.service.questionary;

import com.rbkmoney.questionary.manage.*;
import lombok.RequiredArgsConstructor;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionaryManagerService implements QuestionaryManagerSrv.Iface {

    private final QuestionaryManagerSrv.Iface questionaryManager;

    @Override
    public long save(QuestionaryParams params, long version) throws TException {
        return questionaryManager.save(params, version);
    }

    @Override
    public com.rbkmoney.questionary.manage.Snapshot get(String questionaryId, Reference reference) throws TException {
        return questionaryManager.get(questionaryId, reference);
    }

}
