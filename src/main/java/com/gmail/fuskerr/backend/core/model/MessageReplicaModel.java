package com.gmail.fuskerr.backend.core.model;

import com.gmail.fuskerr.backend.core.entity.ReplicaItem;
import com.gmail.fuskerr.backend.core.type.Topic;
import java.util.List;

public class MessageReplicaModel {
    private final Topic topic;
    private final List<ReplicaItem> data;

    public MessageReplicaModel(Topic topic, List<ReplicaItem> data) {
        this.topic = topic;
        this.data = data;
    }

    public Topic getTopic() {
        return topic;
    }

    public List<ReplicaItem> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "WebSocketResponse{" + "topic=" + topic + ", data=" + data + '}';
    }
}
