package com.chat_server.auth.user.repository;

import com.chat_server.auth.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : com.chat_server.auth.user.repository
 * fileName       : UserRepository
 * author         : parkminsu
 * date           : 25. 5. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 6.        parkminsu       최초 생성
 */
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    boolean existsByUserInputId(String inputId);

}
