//package org.example.memoaserver.domain.post.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.experimental.SuperBuilder;
//import org.example.memoaserver.domain.user.entity.UserEntity;
//
//import java.util.List;
//import java.util.Set;
//
//@Getter
//@SuperBuilder
//@NoArgsConstructor
//@Entity
//public class PostEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long post_id;
//
//    private String title;
//
//    private String content;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private UserEntity user;
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
//    private Set<TagEntity> tags;
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<ImageEntity> images;
//}
