package com.rbkmoney.dark.api.converter.claimmanagement.party.contract;

import com.rbkmoney.damsel.claim_management.PayoutToolModification;
import com.rbkmoney.damsel.claim_management.PayoutToolModificationUnit;
import com.rbkmoney.damsel.claim_management.PayoutToolParams;
import com.rbkmoney.damsel.domain.CurrencyRef;
import com.rbkmoney.damsel.domain.PayoutToolInfo;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.rbkmoney.swag.claim_management.model.ContractModification.ContractModificationTypeEnum.PAYOUTTOOLMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.PayoutToolModification.PayoutToolModificationTypeEnum.CREATION;
import static com.rbkmoney.swag.claim_management.model.PayoutToolModification.PayoutToolModificationTypeEnum.INFOMODIFICATION;

@Component
@RequiredArgsConstructor
public class PayoutToolModificationUnitConverter
        implements DarkApiConverter<PayoutToolModificationUnit, com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit> {

    private final DarkApiConverter<PayoutToolInfo,
            com.rbkmoney.swag.claim_management.model.PayoutToolInfo> payoutToolInfoConverter;

    @Override
    public PayoutToolModificationUnit convertToThrift(
            com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit swagPayoutToolModificationUnit
    ) {
        PayoutToolModificationUnit payoutToolModificationUnit = new PayoutToolModificationUnit();
        payoutToolModificationUnit.setPayoutToolId(swagPayoutToolModificationUnit.getPayoutToolID());

        PayoutToolModification payoutToolModification = new PayoutToolModification();

        var swagPayoutToolModification = swagPayoutToolModificationUnit.getModification();
        switch (swagPayoutToolModification.getPayoutToolModificationType()) {
            case CREATION:
                var swagPayoutToolParams =
                        (com.rbkmoney.swag.claim_management.model.PayoutToolParams) swagPayoutToolModification;
                PayoutToolParams creation = new PayoutToolParams();
                creation.setCurrency(
                        new CurrencyRef().setSymbolicCode(swagPayoutToolParams.getCurrency().getSymbolicCode()));
                creation.setToolInfo(payoutToolInfoConverter.convertToThrift(swagPayoutToolParams.getToolInfo()));
                payoutToolModification.setCreation(creation);
                break;
            case INFOMODIFICATION:
                var swagPayoutToolInfo =
                        (com.rbkmoney.swag.claim_management.model.PayoutToolInfo) swagPayoutToolModification;
                payoutToolModification.setInfoModification(payoutToolInfoConverter.convertToThrift(swagPayoutToolInfo));
                break;
            default:
                throw new IllegalArgumentException("Unknown PayoutTool modification type: " +
                        swagPayoutToolModification.getPayoutToolModificationType());
        }

        payoutToolModificationUnit.setModification(payoutToolModification);
        return payoutToolModificationUnit;
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit convertToSwag(
            PayoutToolModificationUnit payoutToolModificationUnit
    ) {
        var swagPayoutToolModificationUnit =
                new com.rbkmoney.swag.claim_management.model.PayoutToolModificationUnit();
        swagPayoutToolModificationUnit.setPayoutToolID(payoutToolModificationUnit.getPayoutToolId());
        swagPayoutToolModificationUnit.setContractModificationType(PAYOUTTOOLMODIFICATIONUNIT);

        PayoutToolModification payoutToolModification = payoutToolModificationUnit.getModification();

        if (payoutToolModification.isSetCreation()) {
            var swagPayoutToolParams = new com.rbkmoney.swag.claim_management.model.PayoutToolParams();

            swagPayoutToolParams.setPayoutToolModificationType(CREATION);

            PayoutToolParams creation = payoutToolModification.getCreation();

            var swagCurrencyRef = new com.rbkmoney.swag.claim_management.model.CurrencyRef();
            swagCurrencyRef.setSymbolicCode(creation.getCurrency().getSymbolicCode());
            swagPayoutToolParams.setCurrency(swagCurrencyRef);

            PayoutToolInfo toolInfo = creation.getToolInfo();
            var payoutToolInfo = payoutToolInfoConverter.convertToSwag(toolInfo);
            payoutToolInfo.setPayoutToolModificationType(CREATION);
            swagPayoutToolParams.setToolInfo(payoutToolInfo);
            swagPayoutToolModificationUnit.setModification(swagPayoutToolParams);

        } else if (payoutToolModification.isSetInfoModification()) {
            PayoutToolInfo infoModification = payoutToolModification.getInfoModification();
            var swagPayoutToolInfo = payoutToolInfoConverter.convertToSwag(infoModification);
            swagPayoutToolInfo.setPayoutToolModificationType(INFOMODIFICATION);
            swagPayoutToolModificationUnit.setModification(swagPayoutToolInfo);
        } else {
            throw new IllegalArgumentException("Unknown PayoutTool modification type!");
        }

        return swagPayoutToolModificationUnit;
    }
}
