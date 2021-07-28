package com.gmail.fuskerr.backend.requestbody;

import com.gmail.fuskerr.backend.domain.Action;
import com.gmail.fuskerr.backend.domain.Topic;

public class MessageAction {
    private final Topic topic;
    private final Action action;
    private final String data;

    public MessageAction(Topic topic, Action action, String data) {
        this.topic = topic;
        this.action = action;
        this.data = data;
    }

    public Topic getTopic() {
        return topic;
    }

    public Action getAction() {
        return action;
    }

    public String getData() {
        return data;
    }
}
