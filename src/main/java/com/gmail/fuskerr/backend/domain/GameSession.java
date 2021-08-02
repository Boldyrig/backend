package com.gmail.fuskerr.backend.domain;

import com.gmail.fuskerr.backend.configuration.WebSocketReplicaSender;
import com.gmail.fuskerr.backend.requestbody.MessageAction;
import com.gmail.fuskerr.backend.service.ReplicaManager;
import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;

public class GameSession {
    private final int gameId;
    private final int countOfPlayers;
    private boolean finished = true;
    private Set<User> players = ConcurrentHashMap.newKeySet();
    private final Queue<MessageAction> inputQueue = new ConcurrentLinkedQueue<>();
    
    private List<ReplicaItem> replica = new ArrayList<>();
    
    public GameSession(int gameId, int countOfPlayers) {
        this.gameId = gameId;
        this.countOfPlayers = countOfPlayers;
    }
    
    public void startGame(ConsumerList<ReplicaItem> consumer) {
        if(countOfPlayers > players.size()) return;
        consumer.accept(getReplica());
        Thread thread = new Thread();
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
    
    public List<ReplicaItem> getReplica() {
        if(replica.isEmpty()) {
            fillReplica(20, 20, replica);
        }
        return replica;
    }

    private void fillReplica(int width, int height, List<ReplicaItem> replica) {
        int id = 0;
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                replica.add(new ReplicaItem(
                        id,
                        ItemType.WOOD,
                        new Position(x, y)
                ));
                id++;
            }
        }
        replica.add(new Pawn(
                id,
                ItemType.PAWN,
                new Position(30, 30),
                Direction.RIGHT,
                true
        ));
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
