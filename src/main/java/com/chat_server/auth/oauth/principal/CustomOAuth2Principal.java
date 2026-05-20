package com.chat_server.auth.oauth.principal;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2Principal implements OAuth2User {

    private final Long userId;
    private final String userUuid;
    private final Map<String, Object> attributes;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomOAuth2Principal(
            Long userId,
            String userUuid,
            Map<String, Object> attributes,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.userId = userId;
        this.userUuid = userUuid;
        this.attributes = attributes;
        this.authorities = authorities;
    }

    @Override
    public String getName() {
        return userUuid;
    }
}