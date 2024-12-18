package org.example.memoaserver.domain.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.bookmark.dto.response.BookmarkResponse;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;
import org.example.memoaserver.domain.bookmark.exception.BookmarkException;
import org.example.memoaserver.domain.bookmark.repository.BookmarkRepository;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.exception.PostNotFoundException;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.global.security.jwt.support.UserAuthHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserAuthHolder userAuthHolder;

    @Transactional
    public void addOrDeleteBookmark(Long bookmarkRequest) {
        UserEntity user = userAuthHolder.current();
        PostEntity post = postRepository.findById(bookmarkRequest).orElseThrow(PostNotFoundException::new);

        if (!bookmarkRepository.existsByUserAndPost(user, post)) {
            bookmarkRepository.save(BookmarkEntity.builder()
                    .post(post)
                    .user(user)
                    .build());
        } else {
            bookmarkRepository.deleteByUserAndPost(user, post);
        }
    }

    public List<BookmarkResponse> getBookmarkedPostsByUser() {
        return bookmarkRepository.findByUserOrderByCreatedAtDesc(userAuthHolder.current()).orElseThrow(BookmarkException::new).stream()
                .map(BookmarkResponse::fromBookmarkEntity)
                .toList();
    }
}
