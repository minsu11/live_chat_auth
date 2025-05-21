package com.chat_server.auth.securiy.service;

import com.chat_server.auth.securiy.PrincipalUser;
import com.chat_server.auth.securiy.dto.UserAuthResponse;
import com.chat_server.auth.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * packageName    : com.chat_server.auth.securiy.service
 * fileName       : UserAuthService
 * author         : parkminsu
 * date           : 25. 5. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 6.        parkminsu       최초 생성
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // user name 유효성 검사

        if(Objects.isNull(username) || username.isEmpty()) {
            throw new UsernameNotFoundException("Username is null or empty");
        }
        UserAuthResponse userAuthDto = userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
        // principal user 생성
        return new PrincipalUser(userAuthDto.userInputId(), userAuthDto.userInputPassword());
    }

}
