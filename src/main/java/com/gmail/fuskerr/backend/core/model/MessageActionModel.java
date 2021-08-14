package com.gmail.fuskerr.backend.core.model;

import com.gmail.fuskerr.backend.core.type.Direction;
import com.gmail.fuskerr.backend.core.type.Topic;

public class MessageActionModel {
    private final Topic topic;
    private final Direction direction;
    private final String data;

    public MessageActionModel(Topic topic, Direction direction, String data) {
        this.topic = topic;
        this.direction = direction;
        this.data = data;
    }

    public Topic getTopic() {
        return topic;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "MessageAction{" +
                "topic=" + topic +
                ", direction=" + direction +
                ", data='" + data + '\'' +
                '}';
    }
}
