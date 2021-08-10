package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.boundary.MatchMakerBoundary;
import com.gmail.fuskerr.backend.core.entity.User;
import com.gmail.fuskerr.backend.core.gateway.UserRepositoryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MatchMakerService implements MatchMakerBoundary {
    private static final int DEFAULT_COUNT_OF_PLAYERS_IN_GAME = 1;

    private UserRepositoryGateway userRepository;
    private GameManager gameManager;

    private UserMapper userMapper;

    @Autowired
    public void setUserRepositoryGateway(UserRepositoryGateway userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setGameManager(
            @Qualifier("simpleGameManager") GameManager gameManager
    ) {
        this.gameManager = gameManager;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int join(String token) {
        User user = userRepository.getUserByToken(token);
//        if(user == null) {
//            throw new Exception("invalid token");
//        }
        GameSession game = gameManager.getIncompleteGame();
        if(game == null) {
            game = gameManager.addGameSession(DEFAULT_COUNT_OF_PLAYERS_IN_GAME);
        }
        gameManager.connect(userMapper.create(user), game.getGameId());
        return game.getGameId();
    }
}