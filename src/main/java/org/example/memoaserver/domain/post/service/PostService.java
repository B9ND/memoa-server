package org.example.memoaserver.domain.post.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.bookmark.repository.BookmarkRepository;
import org.example.memoaserver.domain.post.dto.req.PostRequest;
import org.example.memoaserver.domain.post.dto.req.SearchPostRequest;
import org.example.memoaserver.domain.post.dto.res.PostResponse;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.post.exception.PostException;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.repository.TagRepository;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.exception.NullUserException;
import org.example.memoaserver.global.security.jwt.support.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  PostService {
    private final UserAuthHolder userAuthHolder;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public List<PostResponse> getPostsByTag(SearchPostRequest searchPostRequest) {
        Pageable pageable = PageRequest.of(searchPostRequest.getPage(), searchPostRequest.getSize());
        UserEntity user = userAuthHolder.current();

        return bookMarkedPosts(
                postRepository.findPostsByFilters(searchPostRequest.getTags(), searchPostRequest.getSearch(), user.getId(), pageable).toList(),
                user
        );
    }

    @Transactional
    public PostResponse save(PostRequest postRequest) {
        Set<TagEntity> tags = postRequest.getTags()
                .stream()
                .map(this::findOrCreateTag)
                .collect(Collectors.toSet());
        PostEntity post = postRequest.toPostEntity(userAuthHolder.current(), tags);
        post.setImages(postRequest.toImageEntities(post));

        return PostResponse.fromPostEntity(postRepository.save(post), false);
    }

    public List<PostResponse> getPostsByAuthor(String nickname) {
        return bookMarkedPosts(postRepository.findByUserOrderByCreatedAtDesc(
            userRepository.findByNickname(nickname).orElseThrow(NullUserException::new)),
            userAuthHolder.current()
        );
    }

    public PostResponse getPostById(Long id) {
        return bookMarkedPost(
            postRepository.findById(id)
                .orElseThrow(() -> new PostException("존재하지 않는 게시물입니다.", HttpStatus.NOT_FOUND)),
            userAuthHolder.current()
        );
    }

    private PostResponse bookMarkedPost(PostEntity post, UserEntity user) {
        return PostResponse.fromPostEntity(post, bookmarkRepository.existsByUserAndPost(user, post));
    }

    private List<PostResponse> bookMarkedPosts(List<PostEntity> postEntities, UserEntity user) {
        return postEntities.stream()
                .map(
                        bookmark -> PostResponse.fromPostEntity(bookmark, bookmarkRepository.existsByUserAndPost(user, bookmark)))
                .toList();
    }

    private TagEntity findOrCreateTag(String tagName) {
        return tagRepository.findByTagName(tagName)
                .orElseGet(() -> TagEntity.builder().tagName(tagName).build());
    }
}