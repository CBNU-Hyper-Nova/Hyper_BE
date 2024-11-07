package org.hypernova.call.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignalService {
    private final SimpMessagingTemplate messagingTemplate;
    private final SessionService sessionService;

    // 발신자가 수신자에게 통화 요청
    public void sendCallRequest(String caller, String receiver) {
        // 발신자의 세션 상태 확인
        String callerStatus = sessionService.getSessionStatus(receiver);
        // 수신자의 세션 상태 확인
        String receiverStatus = sessionService.getSessionStatus(receiver);

        // 발신자 or 수신자가 통화 중
        if ("통화 중".equals(callerStatus)) {
            messagingTemplate.convertAndSend(String.format("/queue/%s/busy", caller), // 발신자에게 메시지 전달
                    String.format("%s님은 현재 다른 통화 중입니다.", caller));
        } else if ("통화 중".equals(receiverStatus)) {

            messagingTemplate.convertAndSend(String.format("/queue/%s/busy", caller), // 발신자에게 메시지 전달
                    String.format("%s님은 현재 다른 통화 중입니다.", receiver));
        } else {
            // 수신자에게 통화 요청 메시지 전달
            messagingTemplate.convertAndSend(String.format("/queue/%s/call", receiver), // 수신자에게 메시지 전달
                    String.format("%s님이 %s님에게 통화를 요청합니다.", caller, receiver));
        }
    }

    // 수신자의 통화 요청 수락
    public void acceptCall(String caller, String receiver) {
        // 발신자에게 통화 수락 메시지 전달
        messagingTemplate.convertAndSend(String.format("/queue/%s/accept", caller),
                String.format("%s님이 통화 요청을 수락하였습니다.", receiver));

        // 세션 생성
        sessionService.createSession(caller, receiver);
    }

    // 수신자의 통화 요청 거절
    public void rejectCall(String caller, String receiver) {
        // 발신자에게 통화 거절 메시지 전달
        messagingTemplate.convertAndSend(String.format("/queue/%s/reject", caller),
                String.format("%s님이 통화 요청을 거절하였습니다.", receiver));
    }

    // 통화 종료
    public void endCall(String user) {
        // 한 사용자가 통화를 종료하면, 모든 사용자에게 메시지 전달
        messagingTemplate.convertAndSend("/topic/end",
                String.format("%s님이 통화를 종료하였습니다.", user));

        // 세션 종료
        sessionService.endSession(user);
    }
}
