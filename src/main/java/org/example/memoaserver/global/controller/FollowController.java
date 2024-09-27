package org.example.memoaserver.global.controller;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.service.FollowService;
import org.example.memoaserver.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    // 친구 맺기(팔로우)
    @PostMapping("/{follower}")
    public ResponseEntity<?> follow(@PathVariable String follower) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        followService.addFollower(email, follower);
        return ResponseEntity.ok().build();
    }

    // 언팔로우
    @DeleteMapping("/{follower}")
    public ResponseEntity<?> unfollow(@PathVariable String follower) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        followService.removeFollower(email, follower);
        return ResponseEntity.ok().build();
    }

    // 조회
    @GetMapping
    public ResponseEntity<?> getFollowers() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        followService.getFollowers(email, getFollowers());
        return ResponseEntity.ok().build();
    }

}