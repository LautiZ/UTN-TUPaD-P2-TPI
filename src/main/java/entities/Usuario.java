/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import entities.enums.Roles;
import java.time.LocalDateTime;

/**
 *
 * @author zullo
 */
public class Usuario {
    private Integer id;
    private boolean eliminado;
    private String username;
    private String email;
    private boolean activo;
    private LocalDateTime fechaRegistro;
    private Roles rol;
    private CredencialAcceso credencial;

    public Usuario(Integer id, String username, String email, boolean activo, LocalDateTime fechaRegistro, String hashPassword, boolean requiereReset, Roles rol) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
        this.rol = rol;
        this.credencial = new CredencialAcceso(hashPassword, requiereReset);
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        if(id == null) {
            this.id = null;
            return;
        }
        this.id = id;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null) {
            throw new Error("El campo username es obligatorio");
        }
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new Error("El campo email es obligatorio");
        }
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public CredencialAcceso getCredencial() {
        return credencial;
    }

    public void setCredencial(CredencialAcceso credencial) {
        this.credencial = credencial;
    }        
    
}
