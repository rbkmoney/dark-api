package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.domain.LegalAgreement;
import com.rbkmoney.damsel.domain.ReportPreferences;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.PartyModificationType.PartyModificationTypeEnum.CONTRACTMODIFICATIONUNIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class ContractModificationUnitConverter
        implements DarkApiConverter<ContractModificationUnit, com.rbkmoney.swag.claim_management.model.ContractModificationUnit> {

    private final DarkApiConverter<ContractParams,
            com.rbkmoney.swag.claim_management.model.ContractParams> contractModificationCreationConverter;

    private final DarkApiConverter<ReportPreferences,
            com.rbkmoney.swag.claim_management.model.ReportPreferences> reportPreferencesConverter;

    private final DarkApiConverter<PayoutToolModificationUnit,
            com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit> payoutToolModificationUnitConverter;

    private final DarkApiConverter<ContractAdjustmentModificationUnit,
            com.rbkmoney.swag.claim_management.model.ContractAdjustmentModificationUnit> adjustmentModificationConverter;

    private final DarkApiConverter<LegalAgreement,
            com.rbkmoney.swag.claim_management.model.LegalAgreement> legalAgreementConverter;

        @Override
    public ContractModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.ContractModificationUnit swagContractModificationUnit
    ) {
        ContractModificationUnit contractModificationUnit = new ContractModificationUnit();
        contractModificationUnit.setId(swagContractModificationUnit.getId());
        ContractModification contractModification = new ContractModification();
        var swagContractModification = swagContractModificationUnit.getModification();

        switch (swagContractModification.getContractModificationType()) {
            case CONTRACTPARAMS:
                var swagContractParams = (com.rbkmoney.swag.claim_management.model.ContractParams) swagContractModification;
                contractModification.setCreation(
                        contractModificationCreationConverter.convertToThrift(swagContractParams)
                );
                break;
            case CONTRACTORID:
                var swagContractorId = (com.rbkmoney.swag.claim_management.model.ContractorID) swagContractModification;
                contractModification.setContractorModification(swagContractorId.getContractorID());
                break;
            case LEGALAGREEMENT:
                var swagLegalAgreement = (com.rbkmoney.swag.claim_management.model.LegalAgreement) swagContractModification;
                contractModification.setLegalAgreementBinding(
                        legalAgreementConverter.convertToThrift(swagLegalAgreement)
                );
                break;
            case REPORTPREFERENCES:
                var swagReportPreferences = (com.rbkmoney.swag.claim_management.model.ReportPreferences) swagContractModification;
                contractModification.setReportPreferencesModification(
                        reportPreferencesConverter.convertToThrift(swagReportPreferences)
                );
                break;
            case CONTRACTTERMINATION:
                var swagContractTerm = (com.rbkmoney.swag.claim_management.model.ContractTermination) swagContractModification;
                contractModification.setTermination(
                        new ContractTermination().setReason(swagContractTerm.getReason())
                );
                break;
            case PAYOUTTOOLMODIFICATIONUNIT:
                var swagPayoutToolModificationUnit =
                        (com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit) swagContractModification;
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
            swagContractModificationUnit.setModification(adjustmentModificationConverter.convertToSwag(adjustmentModification));
        } else if (contractModification.isSetContractorModification()) {
            var swagContractModification = new com.rbkmoney.swag.claim_management.model.ContractorID();
            swagContractModification.setContractModificationType(CONTRACTORID);
            swagContractModification.setContractorID(contractModification.getContractorModification());
            swagContractModificationUnit.setModification(swagContractModification);
        } else if (contractModification.isSetTermination()) {
            ContractTermination contractTermination = contractModification.getTermination();
            var swagContractTermination = new com.rbkmoney.swag.claim_management.model.ContractTermination();
            swagContractTermination.setContractModificationType(CONTRACTTERMINATION);
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
