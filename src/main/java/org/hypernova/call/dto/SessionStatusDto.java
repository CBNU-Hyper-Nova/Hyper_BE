package org.hypernova.call.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SessionStatusDto {
    private String username;
    private String status; // 대기 중, 통화 중
}
