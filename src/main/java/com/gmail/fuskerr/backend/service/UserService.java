package com.gmail.fuskerr.backend.service;

import com.gmail.fuskerr.backend.domain.User;
import com.gmail.fuskerr.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String saveUser(String name) {
        //generate token
        final String token = String.valueOf((int)(Math.random()*100));
        User u = userRepository.save(new User(name, token));
        System.out.println(u);
        return token;
    }
}
