package com.gmail.fuskerr.backend.controller;

import com.gmail.fuskerr.backend.domain.User;
import com.gmail.fuskerr.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registration(
            @RequestBody String userName
    ) throws Exception {
        if(userName.isEmpty()) {
            throw new Exception("invalid name");
        }
        return userService.saveUser(userName);
    }
}
