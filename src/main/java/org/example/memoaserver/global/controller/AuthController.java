package org.example.memoaserver.global.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

    @Operation(
            summary = "회원가입하는 주소입니다.",
            description = "인증된 이메일만 회원가입이 가능합니다."
    )
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

    @Operation(
            summary = "로그인하는 주소입니다.",
            description = "access token 은 bearer 를 포함합니다."
    )
    @PostMapping("/login")
    public void login(@RequestBody UserDTO user) {
        throw new IllegalStateException("This method is handled by a filter.");
    }

    @Operation(
            summary = "인증코드를 전송하는 주소입니다.",
            description = "인증코드는 이메일과 1:1 로 됩니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일", required = true, dataType = "string", paramType = "parameter")
    })
    @GetMapping("/send-code")
    public String sendAuthCode(@RequestParam(name = "email") String email) {
        try {
            authCodeService.sendAuthCode(email);
        } catch (IOException e) {
            log.error(e.getMessage());
            return "Send auth code failed";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "Authentication code sent to " + email;
    }

    @Operation(
            summary = "이메일 인증 확인하는 주소입니다.",
            description = "인증코드 유효기간은 5분 입니다."
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", value = "이메일", required = true, dataType = "string", paramType = "parameter"),
            @ApiImplicitParam(name = "code", value = "인증 코드", required = true, dataType = "string", paramType = "parameter")
    })
    @PostMapping("/verify-code")
    public void verifyAuthCode(@RequestParam(name = "email") String email, @RequestParam(name = "code") String code) throws NoSuchAlgorithmException {
        authCodeService.verifyAuthCode(email, code);
    }

    @Operation(
            summary = "access 토큰 만료시 다시 발급받는 주소입니다.",
            description = "베리어를 포함해서 주세요"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Refresh", value = "JWT access token", required = true, dataType = "string", paramType = "header")
    })
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return refreshTokenService.reissue(request, response);
    }

    @Operation(
            summary = "내 정보를 받는 주소입니다.",
            description = "인자는 없습니다."
    )
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        return ResponseEntity.ok(userService.me());
    }

    @Operation(
            summary = "내정보를 수정하는 주소입니다.",
            description = "학교 변경은 제외합니다."
    )
    @PatchMapping("/me")
    public ResponseEntity<?> updateMe(@RequestBody UpdateUserDTO user) {
        return ResponseEntity.ok(userService.updateMe(user));
    }
}
