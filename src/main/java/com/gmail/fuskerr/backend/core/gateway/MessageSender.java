package com.gmail.fuskerr.backend.core.gateway;

import com.gmail.fuskerr.backend.core.entity.ReplicaItem;

import java.util.List;
import java.util.Set;

public interface MessageSender {
    void sendMessage(List<ReplicaItem> message,  String token);
    void sendMessage(List<ReplicaItem> message, Set<String> tokens);
}
