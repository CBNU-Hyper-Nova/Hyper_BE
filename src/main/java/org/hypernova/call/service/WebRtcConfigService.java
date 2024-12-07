package org.hypernova.call.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WebRtcConfigService {

    @Value("${webrtc.stun.url}")
    private String stunUrl;

    @Value("${webrtc.turn.url}")
    private String turnUrl;

    @Value("${webrtc.turn.username}")
    private String turnUsername;

    @Value("${webrtc.turn.password}")
    private String turnPassword;

    public Map<String, String> getStunServerConfig() {
        Map<String, String> stunConfig = new HashMap<>();
        stunConfig.put("urls", stunUrl);
        return stunConfig;
    }

    public Map<String, String> getTurnServerConfig() {
        Map<String, String> turnConfig = new HashMap<>();
        turnConfig.put("urls", turnUrl);
        turnConfig.put("username", turnUsername);
        turnConfig.put("credential", turnPassword);
        return turnConfig;
    }
}