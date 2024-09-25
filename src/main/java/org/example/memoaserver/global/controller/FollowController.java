package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.service.FollowService;
import org.example.memoaserver.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@Tag(name = "follow", description = "팔로우 관련 API")
public class FollowController {
    private final FollowService followService;
    private final UserService userService;

    // 친구 맺기(팔로우)
    @Operation(
            summary = "특정 유저를 팔로우합니다",
            description = "팔로워의 이메일을 Path 파라미터로 전달합니다"
    )
    @PostMapping("/follow/{follower}")
    public ResponseEntity<?> follow(@PathVariable String follower) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        followService.addFollower(email, follower);
        return ResponseEntity.ok().build();
    }

    // 언팔로우
    @Operation(
            summary = "특정 유저를 언팔로우합니다",
            description = "팔로워의 이메일을 Path 파라미터로 전달합니다"
    )
    @PostMapping("/unfollow/{follower}")
    public ResponseEntity<?> unfollow(@PathVariable String follower) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        followService.removeFollower(email, follower);
        return ResponseEntity.ok().build();
    }

    // 조회
    @Operation(
            summary = "팔로워 목록을 조회합니다",
            description = "목록에 있는 유저의 이메일을 Path 파라미터로 전달합니다"
    )
    @GetMapping("/follow/view/{user}")
    public ResponseEntity<?> getFollowers(@PathVariable String user) {
        List<UserDTO> followings = followService.getFollowers(user);
        return ResponseEntity.ok(followings);
    }

}