package com.gmail.fuskerr.backend.configuration;

import com.gmail.fuskerr.backend.domain.ReplicaItem;
import com.gmail.fuskerr.backend.domain.User;
import java.util.List;
import java.util.Set;
import org.springframework.web.socket.TextMessage;

public interface WebSocketReplicaSender {
    void sendMessage(List<ReplicaItem> message,  String token);
    void sendMessage(List<ReplicaItem> message, Set<User> users);
    void sendMessageBroadcast(TextMessage message);
}
