package org.example.memoaserver.domain.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@Entity(name = "follow")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class FollowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following", nullable = false)
    private UserEntity following;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower", nullable = false)
    private UserEntity follower;

    @CreatedDate
    private LocalDate createdAt;

    @Builder
    public FollowEntity(Long id, UserEntity following, UserEntity follower, LocalDate createdAt) {
        this.id = id;
        this.following = following;
        this.follower = follower;
        this.createdAt = createdAt;
    }
}
