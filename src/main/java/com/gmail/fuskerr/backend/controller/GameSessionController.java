package com.gmail.fuskerr.backend.controller;

import com.gmail.fuskerr.backend.core.boundary.UserConnectionBoundary;
import com.gmail.fuskerr.backend.core.entity.User;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;
import com.gmail.fuskerr.backend.core.game.GameManager;
import com.gmail.fuskerr.backend.repository.JpaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class GameSessionController implements UserConnectionBoundary {
    private GameManager gameManager;
    private JpaUser userGateway;

    @Autowired
    @Qualifier("simpleGameManager")
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Autowired
    public void setUserService(JpaUser userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public void processMessage(MessageActionModel messageAction, String token) {
        User user = userGateway.getUserByToken(token);
        if(messageAction.getTopic() != null && user != null) {
            gameManager.addToInputQueue(messageAction, user.getToken());
        }
    }

    @Override
    public void userConnect(String token) {
        User user = userGateway.getUserByToken(token);
        if(user != null) {
            gameManager.playerIsReady(token);
        }
    }
}
