package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.res.UserResponse;
import org.example.memoaserver.domain.user.service.FollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
@Tag(name = "follow", description = "팔로우 관련 API")
public class FollowController {
    private final FollowService followService;

    @PostMapping
    @Operation(
            summary = "특정 유저를 팔로우합니다",
            description = "팔로워의 닉네임 파라미터로 전달합니다"
    )
    public ResponseEntity<?> follow(@RequestParam(name = "follower") String follower) {
        followService.addFollower(follower);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(
            summary = "특정 유저를 언팔로우합니다",
            description = "팔로워의 닉네임을 파라미터로 전달합니다"
    )
    public ResponseEntity<?> unfollow(@RequestParam(name = "follower") String follower) {
        followService.removeFollower(follower);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "팔로워 목록을 조회합니다",
            description = "목록에 있는 유저의 닉네임을 파라미터로 전달합니다"
    )
    @GetMapping
    public ResponseEntity<List<UserResponse>> getFollowers(@RequestParam(name = "user") String user) {
        return ResponseEntity.ok().body(followService.getFollowers(user));
    }
}