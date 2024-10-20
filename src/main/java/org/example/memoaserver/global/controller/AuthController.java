package org.example.memoaserver.global.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.dto.req.LoginRequest;
import org.example.memoaserver.domain.user.dto.req.RefreshTokenRequest;
import org.example.memoaserver.domain.user.dto.req.RegisterRequest;
import org.example.memoaserver.domain.user.dto.req.UpdateUserRequest;
import org.example.memoaserver.domain.user.dto.res.UserResponse;
import org.example.memoaserver.domain.user.service.AuthCodeService;
import org.example.memoaserver.domain.user.service.UserService;
import org.example.memoaserver.global.security.jwt.dto.JwtTokenDTO;
import org.example.memoaserver.domain.user.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "auth", description = "유저 인증/인가 정보 API")
public class AuthController {
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthCodeService authCodeService;

    @Operation(
            summary = "회원가입하는 주소입니다.",
            description = "인증된 이메일만 회원가입이 가능합니다."
    )
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest user) {
            return ResponseEntity.ok().body(userService.register(user));
    }

    @Operation(
            summary = "로그인하는 주소입니다.",
            description = "access token 을 포함합니다."
    )
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest user) {
        throw new IllegalStateException("필터단...,,,");
    }

    @Operation(
            summary = "로그아웃하는 주소입니다.",
            description = "refresh token 을 헤더로 받습니다."
    )
    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        refreshTokenService.logout(request);
    }

    @Operation(
            summary = "인증코드를 전송하는 주소입니다.",
            description = "인증코드는 이메일과 1:1 로 됩니다."
    )
    @GetMapping("/send-code")
    public String sendAuthCode(
            @Parameter(name = "email", required = true)
            @RequestParam(name = "email") String email) {
        try {
            authCodeService.sendAuthCode(email);
        } catch (IOException e) {
            return "send auth code failed";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "Authentication code sent to " + email;
    }

    @Operation(
            summary = "이메일 인증 확인하는 주소입니다.",
            description = "인증코드 유효기간은 5분 입니다."
    )
    @PostMapping("/verify-code")
    public void verifyAuthCode(
            @Parameter(name = "email", required = true)
            @RequestParam(name = "email") String email,
            @Parameter(name = "code", required = true)
            @RequestParam(name = "code") String code) throws NoSuchAlgorithmException {
        authCodeService.verifyAuthCode(email, code);
    }

    @Operation(
            summary = "access 토큰 만료시 다시 발급받는 주소입니다.",
            description = "refreshToken 에 값을 넣어 주세요."
    )
    @PostMapping("/reissue")
    public ResponseEntity<JwtTokenDTO> reissue(HttpServletRequest request, @RequestBody RefreshTokenRequest token) throws IOException {
        return ResponseEntity.ok().body(refreshTokenService.reissue(request, token));
    }

    @Operation(
            summary = "내정보를 받는 주소입니다.",
            description = "인자는 없습니다."
    )
    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        return ResponseEntity.ok(userService.me());
    }

    @Operation(
            summary = "내정보를 수정하는 주소입니다.",
            description = "학교 변경은 제외합니다."
    )
    @PatchMapping("/me")
    public ResponseEntity<UserResponse> updateMe(@RequestBody UpdateUserRequest user) {
        return ResponseEntity.ok(userService.updateMe(user));
    }
}
