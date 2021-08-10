package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.entity.Player;
import com.gmail.fuskerr.backend.core.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public Player create(User user) {
        return new Player(user.getId(), user.getName(), user.getToken());
    }
}
