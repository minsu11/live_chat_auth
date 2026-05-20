package com.chat_server.auth.oauth.repository;

import com.chat_server.auth.oauth.entity.OauthAccount;
import com.chat_server.auth.oauth.enums.OAuthProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface OAuthAccountRepository extends JpaRepository<OauthAccount, Long> {

    Optional<OauthAccount> findByProviderAndProviderUserId(
            OAuthProvider provider,
            String providerUserId
    );
}