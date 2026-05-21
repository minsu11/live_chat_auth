package com.chat_server.auth.oauth.info;

import java.util.Map;

public class OAuth2UserInfoFactory {

    private OAuth2UserInfoFactory() {
    }

    public static OAuth2UserInfo of(
            String registrationId,
            Map<String, Object> attributes
    ) {
        if ("google".equalsIgnoreCase(registrationId)) {
            return new GoogleOAuth2UserInfo(attributes);
        }

        if ("kakao".equalsIgnoreCase(registrationId)) {
            return new KakaoOAuth2UserInfo(attributes);
        }

        throw new IllegalArgumentException("지원하지 않는 OAuth2 provider입니다: " + registrationId);
    }
}