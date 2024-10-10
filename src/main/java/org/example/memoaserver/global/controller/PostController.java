//package org.example.memoaserver.global.controller;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.example.memoaserver.domain.post.dto.PostDTO;
//import org.example.memoaserver.domain.post.service.PostService;
//import org.example.memoaserver.domain.user.entity.UserEntity;
//import org.example.memoaserver.domain.user.repository.UserAuthHolder;
//import org.example.memoaserver.domain.user.service.UserService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Tag(name = "post", description = "게시물 관련 API")
//@Slf4j
//@RestController
//@RequestMapping("/post")
//@RequiredArgsConstructor
//public class PostController {
//    private final PostService postService;
//    private final UserService userService;
//    private final UserAuthHolder userAuthHolder;
//
//    @Operation(
//            summary = "모든 게시물을 반환하는 코드입니다.",
//            description = "추후에 삭제 예정"
//    )
//    @GetMapping
//    public List<PostDTO> getPosts() {
//        return postService.getAllPosts();
//    }
//
//    @Operation(
//            summary = "게시물 작성 코드입니다.",
//            description = "작성 방식은 수정할 예정"
//    )
//    @PostMapping
//    public void write(@RequestBody PostDTO postDTO) {
//        UserEntity user = userAuthHolder.current();
//        postDTO.setUser(userService.getUserByEmail(user.getEmail()));
//        postService.writePost(postDTO);
//    }
//}
