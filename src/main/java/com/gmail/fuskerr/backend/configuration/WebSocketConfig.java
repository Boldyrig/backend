package com.gmail.fuskerr.backend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;

import java.io.IOException;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private InputActionHandler inputActionHandler;

    @Autowired
    public void setInputActionHandler(InputActionHandler inputActionHandler) {
        this.inputActionHandler = inputActionHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(inputActionHandler, "/ws/action");
    }

    @Bean
    public WebSocketSender webSocketSender() {
        return new AbstractTextMessageWebSocketSender(){
            @Override
            public void sendMessageBroadcast(TextMessage message) {
                for(WebSocketSession webSocketSession : inputActionHandler.getSessions().keySet()) {
                    try {
                        webSocketSession.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
