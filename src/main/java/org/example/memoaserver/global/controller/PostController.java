package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.post.dto.res.PostRes;
import org.example.memoaserver.domain.post.dto.req.PostReq;
import org.example.memoaserver.domain.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "post", description = "게시물 관련 API")
@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostRes>> getSearchedPosts(@RequestParam(required = false) String search) {
        return ResponseEntity.ok().body(postService.getPostsByTitleOrContent(search));
    }

    @Operation(
            summary = "게시물을 아이디로 받을 수 있습니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostRes> getPostById(@PathVariable long id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @Operation(
            summary = "게시물을 생성합니다.",
            description = "postReq 형식으로 데이터를 받고 상태만을 반환합니다."
    )
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostReq postReq) {
        postService.save(postReq);
        return ResponseEntity.ok().build();
    }
}
