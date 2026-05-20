package com.chat_server.auth.oauth.handler;

import com.chat_server.auth.auth.dto.response.TokenResponse;
import com.chat_server.auth.auth.service.AuthService;
import com.chat_server.auth.oauth.principal.CustomOAuth2Principal;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final AuthService authService;

    @Value("${app.front-url}")
    private String frontUrl;

    @Value("${app.cookie.secure:false}")
    private boolean cookieSecure;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        CustomOAuth2Principal principal =
                (CustomOAuth2Principal) authentication.getPrincipal();

        TokenResponse tokenResponse =
                authService.issueTokenByUserUuid(principal.getUserUuid());

        ZonedDateTime expiration = ZonedDateTime.parse(tokenResponse.accessTokenExpiration());
        Duration duration = Duration.between(ZonedDateTime.now(ZoneOffset.UTC), expiration);
        if (duration.isNegative() || duration.isZero()) {
            duration = Duration.ofMinutes(10);
        }
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokenResponse.accessToken())
                .httpOnly(true)
                .secure(cookieSecure)
                .path("/")
                .sameSite("Lax")
                .maxAge(duration)
                .build();

        response.addHeader("Set-Cookie", accessTokenCookie.toString());

        getRedirectStrategy().sendRedirect(
                request,
                response,
                frontUrl + "/oauth/success"
        );
    }
}