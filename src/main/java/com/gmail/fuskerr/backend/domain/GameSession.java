package com.gmail.fuskerr.backend.domain;

import com.gmail.fuskerr.backend.requestbody.MessageAction;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GameSession {
    private final int gameId;
    private final int countOfPlayers;
    private boolean finished = true;
    private Set<User> players = new HashSet<>();
    private final Queue<MessageAction> inputQueue = new ConcurrentLinkedQueue<>();

    public GameSession(int gameId, int countOfPlayers) {
        this.gameId = gameId;
        this.countOfPlayers = countOfPlayers;
    }

    public void joinPlayer(User user) {
        if(user != null) {
            players.add(user);
        }
    }

    public void addInputQueue(MessageAction action) {
        inputQueue.add(action);
    }

    public MessageAction[] pullActionsFromQueue() {
        MessageAction[] result = new MessageAction[inputQueue.size()];
        for(int i = 0; i < inputQueue.size(); i++) {
            result[i] = inputQueue.poll();
        }
        return result;
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

    public boolean isFinished() {
        return finished;
    }
}
