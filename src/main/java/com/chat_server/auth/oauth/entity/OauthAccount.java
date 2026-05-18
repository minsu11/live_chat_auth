package com.chat_server.auth.oauth.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GeneratedColumn;

@Entity
@Table(name = "oauth_account")
public class OauthAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


}
