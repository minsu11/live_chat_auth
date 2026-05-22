package com.chat_server.auth.common.generator;

import com.chat_server.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
public class FriendCodeGenerator {

    private static final String FRIEND_CODE_PREFIX = "CTK-";
    private static final String FRIEND_CODE_CHARS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int FRIEND_CODE_RANDOM_LENGTH = 8;
    private static final int FRIEND_CODE_MAX_RETRY = 10;

    private final SecureRandom secureRandom = new SecureRandom();
    private final UserRepository userRepository;

    public String generateUniqueFriendCode() {
        for (int i = 0; i < FRIEND_CODE_MAX_RETRY; i++) {
            String friendCode = generateFriendCode();

            if (!userRepository.existsByFriendCode(friendCode)) {
                return friendCode;
            }
        }

        throw new IllegalStateException("친구 코드를 생성하지 못했습니다.");
    }

    private String generateFriendCode() {
        StringBuilder builder = new StringBuilder(FRIEND_CODE_PREFIX);

        for (int i = 0; i < FRIEND_CODE_RANDOM_LENGTH; i++) {
            int index = secureRandom.nextInt(FRIEND_CODE_CHARS.length());
            builder.append(FRIEND_CODE_CHARS.charAt(index));
        }

        return builder.toString();
    }
}