package org.hypernova.call.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CallRequestDto {
    private String caller;
    private String receiver;
//    private String message; // 추가적인 메시지 정보
}
