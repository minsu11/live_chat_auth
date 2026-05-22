package com.chat_server.auth.user.entity;

import com.chat_server.auth.logintype.entity.LoginType;
import com.chat_server.auth.user.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "user",
        uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_input_id", columnNames = "input_id"),
                @UniqueConstraint(name = "uk_user_friend_code", columnNames = "friend_code"),
                @UniqueConstraint(name = "uk_user_uuid", columnNames = "uuid")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * OAuth2 사용자는 성별 정보가 없을 수 있으므로 nullable.
     * 현재 Auth 서버에서는 Gender 엔티티를 사용하지 않으므로 genderId로만 매핑한다.
     */
    @Column(name = "gender_id")
    private Integer genderId;

    @Column(name = "input_id", nullable = false, length = 30)
    private String userInputId;

    /**
     * OAuth2 사용자는 비밀번호가 없으므로 nullable.
     */
    @Column(name = "input_password", length = 100)
    private String userInputPassword;

    /**
     * OAuth2 사용자는 나이 정보가 없으므로 nullable.
     */
    @Column(name = "age")
    private Integer age;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "nickname", nullable = false, length = 30)
    private String nickname;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime userCreatedAt;

    @Column(name = "friend_code", nullable = false, length = 20, unique = true)
    private String friendCode;

    @Column(name = "uuid", nullable = false, length = 36)
    private String userUuid;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private UserStatus userStatus;

    @Column(name = "login_lasted_at")
    private LocalDateTime loginLastedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "login_type_id")
    private LoginType loginType;

    public static User createOAuthUser(
            String inputId,
            String name,
            String nickname,
            LoginType loginType,
            String friendCode
    ) {
        LocalDateTime now = LocalDateTime.now();

        return User.builder()
                .genderId(null)
                .userInputId(limit(inputId, 30))
                .userInputPassword(null)
                .age(null)
                .friendCode(friendCode)
                .name(limitOrDefault(name, 30, "소셜사용자"))
                .nickname(limitOrDefault(nickname, 30, "소셜사용자"))
                .userUuid(UUID.randomUUID().toString())
                .userStatus(UserStatus.ACTIVE)
                .userCreatedAt(now)
                .loginLastedAt(now)
                .loginType(loginType)
                .build();
    }


    public void updateLoginLastedAt() {
        this.loginLastedAt = LocalDateTime.now();
    }

    private static String limitOrDefault(String value, int maxLength, String defaultValue) {
        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return limit(value, maxLength);
    }

    private static String limit(String value, int maxLength) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();

        if (trimmed.length() <= maxLength) {
            return trimmed;
        }

        return trimmed.substring(0, maxLength);
    }
}