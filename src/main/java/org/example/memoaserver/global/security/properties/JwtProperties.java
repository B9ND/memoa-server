package org.example.memoaserver.global.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtProperties {
    private String secret;
    private Expiration access;
    private Expiration refresh;

    @Setter
    @Getter
    public static class Expiration {
        private long expiration;

    }

}