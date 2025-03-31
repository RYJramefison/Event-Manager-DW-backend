package school.hei.eventManagerDWBackend.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordEncoder {
    public static String encode(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}