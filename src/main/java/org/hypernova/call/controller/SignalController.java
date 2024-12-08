package org.hypernova.call.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.hypernova.call.dto.SignalMessage;

@Controller
@RequiredArgsConstructor
public class SignalController {
    private static final Logger logger = LoggerFactory.getLogger(SignalController.class);
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/message")
    public void handleMessage(SignalMessage message) {
        try {
            logger.info("메시지 수신: {}", message);
            
            // 메시지 타입에 따른 처리
            switch (message.getType()) {
                case "call-request":
                    logger.info("통화 요청: from={}, to={}", message.getFrom(), message.getTo());
                    break;
                case "call-accept":
                    logger.info("통화 수락: from={}, to={}", message.getFrom(), message.getTo());
                    break;
                case "call-reject":
                    logger.info("통화 거절: from={}, to={}", message.getFrom(), message.getTo());
                    break;
            }
            
            // 수신자의 큐로 메시지 전달
            String destination = "/user/" + message.getTo() + "/queue/messages";
            logger.info("메시지 전달 대상: {}", destination);
            
            messagingTemplate.convertAndSendToUser(
                message.getTo(),
                "/queue/messages",
                message
            );
            
            logger.info("메시지 전달 완료");
        } catch (Exception e) {
            logger.error("메시지 처리 중 오류:", e);
        }
    }
}
