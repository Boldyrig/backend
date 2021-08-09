package com.gmail.fuskerr.backend.service;

import com.gmail.fuskerr.backend.domain.GameSession;
import com.gmail.fuskerr.backend.domain.User;
import com.gmail.fuskerr.backend.requestbody.MessageAction;

import java.util.Set;

public interface GameManager {
    GameSession create(int countOfPlayers);
    void start(int gameId);
    boolean connect(User user, int gameId);
    GameSession getIncompleteGame();
    Set<GameSession> getGames();
    void addToInputQueue(MessageAction messageAction, String token);
    void playerIsReady(String userTOken);
}
