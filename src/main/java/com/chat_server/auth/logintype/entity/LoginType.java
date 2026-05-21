package com.chat_server.auth.logintype.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Table(name = "login_type")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LoginType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static LoginType create(String name) {
        LoginType loginType = new LoginType();
        loginType.name = name;
        loginType.createdAt = LocalDateTime.now();
        return loginType;
    }

}
