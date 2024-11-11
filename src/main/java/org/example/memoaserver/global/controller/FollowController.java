package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.res.FollowUserResponse;
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
            summary = "유저를 팔로우 / 취소합니다.",
            description = "팔로우할 유저가 존재하면 삭제, 없으면 추가됨"
    )
    public ResponseEntity<?> addOrUnfollow(@RequestParam(name = "nickname") String nickname) {
        followService.addOrDeleteFollower(nickname);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "팔로잉 목록을 조회합니다",
            description = "nickname 이 팔로우 하는 사람들, 없으면 자기 자신 기준"
    )
    @GetMapping("/followings")
    public ResponseEntity<List<FollowUserResponse>> getFollowers(@RequestParam(name = "nickname", required = false) String nickname) {
        return ResponseEntity.ok().body(followService.getFollowings(nickname));
    }

    @Operation(
            summary = "팔로우 목록을 조회합니다",
            description = "nickname 을 팔로우 하는 사람들, 없으면 자기 자신 기준"
    )
    @GetMapping("/followers")
    public ResponseEntity<List<FollowUserResponse>> getFollowings(@RequestParam(name = "nickname", required = false) String nickname) {
        return ResponseEntity.ok().body(followService.getFollowers(nickname));
    }

}