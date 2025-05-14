package com.chat_server.auth.user.service;

import com.chat_server.auth.securiy.PrincipalUser;
import com.chat_server.auth.securiy.dto.UserAuthResponse;
import com.chat_server.auth.token.dto.response.TokenResponse;

import java.security.Principal;

/**
 * packageName    : com.chat_server.auth.user.service
 * fileName       : UserLoginService
 * author         : parkminsu
 * date           : 25. 5. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 12.        parkminsu       최초 생성
 */
public interface UserLoginService {

    TokenResponse successLogin(PrincipalUser principal);
}
