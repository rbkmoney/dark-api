package com.rbkmoney.dark.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbkmoney.damsel.merch_stat.BadToken;
import com.rbkmoney.damsel.messages.ConversationsNotFound;
import com.rbkmoney.damsel.questionary_proxy_aggr.DaDataNotFound;
import com.rbkmoney.dark.api.config.AbstractKeycloakOpenIdAsWiremockConfig;
import com.rbkmoney.dark.api.service.*;
import com.rbkmoney.file.storage.FileNotFound;
import com.rbkmoney.questionary.manage.QuestionaryNotFound;
import com.rbkmoney.swag.questionary_aggr_proxy.model.AddressQuery;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataParams;
import com.rbkmoney.swag.questionary_aggr_proxy.model.DaDataRequest;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.apache.thrift.TException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ErrorControllerTest extends AbstractKeycloakOpenIdAsWiremockConfig {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PartyManagementService partyManagementService;

    @MockBean
    private KeycloakService keycloakService;

    @MockBean
    private FileStorageService fileStorageService;

    @MockBean
    private ConversationService conversationService;

    @MockBean
    private QuestionaryAggrProxyService questionaryAggrProxyService;

    @MockBean
    private QuestionaryService questionaryService;

    @MockBean
    private MagistaService magistaService;

    @BeforeEach
    public void setUp() throws Exception {
        doNothing().when(partyManagementService).checkStatus(anyString());
        doNothing().when(partyManagementService).checkStatus();
        when(keycloakService.getPartyId()).thenReturn(string());
    }

    @Test
    public void testThenConversationClientThrowingExceptions() throws Exception {
        when(conversationService.getConversation(anyList(), any())).thenThrow(ConversationsNotFound.class);

        mockMvc.perform(
                get("/conversation", string())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("X-Request-ID", string())
                        .header("Authorization", "Bearer " + generateReadJwt())
                        .param("conversationId", "asd", "asda")
                        .param("conversationStatus", "ACTUAL")
        ).andExpect(status().isNotFound());

        reset(conversationService);
        when(conversationService.getConversation(anyList(), any())).thenThrow(TException.class);

        mockMvc.perform(
                get("/conversation", string())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("X-Request-ID", string())
                        .header("Authorization", "Bearer " + generateReadJwt())
                        .param("conversationId", "asd", "asda")
                        .param("conversationStatus", "ACTUAL")
        ).andExpect(status().isInternalServerError());
    }

    @Test
    public void testThenFileStorageClientThrowingExceptions() throws Exception {
        when(fileStorageService.getFileInfo(anyString())).thenThrow(FileNotFound.class);

        mockMvc.perform(
                get("/files/{fileID}/info", string())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("X-Request-ID", string())
                        .header("Authorization", "Bearer " + generateReadJwt())
        ).andExpect(status().isNotFound());

        reset(fileStorageService);
        when(fileStorageService.getFileInfo(anyString())).thenThrow(TException.class);

        mockMvc.perform(
                get("/files/{fileID}/info", string())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("X-Request-ID", string())
                        .header("Authorization", "Bearer " + generateReadJwt())
        ).andExpect(status().isInternalServerError());
    }

    @Test
    public void testThenQuestionaryAggrProxyClientThrowingExceptions() throws Exception {
        AddressQuery addressQuery = EnhancedRandom.random(AddressQuery.class);
        addressQuery.setDaDataRequestType(DaDataRequest.DaDataRequestTypeEnum.ADDRESSQUERY);
        DaDataParams daDataParams = new DaDataParams();
        daDataParams.setRequest(addressQuery);

        doThrow(DaDataNotFound.class).when(questionaryAggrProxyService).requestDaData(any());

        mockMvc.perform(
                post("/proxy/dadata")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + generateWriteJwt())
                        .header("X-Request-ID", string())
                        .content(objectMapper.writeValueAsBytes(daDataParams))
        ).andExpect(status().isNotFound());

        reset(questionaryAggrProxyService);
        doThrow(TException.class).when(questionaryAggrProxyService).requestDaData(any());

        mockMvc.perform(
                post("/proxy/dadata")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + generateWriteJwt())
                        .header("X-Request-ID", string())
                        .content(objectMapper.writeValueAsBytes(daDataParams))
        ).andExpect(status().isInternalServerError());

        reset(questionaryAggrProxyService);
    }

    @Test
    public void testThenQuestionaryClientThrowingExceptions() throws Exception {
        doThrow(QuestionaryNotFound.class).when(questionaryService)
                .getQuestionary(anyString(), anyString(), anyString());

        mockMvc.perform(
                get("/questionary/{questionaryId}", string())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + generateWriteJwt())
                        .header("X-Request-ID", string())
                        .param("version", string())
        ).andExpect(status().isNotFound());

        reset(questionaryService);
        doThrow(TException.class).when(questionaryService).getQuestionary(anyString(), anyString(), anyString());

        mockMvc.perform(
                get("/questionary/{questionaryId}", string())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + generateWriteJwt())
                        .header("X-Request-ID", string())
                        .param("version", string())
        ).andExpect(status().isInternalServerError());

        reset(questionaryService);
    }

    @Test
    public void testThenSearchClientThrowingExceptions() throws Exception {
        doThrow(BadToken.class).when(magistaService)
                .getRefundsByQuery(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());

        mockMvc.perform(
                get("/search/refunds/{shopID}", string())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + generateInvoicesReadJwt())
                        .header("X-Request-ID", string())
                        .header("X-Request-Deadline", Instant.now().plus(1, ChronoUnit.DAYS).toString())
                        .param("fromTime", LocalDateTime.MIN.atOffset(ZoneOffset.MAX).toString())
                        .param("toTime", LocalDateTime.MAX.atOffset(ZoneOffset.MAX).toString())
                        .param("limit", String.valueOf(123))
        ).andExpect(status().isBadRequest());

        reset(magistaService);
        doThrow(TException.class).when(magistaService)
                .getRefundsByQuery(any(), any(), any(), any(), any(), any(), any(), any(), any(), any());

        mockMvc.perform(
                get("/search/refunds/{shopID}", string())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .header("Authorization", "Bearer " + generateInvoicesReadJwt())
                        .header("X-Request-ID", string())
                        .header("X-Request-Deadline", Instant.now().plus(1, ChronoUnit.DAYS).toString())
                        .param("fromTime", LocalDateTime.MIN.atOffset(ZoneOffset.MAX).toString())
                        .param("toTime", LocalDateTime.MAX.atOffset(ZoneOffset.MAX).toString())
                        .param("limit", String.valueOf(123))
        ).andExpect(status().isInternalServerError());
    }

    private String string() {
        return UUID.randomUUID().toString();
    }
}
