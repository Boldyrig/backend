package com.gmail.fuskerr.backend.service;

import com.gmail.fuskerr.backend.domain.GameSession;
import com.gmail.fuskerr.backend.domain.ReplicaItem;
import com.gmail.fuskerr.backend.domain.Topic;
import com.gmail.fuskerr.backend.domain.User;
import com.gmail.fuskerr.backend.requestbody.MessageAction;
import com.gmail.fuskerr.backend.responsebody.WebSocketResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import com.gmail.fuskerr.backend.configuration.WebSocketReplicaSender;
import com.gmail.fuskerr.backend.domain.Player;
import org.springframework.web.socket.TextMessage;

@Component
@Qualifier("simpleGameManager")
public class SimpleGameManager implements GameManager {
    private final Set<GameSession> games = new HashSet<>();
    private final AtomicInteger lastGameId = new AtomicInteger(0);
    
    private WebSocketReplicaSender sender;

    @Autowired
    public void setSender(WebSocketReplicaSender sender) {
        this.sender = sender;
    }
    
    @Override
    public GameSession create(int countOfPlayers) {
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
    public boolean connect(User user, int gameId) {
        for(GameSession game: games) {
            if(game.getGameId() == gameId && game.getCountOfPlayers() - game.getPlayers().size() > 0) {
                game.joinPlayer(new Player(
                        user.getId(),
                        user.getName(),
                        user.getToken()
                ));
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
    public void addToInputQueue(MessageAction messageAction, String token) {
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
