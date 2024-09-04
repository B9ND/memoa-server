package org.example.memoaserver.global.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.post.dto.PostDTO;
import org.example.memoaserver.domain.post.service.PostService;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final UserAuthHolder userAuthHolder;

    @GetMapping("/")
    public List<PostDTO> getPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/write")
    public void write(@RequestBody PostDTO postDTO) {
        UserEntity user = userAuthHolder.current();
        postDTO.setUser(userService.getUserByEmail(user.getEmail()));
        postService.writePost(postDTO);
    }
}
