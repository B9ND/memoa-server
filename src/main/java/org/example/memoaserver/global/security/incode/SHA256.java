package org.example.memoaserver.global.security.incode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {
    public String encode(String input) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(input.getBytes());

        StringBuilder hexString = new StringBuilder(2 * encodedHash.length);
        for (byte b : encodedHash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    // 매칭 메서드
    public boolean matches(String rawInput, String hashedValue) throws NoSuchAlgorithmException {
        // 입력된 값을 해싱하여 기존 해시 값과 비교
        String hashedInput = encode(rawInput);
        return hashedInput.equals(hashedValue);
    }
}
