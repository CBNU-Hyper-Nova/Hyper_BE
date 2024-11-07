package org.hypernova.call.repository;

import org.hypernova.call.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    // 특정 사용자가 포함된 세션 조회
    Optional<Session> findByUsersContaining(String user);
}
