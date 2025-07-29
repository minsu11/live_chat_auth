package com.chat_server.auth.securiy.handler;

import com.chat_server.auth.common.dto.response.ApiResponse;
import com.chat_server.auth.securiy.PrincipalUser;
import com.chat_server.auth.auth.dto.response.TokenResponse;
import com.chat_server.auth.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

/**
 * packageName    : com.chat_server.security.handler
 * fileName       : SuccessHandler
 * author         : parkminsu
 * date           : 25. 2. 26.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 2. 26.        parkminsu       최초 생성
 */
@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;
    private final AuthService userLoginService;

    // 성공 시
    // todo#2 token 방식으로 교체
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onAuthenticationSuccess");
        log.info("authentication: {}", authentication.getClass());
        Object o = authentication.getPrincipal();
        log.info("principal is " + o.toString());
        log.info("authentication class: {}", authentication.getPrincipal().getClass());

        if (o instanceof PrincipalUser principal) {
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid authentication.");
//            return;
            log.info("onAuthenticationSuccess principal");

            TokenResponse tokenResponse = userLoginService.successLogin(principal);

            ApiResponse<TokenResponse> apiResponse = ApiResponse.success(200, "login success", tokenResponse);

            log.debug("apiResponse: {}", apiResponse);

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            response.setHeader("X-Token-Expires-In", tokenResponse.accessTokenExpiration()); // 초 단위 등
        }


    }
}
