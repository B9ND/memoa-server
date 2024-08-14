package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthCodeService {
    private static final int EXPIRATION_TIME = 5;

    private final EmailService emailService;
    private final RedisTemplate<String, Object> redisTemplate1;

    public AuthCodeService(@Qualifier("redisTemplate1") RedisTemplate<String, Object> redisTemplate1,
                           EmailService emailService) {
        this.redisTemplate1 = redisTemplate1;
        this.emailService = emailService;
    }

    public String generateAuthCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendAuthCode(String email) {
        String authCode = generateAuthCode();
        redisTemplate1.opsForValue().set(email, authCode, EXPIRATION_TIME, TimeUnit.MINUTES);

        // HTML 메시지 작성
        String htmlBody = "<h1>Your Authentication Code</h1>"
                + "<p>Your authentication code is: <strong>" + authCode + "</strong></p>"
                + "<p>This code will expire in " + EXPIRATION_TIME + " minutes.</p>";

        emailService.sendMail(email, "Your Authentication Code", htmlBody);
    }

    public boolean verifyAuthCode(String email, String authCode) {
        String storedCode = (String) redisTemplate1.opsForValue().get(email);
//        System.out.println(storedCode);
        return storedCode != null && storedCode.equals(authCode);
    }
}
