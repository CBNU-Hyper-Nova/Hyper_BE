package org.hypernova.user.service;

import org.hypernova.user.dto.UserRequestDto;
import org.hypernova.user.entity.User;
import org.hypernova.user.jwt.util.JwtUtil;
import org.hypernova.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

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
    public String signup(UserRequestDto requestDto) {
        // 이미 존재하는 사용자 이름인지 확인
        if (userRepository.existsByUsername(requestDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 사용자 객체 생성 및 저장
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(encodedPassword)
                .build();
        userRepository.save(user);

        return "User registered successfully";
    }

    /**
     * 사용자 로그인
     * @param requestDto 로그인에 필요한 사용자 정보
     *                   - username: 사용자 고유 이름
     *                   - password: 사용자 비밀번호
     * @return 로그인 성공 시 JWT 토큰
     */
    public String login(UserRequestDto requestDto) {
        // 해당 이름을 가진 사용자 객체 조회
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        // 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // JWT 토큰 반환
        return jwtUtil.generateToken(user.getUsername());
    }
}