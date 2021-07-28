package com.gmail.fuskerr.backend.configuration;

import com.gmail.fuskerr.backend.requestbody.MessageAction;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new InputActionHandler(), "/ws/action");
    }

    @Bean
    public WebSocketSender webSocketSender() {
        return new AbstractTextMessageWebSocketSender(){
            @Override
            public void sendMessageBroadcast(TextMessage message) {
                for(WebSocketSession webSocketSession : sessions.values()) {
                    try {
                        webSocketSession.sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private class InputActionHandler extends TextWebSocketHandler {
        private Gson gson = new Gson();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            super.afterConnectionEstablished(session);
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
            super.afterConnectionClosed(session, status);
        }

        //TODO внедрить AOP
        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            MessageAction json = gson.fromJson(message.getPayload(), MessageAction.class);
            switch(json.getTopic()) {
                case TOKEN:
                    sessions.put(json.getData(), session);
                    break;
                default:
                    // TODO ошибка
                    break;
            }
            for(WebSocketSession webSocketSession : sessions.values()) {
                webSocketSession.sendMessage(new TextMessage("HI EVERYAONE!"));
            }
            session.sendMessage(new TextMessage("hi"));
        }
    }
}
