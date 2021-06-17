package com.rbkmoney.dark.api.claimmanagement.converter.claim;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.dark.api.converter.claimmanagement.claim.*;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;

import java.io.IOException;

import static com.rbkmoney.swag.claim_management.model.ClaimModificationType.ClaimModificationTypeEnum.*;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;
import static com.rbkmoney.swag.claim_management.model.StatusModificationUnit.StatusEnum.DENIED;
import static com.rbkmoney.swag.claim_management.model.StatusModificationUnit.StatusEnum.REVOKED;
import static org.junit.Assert.assertEquals;

public class ClaimConvertersTest {

    @Test
    public void claimStatusConverterTest() throws IOException {
        ClaimStatusModificationConverter statusConverter = new ClaimStatusModificationConverter();
        var swagStatus = EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.StatusModificationUnit.class);
        swagStatus.setClaimModificationType(STATUSMODIFICATIONUNIT);
        if (swagStatus.getStatus() != DENIED && swagStatus.getStatus() != REVOKED) {
            swagStatus.setReason(null);
        }

        ClaimStatus thriftClaimStatusTmp = statusConverter.convertToThrift(swagStatus);
        var resultSwagStatus = statusConverter.convertToSwag(thriftClaimStatusTmp);
        assertEquals("Swag objects 'StatusModificationUnit' not equals", swagStatus, resultSwagStatus);

        ClaimStatus thriftClaimStatus = new ClaimStatus();
        thriftClaimStatus =
                new MockTBaseProcessor(MockMode.ALL).process(thriftClaimStatus, new TBaseHandler<>(ClaimStatus.class));
        var statusModificationUnitTmp = statusConverter.convertToSwag(thriftClaimStatus);
        ClaimStatus resultClaimStatus = statusConverter.convertToThrift(statusModificationUnitTmp);

        assertEquals("Thrift objects 'ClaimStatus' not equals", thriftClaimStatus, resultClaimStatus);
    }

    @Test
    public void claimStatusModificationUnitConverterTest() throws IOException {
        ClaimStatusModificationUnitConverter converter =
                new ClaimStatusModificationUnitConverter(new ClaimStatusModificationConverter());
        var swagStatusModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.StatusModificationUnit.class);
        swagStatusModificationUnit.setClaimModificationType(STATUSMODIFICATIONUNIT);
        if (swagStatusModificationUnit.getStatus() != DENIED && swagStatusModificationUnit.getStatus() != REVOKED) {
            swagStatusModificationUnit.setReason(null);
        }

        StatusModificationUnit statusModificationUnitTmp = converter.convertToThrift(swagStatusModificationUnit);
        var swagStatusModificationUnitResult = converter.convertToSwag(statusModificationUnitTmp);
        assertEquals("Swag objects 'StatusModificationUnit' not equals",
                swagStatusModificationUnit, swagStatusModificationUnitResult);

        StatusModificationUnit randomStatusModificationUnit = new StatusModificationUnit();
        randomStatusModificationUnit = new MockTBaseProcessor(MockMode.ALL)
                .process(randomStatusModificationUnit, new TBaseHandler<>(StatusModificationUnit.class));
        var tmpSwagstatusModification = converter.convertToSwag(randomStatusModificationUnit);
        StatusModificationUnit resultStatusModificationUnit = converter.convertToThrift(tmpSwagstatusModification);
        assertEquals("Thrift objects 'StatusModificationUnit' not equals",
                randomStatusModificationUnit, resultStatusModificationUnit);
    }

    @Test
    public void claimFileModificationUnitConverterTest() throws IOException {
        ClaimFileModificationUnitConverter converter = new ClaimFileModificationUnitConverter();
        var swagFileModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.FileModificationUnit.class);
        swagFileModificationUnit.setClaimModificationType(FILEMODIFICATIONUNIT);

        var resultSwagFileModificationUnit = converter.convertToSwag(
                converter.convertToThrift(swagFileModificationUnit)
        );
        assertEquals("Swag objects 'FileModificationUnit' not equals",
                swagFileModificationUnit, resultSwagFileModificationUnit);

        FileModificationUnit thriftFileModificationUnit = new FileModificationUnit();
        thriftFileModificationUnit = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftFileModificationUnit, new TBaseHandler<>(FileModificationUnit.class));
        FileModificationUnit resultThriftFileModificationUnit = converter.convertToThrift(
                converter.convertToSwag(thriftFileModificationUnit)
        );
        assertEquals("Thrift objects 'FileModificationUnit' not equals",
                thriftFileModificationUnit, resultThriftFileModificationUnit);
    }

    @Test
    public void claimDocumentModificationConverterTest() throws IOException {
        ClaimDocumentModificationConverter converter = new ClaimDocumentModificationConverter();
        var swagDocumentModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.DocumentModificationUnit.class);
        swagDocumentModificationUnit.setClaimModificationType(DOCUMENTMODIFICATIONUNIT);

        var resultDocumentModificationUnit = converter.convertToSwag(
                converter.convertToThrift(swagDocumentModificationUnit)
        );
        assertEquals("Swag objects 'DocumentModificationUnit' not equals",
                swagDocumentModificationUnit, resultDocumentModificationUnit);

        DocumentModificationUnit thriftDocumentModificationUnit = new DocumentModificationUnit();
        thriftDocumentModificationUnit = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftDocumentModificationUnit, new TBaseHandler<>(DocumentModificationUnit.class));
        thriftDocumentModificationUnit.setType(null);
        DocumentModificationUnit resultThriftDocumentModificationUnit = converter.convertToThrift(
                converter.convertToSwag(thriftDocumentModificationUnit)
        );
        assertEquals("Thrift objects 'DocumentModificationUnit' not equals",
                thriftDocumentModificationUnit, resultThriftDocumentModificationUnit);
    }

    @Test
    public void claimCommentModificationUnitConverterTest() throws IOException {
        ClaimCommentModificationUnitConverter converter = new ClaimCommentModificationUnitConverter();
        var swagCommentModificationUnit =
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.CommentModificationUnit.class);
        swagCommentModificationUnit.setClaimModificationType(COMMENTMODIFICATIONUNIT);

        var resultCommentModificationUnit = converter.convertToSwag(
                converter.convertToThrift(swagCommentModificationUnit)
        );
        assertEquals("Swag objects 'CommentModificationUnit' not equals",
                swagCommentModificationUnit, resultCommentModificationUnit);

        CommentModificationUnit thriftCommentModificationUnit = new CommentModificationUnit();
        thriftCommentModificationUnit = new MockTBaseProcessor(MockMode.ALL)
                .process(thriftCommentModificationUnit, new TBaseHandler<>(CommentModificationUnit.class));
        CommentModificationUnit resultThriftCommentModificationUnit = converter.convertToThrift(
                converter.convertToSwag(thriftCommentModificationUnit)
        );
        assertEquals("Thrift objects 'DocumentModificationUnit' not equals",
                thriftCommentModificationUnit, resultThriftCommentModificationUnit);
    }

    @Test
    public void claimModificationConverterTest() throws IOException {

        var swagStatusModUnit = new com.rbkmoney.swag.claim_management.model.StatusModificationUnit();
        swagStatusModUnit.setClaimModificationType(STATUSMODIFICATIONUNIT);
        swagStatusModUnit.setReason("testReason");
        swagStatusModUnit.setStatus(DENIED);

        swagStatusModUnit.setStatusModification(
                EnhancedRandom.random(com.rbkmoney.swag.claim_management.model.StatusModification.class));

        var swagClaimModification = new com.rbkmoney.swag.claim_management.model.ClaimModification();
        swagClaimModification.setClaimModificationType(swagStatusModUnit);
        swagClaimModification.setModificationType(CLAIMMODIFICATION);

        ClaimModificationConverter converter = new ClaimModificationConverter(
                new ClaimDocumentModificationConverter(),
                new ClaimCommentModificationUnitConverter(),
                new ClaimStatusModificationUnitConverter(new ClaimStatusModificationConverter()),
                new ClaimFileModificationUnitConverter(),
                new ClaimExternalInfoModificationUnitConverter()
        );
        var resultClaimModification = converter.convertToSwag(converter.convertToThrift(swagClaimModification));
        assertEquals("Swag objects 'ClaimModification' not equals", swagClaimModification, resultClaimModification);

        Modification thriftModification = new Modification();
        ClaimModification claimModification = new ClaimModification();
        claimModification = new MockTBaseProcessor(MockMode.ALL)
                .process(claimModification, new TBaseHandler<>(ClaimModification.class));

        if (claimModification.isSetDocumentModification()) {
            claimModification.getDocumentModification().setType(null);
        }
        thriftModification.setClaimModification(claimModification);
        Modification resultThriftModification = converter.convertToThrift(
                converter.convertToSwag(thriftModification)
        );
        assertEquals("Thrift objects 'Modification' not equals", thriftModification, resultThriftModification);
    }

}
