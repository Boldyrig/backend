package com.gmail.fuskerr.backend.core.model;

public class GameIdModel {
    private final int gameId;

    public GameIdModel(int gameId) {
        this.gameId = gameId;
    }

    public int getGameId() {
        return gameId;
    }

    @Override
    public String toString() {
        return "GameIdResponse{" +
                "gameId=" + gameId +
                '}';
    }
}
