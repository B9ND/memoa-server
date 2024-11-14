package org.example.memoaserver.global.security.jwt.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JwtCategory {
    ACCESS("access"),
    REFRESH("refresh");

    final String category;

    public String value() {
        return category;
    }
}
