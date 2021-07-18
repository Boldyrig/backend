package com.gmail.fuskerr.backend.controller;

import com.gmail.fuskerr.backend.requestbody.TokenRequest;
import com.gmail.fuskerr.backend.responsebody.GameIdResponse;
import com.gmail.fuskerr.backend.service.MatchMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MatchMakerController {

    private MatchMakerService matchMakerService;

    @Autowired
    public void setMatchMakerService(MatchMakerService matchMakerService) {
        this.matchMakerService = matchMakerService;
    }

    @PostMapping(
            value = "/matchmaker/join",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    @ResponseBody
    public GameIdResponse join(TokenRequest request) throws Exception {
        if(request != null && !request.getToken().isEmpty()) {
            int gameId = matchMakerService.join(request.getToken());
            return new GameIdResponse(gameId);
        }
        throw new Exception("invalid token");
    }
}
