package org.example.memoaserver.global.security.jwt.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JwtType {
    ACCESS_TOKEN("bearer"),
    REFRESH_TOKEN("refresh");

    final String type;

    public String value() {
        return type;
    }
}
