package org.example.memoaserver.domain.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.exception.VerifyCodeException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(String to, String htmlBody) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject("Memoa 인증코드");
            helper.setText(htmlBody, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new VerifyCodeException("이메일 전송 실패", e);
        }
    }
}