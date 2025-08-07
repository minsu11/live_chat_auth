package com.chat_server.auth.auth.service;

import com.chat_server.auth.auth.dto.response.TokenPair;
import io.jsonwebtoken.Claims;

/**
 * packageName    : com.chat_server.auth.securiy.service
 * fileName       : TokenService
 * author         : parkminsu
 * date           : 25. 5. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 11.        parkminsu       최초 생성
 */

public interface TokenService {
    /**
     * AccessToken과 RefreshToken을 함께 발급한다.
     * @param userId 사용자 고유 ID
     * @return AccessToken, RefreshToken
     */
    TokenPair generateTokenPair(String userId, String accessTokenJti, String refreshTokenJti );

    /**
     * RefreshToken을 Redis 등에서 제거하여 무효화한다.
     * @param refreshToken 삭제할 RefreshToken
     */
    void invalidateRefreshToken(String refreshToken);

    /**
     * 토큰 유효성 검사 (예: 헤더 파싱 후 검증용).
     * @param token access 또는 refresh token
     * @return 유효 여부
     */
    void validToken(String token);

    Claims extractClaimsAllowExpired(String token);
}
