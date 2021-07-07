package com.gmail.fuskerr.backend.controller;

import com.gmail.fuskerr.backend.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping("/authentication")
    public User authentication(
        @RequestBody User user
    ) {
        return user;
    }
}
