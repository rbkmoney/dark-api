package com.rbkmoney.dark.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ServletComponentScan
@SpringBootApplication
public class DarkApiApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarkApiApplication.class, args);
    }

}
