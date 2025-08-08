package com.chat_server.auth.securiy;

import com.chat_server.auth.securiy.handler.CustomFailHandler;
import com.chat_server.auth.securiy.handler.CustomLogoutSuccessHandler;
import com.chat_server.auth.securiy.handler.CustomSuccessHandler;
import com.chat_server.auth.securiy.service.UserAuthService;
import com.chat_server.auth.auth.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * packageName    : com.chat_server.auth.securiy
 * fileName       : SecurityConfig
 * author         : parkminsu
 * date           : 25. 5. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 6.        parkminsu       최초 생성
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final UserAuthService userAuthService;
    private final ObjectMapper objectMapper;
    private final AuthService userLoginService;
    private final String LOGIN_URL = "/api/v1/auth/login";
    // security 허용 경로
    // login 경로 및 refresh 경로만 허용
    // 그 외의 경로는 허용하지 않음
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests
                        (
                                authorizeRequests ->
                                        authorizeRequests.requestMatchers(LOGIN_URL, "/auth/reissue").permitAll()

                        )
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(
                        logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .logoutSuccessHandler(logoutSuccessHandler())
                );

        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter(passwordEncoder());
        userAuthenticationFilter.setFilterProcessesUrl(LOGIN_URL);
        userAuthenticationFilter.setAuthenticationManager(userAuthenticationManager(authenticationConfiguration)); // 등록된 manager 사용

        userAuthenticationFilter.setAuthenticationSuccessHandler(successHandler());
        userAuthenticationFilter.setAuthenticationFailureHandler(failHandler());
        http.addFilterBefore(userAuthenticationFilter, UserAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager userAuthenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userAuthService);
        return authenticationProvider;
    }

    @Bean
    public CustomSuccessHandler successHandler() {
        return new CustomSuccessHandler(objectMapper,userLoginService);
    }

    @Bean
    public CustomFailHandler failHandler() {
        return new CustomFailHandler();
    }

    @Bean
    public CustomLogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
