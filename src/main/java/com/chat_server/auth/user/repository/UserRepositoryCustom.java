package com.chat_server.auth.user.repository;

import com.chat_server.auth.securiy.dto.UserAuthResponse;
import com.chat_server.auth.user.dto.response.UserUuidResponse;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * packageName    : com.chat_server.auth.user.repository
 * fileName       : UserRepositoryCustom
 * author         : parkminsu
 * date           : 25. 5. 8.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 8.        parkminsu       최초 생성
 */
@NoRepositoryBean
public interface UserRepositoryCustom {
    Optional<UserAuthResponse> findByUserName(String userId);

    Optional<UserUuidResponse> findUuidByUsername(String userId);

}
