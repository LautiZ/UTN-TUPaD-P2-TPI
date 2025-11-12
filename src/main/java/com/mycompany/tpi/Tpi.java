/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tpi;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author zullo
 */
public class Tpi {

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("Conectado correctamente a la base de datos.");
            }
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }
    }
    
}
