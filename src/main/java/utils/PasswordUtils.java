package utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {

    // Genera hash (cost por defecto 12; podés subirlo si querés más seguridad)
    public static String hashPassword(String plainPassword) {
        int cost = 12; // work factor; 10-14 es razonable según tu CPU
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(cost));
    }

    // Verifica contraseña
    public static boolean verifyPassword(String plainPassword, String hashed) {
        if (hashed == null || !hashed.startsWith("$2a$") && !hashed.startsWith("$2b$")) {
            throw new IllegalArgumentException("Formato de hash BCrypt inválido");
        }
        return BCrypt.checkpw(plainPassword, hashed);
    }
}
