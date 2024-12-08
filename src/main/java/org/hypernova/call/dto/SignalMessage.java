package org.hypernova.call.dto;

import lombok.Data;

@Data
public class SignalMessage {
    private String type;
    private String from;
    private String to;
    private Object payload;
} 