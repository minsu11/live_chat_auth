package com.chat_server.auth.oauth.info;
import com.chat_server.auth.oauth.enums.OAuthProvider;

public interface OAuth2UserInfo {

    OAuthProvider getProvider();

    String getProviderUserId();

    String getEmail();

    String getName();

    String getNickname();

    String getProfileImageUrl();
}