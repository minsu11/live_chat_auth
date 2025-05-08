package com.chat_server.auth.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.chat_server.auth.user.dto.request
 * fileName       : LoginReqeust
 * author         : parkminsu
 * date           : 25. 5. 8.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 8.        parkminsu       최초 생성
 */

@Getter
@NoArgsConstructor
public class LoginRequest {
    private String userId;
    private String password;
}
