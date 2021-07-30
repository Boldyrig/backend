package com.gmail.fuskerr.backend.service;

import com.gmail.fuskerr.backend.domain.ItemType;
import com.gmail.fuskerr.backend.domain.Position;
import com.gmail.fuskerr.backend.domain.ReplicaItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleRandomReplicaItemManager implements ReplicaManager {
    private List<ReplicaItem> replica = new ArrayList<>();

    @Override
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
    }
}
