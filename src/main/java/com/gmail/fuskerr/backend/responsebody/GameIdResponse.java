package com.gmail.fuskerr.backend.responsebody;

public class GameIdResponse {
    private final int gameId;

    public GameIdResponse(int gameId) {
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
