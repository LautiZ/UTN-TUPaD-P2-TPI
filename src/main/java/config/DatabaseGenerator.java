package config;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseGenerator {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // --- Crear tabla CredencialAcceso (ejemplo)
            String sqlCredencial = """
                CREATE TABLE CredencialAcceso (
                	id INT AUTO_INCREMENT PRIMARY KEY,
                    eliminado BOOLEAN DEFAULT FALSE,
                    hashPassword VARCHAR(255) NOT NULL,
                    salt VARCHAR(64),
                    ultimoCambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                    requiereReset BOOLEAN NOT NULL
                );
            """;

            // --- Crear tabla Usuarios
            String sqlUsuarios = """
                CREATE TABLE IF NOT EXISTS Usuarios (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(30) NOT NULL UNIQUE,
                    email VARCHAR(120) NOT NULL UNIQUE,
                    eliminado BOOLEAN DEFAULT FALSE,
                    activo BOOLEAN DEFAULT TRUE NOT NULL,
                    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    rol VARCHAR(20) DEFAULT "Usuario" CHECK (rol IN (
                        'Usuario', 'Moderador', 'Administrador'
                    )),
                    credencial_id INT NOT NULL,
                    FOREIGN KEY (credencial_id) REFERENCES CredencialAcceso(id) ON DELETE CASCADE ON UPDATE CASCADE
                );
            """;

            // Ejecutar los scripts
            stmt.executeUpdate(sqlCredencial);
            stmt.executeUpdate(sqlUsuarios);

            System.out.println("Tablas creadas o ya existentes.");

        } catch (SQLException e) {
            System.err.println("Error al generar las tablas: " + e.getMessage());
        }
    }
}
