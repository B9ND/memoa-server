package org.example.memoaserver.global.controller;

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

    @GetMapping("/{id}")
    public ResponseEntity<PostRes> getPostById(@PathVariable long id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostReq postReq) {
        postService.save(postReq);
        return ResponseEntity.ok().build();
    }
}
