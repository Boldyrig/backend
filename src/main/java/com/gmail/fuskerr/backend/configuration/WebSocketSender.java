package com.gmail.fuskerr.backend.configuration;

import org.springframework.web.socket.TextMessage;

public interface WebSocketSender {
    void sendMessage(TextMessage message);
    void sendMessageBroadcast(TextMessage message);
}
