package services;

import config.DatabaseConnection;
import daos.UsuarioDao;
import entities.CredencialAcceso;
import entities.Usuario;
import entities.enums.Roles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import services.Generic.GenericService;


public class UsuarioService implements GenericService<Usuario>{
    
    private final UsuarioDao usuarioDao = new UsuarioDao();
    
    public void CrearUsuarioConCredencial(String username, String email, String password, Roles rol) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false); // inicia transacción
            
            // Creacion de la credencial
            CredencialAccesoService cas = new CredencialAccesoService();
            CredencialAcceso ca = new CredencialAcceso(password, false);
            CredencialAcceso caInsertada = cas.insertar(ca, conn);
            
            // Creacion del usuario
            Usuario u = new Usuario(username, email, true, rol, caInsertada);
            Usuario uInsertado = insertar(u, conn);
            
            conn.commit(); // confirma
            System.out.println(uInsertado);
        } catch (SQLException e) {
            conn.rollback(); // deshace si hubo error en alguna de las creaciones
            System.err.println(e.getMessage());
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
        
    }

    @Override
    public Usuario insertar(Usuario usuario, Connection conn) throws SQLException {
        Usuario usuarioCreado;
        try {
            usuarioCreado = usuarioDao.crear(usuario, conn);
        } catch (SQLException e) {
            usuarioCreado = null;
            throw new SQLException("Error insertando usuario: " + e.getMessage(), e);
        }
        return usuarioCreado;
    }

    @Override
    public Usuario actualizar(Integer id, Map<String, Object> nuevosDatos) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        Usuario u = getById(id);
        
        for (Map.Entry<String, Object> entry : nuevosDatos.entrySet()) {
            String clave = entry.getKey();
            Object valor = entry.getValue();
            
            switch (clave) {
                case "username" -> u.setUsername(valor.toString());
                case "email" -> u.setEmail(valor.toString());
                case "activo" -> u.setActivo((boolean) valor);
                case "rol" -> u.setRol(Roles.fromNombre(valor.toString()));
                default -> {
                }
            }
        }
        return usuarioDao.actualizar(u, conn);
    }

    @Override
    public Usuario eliminar(Integer id) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false); // inicia transacción
            
            Usuario usuarioEliminado = usuarioDao.eliminar(id, conn);
            
            conn.commit(); // confirma
            
            return usuarioEliminado;
        } catch (SQLException e) {
            conn.rollback(); // deshace si hubo error en alguna de las creaciones
            throw new SQLException(e.getMessage());
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    @Override
    public Usuario getById(Integer id) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return usuarioDao.leer(id, conn);
        } catch (Exception e) {
            throw new SQLException("Error obteniendo usuario con id: " + id, e);
        }
        
    }

    @Override
    public List<Usuario> getAll() throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return usuarioDao.leerTodos(conn);
        } catch (Exception e) {
            throw new SQLException("Error obteniendo los usuarios." + e.getMessage());
        }
    }
    
}
