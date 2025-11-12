/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities.enums;

/**
 *
 * @author zullo
 */
public enum Roles {
    USUARIO("Usuario"),
    MODERADOR("Moderador"),
    ADMINISTRADOR("Administrador");

    private final String nombre;

    Roles(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

