package com.chat_server.auth.common.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "api")
@RequiredArgsConstructor
public class ApiProperties {

    private String common;

    private String login;

    private String reissue;

    private String logout;

}
