package com.rbkmoney.dark.api.service.questionary.utils;

import com.rbkmoney.swag.dark_api.model.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class QuestionaryUtils {

    public static com.rbkmoney.swag.dark_api.model.Snapshot prepareSnapshot(com.rbkmoney.questionary.manage.Snapshot manageSnapshot) {
        com.rbkmoney.swag.dark_api.model.Snapshot snapshot = new com.rbkmoney.swag.dark_api.model.Snapshot();
        snapshot.setId(manageSnapshot.getQuestionary().getId());
        snapshot.setOwnerId(manageSnapshot.getQuestionary().getOwnerId());
        snapshot.setVersion(Long.valueOf(manageSnapshot.getVersion()).intValue());

        com.rbkmoney.questionary.manage.QuestionaryData manageQuestionaryData = manageSnapshot.getQuestionary().getData();
        com.rbkmoney.swag.dark_api.model.QuestionaryData questionaryData = new com.rbkmoney.swag.dark_api.model.QuestionaryData();
        if (manageQuestionaryData.getBankAccount().isSetRussianBankAccount()) {
            questionaryData.setBankAccount(ConverterManageToSwagModel.prepareRussianBankAccount(manageQuestionaryData));
        }

        questionaryData.setContactInfo(ConverterManageToSwagModel.prepareContactInfo(manageQuestionaryData.getContactInfo()));
        questionaryData.setShopInfo(ConverterManageToSwagModel.prepareShopInfo(manageQuestionaryData));
        questionaryData.setContractor(ConverterManageToSwagModel.prepareContractor(manageQuestionaryData));
        snapshot.setData(questionaryData);
        return snapshot;
    }

    public static com.rbkmoney.questionary.manage.Reference prepareReference(@RequestBody @Valid QuestionaryGetParams questionaryParams) {
        com.rbkmoney.questionary.manage.Reference reference = new com.rbkmoney.questionary.manage.Reference();

        if (questionaryParams.getReference() instanceof Head) {
            reference.setHead(new com.rbkmoney.questionary.manage.Head());
        }

        if (questionaryParams.getReference() instanceof ReferenceVersion) {
            ReferenceVersion referenceVersion = (ReferenceVersion) questionaryParams.getReference();
            reference.setVersion(referenceVersion.getVersion());
        }

        return reference;
    }

    public static com.rbkmoney.questionary.manage.QuestionaryParams prepareQuestionaryParams(QuestionaryParams questionaryParams) {
        com.rbkmoney.questionary.manage.QuestionaryData questionaryData = new com.rbkmoney.questionary.manage.QuestionaryData();

        RussianBankAccount russianBankAccount = (RussianBankAccount) questionaryParams.getData().getBankAccount();
        questionaryData.setBankAccount(ConverterSwagToManageModel.prepareRussianBankAccount(russianBankAccount));

        questionaryData.setContactInfo(ConverterSwagToManageModel.prepareContactInfo(questionaryParams.getData().getContactInfo()));
        questionaryData.setShopInfo(ConverterSwagToManageModel.prepareShopInfo(questionaryParams.getData().getShopInfo()));
        questionaryData.setContractor(ConverterSwagToManageModel.prepareContractor(questionaryParams.getData().getContractor()));

        com.rbkmoney.questionary.manage.QuestionaryParams params = new com.rbkmoney.questionary.manage.QuestionaryParams();
        params.setData(questionaryData);
        params.setOwnerId(questionaryParams.getOwnerId());
        params.setId(questionaryParams.getId());
        return params;
    }

}
