package org.example.memoaserver.domain.user.service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.repository.PostRepository;
import org.example.memoaserver.domain.post.service.PostService;
import org.example.memoaserver.domain.user.dto.BookmarkDTO;
import org.example.memoaserver.domain.user.entity.BookmarkEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.BookmarkRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final PostService postService;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;

    @Transactional
    public BookmarkDTO addBookmark(Long postId, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        PostEntity post = postRepository.findById(postId).
                orElseThrow(() -> new NotFoundException("게시물을 찾을 수 없습니다."));

        if (user == null) throw new UsernameNotFoundException("북마크 하려면 로그인하세요.");

        if (bookmarkRepository.findByUsernameAndPost(user, post) == null) {
            post.setBookmarkCount(post.getBookmarkCount() + 1);
            BookmarkEntity bookmark = Bookmark.addBookmark(user, post);
            bookmarkRepository.save(bookmark);
            return BookmarkDTO.createBookmarkDTO("북마크 처리 완료", bookmark);
        }

        post.setBookmarkCount(post.getBookmarkCount() - 1);
        BookmarkEntity bookmark = bookmarkRepository.findByMemberAndArticle(user, post);
        bookmark.deleteBookmark(article); // false 처리
        bookmarkRepository.deleteByMemberAndPost(user, post);
        return BookmarkDTO.createBookmarkDTO("북마크 취소 완료", bookmark);

    }
}
