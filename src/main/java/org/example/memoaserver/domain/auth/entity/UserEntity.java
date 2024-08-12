package org.example.memoaserver.domain.auth.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(length = 50)
    private String nickname;

    private String school;

    private Integer grade;

    private Date birth;
}
