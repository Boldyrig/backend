package com.gmail.fuskerr.backend.controller;

import com.gmail.fuskerr.backend.core.gateway.UserRepositoryGateway;
import com.gmail.fuskerr.backend.core.model.UserNameModel;
import com.gmail.fuskerr.backend.core.model.TokenModel;
import com.gmail.fuskerr.backend.repository.JpaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserRepositoryGateway repository;

    @Autowired
    public void setUserService(UserRepositoryGateway repository) {
        this.repository = repository;
    }

    @PostMapping(
            value = "/authentication",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @CrossOrigin(origins = "http://192.168.1.79:8080")
    @ResponseBody
    public TokenModel authentication(UserNameModel userName) throws Exception {
        String name = userName.getName();
        if(name != null && !name.isEmpty()) {
            String token = repository.save(userName.getName());
            return new TokenModel(token);
        }
        throw new Exception("invalid name");
    }
}
