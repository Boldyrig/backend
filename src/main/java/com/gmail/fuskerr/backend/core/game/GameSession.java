package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.ConsumerList;
import com.gmail.fuskerr.backend.core.entity.Player;
import com.gmail.fuskerr.backend.core.entity.Position;
import com.gmail.fuskerr.backend.core.entity.ReplicaItem;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;
import java.util.ArrayList;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class GameSession {
    private final int gameId;
    private final int countOfPlayers;
    private boolean finished = true;
    private Set<Player> players = ConcurrentHashMap.newKeySet();
    private final Queue<MessageActionModel> inputQueue = new ConcurrentLinkedQueue<>();
    
    private final List<Tickable> tickables = new CopyOnWriteArrayList<>();
    
    private final Replicator replicator = new Replicator();
    
    private final long MILLISECONDS_IN_SECOND = 1000;
    private final int FRAME_PER_SECOND = 60;
    private final long MILLISECONDS_IN_FRAME = MILLISECONDS_IN_SECOND / FRAME_PER_SECOND;
    
    private List<ReplicaItem> replica = new ArrayList<>();
    private final int WIDTH = 80;
    private final int HEIGHT = 50;
    
    public GameSession(int gameId, int countOfPlayers) {
        this.gameId = gameId;
        this.countOfPlayers = countOfPlayers;
    }
    
    public void startGame(ConsumerList<ReplicaItem> consumer) {
        if(countOfPlayers > players.size()) return;
        finished = false;
        replicator.init(List.of(new Position(40, 40), new Position(760, 460)));
        players.forEach(user -> {
            replicator.addPawn(user.getToken());
        });
        registerTickable(new GameMechanic(replicator, this));
        Thread thread = new Thread(() -> {
            consumer.accept(replicator.getReplica());
            while(!finished) {
                long startTime = System.currentTimeMillis();
                act();
                consumer.accept(replicator.getChangedReplica());
                replicator.clearChangedReplica();
                long endTime = System.currentTimeMillis();
                // Оставшееся время до конца текущего фрейма
                long lastTime = MILLISECONDS_IN_FRAME - (endTime - startTime);
                if(lastTime > 0) {
                    try {
                        Thread.sleep(lastTime);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }, "GameThread - " + gameId);
        thread.start();
    }

    public void joinPlayer(Player player) {
        if(player != null) {
            players.add(player);
        }
    }
    
    public Player getPlayer(String token) {
        for(Player player : players) {
            if(player.getToken().equals(token)) {
                return player;
            }
        }
        return null;
    }
    
    public boolean isAllReady() {
        for(Player player : players) {
            if(!player.isReady()) return false;
        }
        return true;
    }

    public void addInputQueue(MessageActionModel action) {
        inputQueue.add(action);
    }

    public List<MessageActionModel> pullActionsFromQueue() {
        List<MessageActionModel> result = new ArrayList<>();
        while(!inputQueue.isEmpty()) {
            result.add(inputQueue.poll());
        }
        return result;
    }

    public int getGameId() {
        return gameId;
    }

    public int getCountOfPlayers() {
        return countOfPlayers;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public boolean isFinished() {
        return finished;
    }
    
    public Set<String> getTokens() {
        return players.stream()
                .map(Player::getToken)
                .collect(Collectors.toSet());
    }
    
    public void registerTickable(Tickable tickable) {
        if(tickable != null) {
            tickables.add(tickable);
        }
    }
    
    public void unregisterTickable(Tickable tickable) {
        tickables.remove(tickable);
    }
    
    private void act() {
        tickables.forEach(Tickable::tick);
    }
}
