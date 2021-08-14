package com.gmail.fuskerr.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDataMapper, Long> {
    UserDataMapper getUserByToken(String token);
}
