package com.rbkmoney.dark.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@Slf4j
@ServletComponentScan
@SpringBootApplication
public class DarkApiApplication extends SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarkApiApplication.class, args);
    }

}
