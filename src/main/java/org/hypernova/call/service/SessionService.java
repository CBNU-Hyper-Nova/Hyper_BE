package org.hypernova.call.service;

import lombok.RequiredArgsConstructor;
import org.hypernova.call.entity.Session;
import org.hypernova.call.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;

    // 세션 생성
    public void createSession(String caller, String receiver) {
        // 사용자는 하나의 세션에만 존재할 수 있다
        if (sessionRepository.findByUsersContaining(caller).isPresent()) {
            throw new IllegalArgumentException("발신자가 참여 중인 세션이 이미 존재합니다.");
        }
        if (sessionRepository.findByUsersContaining(receiver).isPresent()) {
            throw new IllegalArgumentException("수신자가 참여 중인 세션이 이미 존재합니다.");
        }

        // 발신자와 수신자를 세션에 추가하고, 세션을 "통화 중" 상태로 세팅
        Session session = Session.builder()
                .users(List.of(caller, receiver))
                .status("통화 중")
                .build();
        sessionRepository.save(session);
    }

    // 통화 종료한 사용자가 포함된 세션 종료
    public void endSession(String user) {
        Session session = sessionRepository.findByUsersContaining(user)
                .orElseThrow(() -> new IllegalArgumentException("사용자가 참여 중인 세션이 존재하지 않습니다."));

        sessionRepository.delete(session);
    }

    // 사용자가 참여 중인 세션 상태 확인
    public String getSessionStatus(String user) {
        return sessionRepository.findByUsersContaining(user)
                .map(Session::getStatus) // 사용자가 세션에 존재하면, 세션 상태를 가져온다
                .orElse("대기 중"); // 사용자가 참여 중이 아니라면, 세션은 대기 중
    }
}
