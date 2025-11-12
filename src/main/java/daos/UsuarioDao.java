package daos;

import daos.Generic.GenericDao;
import entities.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class UsuarioDao implements GenericDao<Usuario> {

    @Override
    public void crear(Usuario usuario, Connection conn) throws SQLException {
        String instruccion = "INSERT INTO Usuarios (username, email, activo, rol, credencial_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(instruccion)){
            ps.setString(1, usuario.getUsername());
            ps.setString(2, usuario.getEmail());
            ps.setBoolean(3, usuario.isActivo());
            ps.setString(4, usuario.getRol().getNombre());
            ps.setInt(5, usuario.getCredencial().getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new SQLException("Error al crear el usuario.");
        }
    }

    @Override
    public Usuario leer(long id, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Usuario> leerTodos(Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actualizar(Usuario usuario, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminar(long id, Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
