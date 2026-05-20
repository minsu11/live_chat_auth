package com.chat_server.auth.userprofile.repository;

import com.chat_server.auth.userprofile.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

}
