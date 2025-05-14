package com.chat_server.auth.user.service;

import com.chat_server.auth.user.dto.response.UserUuidResponse;

/**
 * packageName    : com.chat_server.auth.user.service
 * fileName       : UserService
 * author         : parkminsu
 * date           : 25. 5. 12.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 12.        parkminsu       최초 생성
 */
public interface UserService {
    // User 대체키만 가지고 옴

    UserUuidResponse getUserUuid(String userId);

}
