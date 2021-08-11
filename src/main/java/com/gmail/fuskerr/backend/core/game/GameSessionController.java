package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.boundary.UserConnectionBoundary;
import com.gmail.fuskerr.backend.core.entity.User;
import com.gmail.fuskerr.backend.core.gateway.UserRepositoryGateway;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GameSessionController implements UserConnectionBoundary {
    private GameManager gameManager;
    private UserRepositoryGateway userGateway;

    @Autowired
    @Qualifier("simpleGameManager")
    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Autowired
    public void setUserService(UserRepositoryGateway userGateway) {
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
