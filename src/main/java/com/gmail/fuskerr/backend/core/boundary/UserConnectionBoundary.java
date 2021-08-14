package com.gmail.fuskerr.backend.core.boundary;

import com.gmail.fuskerr.backend.core.model.MessageActionModel;

public interface UserConnectionBoundary {
    void processMessage(MessageActionModel messageActionModel, String token);
    void userConnect(String token);
}
