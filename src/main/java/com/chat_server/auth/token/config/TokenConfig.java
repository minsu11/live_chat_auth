package com.chat_server.auth.token.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
@Getter
@Setter
public class TokenConfig {
    private AccessToken accessToken;
    private RefreshToken refreshToken;

    @Getter
    @Setter
    public static class AccessToken {
        private String expiration;
    }

    @Getter
    @Setter
    public static class RefreshToken {
        private String expiration;
    }

}
