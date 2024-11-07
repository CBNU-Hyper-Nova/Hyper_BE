package org.hypernova.call.config;

import org.hypernova.call.dto.IceServerDto;
import org.hypernova.call.service.IceServerService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IceServerConfig {
    @Bean
    public ApplicationRunner initializeIceServers(IceServerService service) {
        return args -> {
            IceServerDto stunServer = new IceServerDto();
            stunServer.setUrl("stun:stun.l.google.com:19302");
            stunServer.setUsername(null);
            stunServer.setCredential(null);

            service.addIceServer(stunServer);
        };
    }
}
