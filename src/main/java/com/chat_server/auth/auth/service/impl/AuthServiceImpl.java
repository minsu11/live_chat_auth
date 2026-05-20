package com.chat_server.auth.auth.service.impl;

import com.chat_server.auth.redis.RedisConfig;
import com.chat_server.auth.redis.RedisProperties;
import com.chat_server.auth.securiy.PrincipalUser;
import com.chat_server.auth.auth.dto.response.TokenPair;
import com.chat_server.auth.auth.dto.response.TokenResponse;
import com.chat_server.auth.auth.service.RefreshTokenService;
import com.chat_server.auth.auth.service.TokenService;
import com.chat_server.auth.securiy.provider.RsaKeyProvider;
import com.chat_server.auth.user.dto.response.UserUuidResponse;
import com.chat_server.auth.auth.service.AuthService;
import com.chat_server.auth.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.Cookie;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * packageName    : com.chat_server.auth.user.service.impl
 * fileName       : UserLoginServiceImpl
 * author         : parkminsu
 * date           : 25. 5. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 12.        parkminsu       최초 생성
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    // todo#4 token service, redis service di 예정

    private final TokenService tokenService;

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

    private final RedisProperties redisProperties;
    private final RedisTemplate<String, Object> redisTemplate;

    // user id 구현
    @Override
    public TokenResponse successLogin(PrincipalUser principal) {
        // 로그인 성공시 토큰 발급 및 redis 에 저장
        String username = principal.getUsername();

        // user uuid 가지고 오기
        UserUuidResponse userUuidResponse = userService.getUserUuid(username);

        // uuid 만들기
        String accessTokenUuid = UUID.randomUUID().toString();
        String refreshTokenUuid = UUID.randomUUID().toString();

        // access token, refresh token 생성
        TokenPair tokenPair =tokenService.generateTokenPair(
                userUuidResponse.uuid(), accessTokenUuid, refreshTokenUuid
        );

        // refresh token redis 저장 시키기
        refreshTokenService.saveRefreshToken(tokenPair.refreshToken(),userUuidResponse.uuid());

        // access token 반환 시키기
        return new TokenResponse(tokenPair.accessToken(), tokenPair.accessTokenExpiration());

    }

    @Override
    public TokenResponse reissueToken(String accessToken) {
        Claims claims = tokenService.extractClaimsAllowExpired(accessToken);

        String userUuid = claims.getSubject();

        String key = redisProperties.getRefreshToken() + ":" + userUuid;
        String refreshToken = (String) redisTemplate.opsForValue().get(key);

        if (refreshToken == null) {
            throw new RuntimeException("refresh token is null");
        }

        tokenService.validToken(refreshToken);

        String accessTokenUuid = UUID.randomUUID().toString();
        String refreshTokenUuid = UUID.randomUUID().toString();

        TokenPair tokenPair = tokenService.generateTokenPair(
                userUuid,
                accessTokenUuid,
                refreshTokenUuid
        );

        refreshTokenService.saveRefreshToken(
                tokenPair.refreshToken(),
                userUuid
        );

        return new TokenResponse(
                tokenPair.accessToken(),
                tokenPair.accessTokenExpiration()
        );
    }

    @Override
    public TokenResponse issueTokenByUserUuid(String userUuid) {
        String accessTokenUuid = UUID.randomUUID().toString();
        String refreshTokenUuid = UUID.randomUUID().toString();

        TokenPair tokenPair = tokenService.generateTokenPair(
                userUuid,
                accessTokenUuid,
                refreshTokenUuid
        );

        refreshTokenService.saveRefreshToken(
                tokenPair.refreshToken(),
                userUuid
        );

        return new TokenResponse(
                tokenPair.accessToken(),
                tokenPair.accessTokenExpiration()
        );
    }


}
