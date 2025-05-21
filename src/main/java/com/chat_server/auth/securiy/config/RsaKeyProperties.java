package com.chat_server.auth.securiy.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.chat_server.auth.securiy.config
 * fileName       : RsaKeyProperties
 * author         : parkminsu
 * date           : 25. 5. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 13.        parkminsu       최초 생성
 */
@Configuration
@ConfigurationProperties(prefix = "auth.key")
@Getter
@Setter
public class RsaKeyProperties {
    private String publicPath;
    private String privatePath;
}
