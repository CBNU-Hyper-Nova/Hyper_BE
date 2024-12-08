package org.hypernova.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Friend {
    private Long id;
    private String name;
    private String profileImage;
    private String signalingId;
} 