package org.hypernova.user.controller;

import lombok.RequiredArgsConstructor;
import org.hypernova.user.dto.UserRequestDto;
import org.hypernova.user.dto.UserResponseDto;
import org.hypernova.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(userService.login(requestDto));
    }
}