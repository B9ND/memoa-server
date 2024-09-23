package org.example.memoaserver.global.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.BookmarkDTO;
import org.example.memoaserver.domain.user.service.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    // 게시글 북마크
    @PostMapping("/post/{postId}/bookmark")
    public ResponseEntity<BookmarkDTO> addBookmark(@PathVariable Long postId,
                                                   HttpServletRequest request) {
        HttpSession session = request.getSession();
        BookmarkDTO bookmrk = bookmarkService.addBookmark(postId, session);
        return ResponseEntity.status(HttpStatus.OK).body(bookmrk);
    }
}
