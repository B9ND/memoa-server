package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.support.RandomCodeGenerator;
import org.example.memoaserver.domain.user.support.ResourceLoader;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.security.encode.SHA256;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
        String authCode = RandomCodeGenerator.generateAuthCode();
        redisService.setOnARedisForAuthCode(email, sha256.encode(authCode), EXPIRATION_TIME);
        emailService.sendMail(email, ResourceLoader.loadEmailHtml(email, authCode, EXPIRATION_TIME));
    }

    public void verifyAuthCode(String email, String authCode) throws NoSuchAlgorithmException {
        String storedCode = redisService.getOnARedisForAuthCode(email);

        if (storedCode == null || !sha256.matches(authCode, storedCode)) {
            throw new RuntimeException("코드가 일치하지 않습니다.");
        }
        redisService.deleteOnRedisForAuthenticEmail(email);
        redisService.setOnRedisForAuthenticEmail(email, EXPIRATION_TIME);
    }
}
