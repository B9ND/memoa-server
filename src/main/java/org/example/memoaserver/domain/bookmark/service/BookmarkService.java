package org.example.memoaserver.domain.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.bookmark.dto.res.BookmarkResponse;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;
import org.example.memoaserver.domain.bookmark.exception.BookmarkException;
import org.example.memoaserver.domain.bookmark.repository.BookmarkRepository;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
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
        PostEntity post = postRepository.findById(bookmarkRequest).orElseThrow(() -> new BookmarkException("존재하지 않는 게시물"));
        Boolean bookmarkExists = bookmarkRepository.existsByUserAndPost(userAuthHolder.current(), post);

        if (!bookmarkExists) {
            bookmarkRepository.save(BookmarkEntity.builder()
                    .post(post)
                    .user(userAuthHolder.current())
                    .build());
        } else {
            bookmarkRepository.deleteByUserAndPost(userAuthHolder.current(), post);
        }
    }

    public List<BookmarkResponse> getBookmarkedPostsByUser() {
        return bookmarkRepository.findByUserOrderByCreatedAtDesc(userAuthHolder.current()).orElseThrow(() -> new BookmarkException("존재하지 않는 북마크")).stream()
                .map(BookmarkResponse::fromBookmarkEntity)
                .toList();
    }
}
