package com.rbkmoney.dark.api.auth;

import com.rbkmoney.dark.api.DarkApiApplication;
import com.rbkmoney.dark.api.auth.utils.JwtTokenBuilder;
import com.rbkmoney.dark.api.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DarkApiApplication.class, TestRestController.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class JwtAuthTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenBuilder jwtTokenBuilder;

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
                        .header("Authorization", "Bearer " + generateJwt())
        ).andExpect(status().isOk()).andExpect(content().string("pong"));

        mockMvc.perform(
                get("/testAdmin")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .header("Authorization", "Bearer " + generateJwt())
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
                        .header("Authorization", "Bearer " + generateJwt())
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
                                "Bearer " + jwtTokenBuilder.generateJwtWithRoles(1, 1, "RBKadmin")
                        )
        ).andExpect(status().isUnauthorized());
    }

    private String generateJwt() {
        return jwtTokenBuilder.generateJwtWithRoles("RBKadmin");
    }

}
