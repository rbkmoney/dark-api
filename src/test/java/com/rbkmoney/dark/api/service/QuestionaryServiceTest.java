package com.rbkmoney.dark.api.service;

import com.rbkmoney.dark.api.utils.QuestionaryCompareUtil;
import com.rbkmoney.dark.api.utils.QuestionaryTestData;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.questionary.manage.QuestionaryManagerSrv;
import com.rbkmoney.swag.questionary.model.QuestionaryParams;
import com.rbkmoney.swag.questionary.model.Snapshot;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Before;
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
    private QuestionaryManagerSrv.Iface questionaryManagerSrv;

    @Autowired
    private QuestionaryService questionaryService;

    private QuestionaryTestData questionaryTestData;

    @Before
    public void setUp() throws Exception {
        MockTBaseProcessor mockTBaseProcessor = new MockTBaseProcessor(MockMode.ALL, 10, 1);
        mockTBaseProcessor.addFieldHandler(structHandler -> {
            structHandler.beginStruct(3);
            structHandler.name("number");
            structHandler.value("test");
            structHandler.name("iban");
            structHandler.value("test");
            structHandler.name("account_holder");
            structHandler.value("test");
            structHandler.endStruct();
        }, "correspondent_account");
        questionaryTestData = new QuestionaryTestData(mockTBaseProcessor);
    }

    @Test
    public void getIndividualEntityQuestionaryTest() throws TException, IOException {
        var questionary = new com.rbkmoney.questionary.manage.Questionary();
        var questionaryParams = questionaryTestData.createIndividualEntityQuestionaryThrift();
        questionary.setId(questionaryParams.getId());
        questionary.setOwnerId(questionaryParams.getOwnerId());
        questionary.setPartyId(questionaryParams.getPartyId());
        questionary.setData(questionaryParams.getData());

        var snapshot = new com.rbkmoney.questionary.manage.Snapshot();
        snapshot.setVersion(0L);
        snapshot.setQuestionary(questionary);

        when(questionaryManagerSrv.get(anyString(), anyString(), any(com.rbkmoney.questionary.manage.Reference.class)))
                .then(invocation -> snapshot);

        Snapshot swagSnapshot = questionaryService.getQuestionary("test", "testPartyId", "1");

        Assert.assertEquals(Long.parseLong(swagSnapshot.getVersion()), snapshot.getVersion());
        Assert.assertEquals(swagSnapshot.getQuestionary().getId(), snapshot.getQuestionary().getId());
        Assert.assertEquals(swagSnapshot.getQuestionary().getOwnerId(), snapshot.getQuestionary().getOwnerId());
        Assert.assertEquals(swagSnapshot.getQuestionary().getPartyId(), snapshot.getQuestionary().getPartyId());
        QuestionaryCompareUtil.bankAccountCompare(snapshot.getQuestionary().getData().getBankAccount(),
                swagSnapshot.getQuestionary().getData().getBankAccount());
        QuestionaryCompareUtil.contactInfoCompare(snapshot.getQuestionary().getData().getContactInfo(),
                swagSnapshot.getQuestionary().getData().getContactInfo());
        QuestionaryCompareUtil.contractorCompare(snapshot.getQuestionary().getData().getContractor(),
                swagSnapshot.getQuestionary().getData().getContractor());
    }

    @Test
    public void getInternationalLegalEntityQuestionaryTest() throws TException, IOException {
        var questionary = new com.rbkmoney.questionary.manage.Questionary();
        var questionaryParams = questionaryTestData.createInternationalLegalEntityQuestionaryThrift();
        questionary.setId(questionaryParams.getId());
        questionary.setOwnerId(questionaryParams.getOwnerId());
        questionary.setPartyId(questionaryParams.getPartyId());
        questionary.setData(questionaryParams.getData());

        var snapshot = new com.rbkmoney.questionary.manage.Snapshot();
        snapshot.setVersion(0L);
        snapshot.setQuestionary(questionary);

        when(questionaryManagerSrv.get(anyString(), anyString(), any(com.rbkmoney.questionary.manage.Reference.class)))
                .then(invocation -> snapshot);

        Snapshot swagSnapshot = questionaryService.getQuestionary("test", "testPartyId", "1");

        Assert.assertEquals(Long.parseLong(swagSnapshot.getVersion()), snapshot.getVersion());
        Assert.assertEquals(swagSnapshot.getQuestionary().getId(), snapshot.getQuestionary().getId());
        Assert.assertEquals(swagSnapshot.getQuestionary().getOwnerId(), snapshot.getQuestionary().getOwnerId());
        Assert.assertEquals(swagSnapshot.getQuestionary().getPartyId(), snapshot.getQuestionary().getPartyId());

        QuestionaryCompareUtil.internationalBankAccountCompare(
                snapshot.getQuestionary().getData().getBankAccount(),
                swagSnapshot.getQuestionary().getData().getBankAccount()
        );
        QuestionaryCompareUtil.internationalLegalEntityCompare(
                snapshot.getQuestionary().getData().getContractor(),
                swagSnapshot.getQuestionary().getData().getContractor()
        );
    }

    @Test
    public void getLegalEntityQuestionaryTest() throws TException, IOException {
        var questionary = new com.rbkmoney.questionary.manage.Questionary();
        var questionaryParams = questionaryTestData.createLegalEntityQuestionaryThrift();
        questionary.setId(questionaryParams.getId());
        questionary.setOwnerId(questionaryParams.getOwnerId());
        questionary.setPartyId(questionaryParams.getPartyId());
        questionary.setData(questionaryParams.getData());

        var snapshot = new com.rbkmoney.questionary.manage.Snapshot();
        snapshot.setVersion(0L);
        snapshot.setQuestionary(questionary);

        when(questionaryManagerSrv.get(anyString(), anyString(), any(com.rbkmoney.questionary.manage.Reference.class)))
                .then(invocation -> snapshot);

        Snapshot swagSnapshot = questionaryService.getQuestionary("test", "testPartyId", "1");

        Assert.assertEquals(Long.parseLong(swagSnapshot.getVersion()), snapshot.getVersion());
        Assert.assertEquals(swagSnapshot.getQuestionary().getId(), snapshot.getQuestionary().getId());
        Assert.assertEquals(swagSnapshot.getQuestionary().getOwnerId(), snapshot.getQuestionary().getOwnerId());
        Assert.assertEquals(swagSnapshot.getQuestionary().getPartyId(), snapshot.getQuestionary().getPartyId());

        QuestionaryCompareUtil.bankAccountCompare(snapshot.getQuestionary().getData().getBankAccount(),
                swagSnapshot.getQuestionary().getData().getBankAccount());
        QuestionaryCompareUtil.contactInfoCompare(snapshot.getQuestionary().getData().getContactInfo(),
                swagSnapshot.getQuestionary().getData().getContactInfo());
        QuestionaryCompareUtil.contractorCompare(snapshot.getQuestionary().getData().getContractor(),
                swagSnapshot.getQuestionary().getData().getContractor());
    }

    @Test
    public void saveIndividualEntityQuestionaryTest() throws TException {
        ArgumentCaptor<com.rbkmoney.questionary.manage.QuestionaryParams> captor =
                ArgumentCaptor.forClass(com.rbkmoney.questionary.manage.QuestionaryParams.class);
        QuestionaryParams questionaryParams = questionaryTestData.createIndividualEntityQuestionarySwag();
        questionaryService.saveQuestionary(questionaryParams, "12345", Long.parseLong(questionaryParams.getVersion()));
        verify(questionaryManagerSrv).save(captor.capture(), anyLong());
        com.rbkmoney.questionary.manage.QuestionaryParams thriftQuestionaryParams = captor.getValue();
        QuestionaryCompareUtil.contractorCompare(thriftQuestionaryParams.getData().getContractor(),
                questionaryParams.getData().getContractor());
    }

    @Test
    public void saveLegalEntityQuestionaryTest() throws TException {
        ArgumentCaptor<com.rbkmoney.questionary.manage.QuestionaryParams> captor =
                ArgumentCaptor.forClass(com.rbkmoney.questionary.manage.QuestionaryParams.class);
        QuestionaryParams questionaryParams = questionaryTestData.createLegalEntityQuestionarySwag();
        questionaryService.saveQuestionary(questionaryParams, "12345", Long.parseLong(questionaryParams.getVersion()));
        verify(questionaryManagerSrv).save(captor.capture(), anyLong());
        com.rbkmoney.questionary.manage.QuestionaryParams thriftQuestionaryParams = captor.getValue();
        QuestionaryCompareUtil.contractorCompare(thriftQuestionaryParams.getData().getContractor(),
                questionaryParams.getData().getContractor());
    }

    @Test
    public void saveInternationalLegalEntityQuestionaryTest() throws TException {
        ArgumentCaptor<com.rbkmoney.questionary.manage.QuestionaryParams> captor =
                ArgumentCaptor.forClass(com.rbkmoney.questionary.manage.QuestionaryParams.class);
        QuestionaryParams questionaryParams = questionaryTestData.createInternationalLegalEntityQuestionarySwag();
        questionaryService.saveQuestionary(
                questionaryParams,
                "12345",
                Long.parseLong(questionaryParams.getVersion())
        );
        verify(questionaryManagerSrv).save(captor.capture(), anyLong());
        com.rbkmoney.questionary.manage.QuestionaryParams thriftQuestionaryParams = captor.getValue();
        QuestionaryCompareUtil.internationalLegalEntityCompare(
                thriftQuestionaryParams.getData().getContractor(),
                questionaryParams.getData().getContractor()
        );
    }

}
