package org.hypernova.call.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커 활성화
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/queue", "/topic"); // 서버 -> 클라이언트로 메시지를 전달하는 목적지 경로
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트 -> 서버로 전송하는 메시지 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 클라이언트는 ws://[서버 주소]/ws에 연결하여 WebSocket 세션을 시작
        // 모든 출처(origin)에서의 접근을 허용하여 CORS 문제를 방지
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS(); // 클라이언트가 WebSocket에 연결을 시작하는 엔드포인트
    }
}
