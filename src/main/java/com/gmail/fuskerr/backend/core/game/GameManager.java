package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.entity.Player;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;

import java.util.Set;

public interface GameManager {
    GameSession addGameSession(int countOfPlayers);
    void start(int gameId);
    boolean connect(Player player, int gameId);
    GameSession getIncompleteGame();
    Set<GameSession> getGames();
    void addToInputQueue(MessageActionModel messageAction, String token);
    void playerIsReady(String playerToken);
}
