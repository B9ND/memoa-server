package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.exception.VerifyCodeException;
import org.example.memoaserver.domain.user.support.RandomCodeGenerator;
import org.example.memoaserver.domain.user.support.ResourceLoader;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.security.encode.SHA256;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthCodeService {

    private final EmailService emailService;
    private final SHA256 sha256;
    private final RedisService redisService;

    private static final int EXPIRATION_TIME = 5;

    public void sendAuthCode(String email)
            throws IOException, NoSuchAlgorithmException {

        if (email == null || email.isEmpty()) {
            throw new VerifyCodeException("이메일 값이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        String authCode = RandomCodeGenerator.generateAuthCode();
        redisService.setOnRedisForAuthCode(email, sha256.encode(authCode), EXPIRATION_TIME);
        emailService.sendMail(email, ResourceLoader.loadEmailHtml(email, authCode, EXPIRATION_TIME));
    }

    public void verifyAuthCode(String email, String authCode) throws NoSuchAlgorithmException {
        String storedCode = redisService.getOnRedisForAuthCode(email);

        if (storedCode == null || !sha256.matches(authCode, storedCode)) {
            throw new VerifyCodeException("코드가 일치하지 않습니다.");
        }
        redisService.deleteOnRedisForAuthenticEmail(email);
        redisService.setOnRedisForAuthenticEmail(email, EXPIRATION_TIME);
    }
}
