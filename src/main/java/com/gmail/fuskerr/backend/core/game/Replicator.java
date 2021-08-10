package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.type.Direction;
import com.gmail.fuskerr.backend.core.type.ItemType;
import com.gmail.fuskerr.backend.core.entity.Pawn;
import com.gmail.fuskerr.backend.core.entity.Position;
import com.gmail.fuskerr.backend.core.entity.ReplicaItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Replicator {
    private final List<ReplicaItem> replica = new ArrayList<>();
    // В changedReplica только изменившиеся объекты (так ожидает клиент)
    private final List<ReplicaItem> changedReplica = new ArrayList<>();
    private final List<ReplicaItem> bombs = new ArrayList<>();
    private final List<Pawn> pawns = new ArrayList<>();
    
    private int id = 0;
    
    private final int WIDTH = 800;
    private final int HEIGHT = 512;
    
    private final int TILE_SIZE = 32;
    
    private final Position spawnA = new Position(32, 32);
    private final Position spawnB = new Position(768, 480);
    private List<Position> spawns;

    private final double PERCENT = 0.1;
    
    public List<ReplicaItem> getReplica() {
        return replica;
    }

    public void init(List<Position> spawns) {
        this.spawns = spawns;
        Random random = new Random();
        
        for(int x = 0; x < WIDTH; x += TILE_SIZE) {
            for(int y = 0; y < HEIGHT; y += TILE_SIZE) {
                Position position = new Position(x, y);
                if(spawns.contains(position)) continue;
                if(random.nextDouble() < PERCENT) {
                    replica.add(new ReplicaItem(
                        id++,
                        ItemType.WOOD,
                        position
                    ));
                }
            }
        }
    }
    
    public void movePawn(String token, Direction direction) {
        pawns.forEach(pawn -> {
            if(pawn.isAlive() && pawn.getToken().equals(token)) {
                pawn.setDirection(direction);
                int x = pawn.getPosition().getX();
                int y = pawn.getPosition().getY();
                switch(direction) {
                    case UP:
                        y++;
                        break;
                    case DOWN:
                        y--;
                        break;
                    case RIGHT:
                        x++;
                        break;
                    case LEFT:
                        x--;
                        break;
                    default:
                        //
                        break;
                }
                Position newPosition = new Position(x, y);
                if(x > 0 && x < WIDTH &&
                        y > 0 && y < HEIGHT &&
                        isEmpty(newPosition)) {
                    pawn.setPosition(newPosition);
                }
            }
        });
    }
    
    public void addPawn(String token) {
        for(int i = 0; i < spawns.size(); i++) {
            if(isEmpty(spawns.get(i))) {
                pawns.add(new Pawn(
                    id++,
                    ItemType.PAWN,
                    spawns.get(i),
                    Direction.UP,
                    true,
                    token
                ));
                break;
            }
        }
    }
    
    public void addBomb(String token) {
        for(Pawn pawn : pawns) {
            if(pawn.isAlive() && pawn.getToken().equals(token)) {
                ReplicaItem bomb = new ReplicaItem(
                    id++,
                    ItemType.BOMB,
                    pawn.getPosition());
                bombs.add(bomb);
                break;
            }
        }
    }
    
    public List<ReplicaItem> getChangedReplica() {
        pawns.forEach(pawn -> {
            changedReplica.add(pawn);
        });
        bombs.forEach(bomb -> {
            changedReplica.add(bomb);
        });
        return changedReplica;
    }
    
    public void clearChangedReplica() {
        changedReplica.clear();
    }
    
    // В реплике находятся только объекты, в ней нет "пустот"
    // Поэтому если есть Position в реплике, то место не свободно
    private boolean isEmpty(Position target) {
        for(ReplicaItem item : replica) {
            Position itemPosition = item.getPosition();
            int itemX = itemPosition.getX();
            int itemY = itemPosition.getY();
            int targetX = target.getX();
            int targetY = target.getY();
            
            boolean bottomLeftPointInItem = 
                    targetX > itemX &&
                    targetX < itemX + TILE_SIZE &&
                    targetY > itemY &&
                    targetY < itemY + TILE_SIZE;
            boolean bottomRightPointInItem = 
                    targetX + TILE_SIZE > itemX &&
                    targetX + TILE_SIZE < itemX + TILE_SIZE &&
                    targetY > itemY &&
                    targetY < itemY + TILE_SIZE;
            boolean topLeftPointInItem = 
                    targetX > itemX &&
                    targetX < itemX + TILE_SIZE &&
                    targetY + TILE_SIZE > itemY &&
                    targetY + TILE_SIZE < itemY + TILE_SIZE;
            boolean topRightPointInItem = 
                    targetX + TILE_SIZE > itemX &&
                    targetX + TILE_SIZE < itemX + TILE_SIZE &&
                    targetY + TILE_SIZE > itemY &&
                    targetY + TILE_SIZE < itemY + TILE_SIZE;
            
            if(bottomLeftPointInItem ||
                    bottomRightPointInItem ||
                    topLeftPointInItem ||
                    topRightPointInItem) {
                return false;
            }
        }
        return true;
    }
}
