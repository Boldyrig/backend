package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.entity.Player;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;

import java.util.Set;

public interface GameManager {
    void start(int gameId);
    //returns gameId
    int connect(Player player);
    Set<GameSession> getGames();
    void addToInputQueue(MessageActionModel messageAction, String token);
    void playerIsReady(String playerToken);
}
