package com.gmail.fuskerr.backend.controller;

import com.gmail.fuskerr.backend.domain.User;
import com.gmail.fuskerr.backend.requestbody.MessageAction;
import com.gmail.fuskerr.backend.service.GameManager;
import com.gmail.fuskerr.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class GameSessionController {
    private GameManager gameManager;
    private UserService userService;

    @Autowired
    @Qualifier("simpleGameManager")
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void processMessage(MessageAction messageAction, String token) {
        User user = userService.getUserByToken(token);
        if(messageAction.getTopic() != null && user != null) {
            gameManager.addToInputQueue(messageAction, user.getToken());
        }
    }
    
    public void userConectToSocket(String token) {
        User user = userService.getUserByToken(token);
        if(user != null) {
            gameManager.playerIsReady(token);
        }
    }
}
