package com.chat_server.auth.token.service;


/**
 * packageName    : com.chat_server.auth.token.service
 * fileName       : RefreshTokenService
 * author         : parkminsu
 * date           : 25. 5. 20.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 20.        parkminsu       최초 생성
 */
public interface RefreshTokenService {
    void saveRefreshToken(String refreshToken, String userId);

}
