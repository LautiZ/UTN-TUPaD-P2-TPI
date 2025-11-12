package entities.enums;


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

