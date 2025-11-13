package entities;

import java.time.LocalDateTime;


public class CredencialAcceso {
    private Integer id;
    private boolean eliminado;
    private String hashPassword;
    private String salt;
    private LocalDateTime ultimoCambio;
    private boolean requiereReset;

    public CredencialAcceso(String hashPassword, boolean requiereReset) {
        this.hashPassword = hashPassword;
        this.requiereReset = requiereReset;
    }

    public CredencialAcceso(Integer id, boolean eliminado, String hashPassword, String salt, LocalDateTime ultimoCambio, boolean requiereReset) {
        this.id = id;
        this.eliminado = eliminado;
        this.hashPassword = hashPassword;
        this.salt = salt;
        this.ultimoCambio = ultimoCambio;
        this.requiereReset = requiereReset;
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

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        if (hashPassword == null) {
            throw new Error("El campo contrasena es obligatorio");
        }
        this.hashPassword = hashPassword;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public LocalDateTime getUltimoCambio() {
        return ultimoCambio;
    }

    public void setUltimoCambio(LocalDateTime ultimoCambio) {
        this.ultimoCambio = ultimoCambio;
    }

    public boolean isRequiereReset() {
        return requiereReset;
    }

    public void setRequiereReset(boolean requiereReset) {
        this.requiereReset = requiereReset;
    }    
    
    @Override
    public String toString() {
        return "CredencialAcceso con id: " + id +
                "\neliminado: " + eliminado +
                "\nhashPassword: '" + hashPassword + '\'' +
                "\nsalt: '" + salt + '\'' +
                "\nultimoCambio: " + ultimoCambio +
                "\nrequiereReset: " + requiereReset;
    }
}
