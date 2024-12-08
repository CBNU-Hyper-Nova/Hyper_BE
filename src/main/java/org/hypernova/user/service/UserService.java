package org.hypernova.user.service;

import org.hypernova.user.dto.UserRequestDto;
import org.hypernova.user.dto.UserResponseDto;
import org.hypernova.user.entity.User;
import org.hypernova.user.jwt.util.JwtUtil;
import org.hypernova.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import org.hypernova.user.dto.Friend;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 사용자 회원가입
     * @param requestDto 회원 가입에 필요한 사용자 정보
     *                   - username: 사용자 고유 이름
     *                   - password: 사용자 비밀번호
     * @return 사용자 등록 성공 메시지
     */
    public UserResponseDto signup(UserRequestDto requestDto) {
        // 이미 존재하는 사용자 이름인지 확인
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            return new UserResponseDto("error", "이미 존재하는 사용자입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 객체 생성 및 저장
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(encodedPassword)
                .build();
        userRepository.save(user);

        return new UserResponseDto("success", "회원가입이 완료되었습니다.");
    }

    /**
     * 사용자 로그인
     * @param requestDto ��그인에 필요한 사용자 정보
     *                   - username: 사용자 고유 이름
     *                   - password: 사용자 비밀번호
     * @return 로그인 성공 시 JWT 토큰
     */
    public UserResponseDto login(UserRequestDto requestDto) {
        // 해당 이름을 가진 사용자 객체 조회
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        // JWT 토큰 생성
        String token = jwtUtil.generateToken(user.getUsername());

        // 로그인한 사용자가 박유경이면 강재구를, 강재구면 박유경을 친구로 반환
        List<Friend> friends = new ArrayList<>();
        if ("박유경".equals(user.getUsername())) {
            friends.add(new Friend(2L, "강재구", "/default-profile.png", "jaegu-signal"));
        } else if ("강재구".equals(user.getUsername())) {
            friends.add(new Friend(1L, "박유경", "/default-profile.png", "yugyeong-signal"));
        }

        return new UserResponseDto("success", "로그인에 성공했습니다.", token);
    }
}