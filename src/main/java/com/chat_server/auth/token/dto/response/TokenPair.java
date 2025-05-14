package com.chat_server.auth.token.dto.response;

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
public record TokenPair(String accessToken, String refreshToken){

}
