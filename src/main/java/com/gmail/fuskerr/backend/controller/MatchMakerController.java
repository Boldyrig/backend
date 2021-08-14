package com.gmail.fuskerr.backend.controller;

import com.gmail.fuskerr.backend.core.boundary.MatchMakerBoundary;
import com.gmail.fuskerr.backend.core.model.TokenModel;
import com.gmail.fuskerr.backend.core.model.GameIdModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchMakerController {

    private MatchMakerBoundary matchMakerBoundary;

    @Autowired
    public void setMatchMakerService(MatchMakerBoundary matchMakerBoundary) {
        this.matchMakerBoundary = matchMakerBoundary;
    }

    @PostMapping(
            value = "/matchmaker/join",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseBody
    public GameIdModel join(TokenModel request) throws Exception {
        if(request != null && !request.getToken().isEmpty()) {
            int gameId = matchMakerBoundary.join(request.getToken());
            return new GameIdModel(gameId);
        }
        throw new Exception("invalid token");
    }
}
