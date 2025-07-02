package com.chat_server.auth.token.dto.response;

/**
 * packageName    : com.chat_server.auth.token.dto.response
 * fileName       : TokenResponse
 * author         : parkminsu
 * date           : 25. 5. 12.
 * description    : access token 응답
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 12.        parkminsu       최초 생성
 */
public record TokenResponse(String accessToken, String accessTokenExpiration) {
}
