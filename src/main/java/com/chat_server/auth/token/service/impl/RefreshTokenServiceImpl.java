package com.chat_server.auth.token.service.impl;

import com.chat_server.auth.redis.RedisProperties;
import com.chat_server.auth.token.config.TokenConfig;
import com.chat_server.auth.token.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

/**
 * packageName    : com.chat_server.auth.token.service.impl
 * fileName       : RefreshTokenServiceImpl
 * author         : parkminsu
 * date           : 25. 5. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 20.        parkminsu       최초 생성
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final TokenConfig tokenConfig;
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisProperties redisProperties;

    // refresh token redis save
    @Override
    public void saveRefreshToken(String refreshToken, String userId) {
        // refresh token을 redis 저장을 시킴
        String refreshTokenPrefix = redisProperties.getRefreshToken();
        String key = refreshTokenPrefix + ":" + userId;
        long ttlInSeconds = Long.parseLong(tokenConfig.getRefreshToken().getExpiration());

        Duration ttl = Duration.ofSeconds(ttlInSeconds);
        redisTemplate.opsForValue().set(key, refreshToken, ttl);
    }

}
