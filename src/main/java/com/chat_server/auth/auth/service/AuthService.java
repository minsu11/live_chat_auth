package com.chat_server.auth.auth.service;

import com.chat_server.auth.auth.dto.response.TokenPair;
import com.chat_server.auth.securiy.PrincipalUser;
import com.chat_server.auth.auth.dto.response.TokenResponse;
import org.springframework.boot.web.server.Cookie;

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
public interface AuthService {

    TokenResponse successLogin(PrincipalUser principal);

    TokenResponse reissueToken(String accessToken);

}
