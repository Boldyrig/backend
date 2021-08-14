package com.gmail.fuskerr.backend.core.game;

import com.gmail.fuskerr.backend.core.type.ItemType;
import com.gmail.fuskerr.backend.core.entity.Position;
import com.gmail.fuskerr.backend.core.entity.ReplicaItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SimpleRandomReplicaItemManager implements ReplicaManager {
    private List<ReplicaItem> replica = new ArrayList<>();

    @Override
    public List<ReplicaItem> getReplica() {
        if(replica.isEmpty()) {
            //fillReplica(864, 544, replica);
            fillReplica(100, 100, replica);
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
