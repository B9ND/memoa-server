package org.example.memoaserver.global.security.jwt.support;

import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.http.HttpServletRequest;
import org.example.memoaserver.global.security.jwt.enums.JwtType;

import java.util.Enumeration;

/**
 * Enum 으로 refresh 타입도 있다면?
 * 오버라이딩 금지
 */

public final class TokenExtractor {
    public static String extract(HttpServletRequest request, JwtType type) {
        Enumeration<String> tokens = request.getHeaders("Authorization");

        while (tokens.hasMoreElements()) {
            String token = tokens.nextElement();
            if (token.strip().toLowerCase().startsWith(type.type())) {
                return token.substring(type.type().length()).strip();
            }
        }

        return Strings.EMPTY;
    }
}
