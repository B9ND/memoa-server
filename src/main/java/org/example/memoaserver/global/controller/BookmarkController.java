package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> bookmark(@RequestParam(name = "bookmark") String bookmark) {

    }
}
