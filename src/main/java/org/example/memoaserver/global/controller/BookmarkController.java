package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.bookmark.dto.res.BookmarkResponse;
import org.example.memoaserver.domain.bookmark.service.BookmarkService;
import org.example.memoaserver.domain.post.dto.res.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/bookmark")
@Tag(name = "bookmark", description = "게시물 북마크 관련 API")
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping
    @Operation(
            summary = "특정 게시물을 북마크합니다",
            description = "북마크하고자 하는 게시물의 아이디를 파라미터로 전달합니다"
    )
    public ResponseEntity<?> addBookmark(@RequestParam(name = "post-id") Long postId) throws Exception {
        bookmarkService.addBookmark(postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(
            summary = "특정 게시물의 북마크를 삭제합니다",
            description = "북마크 삭제하고자 하는 게시물의 아이디를 파라미터로 전달합니다"
    )
    public ResponseEntity<?> removeBookmark(@RequestParam(name = "post-id") Long postId) throws Exception {
        bookmarkService.removeBookmark(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(
            summary = "북마크 목록을 불러옵니다",
            description = "인자는 없습니다"
    )
    public ResponseEntity<List<BookmarkResponse>> getBookmarks() {
        return ResponseEntity.ok(bookmarkService.getBookmarkedPostsByUser());
    }
}
