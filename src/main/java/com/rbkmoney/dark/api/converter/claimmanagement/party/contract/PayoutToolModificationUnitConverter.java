package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.PayoutToolModification;
import com.rbkmoney.damsel.claim_management.PayoutToolModificationUnit;
import com.rbkmoney.damsel.claim_management.PayoutToolParams;
import com.rbkmoney.damsel.domain.CurrencyRef;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import com.rbkmoney.swag.claim_management.model.ContractPayoutToolCreationModification;
import com.rbkmoney.swag.claim_management.model.ContractPayoutToolInfoModification;
import com.rbkmoney.swag.claim_management.model.ContractPayoutToolModificationUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.CONTRACTPAYOUTTOOLMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.ContractPayoutToolModification.PayoutToolModificationTypeEnum.CONTRACTPAYOUTTOOLCREATIONMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.ContractPayoutToolModification.PayoutToolModificationTypeEnum.CONTRACTPAYOUTTOOLINFOMODIFICATION;

@Component
@RequiredArgsConstructor
public class PayoutToolModificationUnitConverter
        implements DarkApiConverter<PayoutToolModificationUnit, ContractPayoutToolModificationUnit> {

    private final DarkApiConverter<PayoutToolInfo,
            com.rbkmoney.swag.claim_management.model.PayoutToolInfo> payoutToolInfoConverter;

    @Override
    public PayoutToolModificationUnit convertToThrift(ContractPayoutToolModificationUnit swagPayoutToolModificationUnit) {
        PayoutToolModificationUnit payoutToolModificationUnit = new PayoutToolModificationUnit();
        payoutToolModificationUnit.setPayoutToolId(swagPayoutToolModificationUnit.getPayoutToolID());

        PayoutToolModification payoutToolModification = new PayoutToolModification();

        var swagPayoutToolModification = swagPayoutToolModificationUnit.getModification();
        switch (swagPayoutToolModification.getPayoutToolModificationType()) {
            case CONTRACTPAYOUTTOOLCREATIONMODIFICATION:
                var swagContractPayoutToolCreation = (ContractPayoutToolCreationModification) swagPayoutToolModification;
                PayoutToolParams creation = new PayoutToolParams();
                creation.setCurrency(
                        new CurrencyRef().setSymbolicCode(swagContractPayoutToolCreation.getCurrency().getSymbolicCode()));
                creation.setToolInfo(payoutToolInfoConverter.convertToThrift(swagContractPayoutToolCreation.getToolInfo()));
                payoutToolModification.setCreation(creation);
                break;
            case CONTRACTPAYOUTTOOLINFOMODIFICATION:
                var swagPayoutToolInfo = (ContractPayoutToolInfoModification) swagPayoutToolModification;
                payoutToolModification.setInfoModification(payoutToolInfoConverter.convertToThrift(swagPayoutToolInfo.getPayoutToolInfo()));
                break;
            default:
                throw new IllegalArgumentException("Unknown PayoutTool modification type: " +
                        swagPayoutToolModification.getPayoutToolModificationType());
        }

        payoutToolModificationUnit.setModification(payoutToolModification);
        return payoutToolModificationUnit;
    }

    @Override
    public ContractPayoutToolModificationUnit convertToSwag(
            PayoutToolModificationUnit payoutToolModificationUnit
    ) {
        var swagPayoutToolModificationUnit = new ContractPayoutToolModificationUnit();
        swagPayoutToolModificationUnit.setPayoutToolID(payoutToolModificationUnit.getPayoutToolId());
        swagPayoutToolModificationUnit.setContractModificationType(CONTRACTPAYOUTTOOLMODIFICATIONUNIT);

        PayoutToolModification payoutToolModification = payoutToolModificationUnit.getModification();

        if (payoutToolModification.isSetCreation()) {
            var contractPayoutToolCreationModification = new ContractPayoutToolCreationModification();

            contractPayoutToolCreationModification.setPayoutToolModificationType(CONTRACTPAYOUTTOOLCREATIONMODIFICATION);

            PayoutToolParams creation = payoutToolModification.getCreation();

            var swagCurrencyRef = new com.rbkmoney.swag.claim_management.model.CurrencyRef();
            swagCurrencyRef.setSymbolicCode(creation.getCurrency().getSymbolicCode());
            contractPayoutToolCreationModification.setCurrency(swagCurrencyRef);

            PayoutToolInfo toolInfo = creation.getToolInfo();
            var payoutToolInfo = payoutToolInfoConverter.convertToSwag(toolInfo);
            contractPayoutToolCreationModification.setToolInfo(payoutToolInfo);
            swagPayoutToolModificationUnit.setModification(contractPayoutToolCreationModification);

        } else if (payoutToolModification.isSetInfoModification()) {
            PayoutToolInfo infoModification = payoutToolModification.getInfoModification();
            var swagPayoutToolInfo = payoutToolInfoConverter.convertToSwag(infoModification);
            swagPayoutToolModificationUnit.setModification(
                    new ContractPayoutToolInfoModification()
                            .payoutToolInfo(swagPayoutToolInfo)
                            .payoutToolModificationType(CONTRACTPAYOUTTOOLINFOMODIFICATION)
            );
        } else {
            throw new IllegalArgumentException("Unknown PayoutTool modification type!");
        }

        return swagPayoutToolModificationUnit;
    }
}
