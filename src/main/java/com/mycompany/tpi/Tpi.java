package com.mycompany.tpi;

import entities.enums.Roles;
import java.sql.SQLException;
import services.UsuarioService;


public class Tpi {

    public static void main(String[] args) throws SQLException {
        UsuarioService us = new UsuarioService();
        us.CrearUsuarioConCredencial("Walter", "walter@mail.com", "Hola mundo!", Roles.MODERADOR);
    }    
}
