package org.example.memoaserver.global.security.jwt.support;

import io.jsonwebtoken.lang.Strings;
import jakarta.servlet.http.HttpServletRequest;
import org.example.memoaserver.global.security.jwt.enums.JwtType;

import java.util.Enumeration;
import java.util.List;

/**
 * Enum 으로 refresh 타입도 있다면?
 */

public final class TokenExtractor {
    public String extract(HttpServletRequest request, JwtType type) {
        Enumeration<String> tokens = request.getHeaders("Authorization");

        while (tokens.hasMoreElements()) {
            String token = tokens.nextElement();
            if (token.strip().toLowerCase().startsWith(type.value())) {
                return token.substring(type.value().length()).strip();
            }
        }

        return Strings.EMPTY;
    }
}
