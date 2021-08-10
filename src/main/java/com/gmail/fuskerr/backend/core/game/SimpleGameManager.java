package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.entity.ReplicaItem;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import com.gmail.fuskerr.backend.core.gateway.MessageSender;
import com.gmail.fuskerr.backend.core.entity.Player;

@Component
@Qualifier("simpleGameManager")
public class SimpleGameManager implements GameManager {
    private final Set<GameSession> games = new HashSet<>();
    private final AtomicInteger lastGameId = new AtomicInteger(0);
    
    private MessageSender sender;

    @Autowired
    public void setSender(MessageSender sender) {
        this.sender = sender;
    }
    
    @Override
    public GameSession addGameSession(int countOfPlayers) {
        GameSession gameSession = new GameSession(lastGameId.getAndIncrement(), countOfPlayers);
        games.add(gameSession);
        return gameSession;
    }

    @Override
    public void start(int gameId) {
        for(GameSession session : games) {
            if(session.getGameId() == gameId) {
                session.startGame((List<ReplicaItem> replica) -> {
                    sender.sendMessage(replica, session.getTokens());
                });
            }
        }
    }

    @Override
    public boolean connect(Player player, int gameId) {
        for(GameSession game: games) {
            if(game.getGameId() == gameId && game.getCountOfPlayers() - game.getPlayers().size() > 0) {
                game.joinPlayer(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public GameSession getIncompleteGame() {
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

    @Override
    public void addToInputQueue(MessageActionModel messageAction, String token) {
        for(GameSession gameSession : games) {
            if(gameSession.isFinished()) break;
            if(gameSession.getPlayer(token) != null) {
                gameSession.addInputQueue(messageAction);
                break;
            }
        }
    }

    @Override
    public void playerIsReady(String playerToken) {
        for(GameSession game : games) {
            Player player = game.getPlayer(playerToken);
            if(player != null) {
                player.setReady(true);
                if(game.isAllReady()) {
                    Set<String> tokens = game.getTokens();
                    game.startGame((List<ReplicaItem> replica) -> {
                        sender.sendMessage(replica, tokens);
                    });
                }
                break;
            }
        }
    }
    
    
}
