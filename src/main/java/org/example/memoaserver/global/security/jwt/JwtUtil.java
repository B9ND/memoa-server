package org.example.memoaserver.global.security.jwt;

import io.jsonwebtoken.Jwts;
import org.example.memoaserver.global.exception.JwtSignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey secretKey;
    public JwtUtil(@Value("${spring.jwt.secret}")String secret) {
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    public String getUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseClaimsJws(token).getPayload().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
    }

    public String getCategory(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("category", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    public String createJWT(String category, String username, String role, Long expiredTime) {
        try {
            return Jwts.builder()
                    .claim("category", category)
                    .claim("username", username)
                    .claim("role", role)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .issuedAt(new Date(System.currentTimeMillis() + expiredTime))
                    .signWith(secretKey)
                    .compact();
        } catch(JwtSignatureException e) {
            throw new JwtSignatureException("Invalid JWT signature");
        } catch(Exception e) {
            throw new RuntimeException("Jwt processing failed");
        }
    }
}
