package org.hypernova.call.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/call")
@RequiredArgsConstructor
public class IceServerController {

    @Value("${webrtc.stun.url}")
    private String stunServerUrl;

    @Value("${webrtc.turn.url}")
    private String turnServerUrl;

    @Value("${webrtc.turn.username}")
    private String turnUsername;

    @Value("${webrtc.turn.password}")
    private String turnPassword;

    @GetMapping("/ice-servers")
    public ResponseEntity<List<Map<String, Object>>> getIceServers() {
        List<Map<String, Object>> iceServers = new ArrayList<>();

        // STUN 서버 설정
        Map<String, Object> stunServer = new HashMap<>();
        stunServer.put("urls", stunServerUrl);
        iceServers.add(stunServer);

        // TURN 서버 설정
        Map<String, Object> turnServer = new HashMap<>();
        turnServer.put("urls", turnServerUrl);
        turnServer.put("username", turnUsername);
        turnServer.put("credential", turnPassword);
        iceServers.add(turnServer);

        return ResponseEntity.ok(iceServers);
    }
}