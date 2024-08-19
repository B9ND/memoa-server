package org.example.memoaserver.global.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.service.AuthCodeService;
import org.example.memoaserver.domain.user.service.UserService;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;
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

    @PostMapping("/send-code")
    public String sendAuthCode(@RequestParam(name = "email") String email) {
        try {
            authCodeService.sendAuthCode(email);
        } catch (IOException e) {
            return "send auth code failed";
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return "Authentication code sent to " + email;
    }

    @PostMapping("/verify-code")
    public String verifyAuthCode(@RequestParam(name = "email") String email, @RequestParam(name = "code") String code) throws NoSuchAlgorithmException {
        boolean isVerified = authCodeService.verifyAuthCode(email, code);
        if (isVerified) {
            authCodeService.saveVerifiedEmail(email);
        }
        return isVerified ? "Code verified successfully!" : "Invalid or expired code.";
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        return refreshTokenService.reissue(request, response);
    }
}
