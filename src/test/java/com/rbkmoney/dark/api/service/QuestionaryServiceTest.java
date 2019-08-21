package com.rbkmoney.dark.api.service;

import com.rbkmoney.dark.api.questionaryaggr.utils.TestDataUtils;
import com.rbkmoney.dark.api.utils.QuestionaryCompareUtil;
import com.rbkmoney.questionary.manage.QuestionaryManagerSrv;
import com.rbkmoney.swag.questionary.model.QuestionaryParams;
import com.rbkmoney.swag.questionary.model.Snapshot;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QuestionaryServiceTest {

    @MockBean
    private QuestionaryManagerSrv.Iface questionaryManager;

    @Autowired
    private QuestionaryService questionaryService;

    @Test
    public void saveIndividualEntityQuestionaryTest() throws TException {
        ArgumentCaptor<com.rbkmoney.questionary.manage.QuestionaryParams> captor =
                ArgumentCaptor.forClass(com.rbkmoney.questionary.manage.QuestionaryParams.class);
        QuestionaryParams questionaryParams = TestDataUtils.createIndividualEntityQuestionarySwag();
        questionaryService.saveQuestionary(questionaryParams, Long.parseLong(questionaryParams.getVersion()));
        verify(questionaryManager).save(captor.capture(), anyLong());
        com.rbkmoney.questionary.manage.QuestionaryParams thriftQuestionaryParams = captor.getValue();
        QuestionaryCompareUtil.contractorCompare(thriftQuestionaryParams.getData().getContractor(), questionaryParams.getData().getContractor());
    }

    @Test
    public void getIndividualEntityQuestionaryTest() throws TException, IOException {
        com.rbkmoney.questionary.manage.QuestionaryParams questionaryParams = TestDataUtils.createIndividualEntityQuestionaryThrift();
        com.rbkmoney.questionary.manage.Snapshot snapshot = new com.rbkmoney.questionary.manage.Snapshot();
        snapshot.setVersion(0L);
        com.rbkmoney.questionary.manage.Questionary questionary = new com.rbkmoney.questionary.manage.Questionary();
        questionary.setId(questionaryParams.getId());
        questionary.setOwnerId(questionaryParams.getOwnerId());
        questionary.setData(questionaryParams.getData());
        snapshot.setQuestionary(questionary);
        when(questionaryManager.get(anyString(), any(com.rbkmoney.questionary.manage.Reference.class)))
                .then(invocation -> snapshot);
        Snapshot swagSnapshot = questionaryService.getQuestionary("test", "1");
        Assert.assertEquals(Long.parseLong(swagSnapshot.getVersion()), snapshot.getVersion());
        Assert.assertEquals(swagSnapshot.getQuestionary().getId(), snapshot.getQuestionary().getId());
        Assert.assertEquals(swagSnapshot.getQuestionary().getOwnerID(), snapshot.getQuestionary().getOwnerId());
        QuestionaryCompareUtil.bankAccountCompare(snapshot.getQuestionary().getData().getBankAccount(), swagSnapshot.getQuestionary().getData().getBankAccount());
        QuestionaryCompareUtil.contactInfoCompare(snapshot.getQuestionary().getData().getContactInfo(), swagSnapshot.getQuestionary().getData().getContactInfo());
        QuestionaryCompareUtil.contractorCompare(snapshot.getQuestionary().getData().getContractor(), swagSnapshot.getQuestionary().getData().getContractor());
    }

    @Test
    public void saveLegalEntityQuestionaryTest() throws TException {
        ArgumentCaptor<com.rbkmoney.questionary.manage.QuestionaryParams> captor =
                ArgumentCaptor.forClass(com.rbkmoney.questionary.manage.QuestionaryParams.class);
        QuestionaryParams questionaryParams = TestDataUtils.createLegalEntityQuestionarySwag();
        questionaryService.saveQuestionary(questionaryParams, Long.parseLong(questionaryParams.getVersion()));
        verify(questionaryManager).save(captor.capture(), anyLong());
        com.rbkmoney.questionary.manage.QuestionaryParams thriftQuestionaryParams = captor.getValue();
        QuestionaryCompareUtil.contractorCompare(thriftQuestionaryParams.getData().getContractor(), questionaryParams.getData().getContractor());
    }

    @Test
    public void getLegalEntityQuestionaryTest() throws TException, IOException {
        com.rbkmoney.questionary.manage.QuestionaryParams questionaryParams = TestDataUtils.createLegalEntityQuestionaryThrift();
        com.rbkmoney.questionary.manage.Snapshot snapshot = new com.rbkmoney.questionary.manage.Snapshot();
        snapshot.setVersion(0L);
        com.rbkmoney.questionary.manage.Questionary questionary = new com.rbkmoney.questionary.manage.Questionary();
        questionary.setId(questionaryParams.getId());
        questionary.setOwnerId(questionaryParams.getOwnerId());
        questionary.setData(questionaryParams.getData());
        snapshot.setQuestionary(questionary);
        when(questionaryManager.get(anyString(), any(com.rbkmoney.questionary.manage.Reference.class)))
                .then(invocation -> snapshot);
        Snapshot swagSnapshot = questionaryService.getQuestionary("test", "1");
        Assert.assertEquals(Long.parseLong(swagSnapshot.getVersion()), snapshot.getVersion());
        Assert.assertEquals(swagSnapshot.getQuestionary().getId(), snapshot.getQuestionary().getId());
        Assert.assertEquals(swagSnapshot.getQuestionary().getOwnerID(), snapshot.getQuestionary().getOwnerId());
        QuestionaryCompareUtil.bankAccountCompare(snapshot.getQuestionary().getData().getBankAccount(), swagSnapshot.getQuestionary().getData().getBankAccount());
        QuestionaryCompareUtil.contactInfoCompare(snapshot.getQuestionary().getData().getContactInfo(), swagSnapshot.getQuestionary().getData().getContactInfo());
        QuestionaryCompareUtil.contractorCompare(snapshot.getQuestionary().getData().getContractor(), swagSnapshot.getQuestionary().getData().getContractor());
    }

}
