package com.chat_server.auth.oauth.entity;

import com.chat_server.auth.oauth.enums.OAuthProvider;
import com.chat_server.auth.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(
        name = "oauth_account",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_oauth_provider_user",
                        columnNames = {"provider", "provider_user_id"}
                )
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OauthAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * OAuth 계정이 연결된 서비스 내부 사용자.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * GOOGLE, KAKAO
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false, length = 30)
    private OAuthProvider provider;

    /**
     * Provider가 내려준 고유 사용자 ID.
     * Google: sub
     * Kakao: id
     */
    @Column(name = "provider_user_id", nullable = false, length = 100)
    private String providerUserId;

    /**
     * Provider에서 내려준 email.
     * 계정 식별/자동 연동 기준으로 사용하지 않고 참고 정보로만 저장한다.
     */
    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "nickname", length = 100)
    private String nickname;

    @Column(name = "profile_image_url", length = 500)
    private String profileImageUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public static OauthAccount create(
            User user,
            OAuthProvider provider,
            String providerUserId,
            String email,
            String nickname,
            String profileImageUrl
    ) {
        OauthAccount account = new OauthAccount();
        account.user = user;
        account.provider = provider;
        account.providerUserId = providerUserId;
        account.email = email;
        account.nickname = nickname;
        account.profileImageUrl = profileImageUrl;
        account.createdAt = LocalDateTime.now();
        return account;
    }
}