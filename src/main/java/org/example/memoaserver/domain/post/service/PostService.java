package org.example.memoaserver.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.post.dto.PostDTO;
import org.example.memoaserver.domain.post.dto.TagDTO;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.repository.TagRepository;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final UserAuthHolder userAuthHolder;

    public void writePost(PostDTO postDTO) {
        UserEntity user = userAuthHolder.current();
        PostEntity postEntity = PostEntity.builder()
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .isReleased(postDTO.getIsReleased())
                .user(user)
                .build();
        postRepository.save(postEntity);
        List<TagEntity> tagEntities = postDTO.getTags().stream()
                .map(p -> TagEntity.builder()
                            .name(p.getName())
                            .postId(postEntity.getId())
                            .build()).toList();
        tagRepository.saveAll(tagEntities);
    }

    private List<TagDTO> findTagById(Long id) {
        return tagRepository.findById(id).stream()
                .map(tagEntity -> {
                    TagDTO dto = new TagDTO();
                    dto.setName(tagEntity.getName());
                    return dto;
                })
                .toList();
    }

    public List<PostDTO> getAllPosts() {
        List<PostEntity> post = postRepository.findByIsReleasedTrue();
        return post.parallelStream().map(postEntity -> PostDTO.builder()
                    .id(postEntity.getId())
                    .title(postEntity.getTitle())
                    .content(postEntity.getContent())
                    .user(postEntity.getUser())
                    .isReleased(postEntity.getIsReleased())
                    .tags(findTagById(postEntity.getId()))
                    .createdAt(postEntity.getCreatedAt())
                    .build()).toList();
    }
}