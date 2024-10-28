package org.example.memoaserver.domain.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.bookmark.dto.res.BookmarkResponse;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.bookmark.repository.BookmarkRepository;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

        PostEntity post = postRepository.findById(bookmarkRequest).orElse(null);

        BookmarkEntity bookmark = BookmarkEntity.builder()
                .post(post)
                .user(user)
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void removeBookmark(Long bookmarkRequest) {

        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());

        PostEntity post = postRepository.findById(bookmarkRequest).orElseThrow(RuntimeException::new);

        bookmarkRepository.deleteByUserAndPost(user, post);
    }

    public List<BookmarkResponse> getBookmarkedPostsByUser() {
        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());

        return Objects.requireNonNull(bookmarkRepository.findByUser(user).orElse(null)).stream()
                .map(BookmarkResponse::fromBookmarkEntity)
                .toList();
    }
}
