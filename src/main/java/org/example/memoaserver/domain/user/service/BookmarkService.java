package org.example.memoaserver.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.user.dto.req.BookmarkRequest;
import org.example.memoaserver.domain.user.entity.BookmarkEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.BookmarkRepository;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;

    @Transactional
    public void addBookmark(BookmarkRequest bookmarkRequest) throws Exception {

        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());
        // null 시 에러 반환 필요

        PostEntity post = postRepository.findById(bookmarkRequest.getPostId()).orElse(null);
        // null 시 에러 반환 필요

        // 이미 북마크 되어있으면 에러 반환
        if (bookmarkRepository.findByUserAndPost(user, post).isPresent()) {
            throw new Exception("Bookmark already exists");
        }

        BookmarkEntity bookmark = BookmarkEntity.builder()
                .post(post)
                .user(user)
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void deleteBookmark(BookmarkRequest bookmarkRequest) {

        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());
        // null 시 에러 반환 필요

        PostEntity post = postRepository.findById(bookmarkRequest.getPostId()).orElse(null);
        // null 시 에러 반환 필요

        Optional<BookmarkEntity> bookmark = bookmarkRepository.findByUserAndPost(user, post);

        bookmarkRepository.delete(bookmark.get());

    }
}
