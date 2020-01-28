package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.domain.Blocked;
import com.rbkmoney.damsel.domain.Blocking;
import com.rbkmoney.damsel.domain.PartyStatus;
import com.rbkmoney.damsel.domain.Unblocked;
import com.rbkmoney.damsel.payment_processing.PartyManagementSrv;
import com.rbkmoney.dark.api.exceptions.client.ForbiddenException;
import com.rbkmoney.dark.api.exceptions.server.DarkApi5xxException;
import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PartyManagementServiceTest {

    @Autowired
    private PartyManagementService partyManagementService;

    @MockBean
    private PartyManagementSrv.Iface partyManagementClient;

    @MockBean
    private KeycloakService keycloakService;

    @Before
    public void setUp() throws Exception {
        when(keycloakService.getPartyId()).thenReturn(UUID.randomUUID().toString());
    }

    @Test
    public void partyManagementServiceTest() throws Exception {
        PartyStatus partyStatus = new PartyStatus();

        partyStatus.setBlocking(Blocking.unblocked(new Unblocked()));
        reset(partyManagementClient);
        when(partyManagementClient.getStatus(any(), anyString())).thenReturn(partyStatus);
        partyManagementService.checkStatus();

        partyStatus.setBlocking(Blocking.blocked(new Blocked()));
        when(partyManagementClient.getStatus(any(), anyString())).thenReturn(partyStatus);
        Assertions.assertThrows(ForbiddenException.class, () -> partyManagementService.checkStatus());

        reset(partyManagementClient);
        when(partyManagementClient.getStatus(any(), anyString())).thenThrow(TException.class);
        Assertions.assertThrows(DarkApi5xxException.class, () -> partyManagementService.checkStatus());
    }
}
