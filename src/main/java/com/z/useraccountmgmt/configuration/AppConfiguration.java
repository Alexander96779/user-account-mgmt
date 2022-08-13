package com.z.useraccountmgmt.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "user-account-app")
@Data
public class AppConfiguration {
    private String clientUrl;
    private String senderEmail;
    private String uploadDir;
}
