package com.gmail.fuskerr.backend.repository;

import com.gmail.fuskerr.backend.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import com.gmail.fuskerr.backend.core.gateway.UserRepositoryGateway;

@Service
public class JpaUser implements UserRepositoryGateway {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String save(String name) {
        final String token = generateToken();
        userRepository.save(new UserDataMapper(name, token));
        return token;
    }

    @Override
    public User getUserByToken(String token) {
        UserDataMapper user = userRepository.getUserByToken(token);
        return new User(
                user.getId(),
                user.getName(),
                user.getToken()
        );
    }

    //TODO переписать
    private String generateToken() {
        final SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[24];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().encodeToString(bytes);
    }
}
