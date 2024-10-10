//package org.example.memoaserver.domain.post.dto;
//
//import lombok.Builder;
//import lombok.Getter;
//import org.example.memoaserver.domain.post.entity.ImageEntity;
//import org.example.memoaserver.domain.post.entity.PostEntity;
//import org.example.memoaserver.domain.post.entity.TagEntity;
//import org.example.memoaserver.domain.user.entity.UserEntity;
//
//import java.util.List;
//import java.util.Set;
//
//@Getter
//@Builder
//public class PostDTO {
//    private String title;
//
//    private String content;
//
//    private String author;
//
//    private Set<String> tags;
//
//    private List<String> images;
//
//    public static PostEntity of(PostDTO postDTO, UserEntity user, Set<TagEntity> tags, List<ImageEntity> images) {
//        return PostEntity.builder()
//                .user(user)
//                .title(postDTO.getTitle())
//                .content(postDTO.getContent())
//                .tags(tags)
//                .images(images)
//                .build();
//    }
//}
