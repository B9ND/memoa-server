package org.example.memoaserver.domain.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.post.dto.req.PostRequest;
import org.example.memoaserver.domain.post.dto.req.SearchPostRequest;
import org.example.memoaserver.domain.post.dto.res.PostResponse;
import org.example.memoaserver.domain.post.entity.ImageEntity;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.post.exception.PostException;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.repository.TagRepository;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class  PostService {
    private final UserAuthHolder userAuthHolder;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Transactional
    public PostResponse save(PostRequest postRequest) {
        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());
        Set<TagEntity> tags = postRequest.getTags().stream().map(this::findOrCreateTag).collect(Collectors.toSet());
        PostEntity post = postRequest.toPostEntity(user, tags);
        List<ImageEntity> images = postRequest.getImages().stream().map(imageUrl -> ImageEntity.builder().url(imageUrl).post(post).build()).collect(Collectors.toList());

        post.setImages(images);

        return PostResponse.fromPostEntity(postRepository.save(post));
    }

    public List<PostResponse> getPostsByTitleOrContent(String name) {
        return postRepository.findByTitleContainingOrContentContaining(name, name)
                .stream()
                .map(PostResponse::fromPostEntity)
                .toList();
    }

    public PostResponse getPostById(Long id) {
        return PostResponse.fromPostEntity(postRepository.findById(id)
                .orElseThrow(() -> new PostException("존재하지 않는 게시물입니다.", HttpStatus.NOT_FOUND)));
    }

    private TagEntity findOrCreateTag(String tagName) {
        return tagRepository.findByTagName(tagName)
                .orElseGet(() -> TagEntity.builder().tagName(tagName).build());
    }

    @Transactional
    public List<PostResponse> getPostsByTag(SearchPostRequest searchPostRequest) {
        Pageable pageable = PageRequest.of(searchPostRequest.getPage(), searchPostRequest.getSize());
        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());

        if (searchPostRequest.getSearch().isEmpty() && searchPostRequest.getTags().isEmpty()) {
            return postRepository.findPostsByFollower(user.getId(), pageable).stream()
                    .map(PostResponse::fromPostEntity).toList();
        }

        return postRepository.findPostsByFilters(searchPostRequest.getTags(), searchPostRequest.getSearch(), user.getId(), pageable).stream()
                .map(PostResponse::fromPostEntity).toList();
    }
}