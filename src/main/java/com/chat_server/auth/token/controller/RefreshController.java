package com.chat_server.auth.token.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController("auth")
@RequiredArgsConstructor
public class RefreshController {

    // 재발급 로직
    @PostMapping("/reissue")
    public String reissue(HttpServletRequest request) {
        // cookie 데이터
        Cookie cookie = WebUtils.getCookie(request, "access_token");
        String access_token = cookie.getValue();


        return null;
    }

}
