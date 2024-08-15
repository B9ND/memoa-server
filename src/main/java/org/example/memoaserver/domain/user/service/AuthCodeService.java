package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.global.security.incode.SHA256;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AuthCodeService {
    private static final int EXPIRATION_TIME = 5;

    private final EmailService emailService;
    private final RedisTemplate<String, Object> redisTemplate1;
    private final RedisTemplate<String, Object> redisTemplate2;
    private final SHA256 sha256 = new SHA256();

    public AuthCodeService(@Qualifier("redisTemplate1")
                           RedisTemplate<String, Object> redisTemplate1,
                           @Qualifier("redisTemplate2")
                           RedisTemplate<String, Object> redisTemplate2,
                           EmailService emailService) {
        this.redisTemplate1 = redisTemplate1;
        this.emailService = emailService;
        this.redisTemplate2 = redisTemplate2;
    }

    public String generateAuthCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public void sendAuthCode(String email)
            throws IOException, NoSuchAlgorithmException {
        String authCode = generateAuthCode();
        String hashedAuthCode = sha256.encode(authCode);
        redisTemplate1.opsForValue().set(email, hashedAuthCode, EXPIRATION_TIME, TimeUnit.MINUTES);

        ClassPathResource resource = new ClassPathResource("templates/mail.html");
        String htmlBody = new String(Files.readAllBytes(resource.getFile().toPath()));

        htmlBody = htmlBody.replace("${authCode}", authCode);
        htmlBody = htmlBody.replace("${expirationTime}", String.valueOf(EXPIRATION_TIME));

        emailService.sendMail(email, "Your Authentication Code", htmlBody);
    }

    public void saveVerifiedEmail(String email) {
        redisTemplate2.opsForValue().set(email, email);
    }

    public boolean verifyAuthCode(String email, String authCode) throws NoSuchAlgorithmException {
        String storedCode = (String) redisTemplate1.opsForValue().get(email);
//        System.out.println(storedCode);
        if (storedCode == null) {
            return false;
        }

        log.info(String.valueOf(sha256.matches(authCode, storedCode)));

        if (!sha256.matches(authCode, storedCode)) {
            return false;
        }



        redisTemplate1.delete(email);
        return true;
    }
}
