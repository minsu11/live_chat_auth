package com.chat_server.auth.user.service.impl;

import com.chat_server.auth.securiy.PrincipalUser;
import com.chat_server.auth.token.dto.response.TokenPair;
import com.chat_server.auth.token.dto.response.TokenResponse;
import com.chat_server.auth.token.service.RefreshTokenService;
import com.chat_server.auth.token.service.TokenService;
import com.chat_server.auth.user.dto.response.UserUuidResponse;
import com.chat_server.auth.user.service.UserLoginService;
import com.chat_server.auth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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
public class UserLoginServiceImpl implements UserLoginService {
    // todo#4 token service, redis service di 예정

    private final TokenService tokenService;

    private final UserService userService;

    private final RefreshTokenService refreshTokenService;

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
        // access token만 반환 시키기
        TokenResponse tokenResponse = new TokenResponse(tokenPair.accessToken());

        return tokenResponse;
    }
}
