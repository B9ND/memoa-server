package org.example.memoaserver.global.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.dto.UpdateUserDTO;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.service.AuthCodeService;
import org.example.memoaserver.domain.user.service.UserService;
import org.example.memoaserver.global.service.RefreshTokenService;
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        try {
            UserEntity userEntity = userService.register(user);

            return ResponseEntity
                    .ok(userEntity);
        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }

    @GetMapping("/send-code")
    public String sendAuthCode(@RequestParam(name = "email") String email) {
        try {
            authCodeService.sendAuthCode(email);
        } catch (IOException e) {
            log.error(e.getMessage());
            return "send auth code failed";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "Authentication code sent to " + email;
    }

    @PostMapping("/verify-code")
    public Boolean verifyAuthCode(@RequestParam(name = "email") String email, @RequestParam(name = "code") String code) throws NoSuchAlgorithmException {
        boolean isVerified = authCodeService.verifyAuthCode(email, code);
        if (isVerified) {
            authCodeService.saveVerifiedEmail(email);
        }
        return isVerified;
    }

    @Operation(
            summary = "access 토큰 만료시 다시 발급받는 주소입니다.",
            description = "베리어를 포함해서 주세요"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "JWT access token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return refreshTokenService.reissue(request, response);
    }

    @Operation()
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok(userService.me());
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateMe(@RequestBody UpdateUserDTO user) {
        return ResponseEntity.ok(userService.updateMe(user));
    }
}
