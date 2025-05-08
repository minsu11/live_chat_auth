package com.chat_server.auth.user.repository.impl;

import com.chat_server.auth.securiy.dto.UserAuthResponse;
import com.chat_server.auth.user.entity.QUser;
import com.chat_server.auth.user.repository.UserRepositoryCustom;
import com.querydsl.core.types.Projections;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Optional;

/**
 * packageName    : com.chat_server.auth.user.repository.impl
 * fileName       : UserRepositoryCustomImpl
 * author         : parkminsu
 * date           : 25. 5. 8.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 8.        parkminsu       최초 생성
 */

public class UserRepositoryCustomImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {
    private final QUser qUser = QUser.user;
    public UserRepositoryCustomImpl() {
        super(QUser.class);
    }


    @Override
    public Optional<UserAuthResponse> findByUserName(String userName) {


        return Optional.ofNullable(
                from(qUser)
                        .select(Projections.constructor(
                                UserAuthResponse.class,
                                qUser.userInputId,
                                qUser.userInputPassword
                        ))
                        .where(qUser.userInputId.eq(userName)
                                .and(qUser.userStatus.userStatusName.eq("활성"))
                        ).fetchOne()
        );
    }
}
