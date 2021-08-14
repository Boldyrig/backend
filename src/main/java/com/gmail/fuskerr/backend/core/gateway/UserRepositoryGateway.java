package com.gmail.fuskerr.backend.core.gateway;

import com.gmail.fuskerr.backend.core.entity.User;

public interface UserRepositoryGateway {
    //returns token
    String save(String name);
    User getUserByToken(String token);
}
