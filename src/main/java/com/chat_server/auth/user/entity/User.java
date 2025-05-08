package com.chat_server.auth.user.entity;

import com.chat_server.auth.user_status.entity.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * packageName    : com.chat_server.auth.user.entity
 * fileName       : User
 * author         : parkminsu
 * date           : 25. 5. 6.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 5. 6.        parkminsu       최초 생성
 */
@Getter
@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_input_id")
    private String userInputId;

    @Column(name = "user_input_password")
    private String userInputPassword;

    @Column(name = "user_created_at")
    private LocalDateTime userCreatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id")
    private UserStatus userStatus;
}
