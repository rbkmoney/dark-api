package com.rbkmoney.dark.api.controller;

import com.rbkmoney.dark.api.config.AbstractKeycloakOpenIdAsWiremockConfig;
import com.rbkmoney.dark.api.service.ClaimManagementService;
import com.rbkmoney.dark.api.service.KeycloakService;
import com.rbkmoney.dark.api.service.PartyManagementService;
import com.rbkmoney.swag.claim_management.model.Claim;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClaimManagementControllerTest extends AbstractKeycloakOpenIdAsWiremockConfig {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartyManagementService partyManagementService;

    @MockBean
    private KeycloakService keycloakService;

    @MockBean
    private ClaimManagementService claimManagementService;

    @BeforeEach
    public void setUp() throws Exception {
        doNothing().when(partyManagementService).checkStatus(anyString());
        doNothing().when(partyManagementService).checkStatus();
        when(keycloakService.getPartyId()).thenReturn(randomUUID());
    }

    @Test
    public void testClaimShopLocationUrlAreConvertedRight() throws Exception {
        doReturn(new Claim().id(1L)).when(claimManagementService).createClaim(any(), any());

        mockMvc.perform(
                post("/processing/claims")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + generateReadJwt())
                        .header("X-Request-ID", randomUUID())
                        .header("X-Request-Deadline", Instant.now().plus(1, ChronoUnit.DAYS).toString())
                        .content("[\n" +
                                "  {\n" +
                                "    \"modificationType\": \"PartyModification\",\n" +
                                "    \"partyModificationType\": {\n" +
                                "      \"partyModificationType\": \"ContractorModificationUnit\",\n" +
                                "      \"id\": \"c454ea42-8598-483a-840a-141b9ab0f3e2\",\n" +
                                "      \"modification\": {\n" +
                                "        \"contractorModificationType\": \"Contractor\",\n" +
                                "        \"contractorType\": {\n" +
                                "          \"contractorType\": \"LegalEntity\",\n" +
                                "          \"legalEntityType\": {\n" +
                                "            \"legalEntityType\": \"RussianLegalEntity\",\n" +
                                "            \"actualAddress\": \"обл Московская, г Ивантеевка, ул Новая Слобода, дом 9\",\n" +
                                "            \"russianBankAccount\": {\n" +
                                "              \"payoutToolModificationType\": \"RussianBankAccount\",\n" +
                                "              \"payoutToolType\": \"RussianBankAccount\",\n" +
                                "              \"account\": \"99999999999999999999\",\n" +
                                "              \"bankBik\": \"044525974\",\n" +
                                "              \"bankName\": \"АО «Тинькофф Банк»\",\n" +
                                "              \"bankPostAccount\": \"30101810145250000974\"\n" +
                                "            },\n" +
                                "            \"inn\": \"5016003824\",\n" +
                                "            \"postAddress\": \"обл Московская, г Ивантеевка, ул Новая Слобода, дом 9\",\n" +
                                "            \"registeredName\": \"ООО \\\"Батя\\\"\",\n" +
                                "            \"registeredNumber\": \"\",\n" +
                                "            \"representativeDocument\": \"1111 111111\",\n" +
                                "            \"representativeFullName\": \"Тархов Юрий Львович\",\n" +
                                "            \"representativePosition\": \"Директор\"\n" +
                                "          }\n" +
                                "        }\n" +
                                "      }\n" +
                                "    }\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"modificationType\": \"PartyModification\",\n" +
                                "    \"partyModificationType\": {\n" +
                                "      \"partyModificationType\": \"ContractModificationUnit\",\n" +
                                "      \"id\": \"3dc3dc36-32ba-4e15-859a-a5c4434c5d92\",\n" +
                                "      \"modification\": {\n" +
                                "        \"contractModificationType\": \"ContractParams\",\n" +
                                "        \"contractorID\": \"c454ea42-8598-483a-840a-141b9ab0f3e2\"\n" +
                                "      }\n" +
                                "    }\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"modificationType\": \"PartyModification\",\n" +
                                "    \"partyModificationType\": {\n" +
                                "      \"partyModificationType\": \"ContractModificationUnit\",\n" +
                                "      \"id\": \"3dc3dc36-32ba-4e15-859a-a5c4434c5d92\",\n" +
                                "      \"modification\": {\n" +
                                "        \"contractModificationType\": \"PayoutToolModificationUnit\",\n" +
                                "        \"payoutToolID\": \"a5648077-a4c7-4e35-95ae-380d171fbc0f\",\n" +
                                "        \"modification\": {\n" +
                                "          \"payoutToolModificationType\": \"PayoutToolParams\",\n" +
                                "          \"currency\": {\n" +
                                "            \"symbolicCode\": \"RUB\"\n" +
                                "          },\n" +
                                "          \"toolInfo\": {\n" +
                                "            \"payoutToolType\": \"RussianBankAccount\",\n" +
                                "            \"account\": \"000000000000000000\",\n" +
                                "            \"bankName\": \"ФИЛИАЛ \\\"РОСТОВСКИЙ\\\" АО \\\"АЛЬФА-БАНК\\\"\",\n" +
                                "            \"bankPostAccount\": \"30101810500000000207\",\n" +
                                "            \"bankBik\": \"046015207\"\n" +
                                "          }\n" +
                                "        }\n" +
                                "      }\n" +
                                "    }\n" +
                                "  },\n" +
                                "  {\n" +
                                "    \"modificationType\": \"PartyModification\",\n" +
                                "    \"partyModificationType\": {\n" +
                                "      \"partyModificationType\": \"ShopModificationUnit\",\n" +
                                "      \"id\": \"80e5b7bd-f57e-482b-a1f0-4065fc7eb584\",\n" +
                                "      \"modification\": {\n" +
                                "        \"shopModificationType\": \"ShopCreationModification\",\n" +
                                "        \"category\": {\n" +
                                "          \"id\": 1\n" +
                                "        },\n" +
                                "        \"location\": {\n" +
                                "          \"locationType\": \"ShopLocationUrl\",\n" +
                                "          \"url\": \"oaofoaofaofoaof\"\n" +
                                "        },\n" +
                                "        \"details\": {\n" +
                                "          \"name\": \"kkfkakfkkafkkafkakfakkfa\"\n" +
                                "        },\n" +
                                "        \"contractID\": \"3dc3dc36-32ba-4e15-859a-a5c4434c5d92\",\n" +
                                "        \"payoutToolID\": \"a5648077-a4c7-4e35-95ae-380d171fbc0f\"\n" +
                                "      }\n" +
                                "    }\n" +
                                "  }\n" +
                                "]")
        ).andExpect(status().isOk());

    }

    private String randomUUID() {
        return UUID.randomUUID().toString();
    }
}
