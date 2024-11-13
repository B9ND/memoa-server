package org.example.memoaserver.domain.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.bookmark.dto.res.BookmarkResponse;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;
import org.example.memoaserver.domain.bookmark.exception.BookmarkException;
import org.example.memoaserver.domain.bookmark.repository.BookmarkRepository;
import org.example.memoaserver.domain.post.dto.res.PostResponse;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.service.PostService;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.domain.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final UserAuthHolder userAuthHolder;
    private final PostService postService;

    @Transactional
    public void addBookmark(Long bookmarkRequest) {
        UserEntity user = userAuthHolder.current();
        PostEntity post = postRepository.findById(bookmarkRequest).orElseThrow(() -> new BookmarkException("존재하지 않는 북마크"));

        bookmarkRepository.save(BookmarkEntity.builder().post(post).user(user).build());
    }

    @Transactional
    public void removeBookmark(Long bookmarkRequest) {
        UserEntity user = userAuthHolder.current();
        PostEntity post = postRepository.findById(bookmarkRequest).orElseThrow(() -> new BookmarkException("존재하지 않는 북마크"));
        Boolean bookmarkExists = bookmarkRepository.existsByUserAndPost(user, post);

        if (!bookmarkExists) {
            throw new BookmarkException(HttpStatus.BAD_REQUEST, "삭제할 수 없습니다.");
        }
        bookmarkRepository.deleteByUserAndPost(user, post);
    }

    public Boolean toggleBookmark(Long bookmarkRequest) throws Exception {
        PostEntity post = postRepository.findById(bookmarkRequest)
                .orElseThrow(() -> new Exception("게시물을 찾을 수 없습니다."));

        // try catch로 고쳐보기
        if (isBookmarked(post)) {
            removeBookmark(bookmarkRequest);
            return false;
        } else {
            addBookmark(bookmarkRequest);
            return true;
        }
    }

    private Boolean isBookmarked(PostEntity post) {
        return bookmarkRepository.existsByUserAndPost(userAuthHolder.current(), post);
    }

    public List<BookmarkResponse> getBookmarkedPostsByUser() {
        UserEntity user = userAuthHolder.current();

        return bookmarkRepository.findByUserOrderByCreatedAtDesc(user).orElseThrow(() -> new BookmarkException("존재하지 않는 북마크")).stream()
                .map(BookmarkResponse::fromBookmarkEntity)
                .toList();
    }
}
