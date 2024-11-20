package org.example.memoaserver.domain.user.support;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ResourceLoader {

    static public String loadEmailHtml(String authCode, int expirationTime) throws IOException {
        Resource resource = new ClassPathResource("templates/mail.html");
        String htmlBody = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        htmlBody = htmlBody
                .replace("${authCode}", authCode)
                .replace("${expirationTime}", String.valueOf(expirationTime));

        return htmlBody;
    }
}
