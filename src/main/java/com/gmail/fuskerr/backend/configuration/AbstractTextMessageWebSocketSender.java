package com.gmail.fuskerr.backend.configuration;

import org.springframework.web.socket.TextMessage;

public abstract class AbstractTextMessageWebSocketSender implements WebSocketSender {
    @Override
    public void sendMessage(TextMessage message) {

    }
}
