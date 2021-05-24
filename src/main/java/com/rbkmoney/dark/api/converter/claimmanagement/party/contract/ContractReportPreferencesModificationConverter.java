package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.domain.*;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ContractReportPreferencesModification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTREPORTPREFERENCESMODIFICATION;

@Component
@RequiredArgsConstructor
public class ContractReportPreferencesModificationConverter
        implements DarkApiConverter<ReportPreferences, ContractReportPreferencesModification> {

    private final DarkApiConverter<RepresentativeDocument,
            com.rbkmoney.swag.claim_management.model.RepresentativeDocument> representativeDocumentConverter;

    @Override
    public ReportPreferences convertToThrift(
            ContractReportPreferencesModification swagReportPreferences
    ) {
        ReportPreferences reportPreferencesModification = new ReportPreferences();
        ServiceAcceptanceActPreferences actPreferences = new ServiceAcceptanceActPreferences();

        var reportPreferences = swagReportPreferences.getReportPreferences();
        if (reportPreferences.getServiceAcceptanceActPreferences() != null) {
            var serviceAcceptanceActPreferences = reportPreferences.getServiceAcceptanceActPreferences();
            actPreferences.setSchedule(new BusinessScheduleRef().setId(
                    serviceAcceptanceActPreferences.getSchedule().getId())
            );
            var swagSigner = serviceAcceptanceActPreferences.getSigner();
            Representative signer = new Representative()
                    .setFullName(swagSigner.getFullName())
                    .setPosition(swagSigner.getPosition())
                    .setDocument(representativeDocumentConverter.convertToThrift(swagSigner.getDocument()));
            actPreferences.setSigner(signer);
        }

        reportPreferencesModification.setServiceAcceptanceActPreferences(actPreferences);
        return reportPreferencesModification;
    }

    @Override
    public ContractReportPreferencesModification convertToSwag(ReportPreferences reportPreferences) {
        ContractReportPreferencesModification contractReportPreferencesModification =
                new ContractReportPreferencesModification();
        contractReportPreferencesModification.setContractModificationType(CONTRACTREPORTPREFERENCESMODIFICATION);
        var swagReportPreferences = new com.rbkmoney.swag.claim_management.model.ReportPreferences();

        if (reportPreferences.isSetServiceAcceptanceActPreferences()) {
            ServiceAcceptanceActPreferences serviceAcceptanceActPreferences =
                    reportPreferences.getServiceAcceptanceActPreferences();
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
            swagReportPreferences.setServiceAcceptanceActPreferences(swagServiceAcceptanceActPreferences);
        }
        contractReportPreferencesModification.setReportPreferences(swagReportPreferences);
        return contractReportPreferencesModification;
    }

}
