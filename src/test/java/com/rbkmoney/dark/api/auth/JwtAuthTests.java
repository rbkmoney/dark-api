package com.rbkmoney.dark.api.auth;

import com.rbkmoney.dark.api.config.AbstractKeycloakOpenIdAsWiremockConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class JwtAuthTests extends AbstractKeycloakOpenIdAsWiremockConfig {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCors() throws Exception {
        mockMvc.perform(options("/ping")
                .header("Origin", "*")
                .header("Access-Control-Request-Method", "GET")
                .header("Access-Control-Request-Headers", "authorization, content-type"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE"))
                .andExpect(header().string("Access-Control-Allow-Headers", "authorization, content-type"));

        mockMvc.perform(options("/ping")
                .header("Origin", "*")
                .header("Access-Control-Request-Method", "PUT")
                .header("Access-Control-Request-Headers", "authorization, content-type")
        ).andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE"))
                .andExpect(header().string("Access-Control-Allow-Headers", "authorization, content-type"));

        mockMvc.perform(options("/ping")
                .header("Origin", "*")
                .header("Access-Control-Request-Method", "DELETE")
                .header("Access-Control-Request-Headers", "authorization, content-type")
        ).andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE"))
                .andExpect(header().string("Access-Control-Allow-Headers", "authorization, content-type"));
    }

    @Test
    public void testCorrectAuth() throws Exception {
        mockMvc.perform(
                get("/ping")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", "Bearer " + generateRbkAdminJwt())
        ).andExpect(status().isOk()).andExpect(content().string("pong"));

        mockMvc.perform(
                get("/testAdmin")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", "Bearer " + generateRbkAdminJwt())
        ).andExpect(status().isOk()).andExpect(content().string("testAdmin!"));
    }

    @Test
    public void testIncorrectAuth() throws Exception {
        mockMvc.perform(
                get("/testAdmin")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().isUnauthorized());

        mockMvc.perform(
                get("/testAdmin")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", "WRONG JWT!")
        ).andExpect(status().isUnauthorized());

        mockMvc.perform(
                get("/testManager")
                        .header("Authorization", "Bearer " + generateRbkAdminJwt())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(status().isForbidden());
    }

    @Test
    public void testOldIat() throws Exception {
        mockMvc.perform(
                get("/ping")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header(
                                "Authorization",
                                "Bearer " + generateJwt(1, 1, "RBKadmin")
                        )
        ).andExpect(status().isUnauthorized());
    }
}
