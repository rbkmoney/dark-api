package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.ContractAdjustmentModificationUnit;
import com.rbkmoney.damsel.claim_management.ContractModification;
import com.rbkmoney.damsel.claim_management.ContractModificationUnit;
import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.damsel.domain.ReportPreferences;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTCONTRACTORMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTTERMINATIONMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.CONTRACTMODIFICATIONUNIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractModificationUnitConverter
        implements
        DarkApiConverter<ContractModificationUnit, com.rbkmoney.swag.claim_management.model.ContractModificationUnit> {

    private final DarkApiConverter<ContractParams, ContractCreationModification> contractModificationCreationConverter;

    private final DarkApiConverter<ReportPreferences, ContractReportPreferencesModification> reportPreferencesConverter;

    private final DarkApiConverter<PayoutToolModificationUnit, ContractPayoutToolModificationUnit>
            payoutToolModificationUnitConverter;

    private final DarkApiConverter<ContractAdjustmentModificationUnit,
            com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit>
            adjustmentModificationConverter;

    private final DarkApiConverter<LegalAgreement, ContractLegalAgreementBindingModification> legalAgreementConverter;

    @Override
    public ContractModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.ContractModificationUnit swagContractModificationUnit
    ) {
        ContractModificationUnit contractModificationUnit = new ContractModificationUnit();
        contractModificationUnit.setId(swagContractModificationUnit.getId());
        ContractModification contractModification = new ContractModification();
        var swagContractModification = swagContractModificationUnit.getModification();

        switch (swagContractModification.getContractModificationType()) {
            case CONTRACTCREATIONMODIFICATION:
                var swagContractParams = (ContractCreationModification) swagContractModification;
                contractModification.setCreation(
                        contractModificationCreationConverter.convertToThrift(swagContractParams)
                );
                break;
            case CONTRACTCONTRACTORMODIFICATION:
                var swagContractorId = (ContractContractorModification) swagContractModification;
                contractModification.setContractorModification(swagContractorId.getContractorID());
                break;
            case CONTRACTLEGALAGREEMENTBINDINGMODIFICATION:
                var swagLegalAgreement = (ContractLegalAgreementBindingModification) swagContractModification;
                contractModification.setLegalAgreementBinding(
                        legalAgreementConverter.convertToThrift(swagLegalAgreement)
                );
                break;
            case CONTRACTREPORTPREFERENCESMODIFICATION:
                var swagReportPreferences = (ContractReportPreferencesModification) swagContractModification;
                contractModification.setReportPreferencesModification(
                        reportPreferencesConverter.convertToThrift(swagReportPreferences)
                );
                break;
            case CONTRACTTERMINATIONMODIFICATION:
                var swagContractTerm = (ContractTerminationModification) swagContractModification;
                contractModification.setTermination(
                        new ContractTermination().setReason(swagContractTerm.getReason())
                );
                break;
            case CONTRACTPAYOUTTOOLMODIFICATIONUNIT:
                var swagPayoutToolModificationUnit =
                        (ContractPayoutToolModificationUnit) swagContractModification;
                contractModification.setPayoutToolModification(
                        payoutToolModificationUnitConverter.convertToThrift(swagPayoutToolModificationUnit)
                );
                break;
            case CONTRACTADJUSTMENTMODIFICATIONUNIT:
                var swagContractAdjustmentModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit) swagContractModification;
                contractModification.setAdjustmentModification(
                        adjustmentModificationConverter.convertToThrift(swagContractAdjustmentModificationUnit)
                );
                break;
            default:
                throw new IllegalArgumentException("Unknown contract modification type: " +
                        swagContractModification.getContractModificationType());
        }
        contractModificationUnit.setModification(contractModification);
        return contractModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ContractModificationUnit convertToSwag(
            ContractModificationUnit contractModificationUnit
    ) {
        var swagContractModificationUnit = new com.rbkmoney.swag.claim_management.model.ContractModificationUnit();
        swagContractModificationUnit.setId(contractModificationUnit.getId());
        swagContractModificationUnit.setPartyModificationType(CONTRACTMODIFICATIONUNIT);
        ContractModification contractModification = contractModificationUnit.getModification();

        if (contractModification.isSetCreation()) {
            ContractParams creation = contractModification.getCreation();
            swagContractModificationUnit.setModification(
                    contractModificationCreationConverter.convertToSwag(creation)
            );
        } else if (contractModification.isSetAdjustmentModification()) {
            ContractAdjustmentModificationUnit adjustmentModification =
                    contractModification.getAdjustmentModification();
            swagContractModificationUnit
                    .setModification(adjustmentModificationConverter.convertToSwag(adjustmentModification));
        } else if (contractModification.isSetContractorModification()) {
            var swagContractModification = new ContractContractorModification();
            swagContractModification.setContractModificationType(CONTRACTCONTRACTORMODIFICATION);
            swagContractModification.setContractorID(contractModification.getContractorModification());
            swagContractModificationUnit.setModification(swagContractModification);
        } else if (contractModification.isSetTermination()) {
            ContractTermination contractTermination = contractModification.getTermination();
            var swagContractTermination = new ContractTerminationModification();
            swagContractTermination.setContractModificationType(CONTRACTTERMINATIONMODIFICATION);
            swagContractTermination.setReason(contractTermination.getReason());
            swagContractModificationUnit.setModification(swagContractTermination);
        } else if (contractModification.isSetLegalAgreementBinding()) {
            LegalAgreement legalAgreementBinding = contractModification.getLegalAgreementBinding();
            swagContractModificationUnit.setModification(
                    legalAgreementConverter.convertToSwag(legalAgreementBinding)
            );
        } else if (contractModification.isSetPayoutToolModification()) {
            PayoutToolModificationUnit payoutToolModificationUnit = contractModification.getPayoutToolModification();
            swagContractModificationUnit.setModification(
                    payoutToolModificationUnitConverter.convertToSwag(payoutToolModificationUnit)
            );
        } else if (contractModification.isSetReportPreferencesModification()) {
            ReportPreferences reportPreferences = contractModification.getReportPreferencesModification();
            swagContractModificationUnit.setModification(reportPreferencesConverter.convertToSwag(reportPreferences));
        } else {
            throw new IllegalArgumentException("Unknown contract modification type!");
        }
        return swagContractModificationUnit;
    }

}
