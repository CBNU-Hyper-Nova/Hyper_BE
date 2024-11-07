package org.hypernova.call.service;

import org.hypernova.call.dto.IceServerDto;
import org.hypernova.call.entity.IceServer;
import org.hypernova.call.repository.IceServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IceServerService {
    private final IceServerRepository repository;

    @Autowired
    public IceServerService(IceServerRepository repository) {
        this.repository = repository;
    }

    public IceServerDto addIceServer(IceServerDto dto) {
        IceServer iceServer = new IceServer();
        iceServer.setUrl(dto.getUrl());
        iceServer.setUsername(dto.getUsername());
        iceServer.setCredential(dto.getCredential());

        repository.save(iceServer);
        return dto;
    }

    public List<IceServerDto> getIceServers() {
        return repository.findAll().stream()
                .map(server -> {
                    IceServerDto dto = new IceServerDto();
                    dto.setUrl(server.getUrl());
                    dto.setUsername(server.getUsername());
                    dto.setCredential(server.getCredential());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
