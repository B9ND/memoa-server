package org.example.memoaserver.domain.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.bookmark.dto.req.BookmarkRequest;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.bookmark.repository.BookmarkRepository;
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
    public void addBookmark(Long bookmarkRequest) throws Exception {

        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());

        PostEntity post = postRepository.findById(bookmarkRequest).orElse(null);

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
    public void deleteBookmark(Long bookmarkRequest) {

        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());
        // null 시 에러 반환 필요

        PostEntity post = postRepository.findById(bookmarkRequest).orElse(null);
        // null 시 에러 반환 필요

        Optional<BookmarkEntity> bookmark = bookmarkRepository.findByUserAndPost(user, post);

        bookmarkRepository.delete(bookmark.get());

    }
}
