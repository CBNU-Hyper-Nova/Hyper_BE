package org.hypernova.call.controller;

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
@RequestMapping("/api/call")
public class IceServerController {

    @GetMapping("/ice-servers")
    public ResponseEntity<List<Map<String, String>>> getIceServers() {
        List<Map<String, String>> iceServers = new ArrayList<>();

        Map<String, String> stunServer = new HashMap<>();
        stunServer.put("urls", "stun:stun.l.google.com:19302");
        iceServers.add(stunServer);

        Map<String, String> turnServer = new HashMap<>();
        turnServer.put("urls", "turn:localhost:3478");
        turnServer.put("username", "hypernova");
        turnServer.put("credential", "nova2777*");
        iceServers.add(turnServer);

        return ResponseEntity.ok(iceServers);
    }
}
