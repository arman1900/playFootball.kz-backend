package kz.playfootball.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncryptionUtil {
    private static final int SALT_ROUNDS = 12;

    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(SALT_ROUNDS);
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
