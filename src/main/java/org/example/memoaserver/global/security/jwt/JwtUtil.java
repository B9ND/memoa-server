package org.example.memoaserver.global.security.jwt;

import io.jsonwebtoken.Jwts;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.global.exception.JwtSignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}")String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getEmail(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public String getDevice(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("device", String.class);
    }

    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJwt(String category, String email, Role role, String deviceIdentifier, Long expiredTime) {
        try {
            return Jwts.builder()
                    .claim("category", category)
                    .claim("email", email)
                    .claim("role", role.value())
                    .claim("device", deviceIdentifier)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiredTime))
                    .signWith(secretKey)
                    .compact();
        } catch (JwtSignatureException e) {
            throw new JwtSignatureException("Invalid JWT signature");
        } catch (Exception e) {
            throw new RuntimeException("JWT processing error");
        }

    }
}
