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
                    if(bomb != null) {
                        gameSession.registerTickable(bomb);
                    }
                    break;
            }
        });
        
        List<Bomb> removeBombs = new ArrayList<>();
        replicator.getBombs().forEach(bomb -> {
            if(!bomb.isExplosion()) return;
            for(int i = 0; i <= bomb.getPower(); i++) {
                for(ExplosionSide side : bomb.getSides()) {
                    if(side.isStopped()) continue;
                    int newX = bomb.getPosition().getX();;
                    int newY = bomb.getPosition().getY();
                    switch(side.getDirection()) {
                        case UP:
                            newY += i * Replicator.TILE_SIZE;
                            break;
                        case DOWN:
                            newY -= i * Replicator.TILE_SIZE;
                            break;
                        case RIGHT:
                            newX += i * Replicator.TILE_SIZE;
                            break;
                        case LEFT:
                            newX -= i * Replicator.TILE_SIZE;
                            break;
                    }
                    Position newPosition = new Position(newX, newY);
                    gameSession.registerTickable(replicator.addFire(newPosition));
                    ReplicaItem itemToDelete = replicator.getReplicaItemByPosition(
                                    new Position(newX + Replicator.TILE_SIZE/2, newY + Replicator.TILE_SIZE/2)
                    );
                    if(itemToDelete != null) side.stop();
                    if(itemToDelete instanceof Pawn) {
                        Pawn pawn = (Pawn) itemToDelete;
                        pawn.setAlive(false);
                    } else {
                        replicator.removeWood(itemToDelete);
                    }
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
