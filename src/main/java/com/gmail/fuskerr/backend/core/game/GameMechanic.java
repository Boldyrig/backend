package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.entity.*;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;

import java.util.ArrayList;
import java.util.List;

public class GameMechanic implements Tickable {
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
            }
        });
        
        List<Bomb> removeBombs = new ArrayList<>();
        replicator.getBombs().forEach(bomb -> {
            if(!bomb.isExplosion()) return;
            int x = bomb.getPosition().getX();
            int y = bomb.getPosition().getX();
            for(int i = -bomb.getPower(); i < bomb.getPower(); i++) {
                Position position1 = new Position(x + i, y);
                Position position2 = new Position(x, y + i);
                gameSession.registerTickable(replicator.addFire(position1));
                gameSession.registerTickable(replicator.addFire(position2));
                ReplicaItem itemToDelete1 = replicator.getReplicaItemByPosition(position1);
                ReplicaItem itemToDelete2 = replicator.getReplicaItemByPosition(position2);
                if(itemToDelete1 instanceof Pawn) {
                    Pawn pawn = (Pawn) itemToDelete1;
                    pawn.setAlive(false);
                } else {
                    replicator.removeWood(itemToDelete1);
                }
                if(itemToDelete2 instanceof Pawn) {
                    Pawn pawn = (Pawn) itemToDelete2;
                    pawn.setAlive(false);
                } else {
                    replicator.removeWood(itemToDelete2);
                }
            }
            removeBombs.add(bomb);
            gameSession.unregisterTickable(bomb);
        });
        replicator.removeAllBombs(removeBombs);

        List<Fire> removeFires = new ArrayList<>();
        replicator.getFires().forEach(fire -> {
            if(fire.isBurn()) return;
            removeFires.add(fire);
            gameSession.unregisterTickable(fire);
        });
        replicator.removeAllFires(removeFires);
    }
}
