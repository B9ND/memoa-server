package org.example.memoaserver.global.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.post.dto.PostDTO;
import org.example.memoaserver.domain.post.service.PostService;
import org.example.memoaserver.domain.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public List<PostDTO> getPosts() {
        return postService.getAllPosts();
    }

    @PostMapping("/write")
    public void write(@RequestBody PostDTO postDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<? extends GrantedAuthority> authoritiesIterator = authorities.iterator();
        GrantedAuthority authority = authoritiesIterator.next();
//        String role = authority.getAuthority();
        log.info("Authorities : {}", email);

        postDTO.setUser(userService.getUserByEmail(email));

        postService.writePost(postDTO);



    }
}
