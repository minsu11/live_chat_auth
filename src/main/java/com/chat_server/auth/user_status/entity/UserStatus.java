package com.chat_server.auth.user_status.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

/**
 * packageName    : com.chat_server.auth.user_status.entity
 * fileName       : UserStatus
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
@Table(name = "user_status")
public class UserStatus{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_status_id")
    private int id;

    @Column(name = "user_status_name")
    private String userStatusName;

    @Column(name = "user_status_created_at")
    private Date userStatusCreatedAt;
}