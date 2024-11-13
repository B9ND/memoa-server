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
        UserEntity user = userAuthHolder.current();
        PostEntity post = postRepository.findById(bookmarkRequest).orElseThrow(() -> new BookmarkException("존재하지 않는 게시물"));
        Boolean bookmarkExists = bookmarkRepository.existsByUserAndPost(user, post);

        if (!bookmarkExists) {
            bookmarkRepository.save(BookmarkEntity.builder()
                    .post(post)
                    .user(user)
                    .build());
        } else {
            bookmarkRepository.deleteByUserAndPost(user, post);
        }
    }

    public List<BookmarkResponse> getBookmarkedPostsByUser() {
        UserEntity user = userAuthHolder.current();

        return bookmarkRepository.findByUserOrderByCreatedAtDesc(user).orElseThrow(() -> new BookmarkException("존재하지 않는 북마크")).stream()
                .map(BookmarkResponse::fromBookmarkEntity)
                .toList();
    }
}
