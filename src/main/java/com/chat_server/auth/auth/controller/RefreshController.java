package com.chat_server.auth.auth.controller;
import com.chat_server.auth.auth.dto.response.TokenResponse;
import com.chat_server.auth.auth.service.AuthService;
import com.chat_server.auth.common.dto.response.ApiResponse;
import com.chat_server.auth.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

@RestController("auth")
@RequiredArgsConstructor
public class RefreshController {
    private final AuthService authService;

    // 재발급 로직
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse> reissue(HttpServletRequest request, HttpServletResponse response) {
        // cookie 데이터
        String accessToken = CookieUtil.getCookie(request, "accessToken");
        TokenResponse tokenResponse = authService.reissueToken(accessToken);
        ApiResponse apiResponse = ApiResponse.success(200, "재발급");

        ZonedDateTime expiration = ZonedDateTime.parse(tokenResponse.accessTokenExpiration());
        Duration duration = Duration.between(ZonedDateTime.now(ZoneOffset.UTC), expiration);

        // 쿠키 생성
        ResponseCookie cookie = ResponseCookie.from("accessToken", tokenResponse.accessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .sameSite("Strict")
                .maxAge(duration)
                .build();

        response.setHeader("Set-Cookie", cookie.toString());


        return ResponseEntity.ok(apiResponse);
    }

}
