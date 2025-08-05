package com.chat_server.auth.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * packageName    : com.chat_server.auth.token.config
 * fileName       : TokenConfig
 * author         : parkminsu
 * date           : 25. 5. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 12.        parkminsu       최초 생성
 */
@Configuration
@ConfigurationProperties(prefix = "auth.token")
@Getter
@Setter
public class TokenConfig {
    private AccessToken accessToken;
    private RefreshToken refreshToken;

    @Getter
    @Setter
    public static class AccessToken {
        private long expiration;

        public long toMillis() {
            return expiration*1000;
        }

        public long toSeconds() {
            return expiration;
        }
    }

    @Getter
    @Setter
    public static class RefreshToken {
        private long expiration;

        public long toMillis() {
            return expiration*1000;
        }

        public long toSeconds() {
            return expiration;
        }
    }

}
