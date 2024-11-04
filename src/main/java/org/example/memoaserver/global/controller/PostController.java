package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.post.dto.req.PostRequest;
import org.example.memoaserver.domain.post.dto.req.SearchPostRequest;
import org.example.memoaserver.domain.post.dto.res.PostResponse;
import org.example.memoaserver.domain.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "post", description = "게시물 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @Operation(summary = "Search API", description = "쿼리 파라미터로 검색 기능 수행")
    @GetMapping
    public ResponseEntity<List<PostResponse>> getSearchedPosts(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "tags") List<String> tags,
            @RequestParam(name = "page", required = false) int page,
            @RequestParam(name = "size", required = false) int size
            ) {
        return ResponseEntity.ok().body(postService.getPostsByTag(SearchPostRequest.builder()
                        .search(search)
                        .tags(tags)
                        .page(page)
                        .size(size)
                .build()));
    }

    @Operation(
            summary = "상세 페이지로 이동합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long id) {
        return ResponseEntity.ok().body(postService.getPostById(id));
    }

    @Operation(
            summary = "유저의 닉네임을 통해 게시물을 가져오기",
            description = "param 에 닉네임이 필수로 입력되어야 합니다."
    )
    @GetMapping("/user")
    public ResponseEntity<List<PostResponse>> getUserPosts(@RequestParam(name = "author") String author) {
        return ResponseEntity.ok().body(postService.getPostsByAuthor(author));
    }

    @Operation(
            summary = "게시물을 생성합니다.",
            description = "postReq 형식으로 데이터를 받고 상태만을 반환합니다."
    )
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok().body(postService.save(postRequest));
    }
}
