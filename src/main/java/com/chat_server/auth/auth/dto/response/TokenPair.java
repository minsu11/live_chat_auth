package com.chat_server.auth.auth.dto.response;

import com.chat_server.auth.util.TimeUtil;

import java.util.Date;

/**
 * packageName    : com.chat_server.auth.token.dto.response
 * fileName       : TokenPair
 * author         : parkminsu
 * date           : 25. 5. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 11.        parkminsu       최초 생성
 */
public record TokenPair(String accessToken,
                        String refreshToken,
                        String accessTokenExpiration,
                        String  refreshTokenExpiration){

    public static TokenPair of(String accessToken, String refreshToken, Date accessTokenExpiration, Date refreshTokenExpiration){
        return new TokenPair(
                accessToken,
                refreshToken,
                TimeUtil.toIso8601(accessTokenExpiration),
                TimeUtil.toIso8601(refreshTokenExpiration)
        );
    }
}
