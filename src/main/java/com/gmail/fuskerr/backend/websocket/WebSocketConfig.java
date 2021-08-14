package com.gmail.fuskerr.backend.websocket;

import com.gmail.fuskerr.backend.core.gateway.MessageSender;
import com.gmail.fuskerr.backend.core.entity.ReplicaItem;
import com.gmail.fuskerr.backend.core.type.Topic;
import com.gmail.fuskerr.backend.core.model.MessageReplicaModel;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private InputActionHandler inputActionHandler;
    
    private final Gson gson = new Gson();

    @Autowired
    public void setInputActionHandler(InputActionHandler inputActionHandler) {
        this.inputActionHandler = inputActionHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(inputActionHandler, "/ws/action");
    }

    @Bean
    public MessageSender webSocketSender() {
        return new MessageSender() {
            @Override
            public void sendMessage(List<ReplicaItem> message, String token) {
                for(Map.Entry<WebSocketSession, String> entry : inputActionHandler.getSessions().entrySet()) {
                    if(entry.getValue().equals(token)) {
                        MessageReplicaModel response = new MessageReplicaModel(Topic.REPLICA, message);
                        TextMessage wsMessage = new TextMessage(gson.toJson(response));
                        try {
                            entry.getKey().sendMessage(wsMessage);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void sendMessage(List<ReplicaItem> message, Set<String> tokens) {
                tokens.forEach(token -> {
                    for(Map.Entry<WebSocketSession, String> entry : inputActionHandler.getSessions().entrySet()) {
                        if(token.equals(entry.getValue())) {
                            MessageReplicaModel response = new MessageReplicaModel(Topic.REPLICA, message);
                            TextMessage wsMessage = new TextMessage(gson.toJson(response));
                            try {
                                entry.getKey().sendMessage(wsMessage);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
    }
}
