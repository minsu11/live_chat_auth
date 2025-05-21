package com.chat_server.auth.user.service.impl;

import com.chat_server.auth.common.exception.UserNotFoundException;
import com.chat_server.auth.user.dto.response.UserUuidResponse;
import com.chat_server.auth.user.repository.UserRepository;
import com.chat_server.auth.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.chat_server.auth.user.service.impl
 * fileName       : UserServiceImpl
 * author         : parkminsu
 * date           : 25. 5. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 12.        parkminsu       최초 생성
 */
@Slf4j
@Service

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    // User Repository di
    private final UserRepository userRepository;


    @Override
    public UserUuidResponse getUserUuid(String userId) {

        UserUuidResponse response = userRepository.findUuidByUsername(userId)
                .orElseThrow(UserNotFoundException::new);

        return response;
    }
}
