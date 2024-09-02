package org.example.memoaserver.global.controller;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.service.FollowService;
import org.example.memoaserver.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    // 친구 맺기
    @PostMapping("/follow/{follower}")
    public ResponseEntity<?> follow(@PathVariable String follower) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        followService.addFollower(email, follower);
        return ResponseEntity.ok().build();
    }

    // 언팔로우
    @PostMapping("/following/view")
    public ResponseEntity<?> unfollow(@PathVariable String follower) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        followService.removeFollower(email, follower);
        return ResponseEntity.ok().build();
    }
}
