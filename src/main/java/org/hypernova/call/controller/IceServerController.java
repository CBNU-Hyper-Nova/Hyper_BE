package org.hypernova.call.controller;

import lombok.RequiredArgsConstructor;
import org.hypernova.call.service.WebRtcConfigService;
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

    private final WebRtcConfigService webRtcConfigService;

    @GetMapping("/ice-servers")
    public ResponseEntity<List<Map<String, String>>> getIceServers() {
        return ResponseEntity.ok(
                List.of(
                        webRtcConfigService.getStunServerConfig(),
                        webRtcConfigService.getTurnServerConfig()
                )
        );
    }
}