package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.entity.Bomb;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;
import java.util.ArrayList;
import java.util.List;

public class GameMechanic implements Tickable {
    private GameManager gameManager;
    private final Replicator replicator;
    private final GameSession gameSession;
    
    public GameMechanic(Replicator replicator, GameSession gameSession) {
        this.replicator = replicator;
        this.gameSession = gameSession;
    }

    @Override
    public void tick() {
        List<MessageActionModel> actions = gameSession.pullActionsFromQueue();
        actions.forEach(action -> {
            switch(action.getTopic()) {
                case MOVE:
                    replicator.movePawn(action.getData(), action.getDirection());
                    break;
                case PLANT_BOMB:
                    Tickable bomb = replicator.addBomb(action.getData());
                    gameSession.registerTickable(bomb);
                    break;
                default:
                    break;
            }
        });
        
        List<Bomb> removeBombs = new ArrayList<>();
        replicator.getBombs().forEach(bomb -> {
            if(!bomb.isExplosion()) return;
            removeBombs.add(bomb);
        });
        replicator.removeAllBombs(removeBombs);
    }
}
