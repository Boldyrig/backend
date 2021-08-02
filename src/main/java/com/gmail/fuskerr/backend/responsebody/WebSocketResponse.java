package com.gmail.fuskerr.backend.responsebody;

import com.gmail.fuskerr.backend.domain.ReplicaItem;
import com.gmail.fuskerr.backend.domain.Topic;
import java.util.List;

public class WebSocketResponse {
    private final Topic topic;
    private final List<ReplicaItem> data;

    public WebSocketResponse(Topic topic, List<ReplicaItem> data) {
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
