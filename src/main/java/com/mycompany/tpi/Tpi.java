package com.mycompany.tpi;

import entities.CredencialAcceso;
import entities.Usuario;
import entities.enums.Roles;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import services.CredencialAccesoService;
import services.UsuarioService;


public class Tpi {
    static UsuarioService us = new UsuarioService();
    static CredencialAccesoService cas = new CredencialAccesoService();
    
    public static void main(String[] args) throws SQLException {        
//        us.CrearUsuarioConCredencial("Walter", "walter@mail.com", "Hola mundo!", Roles.MODERADOR);
//        todosLosUsuarios();
//        todasLasCredencialesQueNecesitanReset();
//        us.eliminar(3);
//        ActualizarUsuario();
//        ActualizarCredencial();
    }
    
    public static void todosLosUsuarios() throws SQLException {
        System.out.println("*****************");
        for (Usuario usuario : us.getAll()) {
            System.out.println(usuario.toStringProtected());
            System.out.println("*****************");
        }
    }
    
    public static void todasLasCredencialesQueNecesitanReset() throws SQLException {
        System.out.println("*****************");
        for (CredencialAcceso credencial : cas.getAll()) {
            System.out.println(credencial.toStringProtected());
            System.out.println("*****************");
        }
    }
    
    public static void ActualizarUsuario() throws SQLException {
        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("email", "nuevo@mail.com");
        nuevosDatos.put("rol", "Moderador");
        
        Usuario editado = us.actualizar(1, nuevosDatos);
        System.out.println(editado);
    }
    
    public static void ActualizarCredencial() throws SQLException {
        Map<String, Object> nuevosDatos = new HashMap<>();
        nuevosDatos.put("salt", "saltnuevo@3333");
        nuevosDatos.put("password", "AAAAAAAA");
        
        CredencialAcceso editado = cas.actualizar(1, nuevosDatos);
        System.out.println(editado);
    }
}
