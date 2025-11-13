package com.mycompany.tpi;

import daos.CredencialAccesoDao;
import daos.UsuarioDao;
import entities.CredencialAcceso;
import entities.Usuario;
import entities.enums.Roles;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import services.CredencialAccesoService;
import services.UsuarioService;


public class Tpi {    
    final static Scanner input = new Scanner(System.in);
    private static UsuarioService usuarioService;
    private static CredencialAccesoService credencialService;

    
    public static void main(String[] args) throws SQLException {        
        CredencialAccesoDao credDao = new CredencialAccesoDao();
        UsuarioDao usuarioDao = new UsuarioDao(credDao);
        
        credencialService = new CredencialAccesoService(credDao, usuarioDao);
        usuarioService = new UsuarioService(usuarioDao, credencialService);

        appMenu();
    }
    
    private static void crearUsuarioConCredencial() throws SQLException {
        // Datos de usuario
        System.out.println("Opcion seleccionada: 'Crear usuario con credencial'");
        System.out.println("Ingrese username: ");
        String username = input.nextLine();
        System.out.println("Ingrese email: ");
        String email = input.nextLine();
        System.out.println("Ingrese password: ");
        String password = input.nextLine();
        System.out.println("Ingrese salt (opcional): ");
        String salt = input.nextLine();

        if ("".equals(salt)) {
            salt = null;
        }

        // Seleccion de rol
        System.out.println("--- Seleccione un rol ---");
        System.out.println("Opciones:");
        System.out.println("(default) Usuario, dejar vacio");
        System.out.println("1. Moderador");
        System.out.println("2. Administrador");
        String linea = input.nextLine();

        int opcionRol;
        if (linea.isBlank()) {  
            opcionRol = 0; // valor por defecto
        } else {
            opcionRol = Integer.parseInt(linea);
        }
        
        Roles rol;
        switch (opcionRol) {
            case 1 -> rol = Roles.MODERADOR;
            case 2 -> rol = Roles.ADMINISTRADOR;
            default -> rol = Roles.USUARIO;
        }

        usuarioService.CrearUsuarioConCredencial(username, email, password, salt, rol);
    }
    
    private static void actualizarUsuario() throws SQLException {
        System.out.println("Opcion seleccionada: 'Editar usuario'");
        System.out.println("Ingrese id de usuario a editar: ");
        int idUsuarioAEditar = Integer.parseInt(input.nextLine());
        Map<String, Object> nuevosDatos = new HashMap<>();                    
        int dato = -1;
        while (dato != 0) {                        
            System.out.println("Ingrese dato a editar o 0 para continuar");
            System.out.println("Opciones:");
            System.out.println("1. Username");
            System.out.println("2. Email");
            System.out.println("3. Acitvo");
            System.out.println("4. Rol");

            dato = Integer.parseInt(input.nextLine());

            switch (dato) {
                case 1 -> {
                    System.out.println("Elegiste 'cambiar el username'");
                    System.out.println("Ingrese nuevo username: ");
                    String username = input.nextLine();
                    nuevosDatos.put("username", username);
                }
                case 2 -> {
                    System.out.println("Elegiste 'cambiar el email'");
                    System.out.println("Ingrese nuevo email: ");
                    String email = input.nextLine();
                    nuevosDatos.put("email", email);
                }
                case 3 -> {
                    System.out.println("Elegiste 'Cambiar actividad de usuario'");
                    System.out.println("Ingrese 0 si desea desactivar el usuario");
                    boolean activo = Boolean.parseBoolean(input.nextLine());
                    nuevosDatos.put("activo", activo);
                }
                case 4 -> {
                    System.out.println("Elegiste 'cambiar el rol'");
                    System.out.println("--- Seleccione un rol ---");
                    System.out.println("Opciones:");
                    System.out.println("(default) Usuario, dejar vacio");
                    System.out.println("1. Moderador");
                    System.out.println("2. Administrador");
                    int opcionRol = Integer.parseInt(input.nextLine());
                    Roles rol = Roles.USUARIO;
                    switch (opcionRol) {
                        case 1 -> rol = Roles.MODERADOR;
                        case 2 -> rol = Roles.ADMINISTRADOR;
                        default -> {
                        }
                    }
                    nuevosDatos.put("rol", rol.getNombre());
                }
                default -> {
                }
            }
        }

        Usuario editado = usuarioService.actualizar(idUsuarioAEditar, nuevosDatos);
        System.out.println(editado);
    }
    
    private static void actualizarCredencial() throws SQLException {
        System.out.println("Opcion seleccionada: 'Editar credencial'");
        System.out.println("Ingrese id de credencial a editar: ");
        int idCredencialAEditar = Integer.parseInt(input.nextLine());
        Map<String, Object> nuevosDatos = new HashMap<>();                    
        int dato = -1;
        while (dato != 0) {                        
            System.out.println("Ingrese dato a editar o 0 para continuar");
            System.out.println("Opciones:");
            System.out.println("1. Password");
            System.out.println("2. salt");

            dato = Integer.parseInt(input.nextLine());

            switch (dato) {
                case 1 -> {
                    System.out.println("Elegiste 'cambiar la password'");
                    System.out.println("Ingrese la nueva password: ");
                    String password = input.nextLine();
                    nuevosDatos.put("password", password);
                }
                case 2 -> {
                    System.out.println("Elegiste 'cambiar el salt'");
                    System.out.println("Ingrese nuevo salt: ");
                    String salt = input.nextLine();
                    nuevosDatos.put("salt", salt);
                }
                default -> {
                }
            }
        }

        CredencialAcceso editado = credencialService.actualizar(idCredencialAEditar, nuevosDatos);
        System.out.println(editado);
    }
    
    private static void appMenu() throws SQLException {
        int opcion = -1;
        
        while (opcion != 0) {
            System.out.println("--- Menu de opciones ---");
            System.out.println("Opciones:");
            System.out.println("1. Crear usuario con credencial");
            System.out.println("2. Editar usuario");
            System.out.println("3. Eliminar usuario");
            System.out.println("4. Editar credencial");
            System.out.println("5. Eliminar credencial");
            System.out.println("6. Mostrar usuario por id");
            System.out.println("7. Mostrar credencial por id");
            System.out.println("8. Mostrar todos los usuarios");
            System.out.println("9. Mostrar todas las credenciales que requieran reset");
            System.out.println("0. Salir");

            opcion = Integer.parseInt(input.nextLine());
            
            switch (opcion) {
                case 1 -> crearUsuarioConCredencial();
                    
                case 2 -> actualizarUsuario();
                    
                case 3 -> {
                    System.out.println("Opcion seleccionada: 'Eliminar usuario'");
                    System.out.println("Ingrese id del usuario a eliminar");
                    int idUsuarioAEliminar = Integer.parseInt(input.nextLine());
                    usuarioService.eliminar(idUsuarioAEliminar);
                }
                    
                case 4 -> actualizarCredencial();
                    
                case 5 -> {
                    try {
                        System.out.println("Opcion seleccionada: 'Eliminar credencial'");
                        System.out.println("Ingrese id de la credencial a eliminar");
                        int idCredencialAEliminar = Integer.parseInt(input.nextLine());
                        credencialService.eliminar(idCredencialAEliminar);
                    } catch (NumberFormatException | SQLException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                    
                case 6 -> {
                    System.out.println("Opcion seleccionada: 'Mostrar usuario por id'");
                    int idUsuarioABuscar = Integer.parseInt(input.nextLine());
                    usuarioService.getById(idUsuarioABuscar);
                }
                    
                case 7 -> {
                    System.out.println("Opcion seleccionada: 'Mostrar credencial por id'");
                    int idCredencialABuscar = Integer.parseInt(input.nextLine());
                    credencialService.getById(idCredencialABuscar);
                }
                    
                case 8 -> {
                    System.out.println("*****************");
                    for (Usuario usuario : usuarioService.getAll()) {
                        System.out.println(usuario.toStringProtected());
                        System.out.println("*****************");
                    }
                }
                    
                case 9 -> {
                    System.out.println("*****************");
                    for (CredencialAcceso credencial : credencialService.getAll()) {
                        System.out.println(credencial.toStringProtected());
                        System.out.println("*****************");
                    }
                }
                    
                default -> System.out.println("Finalizando ejecucion...");
            }
        }
    }
}
