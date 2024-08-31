package org.example.memoaserver.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.post.dto.PostDTO;
import org.example.memoaserver.domain.post.dto.TagDTO;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public void writePost(PostDTO postDTO) {
        PostEntity postEntity = new PostEntity();

        postEntity.setTitle(postDTO.getTitle());
        postEntity.setContent(postDTO.getContent());
        postEntity.setIsReleased(postDTO.getIsReleased());
        postEntity.setUser(postDTO.getUser());

        postRepository.save(postEntity);

        List<TagEntity> tagEntities = postDTO.getTags().stream()
                .map(p -> {
                    TagEntity t = new TagEntity();
                    t.setName(p.getName());
                    t.setPostId(postEntity.getId());
                    return t;
                })
                .toList();

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
        return post.parallelStream().map(postEntity -> {
            return PostDTO.builder()
                    .id(postEntity.getId())
                    .title(postEntity.getTitle())
                    .content(postEntity.getContent())
                    .user(postEntity.getUser())
                    .isReleased(postEntity.getIsReleased())
                    .tags(findTagById(postEntity.getId()))
                    .createdAt(postEntity.getCreatedAt())
                    .build();
        }).toList();
    }
}