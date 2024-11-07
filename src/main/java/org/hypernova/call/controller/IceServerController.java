package org.hypernova.call.controller;

import org.hypernova.call.dto.IceServerDto;
import org.hypernova.call.service.IceServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/iceservers")
public class IceServerController {
    private final IceServerService service;

    @Autowired
    public IceServerController(IceServerService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public IceServerDto addIceServer(@RequestBody IceServerDto dto) {
        return service.addIceServer(dto);
    }

    @GetMapping("/list")
    public List<IceServerDto> getIceServers() {
        return service.getIceServers();
    }
}
