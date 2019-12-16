package com.rbkmoney.dark.api.claimmanagement;

import com.rbkmoney.damsel.claim_management.*;
import com.rbkmoney.damsel.msgpack.Value;
import com.rbkmoney.dark.api.service.ClaimManagementService;
import com.rbkmoney.swag.claim_management.model.ClaimChangeset;
import com.rbkmoney.swag.claim_management.model.ClaimModificationType;
import com.rbkmoney.swag.claim_management.model.DocumentModificationUnit;
import com.rbkmoney.swag.claim_management.model.InlineResponse200;
import org.apache.thrift.TException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rbkmoney.swag.claim_management.model.ClaimModificationType.ClaimModificationTypeEnum.DOCUMENTMODIFICATIONUNIT;
import static com.rbkmoney.swag.claim_management.model.DocumentModification.DocumentModificationTypeEnum.DOCUMENTCREATED;
import static com.rbkmoney.swag.claim_management.model.Modification.ModificationTypeEnum.CLAIMMODIFICATION;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClaimManagementServiceTest {

    @MockBean
    private ClaimManagementSrv.Iface claimManagementClient;

    @Autowired
    private ClaimManagementService claimManagementService;

    @Test
    public void test() throws TException {
        when(claimManagementClient.createClaim(any(String.class), any(ArrayList.class))).thenReturn(getTestCreateClaim());
        when(claimManagementClient.getClaim(any(String.class), any(Long.class))).thenReturn(getTestClaimById());
        when(claimManagementClient.searchClaims(any(ClaimSearchQuery.class))).thenReturn(
                new ClaimSearchResponse(getTestSearchClaim())
                        .setContinuationToken("continuation_token")
        );

        var requestClaim = claimManagementService.createClaim("test_request_1", getModifications());
        assertEquals("Swag objects 'Claim' (create) not equals",
                getTestAnswerCreateClaim().toString(), requestClaim.toString());

        var claimById = claimManagementService.getClaimById("test_request_2", 1L);
        assertEquals("Swag objects 'Claim' (by id) not equals",
                getTestAnswerCreateClaim().toString(), claimById.toString());

        InlineResponse200 response =
                claimManagementService.searchClaims("id", null, 1, "token", new ArrayList<>());
        assertEquals("Swag objects 'Claim' (search) not equals",
                getTestAnswerCreateClaim().toString(), response.getResult().get(0).toString());
        assertEquals("continuation_token", response.getContinuationToken());
    }

    public static com.rbkmoney.swag.claim_management.model.Claim getTestAnswerCreateClaim() {
        var testClaim = new com.rbkmoney.swag.claim_management.model.Claim();
        testClaim.setId(1L);
        testClaim.setStatus("accepted");
        testClaim.setCreatedAt(OffsetDateTime.parse("2019-08-21T12:09:32.449571+03:00"));
        testClaim.setRevision(1);
        testClaim.setUpdatedAt(OffsetDateTime.parse("2019-08-21T12:09:32.449571+03:00"));
        testClaim.setChangeset(getChangeset());
        Map<String, Value> claimMetadata = new HashMap<>();
        claimMetadata.put("test_key", new Value());
        testClaim.setMetadata(claimMetadata);
        return testClaim;
    }

    public static ClaimChangeset getChangeset() {
        ClaimChangeset changeset = new ClaimChangeset();
        var modificationUnit = new com.rbkmoney.swag.claim_management.model.ModificationUnit();
        var swagClaimModification = new com.rbkmoney.swag.claim_management.model.ClaimModification();
        swagClaimModification.setModificationType(CLAIMMODIFICATION);
        modificationUnit.setModificationID(1L);
        modificationUnit.setCreatedAt(OffsetDateTime.parse("2019-08-21T12:09:32.449571+03:00"));
        var documentModificationUnit = new com.rbkmoney.swag.claim_management.model.DocumentModificationUnit();
        documentModificationUnit.setClaimModificationType(DOCUMENTMODIFICATIONUNIT);
        documentModificationUnit.setDocumentId("id_1");
        var documentModification = new com.rbkmoney.swag.claim_management.model.DocumentModification();
        documentModification.setDocumentModificationType(DOCUMENTCREATED);
        documentModificationUnit.setDocumentModification(documentModification);
        swagClaimModification.setClaimModificationType(documentModificationUnit);
        modificationUnit.setModification(swagClaimModification);
        modificationUnit.setUserInfo(
                new com.rbkmoney.swag.claim_management.model.UserInfo()
                        .userId("ID")
                        .username("username")
                        .email("email")
                        .userType(com.rbkmoney.swag.claim_management.model.UserInfo.UserTypeEnum.INTERNAL_USER)
        );

        changeset.add(modificationUnit);
        return changeset;
    }

    public static List<com.rbkmoney.swag.claim_management.model.Modification> getModifications() {
        var documentModification = new com.rbkmoney.swag.claim_management.model.DocumentModification();
        documentModification.setDocumentModificationType(DOCUMENTCREATED);

        return List.of(
                new com.rbkmoney.swag.claim_management.model.ClaimModification()
                        .claimModificationType(
                                new DocumentModificationUnit()
                                        .documentId("document_id")
                                        .documentModification(documentModification)
                                        .claimModificationType(DOCUMENTMODIFICATIONUNIT)
                        )
                        .modificationType(CLAIMMODIFICATION)

        );
    }

    private static Claim getTestCreateClaim() {
        Claim claim = new Claim();
        claim.setCreatedAt("2019-08-21T12:09:32.449571+03:00");
        claim.setId(1L);
        claim.setRevision(1);
        claim.setStatus(ClaimStatus.accepted(new ClaimAccepted()));
        Map<String, Value> claimMetadata = new HashMap<>();
        claimMetadata.put("test_key", new Value());
        claim.setMetadata(claimMetadata);
        claim.setUpdatedAt("2019-08-21T12:09:32.449571+03:00");
        List<com.rbkmoney.damsel.claim_management.ModificationUnit> changeset = new ArrayList<>();
        DocumentModification documentModification = new DocumentModification();
        documentModification.setCreation(new DocumentCreated());

        ClaimModification claimModification = new ClaimModification();
        claimModification.setDocumentModification(
                new com.rbkmoney.damsel.claim_management.DocumentModificationUnit()
                        .setId("id_1")
                        .setModification(documentModification)
        );
        com.rbkmoney.damsel.claim_management.Modification modification = new com.rbkmoney.damsel.claim_management.Modification();
        modification.setClaimModification(claimModification);
        var thriftModificationUnit = new com.rbkmoney.damsel.claim_management.ModificationUnit();
        thriftModificationUnit.setCreatedAt("2019-08-21T12:09:32.449571+03:00");
        thriftModificationUnit.setModificationId(1L);
        thriftModificationUnit.setUserInfo(
                new UserInfo()
                        .setId("ID")
                        .setUsername("username")
                        .setEmail("email")
                        .setType(UserType.internal_user(new InternalUser()))
        );
        thriftModificationUnit.setModification(modification);
        changeset.add(thriftModificationUnit);
        claim.setChangeset(changeset);
        return claim;
    }

    private static Claim getTestClaimById() {
        return getTestCreateClaim();
    }

    private static List<Claim> getTestSearchClaim() {
        List<Claim> claimList = new ArrayList<>();
        claimList.add(getTestCreateClaim());
        return claimList;
    }

}
