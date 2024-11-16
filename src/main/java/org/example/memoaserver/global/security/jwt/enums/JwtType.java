package org.example.memoaserver.global.security.jwt.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JwtType {
    ACCESS_TOKEN("bearer", "access"),
    REFRESH_TOKEN("refresh", "refresh");

    final String type;

    final String category;

    public String type() {
        return type;
    }

    public String category() {
        return category;
    }
}
