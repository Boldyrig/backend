package com.gmail.fuskerr.backend.configuration;

import com.gmail.fuskerr.backend.core.boundary.UserConnectionBoundary;
import com.gmail.fuskerr.backend.core.game.GameSessionController;
import com.gmail.fuskerr.backend.core.model.MessageActionModel;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InputActionHandler extends TextWebSocketHandler {
    private final Map<WebSocketSession, String> sessions = new ConcurrentHashMap<>(); // Map : Session -> Token
    private Gson gson = new Gson();

    private UserConnectionBoundary gameSessionController;

    @Autowired
    public void setUserConnectionBoundary(UserConnectionBoundary gameSessionController) {
        this.gameSessionController = gameSessionController;
    }

    public Map<WebSocketSession, String> getSessions() {
        return sessions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String path = session.getUri().getQuery();
        String token = path.substring(path.indexOf("token=") + 6);
        sessions.put(session, token);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }

    //TODO внедрить AOP
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        MessageActionModel json = gson.fromJson(message.getPayload(), MessageActionModel.class);
        switch(json.getTopic()) {
            case MOVE:
            case PLANT_BOMB:
                gameSessionController.processMessage(json, sessions.get(session));
                break;
            case READY:
                gameSessionController.userConnect(sessions.get(session));
                break;
            default:
                // TODO ошибка
                break;
        }
    }
}
