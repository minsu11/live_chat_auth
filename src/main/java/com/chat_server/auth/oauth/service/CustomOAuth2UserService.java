package com.chat_server.auth.oauth.service;

import com.chat_server.auth.logintype.entity.LoginType;
import com.chat_server.auth.logintype.exception.LoginTypeNotFountException;
import com.chat_server.auth.logintype.repository.LoginTypeRepository;
import com.chat_server.auth.oauth.entity.OauthAccount;
import com.chat_server.auth.oauth.enums.OAuthProvider;
import com.chat_server.auth.oauth.info.OAuth2UserInfo;
import com.chat_server.auth.oauth.info.OAuth2UserInfoFactory;
import com.chat_server.auth.oauth.principal.CustomOAuth2Principal;
import com.chat_server.auth.oauth.repository.OAuthAccountRepository;
import com.chat_server.auth.user.entity.User;
import com.chat_server.auth.user.enums.LoginTypeEnum;
import com.chat_server.auth.user.repository.UserRepository;
import com.chat_server.auth.userprofile.entity.UserProfile;
import com.chat_server.auth.userprofile.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final LoginTypeRepository loginTypeRepository;

    private final OAuthAccountRepository oauthAccountRepository;

    private final UserProfileRepository userProfileRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oauth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId();

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.of(
                registrationId,
                oauth2User.getAttributes()
        );

        OauthAccount oauthAccount = oauthAccountRepository
                .findByProviderAndProviderUserId(
                        userInfo.getProvider(),
                        userInfo.getProviderUserId()
                )
                .orElseGet(() -> createUserAndOAuthAccount(userInfo));

        User user = oauthAccount.getUser();
        user.updateLoginLastedAt();

        return new CustomOAuth2Principal(
                user.getId(),
                user.getUserUuid(),
                oauth2User.getAttributes(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    private OauthAccount createUserAndOAuthAccount(OAuth2UserInfo userInfo) {
        String inputId = createOAuthInputId(
                userInfo.getProvider(),
                userInfo.getProviderUserId()
        );

        String name = resolveName(userInfo);
        String nickname = resolveNickname(userInfo);

        LoginType loginType = getOrCreateOAuthLoginType();
        User user = User.createOAuthUser(
                inputId,
                name,
                nickname,
                loginType
        );

        User savedUser = userRepository.save(user);

        UserProfile userProfile = UserProfile.builder()
                .user(savedUser)
                .stateMessage("")
                .build();

        UserProfile saveUserProfile = userProfileRepository.save(userProfile);

        OauthAccount oauthAccount = OauthAccount.create(
                savedUser,
                userInfo.getProvider(),
                userInfo.getProviderUserId(),
                userInfo.getEmail(),
                userInfo.getNickname(),
                userInfo.getProfileImageUrl()
        );

        return oauthAccountRepository.save(oauthAccount);
    }

    private String createOAuthInputId(
            OAuthProvider provider,
            String providerUserId
    ) {
        String raw = provider.name() + ":" + providerUserId;
        String hash = DigestUtils.sha256Hex(raw).substring(0, 12);

        return "oauth_" + provider.name().toLowerCase() + "_" + hash;
    }

    private String resolveName(OAuth2UserInfo userInfo) {
        String name = userInfo.getName();

        if (name == null || name.isBlank()) {
            return "소셜사용자";
        }

        return limit(name, 30);
    }

    private String resolveNickname(OAuth2UserInfo userInfo) {
        String nickname = userInfo.getNickname();

        if (nickname == null || nickname.isBlank()) {
            return "user_" + UUID.randomUUID().toString().substring(0, 8);
        }

        return limit(nickname, 30);
    }

    private String limit(String value, int maxLength) {
        String trimmed = value.trim();

        if (trimmed.length() <= maxLength) {
            return trimmed;
        }

        return trimmed.substring(0, maxLength);
    }

    private LoginType getOrCreateOAuthLoginType() {
        return loginTypeRepository.findByName(LoginTypeEnum.OAUTH.name())
                .orElseGet(() -> loginTypeRepository.save(
                        LoginType.create(LoginTypeEnum.OAUTH.name())
                ));
    }
}