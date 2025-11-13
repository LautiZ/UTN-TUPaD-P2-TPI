package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Datos de conexión (pueden extraerse de un archivo de configuración o variables de entorno)
    private static final String URL = "jdbc:mysql://localhost:3306/credencialesusuarios?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Constructor privado para evitar instanciación
    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver JDBC de MySQL.");
            e.printStackTrace();
        }

        // Intenta establecer la conexión
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Conexion establecida con MySQL.");
        return conn;
    }
}
