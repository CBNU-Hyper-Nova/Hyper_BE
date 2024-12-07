package org.hypernova.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String status;  // 응답 상태 ("success" 또는 "error")
    private String message; // 응답 메시지
    private String token;   // JWT 토큰 (로그인 성공 시에만 사용)

    // 로그인 외 다른 응답에는 token 필드를 사용하지 않음
    public UserResponseDto(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
