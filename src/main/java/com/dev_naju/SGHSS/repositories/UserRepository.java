package com.dev_naju.SGHSS.repositories;

import com.dev_naju.SGHSS.entities.UserSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserSystem, Long> {
//    UserDetails findByLogin(String login);

    Optional<UserSystem> findByLogin(String login);
}
