package org.hypernova.call.controller;

import lombok.RequiredArgsConstructor;
import org.hypernova.call.dto.CallRequestDto;
import org.hypernova.call.service.SignalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/signal")
public class CallController {
    private final SignalService signalService;

    // 통화 요청
    @PostMapping("/call")
    public ResponseEntity<String> sendCallRequest(@RequestBody CallRequestDto callRequestDto) {
        signalService.sendCallRequest(callRequestDto.getCaller(), callRequestDto.getReceiver());
        return ResponseEntity.ok("통화 요청을 전송했습니다.");
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptCall(@RequestBody CallRequestDto callRequestDto) {
        signalService.acceptCall(callRequestDto.getCaller(), callRequestDto.getReceiver());
        return ResponseEntity.ok("통화 요청을 수락했습니다.");
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectCall(@RequestBody CallRequestDto callRequestDto) {
        signalService.rejectCall(callRequestDto.getCaller(), callRequestDto.getReceiver());
        return ResponseEntity.ok("통화 요청을 거절했습니다.");
    }

    // 통화 종료
    @PostMapping("/end")
    public ResponseEntity<String> endCall(@RequestParam String user) {
        signalService.endCall(user);
        return ResponseEntity.ok("통화를 종료했습니다.");
    }
}
