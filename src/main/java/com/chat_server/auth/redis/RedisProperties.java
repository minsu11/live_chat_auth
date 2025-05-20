package com.chat_server.auth.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.chat_server.auth.redis
 * fileName       : RedisProperties
 * author         : parkminsu
 * date           : 25. 5. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 20.        parkminsu       최초 생성
 */
@Configuration
@ConfigurationProperties(prefix = "redis.key.prefix")
@Getter
@Setter
public class RedisProperties {

    private String refreshToken;

}
