package org.example.memoaserver.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity(name = "post")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Boolean isReleased;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}