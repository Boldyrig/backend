package com.gmail.fuskerr.backend.service;

import com.gmail.fuskerr.backend.domain.User;
import com.gmail.fuskerr.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String saveUser(String name) {
        final String token = generateToken();
        userRepository.save(new User(name, token));
        return token;
    }

    //TODO переписать
    private String generateToken() {
        final SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[24];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }
}
