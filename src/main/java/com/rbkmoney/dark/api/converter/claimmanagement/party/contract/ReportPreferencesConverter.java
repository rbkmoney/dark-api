package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.REPORTPREFERENCES;

@Component
@RequiredArgsConstructor
public class ReportPreferencesConverter
        implements DarkApiConverter<ReportPreferences, com.rbkmoney.swag.claim_management.model.ReportPreferences> {

    private final DarkApiConverter<RepresentativeDocument,
                com.rbkmoney.swag.claim_management.model.RepresentativeDocument> representativeDocumentConverter;

    @Override
    public ReportPreferences convertToThrift(
            com.rbkmoney.swag.claim_management.model.ReportPreferences swagReportPreferences
    ) {
        ReportPreferences reportPreferencesModification = new ReportPreferences();
        ServiceAcceptanceActPreferences actPreferences = new ServiceAcceptanceActPreferences();

        var swagSigner = swagReportPreferences.getServiceAcceptanceActPreferences().getSigner();
        Representative signer = new Representative()
                .setFullName(swagSigner.getFullName())
                .setPosition(swagSigner.getPosition())
                .setDocument(representativeDocumentConverter.convertToThrift(swagSigner.getDocument()));

        actPreferences.setSchedule(new BusinessScheduleRef().setId(
                swagReportPreferences.getServiceAcceptanceActPreferences().getSchedule().getId())
        );

        actPreferences.setSigner(signer);
        reportPreferencesModification.setServiceAcceptanceActPreferences(actPreferences);
        return reportPreferencesModification;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ReportPreferences convertToSwag(ReportPreferences reportPreferences) {
        ServiceAcceptanceActPreferences serviceAcceptanceActPreferences =
                reportPreferences.getServiceAcceptanceActPreferences();
        var swagReportPreferences =
                new com.rbkmoney.swag.claim_management.model.ReportPreferences();
        var swagServiceAcceptanceActPreferences =
                new com.rbkmoney.swag.claim_management.model.ServiceAcceptanceActPreferences();
        var swagSchedule = new com.rbkmoney.swag.claim_management.model.BusinessScheduleRef();
        swagSchedule.setId(serviceAcceptanceActPreferences.getSchedule().getId());

        Representative signer = serviceAcceptanceActPreferences.getSigner();
        var swagSigner = new com.rbkmoney.swag.claim_management.model.Representative();
        swagSigner.setFullName(signer.getFullName());
        swagSigner.setPosition(signer.getPosition());
        swagSigner.setDocument(representativeDocumentConverter.convertToSwag(signer.getDocument()));

        swagServiceAcceptanceActPreferences.setSchedule(swagSchedule);
        swagServiceAcceptanceActPreferences.setSigner(swagSigner);

        swagReportPreferences.setContractModificationType(REPORTPREFERENCES);
        swagReportPreferences.setServiceAcceptanceActPreferences(swagServiceAcceptanceActPreferences);
        return swagReportPreferences;
    }

}
