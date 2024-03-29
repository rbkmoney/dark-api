package com.rbkmoney.dark.api.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "feedback")
@Validated
@Getter
@Setter
public class FeedbackProperties {

    private String fromEmail;

    private List<String> toEmails;

}
