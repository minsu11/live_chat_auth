package com.chat_server.auth.token.service;

import com.chat_server.auth.token.dto.response.TokenPair;

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
     * AccessToken만 재발급한다 (RefreshToken 유효성 통과 시).
     * @param refreshToken 유효한 리프레시 토큰
     * @return 새로운 AccessToken
     */
    String regenerateAccessToken(String refreshToken);

    /**
     * RefreshToken을 갱신한다 (선택 사항, 보통 보안 레벨이 높은 시스템).
     * @param oldRefreshToken 기존 RefreshToken
     * @return 새로운 RefreshToken
     */
    String regenerateRefreshToken(String oldRefreshToken);

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
    boolean isValidToken(String token);

}
