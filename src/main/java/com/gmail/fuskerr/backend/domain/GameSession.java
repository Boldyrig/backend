package com.gmail.fuskerr.backend.domain;

import java.util.HashSet;
import java.util.Set;

public class GameSession {
    private final int gameId;
    private final int countOfPlayers;
    private Set<User> players = new HashSet<>();

    public GameSession(int gameId, int countOfPlayers) {
        this.gameId = gameId;
        this.countOfPlayers = countOfPlayers;
    }

    public void joinPlayer(User user) {
        if(user != null) {
            players.add(user);
        }
    }

    public int getGameId() {
        return gameId;
    }

    public int getCountOfPlayers() {
        return countOfPlayers;
    }

    public Set<User> getPlayers() {
        return players;
    }
}
