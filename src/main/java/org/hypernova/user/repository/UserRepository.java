package org.hypernova.user.repository;

import org.hypernova.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 존재 여부 확인
    boolean existsByUsername(String username);

    // 사용자 찾기
    Optional<User> findByUsername(String username);
}