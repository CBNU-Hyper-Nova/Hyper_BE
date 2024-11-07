package org.hypernova.call.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<String> users = new ArrayList<>(); // 사용자 목록

    private String status; // 대기 중, 통화 중

    @Builder
    public Session(List<String> users, String status) {
        this.users = users;
        this.status = status;
    }

    // 사용자 추가
    public void addUser(String user) {
        if (this.users.size() < 2) { // 최대 두 명까지 추가 가능
            this.users.add(user);
        } else {
            throw new IllegalStateException("세션에는 최대 두 명의 사용자만 참여할 수 있습니다.");
        }
    }

    // 사용자 삭제
    public void removeUser(String user) {
        this.users.remove(user);
    }

    // 두 사용자가 모두 포함되어 있는지 확인
    public boolean containsBothUsers(String user1, String user2) {
        return this.users.contains(user1) && this.users.contains(user2);
    }
}
