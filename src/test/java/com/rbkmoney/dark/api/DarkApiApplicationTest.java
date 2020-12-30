package com.rbkmoney.dark.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DarkApiApplication.class, webEnvironment = RANDOM_PORT)
public class DarkApiApplicationTest {

    @Test
    public void contextLoads() throws IOException {

    }
}
