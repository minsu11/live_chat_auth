package com.chat_server.auth.oauth.repository;

import com.chat_server.auth.oauth.entity.OauthAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OAuthAccountRepository extends JpaRepository<OauthAccount, Integer> {
}
