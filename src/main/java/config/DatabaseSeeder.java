package config;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseSeeder {

    public static void poblarDatos() {
        String sqlCredenciales = """
            INSERT INTO CredencialAcceso (hashPassword, salt, requiereReset)
            VALUES
            ('a94a8fe5ccb19ba61c4c0873d391e987982fbbd3', 's@lt1234', FALSE),
            ('5f4dcc3b5aa765d61d8327deb882cf99', 'abc12345', FALSE),
            ('098f6bcd4621d373cade4e832627b4f6', 'pepper321', TRUE),
            ('25d55ad283aa400af464c76d713c07ad', 's@lt5678', FALSE),
            ('21232f297a57a5a743894a0e4a801fc3', 'mysalt99', TRUE);
        """;

        String sqlUsuarios = """
            INSERT INTO Usuarios (username, email, eliminado, activo, rol, credencial_id)
            VALUES
            ('ignacio', 'ignacio@example.com', FALSE, TRUE, 'ADMINISTRADOR', 1),
            ('maria', 'maria@example.com', FALSE, TRUE, 'MODERADOR', 2),
            ('juan', 'juan@example.com', FALSE, TRUE, 'USUARIO', 3),
            ('sofia', 'sofia@example.com', FALSE, TRUE, 'USUARIO', 4),
            ('carlos', 'carlos@example.com', FALSE, TRUE, 'MODERADOR', 5);
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            conn.setAutoCommit(false); // Iniciamos transacción

            stmt.executeUpdate(sqlCredenciales);
            stmt.executeUpdate(sqlUsuarios);

            conn.commit(); // Confirmamos cambios
            System.out.println("Datos insertados correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al poblar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        poblarDatos();
    }
}
