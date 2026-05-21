package com.chat_server.auth.oauth.info;
import com.chat_server.auth.oauth.enums.OAuthProvider;

import java.util.Map;

@SuppressWarnings("unchecked")
public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private final Map<String, Object> attributes;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getProviderUserId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount =
                (Map<String, Object>) attributes.get("kakao_account");

        if (kakaoAccount == null) {
            return null;
        }

        return (String) kakaoAccount.get("email");
    }

    @Override
    public String getName() {
        return getNickname();
    }

    @Override
    public String getNickname() {
        Map<String, Object> properties =
                (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("nickname");
    }

    @Override
    public String getProfileImageUrl() {
        Map<String, Object> properties =
                (Map<String, Object>) attributes.get("properties");

        if (properties == null) {
            return null;
        }

        return (String) properties.get("profile_image");
    }
}