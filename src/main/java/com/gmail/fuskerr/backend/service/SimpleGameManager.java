package com.gmail.fuskerr.backend.service;

import com.gmail.fuskerr.backend.domain.GameSession;
import com.gmail.fuskerr.backend.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Qualifier("simpleGameManager")
public class SimpleGameManager implements GameManager {
    private final Set<GameSession> games = new HashSet<>();
    private final AtomicInteger lastGameId = new AtomicInteger(0);

    @Override
    public GameSession create(int countOfPlayers) {
        GameSession gameSession = new GameSession(lastGameId.getAndIncrement(), countOfPlayers);
        games.add(gameSession);
        return gameSession;
    }

    @Override
    public void start(int gameId) {

    }

    @Override
    public boolean connect(User user, int gameId) {
        for(GameSession game: games) {
            if(game.getGameId() == gameId && game.getCountOfPlayers() - game.getPlayers().size() > 0) {
                game.joinPlayer(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public GameSession getIncompleteGame(int countOfPlayers) {
        for(GameSession game: games) {
            if(game.getCountOfPlayers() - game.getPlayers().size() > 0) {
                return game;
            }
        }
        return null;
    }

    @Override
    public Set<GameSession> getGames() {
        return games;
    }
}
