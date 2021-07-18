package com.gmail.fuskerr.backend.controller;

import com.gmail.fuskerr.backend.requestbody.UserNameRequest;
import com.gmail.fuskerr.backend.responsebody.TokenResponse;
import com.gmail.fuskerr.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(
            value = "/authentication",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseBody
    public TokenResponse authentication(UserNameRequest userName) throws Exception {
        String name = userName.getName();
        if(name != null && !name.isEmpty()) {
            String token = userService.saveUser(userName.getName());
            return new TokenResponse(token);
        }
        throw new Exception("invalid name");
    }
}
