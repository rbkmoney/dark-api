package com.rbkmoney.dark.api.converter.claimmanagement.party.contractor;

import com.rbkmoney.damsel.claim_management.ContractorIdentityDocumentsModification;
import com.rbkmoney.dark.api.converter.DarkApiConverter;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.stream.Collectors;

import static com.rbkmoney.swag.claim_management.model.ContractorModification.ContractorModificationTypeEnum.CONTRACTORIDENTITYDOCUMENTSMODIFICATION;

@Component
public class ContractorIdentityDocumentsModificationConvertor
        implements DarkApiConverter<ContractorIdentityDocumentsModification, com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification> {

    @Override
    public ContractorIdentityDocumentsModification convertToThrift(
            com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification swagDocumentsModification
    ) {
        List<ByteBuffer> byteBufferList =
                swagDocumentsModification.getIdentityDocuments().stream()
                        .map(ByteBuffer::wrap)
                        .collect(Collectors.toList());

        return new ContractorIdentityDocumentsModification().setIdentityDocuments(byteBufferList);
    }

    @Override
    public com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification convertToSwag(
            ContractorIdentityDocumentsModification identityDocumentsModification
    ) {
        var swagContractorIdentityDocumentsModification =
                new com.rbkmoney.swag.claim_management.model.ContractorIdentityDocumentsModification();
        swagContractorIdentityDocumentsModification.setContractorModificationType(CONTRACTORIDENTITYDOCUMENTSMODIFICATION);
        List<byte[]> identityDocuments = identityDocumentsModification.getIdentityDocuments().stream()
                .map(ByteBuffer::array)
                .collect(Collectors.toList());
        swagContractorIdentityDocumentsModification.setIdentityDocuments(identityDocuments);

        return swagContractorIdentityDocumentsModification;
    }

}
