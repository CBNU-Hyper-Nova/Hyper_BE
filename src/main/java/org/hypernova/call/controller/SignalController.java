package org.hypernova.call.controller;

import lombok.RequiredArgsConstructor;
import org.hypernova.call.service.SignalService;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class SignalController extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // WebSocket 연결이 시작될 때 세션을 저장
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);
        System.out.println("New WebSocket session connected: " + sessionId);
    }

    // 클라이언트로부터 메시지 수신
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonMessage = new JSONObject(message.getPayload());
        String type = jsonMessage.getString("type"); // offer, answer, candidate
        String targetSessionId = jsonMessage.getString("target"); // 수신 대상 클라이언트 ID

        switch (type) {
            case "offer":
            case "answer":
            case "candidate":
                forwardMessage(targetSessionId, jsonMessage.toString());
                break;
            default:
                System.out.println("Unknown message type: " + type);
        }
    }

    // 수신 대상 클라이언트에게 메시지 전달
    private void forwardMessage(String targetSessionId, String message) {
        WebSocketSession targetSession = sessions.get(targetSessionId);
        if (targetSession != null && targetSession.isOpen()) {
            try {
                targetSession.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Target session not found or closed: " + targetSessionId);
        }
    }

    // WebSocket 연결 종료 시 세션 제거
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("WebSocket session closed: " + session.getId());
    }
}
