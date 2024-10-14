package org.example.memoaserver.domain.user.support;

import java.util.Random;

public class RandomCodeGenerator {

    static public String generateAuthCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

}

