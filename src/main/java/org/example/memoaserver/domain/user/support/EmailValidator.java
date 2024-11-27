package org.example.memoaserver.domain.user.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailValidator {
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean isValidEmail(String email) {
        if (email.isEmpty()) {
            return false;
        }
        Matcher matcher = Pattern.compile(EMAIL_PATTERN).matcher(email);
        return !matcher.matches();
    }
}
