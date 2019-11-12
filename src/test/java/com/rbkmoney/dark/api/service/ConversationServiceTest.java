package com.rbkmoney.dark.api.service;

import com.rbkmoney.damsel.messages.GetConversationResponse;
import com.rbkmoney.damsel.messages.MessageServiceSrv;
import com.rbkmoney.swag.messages.model.*;
import org.apache.thrift.TException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConversationServiceTest {

    @MockBean
    private MessageServiceSrv.Iface messageService;

    @Autowired
    private ConversationService conversationService;

    @Test
    public void saveConversationConverterTest() throws TException {
        ArgumentCaptor<List<com.rbkmoney.damsel.messages.Conversation>> conversationCaptor =
                ArgumentCaptor.forClass((Class) List.class);
        ArgumentCaptor<List<com.rbkmoney.damsel.messages.User>> userCaptor =
                ArgumentCaptor.forClass((Class) List.class);
        SaveConversationParams saveConversationParams = saveConversationParams();
        conversationService.saveConversation(saveConversationParams);
        verify(messageService).saveConversations(conversationCaptor.capture(), userCaptor.capture());
        List<com.rbkmoney.damsel.messages.Conversation> conversationList = conversationCaptor.getValue();
        List<com.rbkmoney.damsel.messages.User> userList = userCaptor.getValue();

        com.rbkmoney.damsel.messages.Conversation conversation = conversationList.get(0);
        Conversation expectedConversation = saveConversationParams.getConversations().get(0);

        com.rbkmoney.damsel.messages.Message message = conversation.getMessages().get(0);
        Message expectedMessage = expectedConversation.getMessages().get(0);

        Assert.assertEquals(expectedConversation.getConversationId(), conversation.getConversationId());
        Assert.assertEquals(expectedConversation.getStatus().toString().toLowerCase(), conversation.getStatus().toString().toLowerCase());
        Assert.assertEquals(expectedMessage.getUserId(), message.getUserId());
        Assert.assertEquals(expectedMessage.getMessageId(), message.getMessageId());
        Assert.assertEquals(expectedMessage.getText(), message.getText());
        Assert.assertEquals(expectedMessage.getTimestamp(), message.getTimestamp());

        com.rbkmoney.damsel.messages.User user = userList.get(0);
        User expectedUser = saveConversationParams.getUsers().get(0);

        Assert.assertEquals(expectedUser.getUserId(), user.getUserId());
        Assert.assertEquals(expectedUser.getEmail(), user.getEmail());
        Assert.assertEquals(expectedUser.getFullName(), user.getFullname());
    }

    @Test
    public void getConversationConverterTest() throws TException {
        ArgumentCaptor<List<String>> listCaptor =
                ArgumentCaptor.forClass((Class) List.class);
        ArgumentCaptor<com.rbkmoney.damsel.messages.ConversationFilter> conversationFilterCaptor =
                ArgumentCaptor.forClass((Class) List.class);
        List<String> conversationIds = Collections.singletonList("4254364");
        ConversationFilter swagConversationFilter = new ConversationFilter().conversationStatus(ConversationStatus.ACTUAL);
        GetConversationResponse getConversationResponse = conversationResponse();

        when(messageService.getConversations(anyList(), any(com.rbkmoney.damsel.messages.ConversationFilter.class)))
                .thenReturn(getConversationResponse);
        ConversationResponse conversationResponse = conversationService.getConversation(conversationIds, swagConversationFilter);
        verify(messageService).getConversations(listCaptor.capture(), conversationFilterCaptor.capture());

        com.rbkmoney.damsel.messages.ConversationFilter thriftConversationFilter = conversationFilterCaptor.getValue();

        Assert.assertEquals(thriftConversationFilter.getConversationStatus().toString().toLowerCase(),
                swagConversationFilter.getConversationStatus().toString().toLowerCase());

        com.rbkmoney.damsel.messages.Conversation expectedConversation = getConversationResponse.getConversations().get(0);
        Conversation conversation = conversationResponse.getConversations().get(0);

        Assert.assertEquals(expectedConversation.getConversationId(), conversation.getConversationId());
        Assert.assertEquals(expectedConversation.getStatus().toString().toLowerCase(), conversation.getStatus().toString().toLowerCase());

        com.rbkmoney.damsel.messages.Message expectedMessage = expectedConversation.getMessages().get(0);
        Message message = conversation.getMessages().get(0);

        Assert.assertEquals(expectedMessage.getMessageId(), message.getMessageId());
        Assert.assertEquals(expectedMessage.getText(), message.getText());
        Assert.assertEquals(expectedMessage.getTimestamp(), message.getTimestamp());
        Assert.assertEquals(expectedMessage.getUserId(), message.getUserId());

        com.rbkmoney.damsel.messages.User expectedUser = getConversationResponse.getUsers().get("64");
        ConversationResponseUsers user = conversationResponse.getUsers().get("64");

        Assert.assertEquals(expectedUser.getUserId(), user.getUserId());
        Assert.assertEquals(expectedUser.getEmail(), user.getUser().getEmail());
        Assert.assertEquals(expectedUser.getFullname(), user.getUser().getFullName());
    }

    private SaveConversationParams saveConversationParams() {
        SaveConversationParams saveConversationParams = new SaveConversationParams();

        Conversation conversation = new Conversation();
        conversation.setStatus(ConversationStatus.ACTUAL);
        conversation.setConversationId("4254364");
        Message message = new Message();
        message.setMessageId("53454435");
        message.setText("Test");
        message.setTimestamp("54316475474");
        message.setUserId("32");
        conversation.setMessages(Collections.singletonList(message));
        saveConversationParams.setConversations(Collections.singletonList(conversation));

        User user = new User();
        user.setEmail("test@mail.com");
        user.setFullName("Test FullName");
        user.setUserId("64");
        saveConversationParams.setUsers(Collections.singletonList(user));

        return saveConversationParams;
    }

    private GetConversationResponse conversationResponse() {
        GetConversationResponse conversationResponse = new GetConversationResponse();
        com.rbkmoney.damsel.messages.Conversation conversation = buildConversation();
        conversationResponse.setConversations(Collections.singletonList(conversation));
        com.rbkmoney.damsel.messages.User user = buildUser();
        conversationResponse.setUsers(Map.of("64", user));

        return conversationResponse;
    }

    private com.rbkmoney.damsel.messages.Conversation buildConversation() {
        com.rbkmoney.damsel.messages.Conversation conversation = new com.rbkmoney.damsel.messages.Conversation();
        conversation.setStatus(com.rbkmoney.damsel.messages.ConversationStatus.OUTDATED);
        conversation.setConversationId("45");
        com.rbkmoney.damsel.messages.Message message = buildMessage();
        conversation.setMessages(Collections.singletonList(message));

        return conversation;
    }

    private com.rbkmoney.damsel.messages.Message buildMessage() {
        com.rbkmoney.damsel.messages.Message message = new com.rbkmoney.damsel.messages.Message();
        message.setMessageId("1");
        message.setUserId("64");
        message.setTimestamp("536432547");
        message.setText("Test text");

        return message;
    }

    private com.rbkmoney.damsel.messages.User buildUser() {
        com.rbkmoney.damsel.messages.User user = new com.rbkmoney.damsel.messages.User();
        user.setUserId("64");
        user.setEmail("test@mail.com");
        user.setFullname("Test full name");

        return user;
    }

}
