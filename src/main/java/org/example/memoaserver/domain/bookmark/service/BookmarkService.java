package org.example.memoaserver.domain.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.bookmark.dto.res.BookmarkResponse;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;
import org.example.memoaserver.domain.bookmark.exception.BookmarkException;
import org.example.memoaserver.domain.bookmark.repository.BookmarkRepository;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;

    @Transactional
    public void addBookmark(Long bookmarkRequest) {
        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());
        PostEntity post = postRepository.findById(bookmarkRequest).orElseThrow(() -> new BookmarkException("존재하지 않는 북마크"));

        bookmarkRepository.save(BookmarkEntity.builder().post(post).user(user).build());
    }

    @Transactional
    public void removeBookmark(Long bookmarkRequest) {
        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());
        PostEntity post = postRepository.findById(bookmarkRequest).orElseThrow(() -> new BookmarkException("존재하지 않는 북마크"));
        Boolean bookmarkExists = bookmarkRepository.existsByUserAndPost(user, post);

        if (!bookmarkExists) {
            throw new BookmarkException(HttpStatus.BAD_REQUEST, "삭제 할 수 없습니다.");
        }
        bookmarkRepository.deleteByUserAndPost(user, post);
    }

    public List<BookmarkResponse> getBookmarkedPostsByUser() {
        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());

        return bookmarkRepository.findByUser(user).orElseThrow(() -> new BookmarkException("존재하지 않는 북마크")).stream()
                .map(BookmarkResponse::fromBookmarkEntity)
                .toList();
    }
}
