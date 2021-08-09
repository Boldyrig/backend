package com.gmail.fuskerr.backend.service;

import com.gmail.fuskerr.backend.domain.GameSession;
import com.gmail.fuskerr.backend.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MatchMakerService {
    private final int DEFAULT_COUNT_OF_PLAYERS_IN_GAME = 1;

    private UserService userService;
    private GameManager gameManager;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGameManager(
            @Qualifier("simpleGameManager") GameManager gameManager
    ) {
        this.gameManager = gameManager;
    }

    public int join(String token) throws Exception {
        User user = userService.getUserByToken(token);
        if(user == null) {
            throw new Exception("invalid token");
        }
        GameSession game = gameManager.getIncompleteGame();
        if(game == null) {
            game = gameManager.create(DEFAULT_COUNT_OF_PLAYERS_IN_GAME);
        }
        gameManager.connect(user, game.getGameId());
        return game.getGameId();
    }
}
