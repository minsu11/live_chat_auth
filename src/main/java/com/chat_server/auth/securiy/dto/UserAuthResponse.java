package com.chat_server.auth.securiy.dto;

/**
 * packageName    : com.chat_server.auth.securiy.dto
 * fileName       : UserAuthResponse
 * author         : parkminsu
 * date           : 25. 5. 8.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 8.        parkminsu       최초 생성
 */

public record UserAuthResponse(String userInputId, String userInputPassword) {
}
