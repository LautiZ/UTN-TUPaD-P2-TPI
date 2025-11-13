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
    
    public static Roles fromNombre(String nombre) {
        for (Roles rol : Roles.values()) {
            if (rol.getNombre().equalsIgnoreCase(nombre)) {
                return rol;
            }
        }
        throw new IllegalArgumentException("Rol no válido: " + nombre);
    }
}

